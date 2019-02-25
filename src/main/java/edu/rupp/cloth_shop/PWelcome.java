package edu.rupp.cloth_shop;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;

public class PWelcome extends JPanel {

	/**
	 * Create the panel.
	 */
	public PWelcome() {
		setLayout(new BorderLayout(0, 0));
		JLabel lbl2 = new JLabel("");
		lbl2.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon icon2 = new ImageIcon("resources/welcome.jpg");
		lbl2.setIcon(icon2);
		add(lbl2, BorderLayout.CENTER);
	}

}
