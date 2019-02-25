package edu.rupp.cloth_shop.logic;

public class Customer {
	private int id;
	private String name;
	private String gender;
	private String phone;
	private String address;
	
	public Customer(int id, String name, String gender, String phone, String address) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.phone = phone;
		this.address = address;
	}
	
	public Customer() {
		
	}

	
	public String getName() {
		return name;
	}
	public Object[] getCustomerComBo() {
		return new Object[] {id, name};
	}

	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", gender=" + gender + ", phone=" + phone + ", address="
				+ address + "]";
	}

	public int getId() {
		return id;
	}

	public String getGender() {
		return gender;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
