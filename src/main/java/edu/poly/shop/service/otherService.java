package edu.poly.shop.service;

import edu.poly.shop.domain.Order;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Account;


public interface otherService {

	Order save(Order order);
	List<Order> findByUsers(Account users);
	Order findById(Long Id);
	
}