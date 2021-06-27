package edu.poly.shop.FinalTest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import edu.poly.shop.FinalTest.Service.CategoriesService;
import edu.poly.shop.FinalTest.domain.Categories;
import edu.poly.shop.service.CategoryService;

@Controller
public class HomeControllera {
	@Autowired
	private CategoriesService se;

	@GetMapping("/Finaltest")
	public String doGetIndex(Model model) {

		List<Categories> list = se.findAll();

		model.addAttribute("list", list);
		model.addAttribute("cates", new Categories());
		return "home/index2";
	}

	@PostMapping("/Finaltest")
	public String doadd(Model model, @ModelAttribute("cates") Categories cate) {
		System.out.println(cate.getName() + " ad " + cate.getId());
		System.out.println(cate.getName() + " ad " + cate.getId());
		System.out.println(cate.getName() + " ad " + cate.getId());
		System.out.println(cate.getName() + " ad " + cate.getId());
		se.save(cate);
		
	

		return "redirect:/Finaltest";
	}

	@GetMapping("/Thi/Edit/{id}")
	public String Edit(Model model, @PathVariable("id") int id) {

		Categories c = se.findById(id);

		List<Categories> list = se.findAll();

		model.addAttribute("list", list);
		model.addAttribute("cates", c);
		return "/home/index2";
	}

	@PostMapping("/delete/{id}")
	public String doUpdate(Model model, @PathVariable("id") int id) {

		se.delete(se.findById(id)); 

		return "redirect:/Finaltest";
	}

}
