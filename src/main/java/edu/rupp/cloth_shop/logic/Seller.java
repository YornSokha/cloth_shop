package edu.rupp.cloth_shop.logic;

public class Seller {
	private int id;
	private String firstName;
	private String lastName;
	private String gender;
	private String phone;
	private String address;
	
	public Seller(int id, String firstName, String lastName, String gender, String phone, String address) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.phone = phone;
		this.address = address;
	}
	
	public Seller() {
		
	}

	
	@Override
	public String toString() {
		return "Seller [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", phone=" + phone + ", address=" + address + "]";
	}

	public String getFirstName() {
		return firstName;
	}
	public Object[] getCustomerComBo() {
		return new Object[] {id, firstName};
	}

	public String getLastName() {
		return lastName;
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


	
}
