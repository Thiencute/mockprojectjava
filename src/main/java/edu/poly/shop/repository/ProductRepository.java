package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	Page<Product> findByName(String name, Pageable page);

	Page<Product> findByCategory(Category categoryId,Pageable page);
}
