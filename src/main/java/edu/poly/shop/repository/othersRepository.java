package edu.poly.shop.repository;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface othersRepository extends JpaRepository<Order, Long>{

	List<Order> findByUsers(Account users);
}
