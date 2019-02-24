package edu.rupp.cloth_shop.customer;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class PCustomer extends JPanel {
	private JTextField tfName;
	private JTextField tfId;
	private JTextField tfGender;
	private JTextField tfPhone;
	private JTextField tfAddress;
	private JTable table;
	private DefaultTableModel model;

	
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
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Customer", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(4, 30, 442, 194);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblName = new JLabel("Name: ");
		lblName.setBounds(23, 44, 99, 14);
		panel.add(lblName);
		
		tfName = new JTextField();
		tfName.setBounds(128, 43, 167, 20);
		panel.add(tfName);
		tfName.setColumns(10);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(23, 16, 99, 14);
		panel.add(lblId);
		
		tfId = new JTextField();
		tfId.setBounds(128, 13, 167, 20);
		panel.add(tfId);
		tfId.setColumns(10);
		
		JLabel lblSex = new JLabel("Sex:");
		lblSex.setBounds(23, 78, 99, 14);
		panel.add(lblSex);
		
		tfGender = new JTextField();
		tfGender.setBounds(128, 73, 167, 20);
		panel.add(tfGender);
		tfGender.setColumns(10);
		
		JLabel lblPhone = new JLabel("Phone Number: ");
		lblPhone.setBounds(23, 107, 99, 14);
		panel.add(lblPhone);
		
		tfPhone = new JTextField();
		tfPhone.setBounds(128, 103, 167, 20);
		panel.add(tfPhone);
		tfPhone.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(23, 136, 99, 14);
		panel.add(lblAddress);
		
		tfAddress = new JTextField();
		tfAddress.setBounds(128, 133, 167, 20);
		panel.add(tfAddress);
		tfAddress.setColumns(10);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.setBounds(23, 164, 89, 23);
		panel.add(btnAdd);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.setBounds(316, 164, 89, 23);
		panel.add(btnCancel);
		
		JButton btnEdit = new JButton("EDIT");
		btnEdit.setBounds(118, 164, 89, 23);
		panel.add(btnEdit);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.setBounds(217, 164, 89, 23);
		panel.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
try {
					
					theQuery("delete from customers where id = "+ tfId.getText());
				
				}catch(Exception ex) {}
			}
		});
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					theQuery("update customers set name = '"+tfName.getText()
					+"',gender='"+tfGender.getText()+"',phone='"+tfPhone.getText()+"',address='"+tfAddress.getText()+"' where id="+tfId.getText());
				}catch(Exception ex) {}
			
			}
		});
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
		model = new DefaultTableModel();	
		model.addColumn("ID");
		model.addColumn("FIRST NAME");
		model.addColumn("LAST NAME");
		model.addColumn("GENDER");
		model.addColumn("EMAIL");
		scrEmpTable.setViewportView(table);
		pContainerListEmp.add(scpEmpReportTable, BorderLayout.CENTER);
		scpEmpReportTable.setViewportView(pContainScrEmpTable);
		
		JScrollPane scpCusReportTable = new JScrollPane();
		scpCusReportTable.setBounds(4, 235, 442, 158);
		add(scpCusReportTable);
		
		JPanel pContainerScrCusTable = new JPanel();
		scpCusReportTable.setColumnHeaderView(pContainerScrCusTable);
		pContainerScrCusTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		pContainerScrCusTable.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scpCustomerTable = new JScrollPane();
		pContainerScrCusTable.add(scpCustomerTable);
		
		table = new JTable();
		scpCustomerTable.setViewportView(table);
		table.setRowMargin(0);
		table.setRowHeight(25);
		table.setSelectionBackground(new Color(51, 153, 204));
		table.setGridColor(Color.BLACK);
		table.setBackground(Color.WHITE);
		table.setModel(model);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setBorder(new LineBorder(SystemColor.activeCaption, 2));
		
		
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
