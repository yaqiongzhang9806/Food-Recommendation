package ss;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectName {
	public HashSet<String> arry=new HashSet<String>();
	public String sss;
	public SelectName(String s){
		this.sss=s;
	}
	public boolean contain(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		String url="jdbc:mysql://localhost/Chinook?user=root&password=Ma19921013";
 		Connection conn=null;
 		Statement s=null;
 		ResultSet rs=null;
 		try{
 			conn=DriverManager.getConnection(url);
 		 	s=conn.createStatement(); 
 		 	String sql="Select CustomerAccount from Customer";
 		 	rs=s.executeQuery(sql);
 		 	
 		 	while(rs.next()){
 		 		arry.add(rs.getString(1));
 		 	}
 		 	
 		}catch(SQLException ex){
 			ex.printStackTrace();
 		}finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (s != null) {
                    s.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
 		
 		for(int i=0;i<arry.size();i++){
			if(arry.add(sss)) return false;
			
		}
		return true;
	}
	 	
	 	
	 	
	}
	

