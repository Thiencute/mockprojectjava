package edu.poly.shop.controller.home;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.constans.SessionConst;
import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Product;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.service.UsersService;



@Controller
public class usersController {
	
	@Autowired
	private UsersService userService;
	
	
	
}
