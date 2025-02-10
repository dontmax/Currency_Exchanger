package models;

public class Currency {
	
	private int id;
	private String code;
	private String name;
	private String sign;
	
	public Currency(int id, String Code, String FullName, String Sign) {
		this.id=id;
		this.code=Code;
		this.name=FullName;
		this.sign=Sign;
	}

	public Currency(String Code, String FullName, String Sign) {
		this.code=Code;
		this.name=FullName;
		this.sign=Sign;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFullName() {
		return name;
	}

	public void setFullName(String fullName) {
		this.name = fullName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
