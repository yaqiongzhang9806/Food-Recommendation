package ss;

import java.util.HashMap;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuildRelation {
	
	public HashMap<Integer, Double> relaTable = new HashMap<Integer, Double>();
	private ArrayList<Integer> others = new ArrayList<Integer>();
	private int cCount = 0;
	private int input;
    
    public BuildRelation(int in) {
    	this.input = in;
    	build();
    }
    
    public void build() {
    	
    	Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String url="jdbc:mysql://localhost/Chinook?user=root&password=Ma19921013";
        
        try {
            
            con = DriverManager.getConnection(url);
            pst = con.prepareStatement("select Customer_Id from Customer");
            rs = pst.executeQuery();
            cCount = rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            
            for (int i=0; i<cCount; i++) {
            	rs.next();
            	if (rs.getInt(1) != input) {
            		others.add(rs.getInt(1));
            	}
            }
            
            for (int i=0; i<others.size(); i++) {
            	GetRelation test = new GetRelation(input, others.get(i));
            	relaTable.put(others.get(i), test.relativity);
            }
//            System.out.println(relaTable);
            
            
        } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(GetRelation.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(GetRelation.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    	
    }
}
