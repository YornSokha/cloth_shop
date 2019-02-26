package edu.rupp.cloth_shop.sale;

import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.rupp.cloth_shop.backend.DB;
import edu.rupp.cloth_shop.logic.Customer;
import edu.rupp.cloth_shop.logic.Product;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.SystemColor;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

// TODO login error in SaleDetial Report
@SuppressWarnings("serial")
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
	private int saleId;
	private Map<Integer,Product> boughtProducts;
	private Map<Integer, Float> totalRows;
	private Map<Integer,Float> discounts;
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
		products = new TreeMap<String, Product>();
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
        	e.printStackTrace();
		}
		
	}


	private void initComponent() {
		
		model = new DefaultTableModel();	
		model.addColumn("Product");
		model.addColumn("Unit Price");
		model.addColumn("Qty");
		model.addColumn("Discount");
		model.addColumn("Total");
		
		JScrollPane scpTblSale = new JScrollPane();
		scpTblSale.setBounds(23, 190, 1128, 241);
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
		pSaleOperation.setBounds(23, 57, 1128, 105);
		add(pSaleOperation);
		pSaleOperation.setLayout(null);
		cboCustomer = createCusCombo();
		cboCustomer.setBounds(0, 47, 156, 35);
		pSaleOperation.add(cboCustomer);
		cboCustomer.setMaximumRowCount(6);
		
		JLabel lblCustomer = new JLabel("Customer");
		lblCustomer.setBounds(0, 29, 86, 14);
		pSaleOperation.add(lblCustomer);
		
		JLabel lblProduct = new JLabel("Product");
		lblProduct.setBounds(166, 29, 93, 14);
		pSaleOperation.add(lblProduct);
		
		cboProduct = createProCombo();
		cboProduct.setBounds(166, 47, 150, 35);
		pSaleOperation.add(cboProduct);
		cboProduct.setMaximumRowCount(6);
		
		txtQty = new JTextField();
		txtQty.setText("1");
		txtQty.setBounds(326, 47, 72, 35);
		pSaleOperation.add(txtQty);
		txtQty.setColumns(10);
		
		JLabel lblQty = new JLabel("Qty");
		lblQty.setBounds(326, 29, 46, 14);
		pSaleOperation.add(lblQty);
		
		boughtProducts = new TreeMap<Integer, Product>();
		discounts = new HashMap<Integer,Float>();
		totalRows = new HashMap<Integer, Float>();
		txtDiscount = new JTextField();
		txtDiscount.setText("0");
		txtDiscount.setColumns(10);
		txtDiscount.setBounds(408, 47, 72, 35);
		pSaleOperation.add(txtDiscount);
		
		JLabel lblDiscount = new JLabel("Discount");
		lblDiscount.setBounds(409, 29, 71, 14);
		pSaleOperation.add(lblDiscount);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(490, 47, 89, 35);
		pSaleOperation.add(btnAdd);
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
				totalRows.put(id,  total);
				updateTotal();
				
				columns[0] = productName;
				columns[1] = price;
				columns[2] = qty;
				columns[3] = discount;
				columns[4] = total;
				//Check if product has already existed in the table then it'll update qty
				if(boughtProducts.containsKey(id)) {
					Product p = boughtProducts.get(id);
					int oldQty = p.getQty();
					boughtProducts.put(id, new Product(id, categoryId, code, productName, cost, price, qty + oldQty));
					
					int rowCount = model.getRowCount();
					//Remove rows one by one from the end of the table
					for (int i = rowCount - 1; i >= 0; i--) {
					    model.removeRow(i);
					}
					float tota;
					for(Entry<Integer, Product> entry : boughtProducts.entrySet()) {
					    Product pro = entry.getValue();
						if(pro.getId() == id) {
							tota = pro.getQty() * pro.getPrice() - (pro.getQty() * pro.getPrice()*discount/100);
						    discounts.put(pro.getId(), discount);
							columns[3] = discount;	
						}else {
							tota = pro.getQty() * pro.getPrice() - (pro.getQty() * pro.getPrice()*discounts.get(pro.getId())/100);
							columns[3] = discounts.get(pro.getId());
						}
						columns[0] = pro.getName();
					    columns[1] = pro.getPrice();
						columns[2] = pro.getQty();
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
					discounts.put(id, discount);
				}	
				generateTotal();
				clearControl();
			}
			private void generateTotal() {
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
			}
		});
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSave.setBounds(1060, 457, 89, 35);
		add(btnSave);
		JLabel lblSale = new JLabel("Sale");
		lblSale.setBounds(23, 0, 96, 58);
		add(lblSale);
		lblSale.setFont(new Font("Tahoma", Font.PLAIN, 32));
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] returnId = { "SALEID" };
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
		            if (res.next()) {
		            	id = res.getInt(1);
		            	receiptNo = id + 1;
		            }
		            con.close();
		        }catch (Exception e) {
		        	e.printStackTrace();
				}
				
				try{
					Connection con = DB.getConnection();
					String sql = "insert into sales(receipt_no, seller_id, customer_id, total) values(?,?,?,?);";
					PreparedStatement ps=con.prepareStatement(sql, returnId);
					ps.setString(1, String.format("%05d", receiptNo));
				    ps.setInt(2, sellerId);
				    ps.setInt(3, customerId);
				    ps.setFloat(4, grandTotal);
				    int i = ps.executeUpdate();
				    int rowCount = model.getRowCount();
				    if (i != 0) {
				        JOptionPane.showMessageDialog(null, "Purchase success!", "Information", JOptionPane.INFORMATION_MESSAGE);
				      //Remove rows one by one from the end of the table
						for (int j = rowCount - 1; j >= 0; j--) {
						    model.removeRow(j);
						}										
						
						//clear total
						grandTotal = 0.0F;
				    } else {
				        System.out.println("not Inserted");
				    }
				    
				    //Get the last inserted id
				    try (ResultSet rs = ps.getGeneratedKeys()) {
				        if (rs.next()) {				            
				            saleId = rs.getInt(1);
				        }
				        rs.close();

				    }
					con.close();
				}catch(Exception e)
				{
					System.out.println(e);
				}
				
				//Get Product qty
				
				final List<Integer> productQties = new ArrayList<Integer>();
				try {
					Connection con = DB.getConnection();
					Connection conProduct = DB.getConnection();
					Connection conUpdateProductQty = DB.getConnection();
					String command = "INSERT INTO sale_details(sale_id, product_id, qty, discount, cost, price) VALUE(?,?,?,?,?,?)";
					PreparedStatement ps = con.prepareStatement(command);
					String sql = "SELECT qty FROM products WHERE id = ?";
					PreparedStatement psProduct = conProduct.prepareStatement(sql);
					String sqlUpdateQty = "UPDATE products SET qty = ? WHERE id = ?";
					PreparedStatement psUpdateQty = conUpdateProductQty.prepareStatement(sqlUpdateQty);
					int index = 0;
					for(Entry<Integer, Product> entry : boughtProducts.entrySet()) {
					    int pId = entry.getKey();
					    Product pro = entry.getValue();
					    //Get old product qty
					    psProduct.setInt(1, pId);					
					    ResultSet rs = psProduct.executeQuery();
					    while(rs.next()) {
					    	productQties.add(rs.getInt(1));
					    }
					    
					    //Update product qty
					    psUpdateQty.setInt(1, productQties.get(index) - pro.getQty());
					    psUpdateQty.setInt(2, pro.getId());
					    psUpdateQty.executeUpdate();
					    
					    //Insert product to tbl_sale_details
						ps.setInt(1,saleId);
						ps.setInt(2, pro.getId());
						ps.setInt(3, pro.getQty());
						ps.setFloat(4, discounts.get(pro.getId()));
						ps.setFloat(5, pro.getCost());						
						ps.setFloat(6, pro.getPrice());
						ps.executeUpdate();
//					    System.out.println(pro.toString());
					    // do what you have to do here
					    // In your case, another loop.
					}
					
					//remove previous products in list
					boughtProducts = new HashMap<Integer, Product>();
					discounts = new HashMap<Integer, Float>();
					totalRows = new HashMap<Integer, Float>();
					cboCustomer.setSelectedIndex(0);
					conProduct.close();
					conUpdateProductQty.close();
					con.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
	}

	protected void updateTotal() {
		grandTotal = 0;
		for(Entry<Integer,Float> entry : totalRows.entrySet()) {
			grandTotal += entry.getValue();
		}
		
	}


	@SuppressWarnings("unchecked")
	private JComboBox<Product> createProCombo() {
		@SuppressWarnings("rawtypes")
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


	@SuppressWarnings("unchecked")
	private JComboBox<Customer> createCusCombo() {
		@SuppressWarnings("rawtypes")
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
        customers = new TreeMap<String, Customer>();
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
        	e.printStackTrace();
		}

	}
}
