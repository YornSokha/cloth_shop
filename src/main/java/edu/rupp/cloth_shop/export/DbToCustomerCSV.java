package edu.rupp.cloth_shop.export;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

import edu.rupp.cloth_shop.backend.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
public class DbToCustomerCSV {
    public DbToCustomerCSV() {
        String filename ="C:\\customers.csv";
        try {
            FileWriter fw = new FileWriter(filename);
            Connection conn = DB.getConnection();
            String query = "select * from customers";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            fw.append("ID");
            fw.append(',');
            fw.append("Name");
            fw.append(',');
            fw.append("Gender");
            fw.append(',');
            fw.append("Phone");
            fw.append(',');
            fw.append("Address");
            fw.append(',');
            fw.append('\n');
            while (rs.next()) {
                fw.append("" + rs.getInt(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(4));
                fw.append(',');
                fw.append(rs.getString(5));
                fw.append(',');
                fw.append('\n');
               }
            fw.flush();
            fw.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Customer report has been generated successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}