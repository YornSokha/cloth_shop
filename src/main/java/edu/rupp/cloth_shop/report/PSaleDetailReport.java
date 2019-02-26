package edu.rupp.cloth_shop.report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.rupp.cloth_shop.backend.DB;
import edu.rupp.cloth_shop.export.DbToSaleDetailsCSV;
import edu.rupp.cloth_shop.logic.Sale;
import edu.rupp.cloth_shop.logic.SaleDetail;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class PSaleDetailReport extends JPanel {
	private JTable table;
	private DefaultTableModel model;
	private float grandTotal;
	private float totalCost;
	private float total;
	private HashMap<Integer, SaleDetail> saleDetails;
	/**
	 * Create the panel.
	 */
	public PSaleDetailReport() {
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 88, 1129, 435);
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
		model.addColumn("Product");
		model.addColumn("Qty");
		model.addColumn("Discount");
		model.addColumn("Cost");
		model.addColumn("Unit Price");
		model.addColumn("Total Cost");
		model.addColumn("Total");
		getRows();
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DbToSaleDetailsCSV();
			}
		});
		btnExport.setBounds(1061, 44, 91, 33);
		add(btnExport);

	}
	private void getRows() {
		
		saleDetails = new HashMap<Integer, SaleDetail>();
		try {
			
			Connection con = DB.getConnection();
			String sql = "select sales.receipt_no, customers.name, products.name, sale_details.qty, sale_details.discount, sale_details.cost, sale_details.price\r\n" + 
					"from sale_details\r\n" + 
					"inner join products\r\n" + 
					"on sale_details.product_id = products.id\r\n" + 
					"inner join sales\r\n" + 
					"on sale_details.sale_id = sales.id\r\n" + 
					"inner join customers\r\n" + 
					"on sales.customer_id = customers.id;";
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			String receiptNo, customerName, productName;
			float discount, cost, price;
			int id, sellerId, customerId, qty;
			Object[] columns = new Object[10];
			int number = 1;
			while(rs.next()) {				
				receiptNo = rs.getString(1);
				customerName = rs.getString(2);
				productName = rs.getString(3);
				qty = rs.getInt(4);
				discount = rs.getFloat(5);
				cost = rs.getFloat(6);
				price = rs.getFloat(7);
				
				
				//add row to table
				float rowCostTotal = (cost * qty);
				totalCost += rowCostTotal;
				float rowTotal = (price * qty - (price * qty * discount)/100);
				total += rowTotal;
				columns[0] = number++;
				columns[1] = receiptNo;
				columns[2] = customerName;
				columns[3] = productName;
				columns[4] = qty;
				columns[5] = discount;
				columns[6] = cost;
				columns[7] = price;
				columns[8] = rowCostTotal;
				columns[9] = rowTotal;
				model.addRow(columns);
//				saleDetails.put(id, new Sale(id, receiptNo, sellerId, customerId, total));
			}
			if(number != 1) {
				//add row to table
//				calculateTotal();
				columns[0] = "";
				columns[1] = "";
				columns[2] = "";
				columns[3] = "";
				columns[4] = "";
				columns[5] = "";
				columns[6] = "";
				columns[7] = "(Total Cost, Total)";
				columns[8] = totalCost;
				columns[9] = total;
				model.addRow(columns);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
