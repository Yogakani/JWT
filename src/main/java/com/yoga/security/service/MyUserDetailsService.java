/**
 * 
 */
package com.yoga.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yoga.security.Entity.Role;
import com.yoga.security.Entity.Tenant;
import com.yoga.security.Repository.RoleRepository;
import com.yoga.security.Repository.TenantRepository;

/**
 * @author Yoga
 *
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private TenantRepository tenantRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Tenant tenant = tenantRepo.findTenantByUserName(username);
		if(tenant == null) {
			throw new UsernameNotFoundException("User Not Found!!");
		}
		Optional<Role> optionalRole = roleRepo.findById(tenant.getRoleId());
		Role role = null;
		if(optionalRole != null) {
			role = optionalRole.get();
		}
		if(role.getName().equals("ADMIN")) {
			role.setName("ROLE_" + role.getName());
		}
		return new MyUserDetails(tenant, role);
	}

}
