package edu.poly.shop.FinalTest.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.shop.FinalTest.domain.Categories;


@Repository
public interface CategoriesRepo extends JpaRepository<Categories, Integer>{

}
