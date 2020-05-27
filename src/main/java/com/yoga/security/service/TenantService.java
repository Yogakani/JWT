/**
 * 
 */
package com.yoga.security.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yoga.security.model.TenantModel;

/**
 * @author Yoga
 *
 */
@Component
@Scope("prototype")
public interface TenantService {
	
	List<TenantModel> getAllTenants();
	
	void createSession();

}
