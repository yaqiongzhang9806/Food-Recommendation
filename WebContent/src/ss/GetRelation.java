package ss;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetRelation {
    
    private int count = 0;
    public double relativity = 0;
    private ArrayList<Integer> u1 = new ArrayList<Integer>();
    private ArrayList<Integer> u2 = new ArrayList<Integer>();
    private int in1;
    private int in2;
    
    public GetRelation(int q, int w) {
    	this.in1 = q;
    	this.in2 = w;
    	start();
    }
    	
    public void start() {
    
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String url="jdbc:mysql://localhost/Chinook?user=root&password=Ma19921013";

        try {
            
            con = DriverManager.getConnection(url);
            pst = con.prepareStatement("select tag.* from(select r.* from rating r where r.Dish_Id in (select r2.Dish_Id from Rating r2 where r2.Customer_Id = "+ 
            in1 
            +") and r.R_Id in (select r3.R_Id from Rating r3 where r3.Customer_Id = "+ 
            in1 
            +") and (r.Customer_Id = "+ 
            in1 
            +" or r.Customer_Id = "+ 
            in2 
            +"))as tag inner join (select r.Dish_Id, r.R_Id from rating r where r.Dish_Id in (select r2.Dish_Id from Rating r2 where r2.Customer_Id = "+ 
            in1 
            +") and r.R_Id in (select r3.R_Id from Rating r3 where r3.Customer_Id = "+ 
            in1 
            +") and (r.Customer_Id = "+ 
            in1 
            +" or r.Customer_Id = "+ 
            in2 
            +") group by Dish_Id, R_Id Having Count(*) > 1) as gag on tag.Dish_Id=gag.Dish_Id and tag.R_Id=gag.R_Id");
            rs = pst.executeQuery();
            count = (rs.last() ? rs.getRow() : 0)/2;
            
            rs.beforeFirst();
            for (int i=0; i<count; i++) {
            	rs.next();
            	rs.next();
            	u1.add(Integer.parseInt(rs.getString(4)));
            }
            
            rs.beforeFirst();
            rs.next();
            for (int i=0; i<count; i++) {
            	u2.add(Integer.parseInt(rs.getString(4)));
            	rs.next();
            	rs.next();
            }
            
            for (int i=0; i<count; i++) {
            	relativity += (5.0 - Math.abs(u1.get(i)-u2.get(i)))/5.0;
            }
            relativity /= count;
//            System.out.println(relativity);
            
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
