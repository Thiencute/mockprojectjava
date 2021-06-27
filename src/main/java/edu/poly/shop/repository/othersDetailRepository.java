package edu.poly.shop.repository;

import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.OrderDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface othersDetailRepository extends JpaRepository<OrderDetail, Long>{

	
	
	
}
