/**
 * 
 */
package com.yoga.security.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.yoga.security.Entity.Role;
import com.yoga.security.Entity.Tenant;

/**
 * @author Yoga
 *
 */
public class MyUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8048054437963279131L;
	
	private Tenant tenant;
	
	private Role role;
	
	public MyUserDetails(Tenant tenant, Role role) {
		this.tenant = tenant;
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(role.getName()));
	}

	@Override
	public String getPassword() {
		return tenant.getPassword();
	}

	@Override
	public String getUsername() {
		return tenant.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public Tenant getTenant() {
		return tenant;
	}

}
