/**
 * 
 */
package com.yoga.security.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yoga.security.Entity.Role;

/**
 * @author Yoga
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
