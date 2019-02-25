package edu.rupp.cloth_shop.sale;

import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.rupp.cloth_shop.backend.DB;
import edu.rupp.cloth_shop.logic.Customer;
import edu.rupp.cloth_shop.logic.Product;
import edu.rupp.cloth_shop.logic.Sale;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.SystemColor;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

public class PSale extends JPanel {
	private JTextField txtQty;
	private JComboBox<Customer> cboCustomer;
	private JComboBox<Product> cboProduct;
	private Map<String, Customer> customers;
	private Map<String, Product> products;
	private JTable tblSale;
	private DefaultTableModel model;
	private JTextField txtDiscount;
	private Customer customer;
	private Product  product;
	private float grandTotal;
	private HashMap<Integer,Product> boughtProducts;

	/**
	 * Create the panel.
	 */
	public PSale() {
		setLayout(null);
		getCustomers();
		getProducts();
		initComponent();
		
	}
	

	private void getProducts() {
		products = new HashMap<String, Product>();
        try {
        	Connection con = DB.getConnection();
        	String sql = "SELECT * FROM products";
        	Statement st = con.createStatement();
            ResultSet res = st.executeQuery(sql);
            int id, categoryId, qty;
            String name, code;
            float cost, price;
            while (res.next()) {
            	id = res.getInt(1);
            	categoryId = res.getInt(2);
            	code = res.getString(3);
            	name = res.getString(4);
            	cost = res.getFloat(5);
            	price = res.getFloat(6);
            	qty = res.getInt(7);
            	Product pro = new Product(id, categoryId, code, name, cost, price, qty);
            	products.put(pro.getName(), pro);
            }
            con.close();
        }catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
		
	}


	private void initComponent() {
		JLabel lblSale = new JLabel("Sale");
		lblSale.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSale.setBounds(43, 11, 46, 14);
		add(lblSale);
		
		model = new DefaultTableModel();	
		model.addColumn("PRODUCT");
		model.addColumn("UNIT_PRICE");
		model.addColumn("QUANTITY");
		model.addColumn("DISCOUNT");
		model.addColumn("TOTAL");
		
		JScrollPane scpTblSale = new JScrollPane();
		scpTblSale.setBounds(43, 137, 553, 241);
		add(scpTblSale);
		
		tblSale = new JTable() {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		scpTblSale.setViewportView(tblSale);
		tblSale.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblSale.setSelectionBackground(new Color(51, 153, 204));
		tblSale.setRowMargin(0);
		tblSale.setRowHeight(25);
		tblSale.setGridColor(Color.BLACK);
		tblSale.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tblSale.setBorder(new LineBorder(SystemColor.activeCaption, 2));
		tblSale.setBackground(Color.WHITE);
		tblSale.setModel(model);
		tblSale.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JPanel pSaleOperation = new JPanel();
		pSaleOperation.setBounds(36, 11, 572, 81);
		add(pSaleOperation);
		pSaleOperation.setLayout(null);
		cboCustomer = createCusCombo();
		cboCustomer.setBounds(10, 54, 156, 20);
		pSaleOperation.add(cboCustomer);
		cboCustomer.setMaximumRowCount(3);
		
		JLabel lblCustomer = new JLabel("Customer");
		lblCustomer.setBounds(10, 29, 86, 14);
		pSaleOperation.add(lblCustomer);
		
		JLabel lblProduct = new JLabel("Product");
		lblProduct.setBounds(176, 29, 93, 14);
		pSaleOperation.add(lblProduct);
		
		cboProduct = createProCombo();
		cboProduct.setBounds(176, 54, 150, 20);
		pSaleOperation.add(cboProduct);
		
		txtQty = new JTextField();
		txtQty.setText("1");
		txtQty.setBounds(336, 54, 59, 20);
		pSaleOperation.add(txtQty);
		txtQty.setColumns(10);
		
		JLabel lblQty = new JLabel("Qty");
		lblQty.setBounds(337, 29, 46, 14);
		pSaleOperation.add(lblQty);
		
		boughtProducts = new HashMap<Integer, Product>();
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			Object[] columns = new Object[5];
			public void actionPerformed(ActionEvent arg0) {
				if(product == null) {
					JOptionPane.showMessageDialog(null, "Cannot add empty product", "Information", JOptionPane.WARNING_MESSAGE);
					return;
				}
				String productName = cboProduct.getSelectedItem().toString();
				int qty = 1, categoryId, id;
				String code;
				float cost, price;
				float discount = 0.0F;
				try {
					qty = Integer.parseInt(txtQty.getText());			
				}catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Quantity must be number", "Information", JOptionPane.WARNING_MESSAGE);
					return;
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				try {
					discount = Float.parseFloat(txtDiscount.getText());			
				}catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Discount must be number", "Information", JOptionPane.WARNING_MESSAGE);
					return;
				}catch(Exception e) {
					e.printStackTrace();
				}
				float total = qty * product.getPrice() - (qty * product.getPrice()*discount/100);
//				System.out.println(product.toString());
				id = product.getId();
				categoryId = product.getCategoryId();
				code = product.getCode();
				cost = product.getCost();
				price = product.getPrice();
				updateTotal(total);
				
				columns[0] = productName;
				columns[1] = price;
				columns[2] = qty;
				columns[3] = discount;
				columns[4] = total;
				if(boughtProducts.containsKey(id)) {
					Product p = boughtProducts.get(id);
					int oldQty = p.getQty();
					System.out.println(oldQty);
					boughtProducts.put(id, new Product(id, categoryId, code, productName, cost, price, qty + oldQty));
					
					int rowCount = model.getRowCount();
					//Remove rows one by one from the end of the table
					for (int i = rowCount - 1; i >= 0; i--) {
					    model.removeRow(i);
					}
					
					for(Entry<Integer, Product> entry : boughtProducts.entrySet()) {
					    int pId = entry.getKey();
					    Product pro = entry.getValue();
						
					    float tota = pro.getQty() * pro.getPrice() - (pro.getQty() * pro.getPrice()*discount/100);
					    
					    columns[0] = pro.getName();
					    columns[1] = pro.getPrice();
						columns[2] = pro.getQty();
						columns[3] = discount;
						columns[4] = tota;
					    model.addRow(columns);
					    // do what you have to do here
					    // In your case, another loop.
					}
				}else {
					boughtProducts.put(id, new Product(id, categoryId, code, productName, cost, price, qty));
					int rowCount = model.getRowCount();
					if(rowCount > 0)
						model.removeRow(rowCount-1);
					model.addRow(columns);	
				}	
				generateTotal();
				clearControl();
			}
			private void generateTotal() {
				// TODO Auto-generated method stub
				columns[0] = "";
			    columns[1] = "";
				columns[2] = "";
				columns[3] = "Total";
				columns[4] = grandTotal;
			    model.addRow(columns);
			}
			private void clearControl() {
				txtQty.setText("1");
				txtDiscount.setText("0");
				cboProduct.setSelectedItem("--Select Product--");
//				cboCustomer.setSelectedItem("--Select Customer--");
			}
		});
		btnAdd.setBounds(474, 53, 89, 23);
		pSaleOperation.add(btnAdd);
		
		txtDiscount = new JTextField();
		txtDiscount.setText("0");
		txtDiscount.setColumns(10);
		txtDiscount.setBounds(405, 54, 59, 20);
		pSaleOperation.add(txtDiscount);
		
		JLabel lblDiscount = new JLabel("Discount");
		lblDiscount.setBounds(406, 29, 71, 14);
		pSaleOperation.add(lblDiscount);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSave.setBounds(507, 400, 89, 23);
		add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int receiptNo = 1;
				int sellerId = 1;
				int customerId;
				if(customer == null)
					customerId = 1;
				else
					customerId = customer.getId();
				try {
		        	Connection con = DB.getConnection();
		        	String sql = "SELECT id FROM sales ORDER BY id DESC";
		        	Statement st = con.createStatement();
		            ResultSet res = st.executeQuery(sql);
		            int id;
		            float tot;
		            if (res.next()) {
		            	id = res.getInt(1);
		            	receiptNo = id + 1;
//		            	receiptNo = res.getInt(2);
//		            	sellerId = res.getInt(3);
//		            	customerId = res.getInt(4);
//		            	tot = res.getFloat(5);
//		            	Sale sale = new Sale(id, receiptNo, sellerId, customerId, tot);
//		            	System.out.println(sale.toString());
		            }
		            con.close();
		        }catch (Exception e) {
					// TODO: handle exception
		        	e.printStackTrace();
				}
			
				
				try{
					Connection con = DB.getConnection();
					String command = "insert into sales(receipt_no, seller_id, customer_id, total) values(?,?,?,?);";
					PreparedStatement ps=con.prepareStatement(command);
					ps.setString(1, String.format("%05d", receiptNo));
				    ps.setInt(2, sellerId);
				    ps.setInt(3, customerId);
				    ps.setFloat(4, grandTotal);
				    int i = ps.executeUpdate();
				    if (i != 0) {
				        JOptionPane.showMessageDialog(null, "Purchase success!", "Information", JOptionPane.INFORMATION_MESSAGE);
				    } else {
				        System.out.println("not Inserted");
				    }
					con.close();
					
				}catch(Exception e)
				{
					System.out.println(e);
				}
				
			}
		});
		
	}

	protected void updateTotal(float total) {
		grandTotal += total;
		
	}


	private JComboBox<Product> createProCombo() {
		final JComboBox cbox = new JComboBox();
		cbox.addItem("--Select Product--");
        for (String name : products.keySet()) {
            cbox.addItem(name);
        }

        cbox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String name = (String)cbox.getSelectedItem();
//                System.out.println(products.get(name));
                product = products.get(name);
            }
        });

        return cbox;
	}


	private JComboBox<Customer> createCusCombo() {
		final JComboBox cbox = new JComboBox();
		cbox.addItem("--Select Customer--");
        for (String name : customers.keySet()) {
            cbox.addItem(name);
        }

        cbox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String name = (String)cbox.getSelectedItem();
//                System.out.println(customers.get(name));
                customer = customers.get(name);
            }
        });

        return cbox;
	}

	private void getCustomers() {
        customers = new HashMap<String, Customer>();
        try {
        	Connection con = DB.getConnection();
        	String sql = "SELECT * FROM customers";
        	Statement st = con.createStatement();
            ResultSet res = st.executeQuery(sql);
            int id;
            String name, gender, phone, address;
            while (res.next()) {
            	id = res.getInt(1);
            	name = res.getString(2);
            	gender = res.getString(3);
            	phone = res.getString(4);
            	address = res.getString(5);
            	Customer cus = new Customer(id, name, gender, phone, address);
            	customers.put(cus.getName(), cus);
            }
            con.close();
        }catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}

	}
}
