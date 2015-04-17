/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MarioDiaz
 */

import java.sql.*;
import java.util.Date;
import java.util.Vector;

public class Revista {
    //Atributos para conexion a la base de datos
    Connection conn;
    Statement stmt;
    PreparedStatement pStmt;
    
    //Metodo constructor
    public Revista(Conexion connect){
        this.conn = connect.conn;
        this.stmt = connect.statem;
    }
    
    //Metodos get
    public Date getFechaDePublicacion(int idRevista) {
        Date dFechaPublicacion = null;
        
        try {
            stmt.executeQuery("SELECT fechaPublicacion FROM revista WHERE idRevista = "
            + idRevista);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            dFechaPublicacion = rs.getDate("fechaPublicacion");
            rs.close();
            return(dFechaPublicacion);
            
        } catch(SQLException e) {
            System.out.println("Cannot getFechaDePublicacion()" + e);
        }
        return dFechaPublicacion;  
    }

    public int getIdRevista(Date fechaRevista) {
        int iIdRevista = -1;
        try {
            stmt.executeQuery("SELECT idRevista FROM revista WHERE fechaPublicacion = " 
                + fechaRevista);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            iIdRevista = rs.getInt("idRevista");
            rs.close();
            return(iIdRevista);
        } catch (SQLException e) {
            System.out.println("Cannot getIdRevista()" + e);
        }
        return iIdRevista;
    }
    
    public String getCartaDeEditor(int idRevista) {
        String sCartaEditor = "";
        try {
            stmt.executeQuery("SELECT cartaEditor FROM revista WHERE idrevista = "
            + idRevista);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            sCartaEditor = rs.getString("cartaEditor");
            rs.close();
            return(sCartaEditor);
        } catch(SQLException e) {
            System.out.println("cannot getCardaDeEditor()" + e);
        }
        return sCartaEditor;
        
    }
    
    public void setCartaDeEditor(int idRevista, String sCarta) {
        try {
            String sqlString = "UPDATE revista SET cartaEditor = '" + sCarta + 
                    "' WHERE idrevista = " + idRevista;
            stmt.executeUpdate(sqlString);
        } catch(SQLException e) {
            System.out.println("Cannot execute setCartadeEditor()" + e);
        }
    }
    
    public int guardaRevista(Date fecha, String cartaEditor) {
        try {
            pStmt = conn.prepareStatement("INSERT INTO revista (fechaPublicacion,cartaEditor)" +
                    " VALUES(?, ?) ", new String[] { "idrevista" });
            pStmt.setDate(1, new java.sql.Date(fecha.getTime()));
            pStmt.setString(2, cartaEditor);
            pStmt.executeUpdate();;
            
            //Sacar el id de la revista
            int idRev = -1;
            ResultSet generatedKeys = pStmt.getGeneratedKeys();
            if(null != generatedKeys && generatedKeys.next()) {
                idRev = generatedKeys.getInt(1);
            }
            return idRev;
        } catch(Exception e){
            System.out.println("Cannot update database" + e);
            return -1;
        }
    }
    
    //Metodos setter
    
}
