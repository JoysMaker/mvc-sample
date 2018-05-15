package comm.demo.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comm.demo.mvc.mapper.RoleMapper;
import comm.demo.mvc.pojo.Role;

@Service
public class RoleService {
	
	 @Autowired
	 private RoleMapper roleMapper;
	 
	 public Role findRoleById(Integer id) {
		 
		 Role role = roleMapper.findRoleById(id);
		 
		 return role;
	 }

}
