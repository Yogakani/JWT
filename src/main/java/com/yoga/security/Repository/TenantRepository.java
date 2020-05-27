/**
 * 
 */
package com.yoga.security.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yoga.security.Entity.Tenant;

/**
 * @author Yoga
 *
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
	
	Tenant findTenantByUserName(final String userName);

}
