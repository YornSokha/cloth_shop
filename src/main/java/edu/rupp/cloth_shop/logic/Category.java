package edu.rupp.cloth_shop.logic;

public class Category {
	private int id;
	private String name;
	
	public Category() {
		
	}
	
	public Category(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;	
	}
	
	public int getId() {
		return id;
	}
}
