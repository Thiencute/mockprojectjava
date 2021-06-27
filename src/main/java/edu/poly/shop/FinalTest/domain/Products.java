package edu.poly.shop.FinalTest.domain;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@Table(name = "Thi_products")
public class Products {
	@Id
	private int id;
	private String name;
	private boolean available;
	private Date createDate;
	private String img;
	private int price;
	private int categoriesId;
	
	
}
