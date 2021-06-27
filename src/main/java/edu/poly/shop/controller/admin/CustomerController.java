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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.constans.SessionConst;
import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;
import edu.poly.shop.model.AccountDto;
import edu.poly.shop.model.CategoryDto;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.UsersService;

@Controller
public class CustomerController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UsersService userService;
	
	@GetMapping("/admin/customer/add")
	public String add(Model model) {
		AccountDto dto = new AccountDto();
		model.addAttribute("category", dto);
		return "admin/customer/addOrEdit";
	}

	@GetMapping("/admin/customer/edit/{customerId}")
	public ModelAndView edit(ModelMap model, @PathVariable("customerId") String categoryId) {
		Account opt = userService.findById(categoryId);
		AccountDto dto = new AccountDto();
		System.out.println(dto.getIsEdit());
		if (opt != null) {
			BeanUtils.copyProperties(opt, dto);
			dto.setIsEdit(true);
			model.addAttribute("category", dto);
			return new ModelAndView("admin/customer/addOrEdit", model);
		}
		model.addAttribute("mesage", "Thể loại không tồn tại");
		return new ModelAndView("forward:/admin/customer", model);

	}

	@GetMapping("/admin/customer/delete/{customerId}")
	public ModelAndView delete(ModelMap model, @PathVariable("customerId") String customerId) {
		userService.delete(customerId);
		model.addAttribute("message", "Xóa thành công");
		return new ModelAndView("redirect:/admin/customer/search", model);
	}
			///    admin/customer/saveOrUpdate
	@RequestMapping(value = "/admin/customer/saveOrUpdate",method = RequestMethod.POST)
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("category") AccountDto dto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/customer/addOrEdit");
		}
		Account entity = new Account();
		BeanUtils.copyProperties(dto, entity);
		userService.create(entity);
		System.out.println("chao");
		model.addAttribute("message", "Người dùng được thêm thành công !!!");
		return new ModelAndView("redirect:/admin/customer/searchpaginated", model);
	}
	
	

	@RequestMapping("/admin/customer")
	public String list(ModelMap model) {
		List<Account> list = userService.findAll();
		model.addAttribute("categorie", list);
		return "admin/customer/list";
	}

	@GetMapping("/admin/customer/search")
	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {
		List<Account> list = null;

		if (StringUtils.hasText(name)) {
			list = userService.findByUsernameContaining(name);
		} else {
			list = userService.findAll();
		}

		model.addAttribute("categories", list);

		return "admin/customer/search";
	}
	
	

	
	
	

	@GetMapping("/admin/customer/searchpaginated")
	public String searchpaginated(ModelMap model,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int curentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		Pageable pageable = PageRequest.of(curentPage - 1, pageSize, Sort.by("username"));
		Page<Account> resultPage = null;

		if (StringUtils.hasText(name)) {
			resultPage = userService.findByUsernameContaining(name, pageable);
			model.addAttribute("name", name);
		} else {
			resultPage = userService.findAll(pageable);
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

		return "admin/customer/searchpaginated";
	}

}
