package edu.poly.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Order;
import edu.poly.shop.repository.UsersRepository;
import edu.poly.shop.repository.othersRepository;
import edu.poly.shop.service.UsersService;
import edu.poly.shop.service.otherService;



@Service
public class OtherDetailServiceImpl implements otherService {

	@Autowired
	private othersRepository repo;
	
	@Override
	public Order save(Order order) {
		return  repo.save(order);
	}

	@Override
	public List<Order> findByUsers(Account users) {
		return repo.findByUsers(users);
	}

	@Override
	public Order findById(Long Id) {
		return repo.findById(Id).get();
	}
	
}
