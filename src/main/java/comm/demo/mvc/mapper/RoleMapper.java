package comm.demo.mvc.mapper;

import com.github.abel533.mapper.Mapper;

import comm.demo.mvc.pojo.Role;

public interface RoleMapper extends Mapper<Role> {

	
	public Role findRoleById(Integer id);
}
