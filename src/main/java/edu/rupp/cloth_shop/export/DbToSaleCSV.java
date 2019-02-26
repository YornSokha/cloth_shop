package edu.rupp.cloth_shop.export;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

import edu.rupp.cloth_shop.backend.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
public class DbToSaleCSV {
	public DbToSaleCSV() {
		String filename ="C:\\sales.csv";
        try {
            FileWriter fw = new FileWriter(filename);
            Connection conn = DB.getConnection();
            String sql = "SELECT sales.id, sales.receipt_no, customers.name, sales.total, sales.seller_id, customers.id, customers.phone FROM sales INNER JOIN customers ON sales.customer_id = customers.id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            fw.append("No");
            fw.append(",");
            fw.append("Receipt No");
            fw.append(',');
            fw.append("Customer Name");
            fw.append(',');
            fw.append("Phnone");
            fw.append(',');
            fw.append("Total");
            fw.append(',');
            fw.append('\n');
            int i = 1;
            while (rs.next()) {
                fw.append("" + i++);
                fw.append(',');
                fw.append(String.format("%04d", Integer.parseInt(rs.getString(2))));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(7));
                fw.append(',');
                fw.append("$" + rs.getFloat(4));
                fw.append(',');
                fw.append('\n');
               }
            fw.flush();
            fw.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Sale report has been generated successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
    
       
}