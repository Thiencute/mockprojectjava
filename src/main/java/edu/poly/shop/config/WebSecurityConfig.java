package edu.poly.shop.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import edu.poly.shop.constans.RoleConst;
import edu.poly.shop.jwt.JwtAuthenticationFilter;
import edu.poly.shop.service.impl.CustomUserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserServiceImpl customUserService;

	@Autowired
	private DataSource dataSource;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		/*
		  	Su dung doan code nay de chan cac request tuong ung voi role ADMIN
		  	Khi xay dung chuc nang cho phia admin thi dung doan code nay de khong cho
		     cac user thong thuong duoc goi api admin va truy cap trang admin
		 
		 http.authorizeRequests().antMatchers("/admin/**").hasAuthority(RoleConst.ROLE_ADMIN);
		 */
		http.authorizeRequests().antMatchers("/admin/**").hasAuthority(String.valueOf(RoleConst.AdminConst));
		http.authorizeRequests().and().rememberMe()
					.tokenRepository(this.persistentTokenRepository())
					.tokenValiditySeconds(30*60);
		http.cors()
				.and().authorizeRequests()
				.antMatchers("/", "/index", "/login", "/logout2", "/sanpham/**", "/cart", "/register","/info").permitAll() // Cho phep tat ca truy cap link nay
				.anyRequest().authenticated(); // Cac link con lai thi phai xac thuc
	
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/home/*", "/home/css/*","/admin/products/images/*", "/cart/**", "/home/img/*", "/home/js/*");
		
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationManager();
	}
}
