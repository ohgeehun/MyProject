package Model;

public class CustomerVO {

	private int cusNo;
	private String name;
	private String phone;
	private String pessangerPhone;
	private String email;
	private String accountNumber;

	public CustomerVO() {
		super();
	}

	public CustomerVO(String name, String phone, String pessangerPhone, String email, String accountNumber) {
		super();
		this.name = name;
		this.phone = phone;
		this.pessangerPhone = pessangerPhone;
		this.email = email;
		this.accountNumber = accountNumber;
	}

	public CustomerVO(String name, String phone, String email, String accountNumber) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.accountNumber = accountNumber;
	}

	public CustomerVO(int cusNo, String name, String phone, String email, String accountNumber) {
		super();
		this.cusNo = cusNo;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.accountNumber = accountNumber;
	}

	public CustomerVO(int cusNo, String name, String phone, String accountNumber) {
		super();
		this.cusNo = cusNo;
		this.name = name;
		this.phone = phone;
		this.accountNumber = accountNumber;
	}

	public CustomerVO(int cusNo, String name, String phone, String pessangerPhone, String email, String accountNumber) {
		super();
		this.cusNo = cusNo;
		this.name = name;
		this.phone = phone;
		this.pessangerPhone = pessangerPhone;
		this.email = email;
		this.accountNumber = accountNumber;
	}

	public int getCusNo() {
		return cusNo;
	}

	public void setCusNo(int cusNo) {
		this.cusNo = cusNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPessangerPhone() {
		return pessangerPhone;
	}

	public void setPessangerPhone(String pessangerPhone) {
		this.pessangerPhone = pessangerPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

}
