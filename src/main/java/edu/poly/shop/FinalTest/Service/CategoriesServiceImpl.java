package edu.poly.shop.FinalTest.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.shop.FinalTest.Repo.CategoriesRepo;
import edu.poly.shop.FinalTest.domain.Categories;
@Service
public class CategoriesServiceImpl implements CategoriesService {

	@Autowired
	private CategoriesRepo repo;
	
	@Override
	public List<Categories> findAll() {
		return repo.findAll();
	}

	@Override
	public Categories save(Categories cate) {
		return repo.saveAndFlush(cate);
	}

	@Override
	public void delete(Categories cate) {
		 repo.delete(cate);
	}

	@Override
	public Categories findById(int id) {
		return repo.findById(id).get();
	}
}
