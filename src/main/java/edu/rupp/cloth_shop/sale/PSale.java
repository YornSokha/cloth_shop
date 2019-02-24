package edu.rupp.cloth_shop.sale;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class PSale extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public PSale() {
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(166, 100, 86, 20);
		add(textField);
		textField.setColumns(10);

	}
}
