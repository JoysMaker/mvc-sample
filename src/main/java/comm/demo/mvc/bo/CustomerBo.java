package comm.demo.mvc.bo;

import java.io.Serializable;

public class CustomerBo implements Serializable{
	
	
	private String userId;
	
	//用户类型: 1.买手  2.消费者 3.无订单时等待的买手
	private String type;
    
	//位置
	private String location;
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
