package org.wallet.phone;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	Role findByRoleId(int id);
	
	Role findByRoleName(String role);
	
	Role findByRoleNameEndingWith(String profile);
	
	Role findBySubRole(String role);
	
	Role findByAccessLevel(int level);
	
	
}
