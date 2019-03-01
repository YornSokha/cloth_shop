package edu.rupp.cloth_shop.seller;

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
import edu.rupp.cloth_shop.export.DbToSellerCSV;
import edu.rupp.cloth_shop.export.DbToSaleDetailsCSV;
import edu.rupp.cloth_shop.logic.Seller;
import edu.rupp.cloth_shop.logic.Product;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class PSeller extends JPanel {
	private JTextField txtId;
	private JTextField txtLastName;
	private JTextField txtPhone;
	private JTextField txtGender;
	private JTextField txtAddress;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnUpdate;
	private JButton btnClear;
	private JTable table;
	private DefaultTableModel model;
	private TreeMap<Integer, Seller> Sellers;
	private TreeMap<Integer, Seller> searchSellers;
	private static Object[] columns = new Object[6];
	private static int seletedRow = -1;
	private JButton btnSearch;
	private JLabel lblFirstName;
	private JTextField txtFirstName;
	/**
	 * Create the panel.
	 */
	public PSeller() {
		setLayout(null);
		initComponent();
		getSellers();
		addSellerToTable(Sellers);
	}

	private void addSellerToTable(TreeMap<Integer, Seller> Sellers) {
		model.setRowCount(0);
		int index = 0;
		for(Entry<Integer, Seller> entry : Sellers.entrySet()) {
			Seller Seller = entry.getValue();
			columns[0] = ++index;
			columns[1] = Seller.getFirstName();
			columns[2] = Seller.getLastName();
			columns[3] = Seller.getGender();
			columns[4] = Seller.getPhone();
			columns[5] = Seller.getAddress();
			model.addRow(columns);
		}
		
	}

	private void getSellers() {
        Sellers = new TreeMap<Integer, Seller>();
        try {
        	Connection con = DB.getConnection();
        	String sql = "SELECT * FROM Sellers";
        	Statement st = con.createStatement();
            ResultSet res = st.executeQuery(sql);
            int id;
            String firstName, lastName, gender, phone, address;
            while (res.next()) {
            	id = res.getInt(1);
            	firstName = res.getString(2);
            	lastName = res.getString(3);
            	gender = res.getString(4);
            	phone = res.getString(5);
            	address = res.getString(6);
            	Seller seller = new Seller(id, firstName, lastName, gender, phone, address);
            	Sellers.put(seller.getId(), seller);
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
		panel.setLayout(new GridLayout(6, 2, 10, 10));
		
		JLabel lblId = new JLabel("ID");
		panel.add(lblId);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		panel.add(txtId);
		txtId.setColumns(10);
		
		lblFirstName = new JLabel("First Name");
		panel.add(lblFirstName);
		
		txtFirstName = new JTextField();
		txtFirstName.setColumns(10);
		panel.add(txtFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		panel.add(lblLastName);
		
		txtLastName = new JTextField();
		panel.add(txtLastName);
		txtLastName.setColumns(10);
		
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
		model = new DefaultTableModel();
		model.addColumn("No");
		model.addColumn("First Name");
		model.addColumn("Last Name");
		model.addColumn("Gender");
		model.addColumn("Phone");
		model.addColumn("Address");
		table.setModel(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				seletedRow = table.getSelectedRow();
	        	String firstName = (String)table.getValueAt(seletedRow, 1);
	        	String lastName = (String)table.getValueAt(seletedRow, 2);
	        	String gender = (String)table.getValueAt(seletedRow, 3);
	        	String phone = (String)table.getValueAt(seletedRow, 4);
	        	String address = (String)table.getValueAt(seletedRow, 5);
	        	int id = 0;
	        	try {
	        		Connection con = DB.getConnection();
	        		String sql = "SELECT id FROM Sellers WHERE first_name = ? AND last_name = ?";
	        		PreparedStatement ps = con.prepareStatement(sql);
	        		ps.setString(1, firstName);
	        		ps.setString(2, lastName);
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
	        	txtFirstName.setText(firstName);
	        	txtLastName.setText(lastName);
	        	txtGender.setText(gender);
	        	txtPhone.setText(phone);
	        	txtAddress.setText(address);		
			}
		});
		scrollPane.setViewportView(table);
		
		JPanel pButton = new JPanel();
		pButton.setBounds(114, 264, 584, 32);
		add(pButton);
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] returnId = { "SellerID" };
				String firstName = txtFirstName.getText();
				String lastName = txtLastName.getText();
				String gender = txtGender.getText();
				String phone = txtPhone.getText();
				String address = txtAddress.getText();
				if(firstName.equals("") || lastName.equals("") || gender.equals("")) {
					JOptionPane.showMessageDialog(null, "Name and gender must have value!", "Information", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
					
				int id = 0;
				try {
					Connection con = DB.getConnection();
					String sql = "INSERT INTO Sellers(first_name, last_name, gender, phone, address) VALUES(?,?,?,?,?)";
					PreparedStatement ps = con.prepareStatement(sql, returnId);
					ps.setString(1, firstName);
					ps.setString(2, lastName);
					ps.setString(3, gender);
					ps.setString(4, phone);
					ps.setString(5, address);
					int i = ps.executeUpdate();
					if (i != 0) {
				        JOptionPane.showMessageDialog(null, "Seller has been added successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
				        int rowCount = model.getRowCount();
				        columns[0] = rowCount + 1;
						columns[1] = firstName;
						columns[2] = lastName;
						columns[3] = gender;
						columns[4] = phone;
						columns[5] = address;
																						
				    } else {
				        System.out.println("not Inserted");
				    }
					//Get the last inserted id
				    try (ResultSet rs = ps.getGeneratedKeys()) {
				        if (rs.next()) {				            
				            id = rs.getInt(1);
				            Sellers.put(id, new Seller(id, firstName, lastName, gender, phone, address));
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
					String sql = "UPDATE Sellers SET first_name = ?, last_name = ?, gender = ?, phone = ?, address = ? WHERE id = ?";
					PreparedStatement ps = con.prepareStatement(sql);
					int id = Integer.parseInt(txtId.getText());
					String firstName = txtFirstName.getText();
					String lastName = txtLastName.getText();
					String gender = txtGender.getText();
					String phone = txtPhone.getText();
					String address = txtAddress.getText();
					ps.setString(1, firstName);
					ps.setString(2, lastName);
					ps.setString(3, gender);
					ps.setString(4, phone);
					ps.setString(5, address);
					ps.setInt(6, id);
					int i = ps.executeUpdate();
					if(i != 0) {
						 JOptionPane.showMessageDialog(null, "Update success!", "Information", JOptionPane.INFORMATION_MESSAGE);
						 Sellers.put(id, new Seller(id, firstName, lastName, gender, phone, address));
						 columns[0] = seletedRow + 1;
						 columns[1] = firstName;
						 columns[2] = lastName;
						 columns[3] = gender;
						 columns[4] = phone;
						 columns[5] = address;
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
				searchSellers = new TreeMap<Integer, Seller>();
				String search = JOptionPane.showInputDialog("Enter keyword:");
				if(search.equals(""))
					return;
				String sql =  "SELECT * FROM Sellers WHERE first_name LIKE ? OR last_name LIKE ?";
				try {
					Connection con = DB.getConnection();
					PreparedStatement ps = null;
					ps = con.prepareStatement(sql);
					ps.setString(1, "%" + search + "%");				
					ps.setString(2, "%" + search + "%");
					
					ResultSet rs = ps.executeQuery();
					int id;
					String firstName, lastName, gender, phone, address;
					int index = 0;
					model.setRowCount(0);
					while(rs.next()) {
						id = rs.getInt(1);
						firstName = rs.getString(2);
						lastName = rs.getString(3);
						gender = rs.getString(4);
						phone = rs.getString(5);
						address = rs.getString(6);
						searchSellers.put(id, new Seller(id, firstName, lastName, gender, phone, address));
					}
					addSellerToTable(searchSellers);
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
					String sql = "DELETE FROM Sellers WHERE id = ?";
					PreparedStatement ps = con.prepareStatement(sql);
					int id = Integer.parseInt(txtId.getText());
					ps.setInt(1, id);
					int j = ps.executeUpdate();								
					if(j != 0) {
						JOptionPane.showMessageDialog(null, "Delete success!", "Information", JOptionPane.INFORMATION_MESSAGE);
						//Remove rows one by one from the end of the table
						Sellers.remove(id);
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
				addSellerToTable(Sellers);
				btnClear.doClick();
			}
		});
		pButton.add(btnDelete);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clearControls();
				addSellerToTable(Sellers);
				btnAdd.setEnabled(true);
				btnUpdate.setEnabled(false);
				btnDelete.setEnabled(false);
				txtFirstName.requestFocus();
			}
		});
		pButton.add(btnClear);		
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DbToSellerCSV();
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
		txtFirstName.setText("");
		txtLastName.setText("");
		txtGender.setText("");
		txtPhone.setText("");
		txtAddress.setText("");
	}
}
