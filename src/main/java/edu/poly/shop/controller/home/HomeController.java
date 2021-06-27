package edu.poly.shop.controller.home;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import edu.poly.shop.constans.SessionConst;
import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.Product;
import edu.poly.shop.jwt.CustomUser;
import edu.poly.shop.jwt.JwtTokenProvider;
import edu.poly.shop.model.ProductDto;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.service.UsersService;
import edu.poly.shop.service.otherService;



@Controller
public class HomeController {
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService cateService;
	
	@Autowired
	private otherService otherService;
	
	
	@Autowired
	private AuthenticationManager authenManager;
	
    @Autowired
    private JwtTokenProvider tokenProvider;
	
	@GetMapping({"/index", "/"})
	public String doGetIndex(Model model, HttpSession session,@RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 4);
		Page<Product> listProduct = productService.findAll(pageable);
		List<Category> cates = cateService.findAll();
		
		model.addAttribute("cates",cates);
		model.addAttribute("listProduct", listProduct);
		

		return "home/index";
	}
	
	@GetMapping({"/sanpham/{id}",})
	public String doGetProduct(Model model, HttpSession session,@RequestParam("p") Optional<Integer> p,
			@PathVariable("id") Long id
			) {
		Pageable pageable = PageRequest.of(p.orElse(0), 4);
		
		Page<Product> listProduct = productService.findBycategory(cateService.findById(id).get(), pageable);
		Page<Category> cates = cateService.findAll(pageable);
		model.addAttribute("cates",cates);
		model.addAttribute("listProduct", listProduct);
		

		return "home/index";
	}
	
	
	
	@GetMapping({"/info"})
	public String doGetInfo(Model model, HttpSession session,@RequestParam("p") Optional<Integer> p
			) {
		if(ObjectUtils.isEmpty(session.getAttribute(SessionConst.CURRENT_USER))) {
			return "redirect:/login";
		}
		Account acc = (Account) session.getAttribute(SessionConst.CURRENT_USER);
		List<Order> order = otherService.findByUsers(acc);
		Pageable pageable = PageRequest.of(0, 4);
		Page<Category> cates = cateService.findAll(pageable);
		model.addAttribute("cates",cates);
		model.addAttribute("acc", acc);
		model.addAttribute("orders", order);
		return "home/detail";
	}
	
	@GetMapping({"/info/detail/{id}"})
	public String doGetInfoDetail(Model model,@PathVariable("id") Long id,HttpSession sess) {
		if(ObjectUtils.isEmpty(sess.getAttribute(SessionConst.CURRENT_USER))) {
			return "redirect:/login";
		}
		
		Order o = otherService.findById(id);
		
		model.addAttribute("o", o);
		model.addAttribute("detail", o.getOrderDetails());
		System.out.println(o.getOrderDetails().size());
		return "home/odersdetail";
	}
	
	@GetMapping("/login")
	public String doGetLogin(Model model) {
List<Category> cates = cateService.findAll();
		model.addAttribute("cates",cates);
		model.addAttribute("user", new Account());
		model.addAttribute("check", false);
		return "home/login";
	}
	
//	@PostMapping("/login")
//	public String doPostLogin(Model model,
//			@ModelAttribute("user") Account userRequest,
//			HttpSession session) {
//		System.out.println(userRequest.getUsername());
//		
//		Account userResponse = userService.login(userRequest.getUsername(), 
//												userRequest.getPassword());
//		
//		
//		if(userResponse==null) {
//			model.addAttribute("check", true);
//			return "home/login";
//		}
//		session.setAttribute(SessionConst.CURRENT_USER, userResponse);
//		return "redirect:/index";
//	}
	@PostMapping("/login")
	public String doPostLogin(Model model,
			@ModelAttribute("user") Account userRequest,
			HttpSession session) {
		try {
			UsernamePasswordAuthenticationToken authenInfo = new UsernamePasswordAuthenticationToken(
					userRequest.getUsername(),userRequest.getPassword());
			Authentication authentication = authenManager.authenticate(authenInfo);
			
			// Neu khong throw Exception tuc la thong tin hop le
	        // Set thông tin authentication vào Security Context
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	        CustomUser customUser = (CustomUser) authentication.getPrincipal();
	        Account userResponse = customUser.getUser();
        	session.setAttribute(SessionConst.JWT, tokenProvider.generateToken(customUser));
			session.setAttribute(SessionConst.CURRENT_USER, userResponse);
			return "redirect:/index";
		} catch (Exception ex) {
			String message = "Login failed, please try again!";
			model.addAttribute("message", message);
			return "home/login";
		}
	}
	
	@GetMapping("/register")
	public String dogetResgister(Model model
			) {
		model.addAttribute("user", new Account());
		return "home/register";
	}
	@PostMapping("/register")
	public String doPostRegister(Model model,
			@ModelAttribute("user") Account userRequest,
			HttpSession session) {
		Account userResponse = userService.create(userRequest);
		
		if (ObjectUtils.isEmpty(userResponse)) {
			model.addAttribute("message", "Register failed, please try again!");
			return "home/register";
		} else {
			session.setAttribute(SessionConst.CURRENT_USER, userResponse);
			return "redirect:/index";			
		}
	}
	
	@GetMapping("/logout2")
	public String doGetLogOut(Model model, HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
		    new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/index";
	}
	
}
