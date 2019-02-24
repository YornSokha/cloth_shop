package edu.rupp.cloth_shop.customer;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class PCustomer extends JPanel {
	private JTextField tfName;
	private JTextField tfId;
	private JTextField tfGender;
	private JTextField tfPhone;
	private JTextField tfAddress;
	private JTable table;
	DefaultTableModel dtm;

	/**
	 * Create the panel.
	 */
	public PCustomer() {
		setName("");
		setLayout(null);
		
		JLabel lblCustomerInformation = new JLabel("CUSTOMER INFORMATION");
		lblCustomerInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblCustomerInformation.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCustomerInformation.setBounds(105, 11, 254, 26);
		add(lblCustomerInformation);
		
		JLabel lblName = new JLabel("Customer Name: ");
		lblName.setBounds(10, 77, 99, 14);
		add(lblName);
		
		tfName = new JTextField();
		tfName.setBounds(115, 76, 167, 20);
		add(tfName);
		tfName.setColumns(10);
		
		JLabel lblId = new JLabel("Customer ID:");
		lblId.setBounds(10, 49, 99, 14);
		add(lblId);
		
		tfId = new JTextField();
		tfId.setBounds(115, 46, 167, 20);
		add(tfId);
		tfId.setColumns(10);
		
		JLabel lblSex = new JLabel("Sex:");
		lblSex.setBounds(10, 111, 99, 14);
		add(lblSex);
		
		tfGender = new JTextField();
		tfGender.setBounds(115, 106, 167, 20);
		add(tfGender);
		tfGender.setColumns(10);
		
		JLabel lblPhone = new JLabel("Phone Number: ");
		lblPhone.setBounds(10, 140, 99, 14);
		add(lblPhone);
		
		tfPhone = new JTextField();
		tfPhone.setBounds(115, 136, 167, 20);
		add(tfPhone);
		tfPhone.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(10, 169, 99, 14);
		add(lblAddress);
		
		tfAddress = new JTextField();
		tfAddress.setBounds(115, 166, 167, 20);
		add(tfAddress);
		tfAddress.setColumns(10);
		
		table = new JTable();
		table.setBounds(10, 235, 430, 135);
		add(table);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
try {
					
					theQuery("insert into customers (id,name,gender,phone,address)"
							+ " values('"+tfId.getText()+"','"
							+tfName.getText()+"','"+tfGender.getText()+"','"+tfPhone.getText()+"','"+tfAddress.getText()+"')");
										
				}catch(Exception ex){}
				tfId.setText("");
				tfName.setText("");
				tfGender.setText("");
				tfPhone.setText("");
				tfAddress.setText("");
			
			}						
		});
				
		btnAdd.setBounds(30, 201, 89, 23);
		add(btnAdd);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.setBounds(323, 201, 89, 23);
		add(btnCancel);
		
		JButton btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					theQuery("update customers set name = '"+tfName.getText()
					+"',gender='"+tfGender.getText()+"',phone='"+tfPhone.getText()+"',address='"+tfAddress.getText()+"' where id="+tfId.getText());
				}catch(Exception ex) {}
			
			}
		});
		btnEdit.setBounds(125, 201, 89, 23);
		add(btnEdit);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
try {
					
					theQuery("delete from customers where id = "+ tfId.getText());
				
				}catch(Exception ex) {}
			}
		});
		btnDelete.setBounds(224, 201, 89, 23);
		add(btnDelete);
		
		
	}
	public void theQuery (String query) {
		Connection con= null;
		Statement st = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cloth_shop_sys","root","");
			st = con.createStatement();
			st.executeUpdate(query);
			JOptionPane.showMessageDialog(null, "Query Executed");
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
}
