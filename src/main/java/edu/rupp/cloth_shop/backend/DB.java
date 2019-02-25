package edu.rupp.cloth_shop.backend;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
	public static Connection getConnection(){
		Connection con = null;
		try{
//			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/cloth_shop_sys","root","");

			}catch(Exception e){System.out.println(e);}return con;
		}
}
