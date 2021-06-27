package edu.poly.shop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;



public interface UsersService {

	List<Account> findAll();
	List<Account> findByUsernameContaining(String username);
	Account findByUsername(String username);
	Account findById(String id);
	Account login(String username, String password);
	Account create(Account user);
	void delete(String id);
	
	Page<Account> findByUsernameContaining(String username, Pageable pageable);
	Page<Account> findAll(Pageable pageable);
}