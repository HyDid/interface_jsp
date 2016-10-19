package com.Alloc;

import java.sql.*;

public class DBHelper {

	
	public static final String dataBaseURL = "jdbc:mysql://localhost:3306/phpcast?+useUnicode=true&characterEncoding=utf-8&useSSL=true";
	public static final String name = "com.mysql.jdbc.Driver"; 
	public static final String user = "root";  
    public static final String password = "123456"; 
    
    public Connection conn = null;  
    public PreparedStatement pst = null;  
    
    public DBHelper (String sql) {
		
    	try {
			Class.forName(name);
			conn = DriverManager.getConnection(dataBaseURL, user, password);
			pst = conn.prepareStatement(sql);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
    
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
    
}
