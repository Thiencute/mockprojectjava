package edu.poly.shop.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.constans.SessionConst;
import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;
import edu.poly.shop.model.CategoryDto;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.UsersService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UsersService userService;
	
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("category", new CategoryDto());
		return "admin/categories/addOrEdit";
	}

	@GetMapping("edit/{categoryId}")
	public ModelAndView edit(ModelMap model, @PathVariable("categoryId") Long categoryId) {
		Optional<Category> opt = categoryService.findById(categoryId);
		CategoryDto dto = new CategoryDto();
		if (opt.isPresent()) {
			Category entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			model.addAttribute("category", dto);
			return new ModelAndView("admin/categories/addOrEdit", model);
		}
		model.addAttribute("mesage", "Thể loại không tồn tại");
		return new ModelAndView("forward:/admin/categories", model);

	}

	@GetMapping("delete/{categoryId}")
	public ModelAndView delete(ModelMap model, @PathVariable("categoryId") Long categoryId) {

		categoryService.deleteById(categoryId);
		model.addAttribute("message", "Xóa thành công");
		return new ModelAndView("redirect:/admin/categories/search", model);
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("category") CategoryDto dto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/categories/addOrEdit");
		}
		Category entity = new Category();
		BeanUtils.copyProperties(dto, entity);
		categoryService.save(entity);
		model.addAttribute("message", "Thể loại được thêm thành công !!!");
		return new ModelAndView("forward:/admin/categories", model);
	}

	@RequestMapping("")
	public String list(ModelMap model) {
		List<Category> list = categoryService.findAll();
		model.addAttribute("categories", list);
		return "admin/categories/list";
	}

	@GetMapping("search")
	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {
		List<Category> list = null;

		if (StringUtils.hasText(name)) {
			list = categoryService.findByNameContaining(name);
		} else {
			list = categoryService.findAll();
		}

		model.addAttribute("categories", list);

		return "admin/categories/search";
	}
	
	

	
	
	

	@GetMapping("searchpaginated")
	public String searchpaginated(ModelMap model,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int curentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		Pageable pageable = PageRequest.of(curentPage - 1, pageSize, Sort.by("name"));
		Page<Category> resultPage = null;

		if (StringUtils.hasText(name)) {
			resultPage = categoryService.findByNameContaining(name, pageable);
			model.addAttribute("name", name);
		} else {
			resultPage = categoryService.findAll(pageable);
		}
		int totalPages = resultPage.getTotalPages();
		if (totalPages > 0) {
			int start = Math.max(1, curentPage - 2);
			int end = Math.min(curentPage + 2, totalPages);
			if(totalPages>5) {
				if(end == totalPages) start = end -5;
				else if (start == 1) 
					end = start + 5;	
			}
			List<Integer> pageNumbers = IntStream.range(start, end)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers",pageNumbers);
			}
		model.addAttribute("categoryPage", resultPage);

		return "admin/categories/searchpaginated";
	}
	//user
//	@GetMapping("login")
//	public String doGetLogin(Model model) {
//		model.addAttribute("user", new Account());
//		return "home/login";
//	}
//
//	
//	@PostMapping("/login")
//	public String doPostLogin(Model model,
//			@ModelAttribute("user") Users userRequest,
//			HttpSession session) {
//		Users userResponse = userService.login(userRequest.getUsername(), 
//												userRequest.getHashPassword());
//		if (userResponse != null) {
//			session.setAttribute(SessionConst.CURRENT_USER, userResponse);
//			return "redirect:/index";
//		} else {
//			String message = "Login failed, please try again!";
//			model.addAttribute("message", message);
//			return "home/login";
//		}
//	}

}
