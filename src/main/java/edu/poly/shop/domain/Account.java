package edu.poly.shop.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements Serializable {
	@Id
	@Column(length = 30)
	private String username;
	@Column(length = 20, nullable = false)
	private String password;

	@Column(length = 20, nullable = false)
	private String fullname;
	
	@Column(length = 20, nullable = false)
	private String email;
	
	@Column(name = "roles")
	private Boolean roles;

	@Column(length = 20, nullable = false)
	public String getUsername() {
		return username;
	}

	
	@Column(length = 20, nullable = false)
	public void setUsername(String username) {
		this.username = username;
	}

	@OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
	private List<Order> orders;
	
	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getRoles() {
		return roles;
	}

	public void setRoles(Boolean roles) {
		this.roles = roles;
	}

}
