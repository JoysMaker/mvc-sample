package comm.demo.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import comm.demo.mvc.pojo.Role;
import comm.demo.mvc.service.RoleService;
import comm.demo.mvc.vo.DataResult;

@Controller
@RequestMapping("mvc")
public class MvcController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("demo")
	@ResponseBody
	public DataResult demo(HttpServletRequest request) {
		String id = request.getParameter("id");
		DataResult ds = new DataResult();
		Role role = roleService.findRoleById(Integer.valueOf(id));
		ds.setData(JSON.toJSONString(role));
		return ds;
	}

}
