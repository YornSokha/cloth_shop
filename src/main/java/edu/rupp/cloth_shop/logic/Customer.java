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

	public Customer(int id, String name) {
		super();
		this.name = name;
		this.gender = gender;
	}
	
	public Customer getCustomer() {
		
		return new Customer(id, name);
	}
	
	public Object[] getCustomerComBo() {
		return new Object[] {id, name};
	}

//	@Override
//	public String toString() {
//		return "Customer [id=" + id + ", name=" + name + ", gender=" + gender + ", phone=" + phone + ", address="
//				+ address + "]";
//	}
	
	@Override
	public String toString() {
		return name;
	}
	
	class Item {

		  private int id;
		  private String description;
		  public Item(int id, String description) {
		    this.id = id;
		    this.description = description;
		  }

		  public int getId() {
		    return id;
		  }

		  public String getDescription() {
		    return description;
		  }

		  @Override
		  public String toString() {
		    return description;
		  }
		}
	
}
