package edu.rupp.cloth_shop.logic;

public class Sale {

	private int id;
	private String receiptNo;
	private int sellerId;
	private int customerId;
	private float total;
	public Sale(int id, String receiptNo, int sellerId, int customerId, float total) {
		super();
		this.id = id;
		this.receiptNo = receiptNo;
		this.sellerId = sellerId;
		this.customerId = customerId;
		this.total = total;
	}
	public int getId() {
		return id;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public int getSellerId() {
		return sellerId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public float getTotal() {
		return total;
	}
	@Override
	public String toString() {
		return "Sale [id=" + id + ", receiptNo=" + receiptNo + ", sellerId=" + sellerId + ", customerId=" + customerId
				+ ", total=" + total + "]";
	}
	
	
}
