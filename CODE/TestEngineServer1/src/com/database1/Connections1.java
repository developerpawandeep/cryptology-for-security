package com.database1;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;

public class Connections1 {
   public static Connection c;
    public static PreparedStatement pst;
    
    
   public static PreparedStatement makeConnection(String sql)
    {  
        try
        {
        Class.forName("com.mysql.jdbc.Driver");
        c=DriverManager.getConnection("jdbc:mysql://localhost:3306/testengine","root","123");
        pst=c.prepareStatement(sql);
        
          }
        catch(Exception e)
        {
        }
        return pst;
    }
}
