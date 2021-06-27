package edu.poly.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.Account;
import edu.poly.shop.jwt.CustomUser;
import edu.poly.shop.service.UsersService;

@Service
public class CustomUserServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsersService userService;

	@Override
    public UserDetails loadUserByUsername(String username) {
		Account user = userService.findByUsername(username);
    	if (user == null) {
            throw new UsernameNotFoundException(username);
        }
    	try {
    		List<GrantedAuthority> grantList = new ArrayList<>();
    		GrantedAuthority authority = new SimpleGrantedAuthority(String.valueOf(user.getRoles()));//.getDescription()
    		grantList.add(authority);
            return new CustomUser(user, grantList);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
	
}
