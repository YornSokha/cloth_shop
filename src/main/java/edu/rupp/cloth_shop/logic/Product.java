package edu.rupp.cloth_shop.logic;

public class Product {
	private int id;
	private int categoryId;
	private String code;
	private String name;
	private float cost;
	private float price;
	private int qty;
	public Product(int id, int categoryId, String code, String name, float cost, float price, int qty) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.code = code;
		this.name = name;
		this.cost = cost;
		this.price = price;
		this.qty = qty;
	}
	
	public Product() {
		super();
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", categoryId=" + categoryId + ", code=" + code + ", name=" + name + ", cost="
				+ cost + ", price=" + price + ", qty=" + qty + "]";
	}

	public int getId() {
		return id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getCode() {
		return code;
	}

	public float getCost() {
		return cost;
	}

	public float getPrice() {
		return price;
	}

	public int getQty() {
		return qty;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
