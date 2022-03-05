package com.shopme.admin.user;

import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Integer>{
	
}
