/**
 * 
 */
package com.yoga.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.yoga.security.Entity.Role;
import com.yoga.security.Entity.Tenant;
import com.yoga.security.Repository.RoleRepository;
import com.yoga.security.Repository.TenantRepository;
import com.yoga.security.model.TenantModel;

/**
 * @author Yoga
 *
 */
@Service
public class TenantServiceImpl implements TenantService {
	
	@Autowired
	private TenantRepository tenantRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private Config config;

	@Override
	public List<TenantModel> getAllTenants() {
		List<Tenant> tenants = tenantRepo.findAll();
		List<Role> roles = getAllRoles();
		Map<Long, String> roleMap = roles.stream().collect(Collectors.toMap(Role :: getId, Role :: getName));
		List<Tenant> users = tenants.stream().filter(tenant -> roleMap.get(tenant.getRoleId()).equalsIgnoreCase("USER\n")).collect(Collectors.toList());
		List<TenantModel> tenantModels = new ArrayList<>();
		users.stream().forEach(user -> {
			TenantModel tenantModel = new TenantModel();
			tenantModel.setId(user.getId());
			tenantModel.setUserName(user.getUserName());
			tenantModels.add(tenantModel);
		});
		return tenantModels;
	}	
	
	private List<Role> getAllRoles() {
		return roleRepo.findAll();
	}

	@Override
	public void createSession() {
		MapConfig mapConfig = new MapConfig();
		mapConfig.setName("session");
		mapConfig.setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE));
		mapConfig.setTimeToLiveSeconds(200);
		config.addMapConfig(mapConfig);
	}
}
