/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *
 * @author E15
 */
public class Conexion {
   public Connection conn;
   public Statement statem;

   Conexion(){
      try {
        String userName = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/revista?zeroDateTimeBehavior=convertToNull";
        Class.forName ("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection (url, userName, password);    
        statem = conn.createStatement();
      }catch (Exception e) { System.out.println ("Cannot connect to database server"); }
   }
}
