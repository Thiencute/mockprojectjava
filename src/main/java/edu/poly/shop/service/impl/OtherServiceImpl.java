package edu.poly.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.OrderDetail;
import edu.poly.shop.repository.UsersRepository;
import edu.poly.shop.repository.othersDetailRepository;
import edu.poly.shop.repository.othersRepository;
import edu.poly.shop.service.UsersService;
import edu.poly.shop.service.otherDetailService;
import edu.poly.shop.service.otherService;



@Service
public class OtherServiceImpl implements otherDetailService {

	@Autowired
	private othersDetailRepository repo;
	
	@Override
	public OrderDetail save(OrderDetail order) {
		
		return repo.save(order);
	}

	
	
}
