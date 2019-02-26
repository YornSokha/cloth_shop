package edu.rupp.cloth_shop.report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.rupp.cloth_shop.backend.DB;
import edu.rupp.cloth_shop.export.DbToSaleCSV;
import edu.rupp.cloth_shop.logic.Sale;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class PSaleReport extends JPanel {
	private JTable table;
	private DefaultTableModel model;
	private float grandTotal;
	private HashMap<Integer, Sale> sales;
	/**
	 * Create the panel.
	 */
	public PSaleReport() {
		setLayout(null);
		initComponent();
		
	}
	private void initComponent() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 85, 1127, 429);
		add(scrollPane);
		
		table = new JTable(){
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		model = new DefaultTableModel();
		model.addColumn("No");
		model.addColumn("ReceiptNo");
		model.addColumn("Customer");
		model.addColumn("Phone");
		model.addColumn("Total");
		getRows();
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new DbToSaleCSV();
			}
		});
		btnExport.setBounds(1060, 41, 90, 33);
		add(btnExport);

		
	}
	private void getRows() {
		sales = new HashMap<Integer, Sale>();
		try {
			
			Connection con = DB.getConnection();
			String sql = "SELECT sales.id, sales.receipt_no, customers.name, sales.total, sales.seller_id, customers.id, customers.phone FROM sales INNER JOIN customers ON sales.customer_id = customers.id";
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			String receiptNo, customerName, customerPhone;
			float total;
			int id, sellerId, customerId;
			Object[] columns = new Object[5];
			int number = 1;
			while(rs.next()) {
				id = rs.getInt(1);
				receiptNo = rs.getString(2);
				customerName = rs.getString(3);
				total = rs.getFloat(4);
				sellerId = rs.getInt(5);
				customerId = rs.getInt(6);
				customerPhone = rs.getString(7);
				
				//add row to table
				columns[0] = number++;
				columns[1] = receiptNo;
				columns[2] = customerName;
				columns[3] = customerPhone;
				columns[4] = "$" + total;
				model.addRow(columns);
				sales.put(id, new Sale(id, receiptNo, sellerId, customerId, total));
			}
			if(number != 1) {
				//add row to table
				calculateTotal();
				columns[0] = "";
				columns[1] = "";
				columns[2] = "";
				columns[3] = "Total";
				columns[4] = "$" + grandTotal;
				model.addRow(columns);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void calculateTotal() {
		for(Entry<Integer, Sale> entry : sales.entrySet()) {
			
		    Sale sale = entry.getValue();
		    grandTotal += sale.getTotal();		
		}
		
	}
}
