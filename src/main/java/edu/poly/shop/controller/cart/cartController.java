package edu.poly.shop.controller.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.constans.SessionConst;
import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.OrderDetail;
import edu.poly.shop.domain.Product;
import edu.poly.shop.model.ProductDto;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.service.UsersService;
import edu.poly.shop.service.otherDetailService;
import edu.poly.shop.service.otherService;

@Controller
public class cartController {

	@Autowired
	private ProductService service;

	@Autowired
	private UsersService Uservice;

	@Autowired
	private otherService Oservice;
	
	@Autowired
	private otherDetailService ODeservice;
	
	
	@Autowired
	private CategoryService Cservice;
	
	@GetMapping("/cart/add/{id}")
	public String addCart(@PathVariable("id") Long id, HttpSession sess) {
		Object o  =sess.getAttribute(SessionConst.CURRENT_CART);
		Double total = 0D;
		List<ProductDto> list = new ArrayList<ProductDto>();
		System.out.println(!ObjectUtils.isEmpty(o));
		if (!ObjectUtils.isEmpty(o)) {
			
			 List<ProductDto> attribute = (List<ProductDto>) sess.getAttribute(SessionConst.CURRENT_CART);
			list = attribute;
			
		}
		Optional<Product> product = service.findById(id);
		ProductDto dto = new ProductDto();
		if (product.isPresent()) {
			boolean check = true;

			dto = this.cpy(product.get());

			for (ProductDto p : list) {
				if(p.getProductId().equals(dto.getProductId())) {
					p.setDiscount(p.getDiscount()+1);
					check = false;
					break;
				}
			}
			if(check) {
				list.add(dto);
			}
			
			
			for (ProductDto p : list) {
				total += p.getUnitPrice() * p.getDiscount();
			}
		}
		
		sess.setAttribute("total", total);
		sess.setAttribute(SessionConst.CURRENT_CART, list);

		return "redirect:/index";
	}
	public ProductDto cpy(Product pro) {
		ProductDto dto = new ProductDto();
		dto.setProductId(pro.getProductId());
		dto.setName(pro.getName());
		dto.setQuatity(pro.getQuantity());
		dto.setUnitPrice(pro.getUnitPrice());
		dto.setImage(pro.getImage());
		dto.setDescription(pro.getDescription());
		dto.setDiscount(1);
		dto.setCategoryId(pro.getCategory().getCategoryId());;;
		return dto;
	}
	@GetMapping(value = {"/cart/show","/cart/"})
	public String ShowCart(Model model, HttpSession sess) {
		Object o  =sess.getAttribute(SessionConst.CURRENT_CART);
		Double total = 0D;
		List<ProductDto> list = new ArrayList<ProductDto>();
		if (!ObjectUtils.isEmpty(o)) {
			 List<ProductDto> attribute = (List<ProductDto>) sess.getAttribute(SessionConst.CURRENT_CART);
			list = attribute;
			
		}
		Pageable p = PageRequest.of(0, 4);
		Page<Product> page = service.findAll(p);
		
		
		model.addAttribute("listProduct", list);
		model.addAttribute("prod", page);
		
		
		return "home/cart";
	}
	@PostMapping("/cart/show")
	public String sellsCart(Model model, HttpSession sess) {
		if(ObjectUtils.isEmpty(sess.getAttribute(SessionConst.CURRENT_USER))) {
			return "redirect:/login";
		}
		
	
		Account a = (Account) sess.getAttribute(SessionConst.CURRENT_USER);
		List<ProductDto> listpro =  (List<ProductDto>) sess.getAttribute(SessionConst.CURRENT_CART);
		Double amount = (Double) sess.getAttribute("total");
		Account customer = Uservice.findById(a.getUsername());
		
		List<OrderDetail> listO = new ArrayList<OrderDetail>();
		Order order = new Order();
		order.setAmount(amount);
		order.setUsers(customer);
		order = Oservice.save(order);
		
		for (ProductDto o : listpro) {
			OrderDetail od = new OrderDetail();
			od.setOrder(order);
			od.setQuantity(o.getQuatity());
			od.setUnitPrice(o.getUnitPrice());
			od.setProduct(cpy2(o));
			od = ODeservice.save(od);
		}
		return "redirect:/cart/clear";
	}
	public Product cpy2(ProductDto pro) {
		Product dto = new Product();
		dto.setProductId(pro.getProductId());
		dto.setName(pro.getName());
		dto.setQuantity(pro.getQuatity());
		dto.setUnitPrice(pro.getUnitPrice());
		dto.setImage(pro.getImage());
		dto.setDescription(pro.getDescription());
		dto.setDiscount(1);
		dto.setCategory(Cservice.getById(pro.getCategoryId()));
		return dto;
	}
	@GetMapping("/cart/remove/{id}")
	public String getRemove(@PathVariable("id") Long id, HttpSession sess) {
		List<ProductDto> list = (List<ProductDto>) sess.getAttribute(SessionConst.CURRENT_CART);
		Double total = (Double) sess.getAttribute("total");
		for(int i = 0; i <list.size();i++) {
			if(list.get(i).getProductId() == id) {
				total-=list.get(i).getUnitPrice()*list.get(i).getDiscount();
				sess.setAttribute("total", total);
				list.remove(i);
				break;
			}
		}
		
		
		return "redirect:/cart/show";
	}

	@GetMapping("/cart/clear")
	public String clearCart(Model model, HttpSession sess) {
		sess.removeAttribute(SessionConst.CURRENT_CART);
		sess.setAttribute("total", 0);
		return "redirect:/cart/show";
	}
	
	
	
	
	
	
	
	
}
