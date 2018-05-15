package comm.demo.mvc.vo;
public class DataResult {
	
	//0.表示请求成功 1.表示请求不是理想效果
	private String code = "1";
	
	//提示信息
	private String info;
	
	//数据 json字符串
	private String data = "{}";

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
