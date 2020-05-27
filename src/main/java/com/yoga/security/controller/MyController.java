/**
 * 
 */
package com.yoga.security.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.HazelcastInstance;
import com.yoga.security.model.AuthResponse;
import com.yoga.security.model.TenantModel;
import com.yoga.security.service.JWTUtil;
import com.yoga.security.service.MyUserDetailsService;
import com.yoga.security.service.TenantService;

/**
 * @author Yoga
 *
 */
@RestController
public class MyController {
	
	@Autowired
	private DaoAuthenticationProvider authProvider;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private HazelcastInstance castInstance;
	
		
	@PostMapping(value="/authenticate")
	public AuthResponse doLogin(@RequestBody TenantModel tenant) {
		Authentication auth = authProvider.authenticate(new UsernamePasswordAuthenticationToken(tenant.getUserName(), tenant.getPassword()));
		AuthResponse res = new AuthResponse();
		if(auth.isAuthenticated()) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(((UserDetails)auth.getPrincipal()).getUsername());
			String token = jwtUtil.generateToken(userDetails);
			tenantService.createSession();
			Map<String, String> session = castInstance.getMap("session");
			session.put("username", userDetails.getUsername());
			res.setResult("Login Successfull");
			res.setToken(token);
			return res;
		}
		res.setResult("Login Failed");
		return res;
	}
	
	@GetMapping(value="/home")
	public String postLogin() {
		Map<String, String> session = castInstance.getMap("session");
		String loggedInUserName = session.get("username");
		return "Welcome Home " + loggedInUserName + " !";
	}

	@GetMapping(value="/getAllUsers")
	public List<TenantModel> getAllTenants() {
		return tenantService.getAllTenants();
	}
}
