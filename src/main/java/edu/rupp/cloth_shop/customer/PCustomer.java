package edu.rupp.cloth_shop.customer;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextField;

public class PCustomer extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Create the panel.
	 */
	public PCustomer() {
		setLayout(null);
		
		JLabel lblCustomerInformation = new JLabel("CUSTOMER INFORMATION");
		lblCustomerInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblCustomerInformation.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCustomerInformation.setBounds(105, 11, 254, 26);
		add(lblCustomerInformation);
		
		JLabel lblCustomerName = new JLabel("Customer Name: ");
		lblCustomerName.setBounds(10, 77, 99, 14);
		add(lblCustomerName);
		
		textField = new JTextField();
		textField.setBounds(115, 74, 167, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblCustomerId = new JLabel("Customer ID:");
		lblCustomerId.setBounds(10, 49, 99, 14);
		add(lblCustomerId);
		
		textField_1 = new JTextField();
		textField_1.setBounds(115, 46, 167, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblSex = new JLabel("Sex:");
		lblSex.setBounds(10, 102, 46, 14);
		add(lblSex);
		
		textField_2 = new JTextField();
		textField_2.setBounds(118, 105, 86, 20);
		add(textField_2);
		textField_2.setColumns(10);

	}
}
