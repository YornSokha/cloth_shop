/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.rupp.cloth_shop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Somalika
 */
public class MyApp {
    public static void main(String[] args) throws SQLException{
        String jdbcUrl = "jdbc:mysql://localhost:3306/cloth_shop_sys?useSSL=false";
         String user = "root";
         String password = "";
         Connection con = null;
         try {
         	 con = DriverManager.getConnection(jdbcUrl, user, password);
         	System.out.println("Success");
         }catch (Exception e) {
 			// TODO: handle exception
         	e.printStackTrace();
 		}
         String sql = "INSERT INTO customers(name, gender, address) VALUES(?,?,?)";
         PreparedStatement ps = con.prepareStatement(sql);
        
         ps.setString(1, "Boeuy Somalika");
         ps.setString(2, "Female");
         ps.setString(3, null);
         ps.executeUpdate();
         new Categories().setVisible(true);
    }
}
