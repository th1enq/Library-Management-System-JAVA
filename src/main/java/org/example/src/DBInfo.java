package org.example.src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class DBInfo 
{
	static
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver Loaded...");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static Connection conn() 
	{
		Connection con=null;
		try {
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/TESTT","root","");
			System.out.println("Connection Established...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	public static Vector<String> getValue(String name) 
	{
		Vector<String> v=new Vector<>();

		return v;
	}
	public static Vector<Vector> outerVector;
	public static Vector colsName;

	public static void allBooks() throws SQLException 
	{

	}
	public static Vector<Vector> outerVector1;
	public static Vector colsName1;

	public static void searchBooks_by(String itemName,String values) throws SQLException
	{

	}
	public static Vector<Vector> outerVector2;
	public static Vector colsName2;

	public static void viewLibrarians(String usertype) throws SQLException
	{

	}
	public static String value;
	public static String getNotice()
	{

		return value;
	}
}
