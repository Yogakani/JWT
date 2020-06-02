/**
 * 
 */
package com.yoga.security.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public ResponseEntity<AuthResponse> doLogin(@RequestBody TenantModel tenant) {
		AuthResponse res = new AuthResponse();
		try {
			Authentication auth = authProvider.authenticate(new UsernamePasswordAuthenticationToken(tenant.getUserName(), tenant.getPassword()));
			if(auth.isAuthenticated()) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(((UserDetails)auth.getPrincipal()).getUsername());
				String token = jwtUtil.generateToken(userDetails);
				tenantService.createSession();
				Map<String, String> session = castInstance.getMap("session");
				session.put("username", userDetails.getUsername());
				res.setResult("Success");
				res.setToken(token);			}
		} catch(Exception e) {
			res.setResult("Failed");
		}
		return new ResponseEntity<AuthResponse>(res, HttpStatus.OK);
	}
	
	@GetMapping(value="/home")
	public ResponseEntity<String> postLogin() {
		Map<String, String> session = castInstance.getMap("session");
		String loggedInUserName = session.get("username");
		String msg = "Welcome Home " + loggedInUserName;
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}

	@GetMapping(value="/getAllUsers")
	public ResponseEntity<Integer> getAllTenants() {
		int count = tenantService.getAllTenants().size();
		return new ResponseEntity<Integer>(count, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/exit")
	public ResponseEntity<String> logout() {
		castInstance.shutdown();
		String msg =  "Success";
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
}
