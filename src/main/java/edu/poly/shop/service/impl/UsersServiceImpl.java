package edu.poly.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



import edu.poly.shop.domain.Account;
import edu.poly.shop.repository.UsersRepository;
import edu.poly.shop.service.UsersService;



@Service
public class UsersServiceImpl implements UsersService {
	
	private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	
	
	@Autowired
	private UsersRepository repo;

	@Override
	public List<Account> findAll() {
		return repo.findAll();
	}

	@Override
	public Account findByUsername(String username) {
		return repo.findByUsername(username);
	}

	

	@Override
	public Account login(String username, String password) {
		// TODO Auto-generated method stub
		return repo.findByUsernameAndPassword(username, password);
	}

	@Override
	public Account create(Account user) {
		user.setPassword(bcrypt.encode(user.getPassword()));
		user.setRoles(false);
		return repo.save(user);
	}
	

	

	@Override
	public Account findById(String id) {
		Optional<Account> result = repo.findById(id);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public void delete(String id) {
		repo.deleteById(id);
		
	}

	@Override
	public Page<Account> findByUsernameContaining(String username, Pageable pageable) {
		
		return repo.findByUsernameContaining(username, pageable);
	}

	@Override
	public Page<Account> findAll(Pageable pageable) {
		
		return repo.findAll(pageable);
	}

	@Override
	public List<Account> findByUsernameContaining(String username) {
		return repo.findByUsernameContaining(username);
	}

	

}
