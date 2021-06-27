package edu.poly.shop.FinalTest.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.poly.shop.FinalTest.domain.Categories;


public interface CategoriesService {
	Categories findById(int id);
	List<Categories> findAll();
	Categories save(Categories cate);
	void delete(Categories cate);
}
