package edu.poly.shop.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int customerId;
	@Column(columnDefinition = "nvarchar(50) not null")
private String name;
	@Column(columnDefinition = "nvarchar(100) not null")
private String email;
	@Column(length = 20)
private String phone;
	@Temporal(TemporalType.DATE)
private Date registeredDate;
	@Column(nullable = false)
private short status;
	
	@OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
	private List<Order> users;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public List<Order> getUsers() {
		return users;
	}

	public void setUsers(List<Order> users) {
		this.users = users;
	}

	
	
}
