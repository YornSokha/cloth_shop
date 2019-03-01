package edu.rupp.cloth_shop.export;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

import edu.rupp.cloth_shop.backend.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
public class DbToSaleDetailsCSV {
    public DbToSaleDetailsCSV() {
        String filename ="C:\\Sale details.csv";
        try {
            FileWriter fw = new FileWriter(filename);
            Connection conn = DB.getConnection();
            String sql = "select sales.receipt_no, customers.name, products.name, sale_details.qty, sale_details.discount, sale_details.cost, sale_details.price\r\n" + 
					"from sale_details\r\n" + 
					"inner join products\r\n" + 
					"on sale_details.product_id = products.id\r\n" + 
					"inner join sales\r\n" + 
					"on sale_details.sale_id = sales.id\r\n" + 
					"inner join customers\r\n" + 
					"on sales.customer_id = customers.id;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            fw.append("No");
            fw.append(",");
            fw.append("Receipt No");
            fw.append(',');
            fw.append("Customer Name");
            fw.append(',');
            fw.append("Product");
            fw.append(',');
            fw.append("Qty");
            fw.append(',');
            fw.append("Discount");
            fw.append(',');
            fw.append("Cost");
            fw.append(',');
            fw.append("Unit Price");
            fw.append(',');
            fw.append("Total Cost");
            fw.append(',');
            fw.append("Total");
            fw.append(',');
            fw.append('\n');
            int i = 1;
            float cost, price, totalCost = 0, total = 0;
            while (rs.next()) {
                fw.append("" + i++);
                fw.append(',');
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append("" +  rs.getInt(4));
                fw.append(',');
                fw.append("%" + rs.getFloat(5));
                fw.append(',');
                fw.append("$ " + (cost = rs.getFloat(6)));
                fw.append(',');
                fw.append("$ " + (price = rs.getFloat(7)));
                fw.append(',');
                fw.append("$ " + cost * rs.getInt(4));
                totalCost += cost * rs.getInt(4);
                fw.append(',');
                fw.append("$ " + ((rs.getInt(4) * price) - (rs.getInt(4) * price * rs.getFloat(5))/100));
                total = ((rs.getInt(4) * price) - (rs.getInt(4) * price * rs.getFloat(5))/100);
                fw.append('\n');
               }
            for(int j = 0; j < 7; j++) {
            	fw.append("");        
                fw.append(',');	
            }
            
            fw.append("Total");
            fw.append(',');
            fw.append("$ " + total);
            fw.append(',');
            fw.append("$ " + totalCost);
            fw.append(',');
            fw.append('\n');
            for(int j = 0; j < 8; j++) {
            	fw.append("");        
                fw.append(',');	
            }
            fw.append("Profit");
            fw.append(',');
            fw.append("$ " + (total - totalCost));
            fw.flush();
            fw.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Sale details report has been generated successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}