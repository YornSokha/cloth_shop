package edu.rupp.cloth_shop.customer;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import edu.rupp.cloth_shop.backend.DB;
import edu.rupp.cloth_shop.export.DbToCustomerCSV;
import edu.rupp.cloth_shop.export.DbToSaleDetailsCSV;
import edu.rupp.cloth_shop.logic.Customer;
import edu.rupp.cloth_shop.logic.Product;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.Console;
import java.util.Map.Entry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PCustomer extends JPanel {
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtPhone;
	private JTextField txtGender;
	private JTextField txtAddress;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnUpdate;
	private JButton btnClear;
	private JTable table;
	private DefaultTableModel model;
	private TreeMap<Integer, Customer> customers;
	private TreeMap<Integer, Customer> searchCustomers;
	private static Object[] columns = new Object[5];
	private static int seletedRow = -1;
	private JButton btnSearch;
	/**
	 * Create the panel.
	 */
	public PCustomer() {
		setBackground(Color.CYAN);
		setLayout(null);
		initComponent();
		getCustomers();
		addCustomerToTable(customers);
	}

	private void addCustomerToTable(TreeMap<Integer, Customer> customers) {
		model.setRowCount(0);
		int index = 0;
		for(Entry<Integer, Customer> entry : customers.entrySet()) {
			Customer customer = entry.getValue();
			columns[0] = ++index;
			columns[1] = customer.getName();
			columns[2] = customer.getGender();
			columns[3] = customer.getPhone();
			columns[4] = customer.getAddress();
			model.addRow(columns);
		}
		
	}

	private void getCustomers() {
        customers = new TreeMap<Integer, Customer>();
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
            	customers.put(cus.getId(), cus);
            }
            con.close();
        }catch (Exception e) {
        	e.printStackTrace();
		}

	}

	private void initComponent() {
		JPanel panel = new JPanel();
		panel.setBounds(114, 53, 584, 192);
		add(panel);
		panel.setLayout(new GridLayout(5, 2, 10, 20));
		
		JLabel lblId = new JLabel("ID");
		panel.add(lblId);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		panel.add(txtId);
		txtId.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		panel.add(lblName);
		
		txtName = new JTextField();
		panel.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblGender = new JLabel("Gender");
		panel.add(lblGender);
		
		txtGender = new JTextField();
		panel.add(txtGender);
		txtGender.setColumns(10);
		
		JLabel lblPhone = new JLabel("Phone");
		panel.add(lblPhone);
		
		txtPhone = new JTextField();
		panel.add(txtPhone);
		txtPhone.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		panel.add(lblAddress);
		
		txtAddress = new JTextField();
		panel.add(txtAddress);
		txtAddress.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(114, 307, 584, 225);
		add(scrollPane);
		
		table = new JTable(){
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				seletedRow = table.getSelectedRow();
	        	String name = (String)table.getValueAt(seletedRow, 1);
	        	String gender = (String)table.getValueAt(seletedRow, 2);
	        	String phone = (String)table.getValueAt(seletedRow, 3);
	        	String address = (String)table.getValueAt(seletedRow, 4);
	        	int id = 0;
	        	try {
	        		Connection con = DB.getConnection();
	        		String sql = "SELECT id FROM customers WHERE name = ? AND phone = ?";
	        		PreparedStatement ps = con.prepareStatement(sql);
	        		ps.setString(1, name);
	        		ps.setString(2, phone);
	        		ResultSet rs = ps.executeQuery();
	        		if(rs.next()) {
	        			id = rs.getInt(1);
	        		}
	        		rs.close();
	        		con.close();
	        	}catch (Exception e) {
					// TODO: handle exception
	        		e.printStackTrace();
				}
	        	
	        	btnAdd.setEnabled(false);
	        	btnDelete.setEnabled(true);
	        	btnUpdate.setEnabled(true);
	        	txtId.setText("" + id);
	        	txtName.setText(name);
	        	txtGender.setText(gender);
	        	txtPhone.setText(phone);
	        	txtAddress.setText(address);
			}
		});
		model = new DefaultTableModel();
		model.addColumn("No");
		model.addColumn("Name");
		model.addColumn("Gender");
		model.addColumn("Phone");
		model.addColumn("Address");
		table.setModel(model);

//		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
//	        public void valueChanged(ListSelectionEvent event) {
//	        	if(!isDelete) {
//	        		seletedRow = table.getSelectedRow();
//		        	String name = (String)table.getValueAt(seletedRow, 1);
//		        	String gender = (String)table.getValueAt(seletedRow, 2);
//		        	String phone = (String)table.getValueAt(seletedRow, 3);
//		        	String address = (String)table.getValueAt(seletedRow, 4);
//		        	int id = 0;
//		        	try {
//		        		Connection con = DB.getConnection();
//		        		String sql = "SELECT id FROM customers WHERE name = ? AND phone = ?";
//		        		PreparedStatement ps = con.prepareStatement(sql);
//		        		ps.setString(1, name);
//		        		ps.setString(2, phone);
//		        		ResultSet rs = ps.executeQuery();
//		        		if(rs.next()) {
//		        			id = rs.getInt(1);
//		        		}
//		        		rs.close();
//		        		con.close();
//		        	}catch (Exception e) {
//						// TODO: handle exception
//		        		e.printStackTrace();
//					}
//		        	
//		        	btnAdd.setEnabled(false);
//		        	btnDelete.setEnabled(true);
//		        	btnUpdate.setEnabled(true);
//		        	txtId.setText("" + id);
//		        	txtName.setText(name);
//		        	txtGender.setText(gender);
//		        	txtPhone.setText(phone);
//		        	txtAddress.setText(address);		
//	        	}else {
//	        		clearControls();	        		
//	        		isDelete = !isDelete;
//	        	}
//	        }
//	        
//		});
		
		scrollPane.setViewportView(table);
		
		JPanel pButton = new JPanel();
		pButton.setBounds(114, 264, 584, 32);
		add(pButton);
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] returnId = { "CUSTOMERID" };
				String name = txtName.getText();
				String gender = txtGender.getText();
				String phone = txtPhone.getText();
				String address = txtAddress.getText();
				if(name.equals("") || gender.equals("")) {
					JOptionPane.showMessageDialog(null, "Name and gender must have value!", "Information", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
					
				int id = 0;
				try {
					Connection con = DB.getConnection();
					String sql = "INSERT INTO customers(name, gender, phone, address) VALUES(?,?,?,?)";
					PreparedStatement ps = con.prepareStatement(sql, returnId);
					ps.setString(1, name);
					ps.setString(2, gender);
					ps.setString(3, phone);
					ps.setString(4, address);
					int i = ps.executeUpdate();
					if (i != 0) {
				        JOptionPane.showMessageDialog(null, "Purchase success!", "Information", JOptionPane.INFORMATION_MESSAGE);
				        int rowCount = model.getRowCount();
				        columns[0] = rowCount + 1;
						columns[1] = name;
						columns[2] = gender;
						columns[3] = phone;
						columns[4] = address;																							
				    } else {
				        System.out.println("not Inserted");
				    }
					//Get the last inserted id
				    try (ResultSet rs = ps.getGeneratedKeys()) {
				        if (rs.next()) {				            
				            id = rs.getInt(1);
				            customers.put(id, new Customer(id, name, gender, phone, address));
				            btnClear.doClick();
				        }
				        rs.close();
				    }
				    con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
						
			}
		});
		pButton.setLayout(new GridLayout(0, 5, 0, 0));
		pButton.add(btnAdd);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!idExisted())
					return;
				Connection con = DB.getConnection();
				try {
					con = DB.getConnection();
					String sql = "UPDATE customers SET name = ?, gender = ?, phone = ?, address = ? WHERE id = ?";
					PreparedStatement ps = con.prepareStatement(sql);
					int id = Integer.parseInt(txtId.getText());
					String name = txtName.getText();
					String gender = txtGender.getText();
					String phone = txtPhone.getText();
					String address = txtAddress.getText();
					ps.setString(1, name);
					ps.setString(2, gender);
					ps.setString(3, phone);
					ps.setString(4, address);
					ps.setInt(5, id);
					int i = ps.executeUpdate();
					if(i != 0) {
						 JOptionPane.showMessageDialog(null, "Update success!", "Information", JOptionPane.INFORMATION_MESSAGE);
						 customers.put(id, new Customer(id, name, gender, phone, address));
						 columns[0] = seletedRow + 1;
						 columns[1] = name;
						 columns[2] = gender;
						 columns[3] = phone;
						 columns[4] = address;
						 for(int j = 0; j < model.getColumnCount(); j++) {
							 model.setValueAt(columns[j], seletedRow, j);
						 }
					}else {
						JOptionPane.showMessageDialog(null, "Update failed!", "Information", JOptionPane.WARNING_MESSAGE);
					}
					con.close();
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchCustomers = new TreeMap<Integer, Customer>();
				String search = JOptionPane.showInputDialog("Enter keyword:");
				if(search.equals(""))
					return;
				String sql =  "SELECT * FROM customers WHERE name LIKE ?";
				try {
					Connection con = DB.getConnection();
					PreparedStatement ps = null;
					ps = con.prepareStatement(sql);
					ps.setString(1, "%" + search + "%");					
					
					ResultSet rs = ps.executeQuery();
					int id;
					String name, gender, phone, address;
					int index = 0;
					model.setRowCount(0);
					while(rs.next()) {
						id = rs.getInt(1);
						name = rs.getString(2);
						gender = rs.getString(3);
						phone = rs.getString(4);
						address = rs.getString(5);
						searchCustomers.put(id, new Customer(id, name, gender, phone, address));
					}
					addCustomerToTable(searchCustomers);
					rs.close();
					con.close();
				}catch (Exception e2) {
					JOptionPane.showConfirmDialog(null, e2.getMessage());
				}
			}
		});
		pButton.add(btnSearch);
		btnUpdate.setEnabled(false);
		pButton.add(btnUpdate);
		
		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!idExisted())
					return;
				int i = JOptionPane.showConfirmDialog(null, "Are you sure?", "Warning", JOptionPane.YES_NO_OPTION);
				if(i != 0)
					return;
				try {
					Connection con = DB.getConnection();
					String sql = "DELETE FROM customers WHERE id = ?";
					PreparedStatement ps = con.prepareStatement(sql);
					int id = Integer.parseInt(txtId.getText());
					ps.setInt(1, id);
					int j = ps.executeUpdate();								
					if(j != 0) {
						JOptionPane.showMessageDialog(null, "Delete success!", "Information", JOptionPane.INFORMATION_MESSAGE);
						//Remove rows one by one from the end of the table
						customers.remove(id);
						clearRow();
					}else {
						JOptionPane.showMessageDialog(null, "Delete failed!", "Information", JOptionPane.WARNING_MESSAGE);
					}
					con.close();
				}catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage(), "Information", JOptionPane.WARNING_MESSAGE);
				}
			}

			private void clearRow() {
				model.setRowCount(0);
				addCustomerToTable(customers);
				btnClear.doClick();
			}
		});
		pButton.add(btnDelete);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clearControls();
				addCustomerToTable(customers);
				btnAdd.setEnabled(true);
				btnUpdate.setEnabled(false);
				btnDelete.setEnabled(false);
				txtName.requestFocus();
			}
		});
		pButton.add(btnClear);		
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DbToCustomerCSV();
			}
		});
		btnExport.setBounds(609, 11, 89, 31);
		add(btnExport);
		
	}
	private boolean idExisted() {
		String checkId = txtId.getText();
		if(checkId.equals(""))
			return false;
		return true;
	}
	private void clearControls() {
		txtId.setText("");
		txtName.setText("");
		txtGender.setText("");
		txtPhone.setText("");
		txtAddress.setText("");
	}
}
