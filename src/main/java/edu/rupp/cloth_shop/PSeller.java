/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.rupp.cloth_shop;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author Somalika
 */
public class PSeller extends javax.swing.JPanel {
	private JTextField tfFname;
	private JTextField tfId;
	private JTextField tfGender;
	private JTextField tfPhone;
	private JTextField tfAddress;
	private JTextField tfLname;
	private JTable table;

    /**
     * Creates new form PSeller
     */
    public PSeller() {
    	setLayout(null);
    	
    	JLabel lblSellerInformation = new JLabel("SELLER INFORMATION");
    	lblSellerInformation.setHorizontalAlignment(SwingConstants.CENTER);
    	lblSellerInformation.setFont(new Font("Tahoma", Font.PLAIN, 18));
    	lblSellerInformation.setBounds(108, 11, 215, 27);
    	add(lblSellerInformation);
    	
    	JPanel panel = new JPanel();
    	panel.setLayout(null);
    	panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Seller", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    	panel.setBounds(10, 49, 442, 247);
    	add(panel);
    	
    	JLabel lblFirstname = new JLabel("First Name: ");
    	lblFirstname.setBounds(23, 44, 99, 14);
    	panel.add(lblFirstname);
    	
    	tfFname = new JTextField();
    	tfFname.setColumns(10);
    	tfFname.setBounds(128, 43, 167, 20);
    	panel.add(tfFname);
    	
    	JLabel lblId = new JLabel("ID:");
    	lblId.setBounds(23, 16, 99, 14);
    	panel.add(lblId);
    	
    	tfId = new JTextField();
    	tfId.setColumns(10);
    	tfId.setBounds(128, 13, 167, 20);
    	panel.add(tfId);
    	
    	JLabel lblSex = new JLabel("Sex:");
    	lblSex.setBounds(25, 108, 99, 14);
    	panel.add(lblSex);
    	
    	tfGender = new JTextField();
    	tfGender.setColumns(10);
    	tfGender.setBounds(128, 103, 167, 20);
    	panel.add(tfGender);
    	
    	JLabel lblPhone = new JLabel("Phone Number: ");
    	lblPhone.setBounds(25, 137, 99, 14);
    	panel.add(lblPhone);
    	
    	tfPhone = new JTextField();
    	tfPhone.setColumns(10);
    	tfPhone.setBounds(128, 133, 167, 20);
    	panel.add(tfPhone);
    	
    	JLabel lblAddress = new JLabel("Address:");
    	lblAddress.setBounds(25, 166, 99, 14);
    	panel.add(lblAddress);
    	
    	tfAddress = new JTextField();
    	tfAddress.setColumns(10);
    	tfAddress.setBounds(128, 163, 167, 20);
    	panel.add(tfAddress);
    	
    	JButton btnAdd = new JButton("ADD");
    	btnAdd.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
try {
					
					theQuery("insert into sellers (id,first_name,last_name,gender,phone,address)"
							+ " values('"+tfId.getText()+"','"
							+tfFname.getText()+"','"+tfLname.getText()+"','"+tfGender.getText()+"','"+tfPhone.getText()+"','"+tfAddress.getText()+"')");
										
				}catch(Exception ex){}
				tfId.setText("");
				tfFname.setText("");
				tfLname.setText("");
				tfGender.setText("");
				tfPhone.setText("");
				tfAddress.setText("");
			
			}
    	});
    	btnAdd.setBounds(25, 194, 89, 23);
    	panel.add(btnAdd);
    	
    	JButton btnCancel = new JButton("CANCEL");
    	btnCancel.setBounds(318, 194, 89, 23);
    	panel.add(btnCancel);
    	
    	JButton btnUpdate = new JButton("UPDATE");
    	btnUpdate.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			try {
					theQuery("update sellers set first_name = '"+tfFname.getText()
					+"',last_name = '"+tfLname.getText()+"',gender='"+tfGender.getText()+"',phone='"+tfPhone.getText()+"',address='"+tfAddress.getText()+"' where id="+tfId.getText());
				}catch(Exception ex) {}
			
    		}
    	});
    	btnUpdate.setBounds(120, 194, 89, 23);
    	panel.add(btnUpdate);
    	
    	JButton btnDelete = new JButton("DELETE");
    	btnDelete.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    		
    		try {
				
				theQuery("delete from sellers where id = "+ tfId.getText());
			
			}catch(Exception ex) {}
    		}
    	});
    	btnDelete.setBounds(219, 194, 89, 23);
    	panel.add(btnDelete);
    	
    	tfLname = new JTextField();
    	tfLname.setColumns(10);
    	tfLname.setBounds(128, 74, 167, 20);
    	panel.add(tfLname);
    	
    	JLabel lblLastName = new JLabel("Last Name: ");
    	lblLastName.setBounds(23, 77, 99, 14);
    	panel.add(lblLastName);
    	
    	table = new JTable();
    	table.setBounds(10, 300, 430, 137);
    	add(table);
        initComponents();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
