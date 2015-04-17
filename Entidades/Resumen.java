/**
 * Analisis y modelacion de sistemas de software: Proyecto final
 * Prof. Guillermo Jimenez
 * Equipo #4   
 * @authors Luis Mario Diaz, Humberto Makoto Morimoto,
 * Eduardo Zardain, Mario Sergio Fuentes
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Clase que modela los Resumenes vinculados a Articulos
public class Resumen {
    //atributos para la conexion a la BD
    Connection conn;
    Statement stmt;
    PreparedStatement pStmt;
    
    public Resumen(Conexion connect){
        this.conn = connect.conn;
        this.stmt = connect.statem;
    }
    
    //getters
    public String getResumen(int idResumen) {
        String sNombre = ""; 
        try {
            stmt.executeQuery ("SELECT textoResumen FROM resumen WHERE idresumen = " + idResumen);
            ResultSet rs = stmt.getResultSet();
            rs.next(); //Va al registro ya validado
            sNombre = rs.getString("textoResumen");
            rs.close();
            return (sNombre);
        } catch (SQLException e) {
            System.out.println ("Cannot getResumen()" + e);
        }
        return sNombre; 
    }
    
    //setters
    public void setResumen(int idResumen, String sResumen) {
        try {
            String sqlString = "UPDATE resumen SET textoResumen = '" + sResumen + 
                    "' WHERE idresumen = " + idResumen;
            stmt.executeUpdate(sqlString);
        } catch(SQLException e) {
            System.out.println("Cannot execute setResumen()" + e);
        }
    }
    
    //metodo para guardar el resumen
    public int guardaResumen(String textoResumen) {
        try {
            pStmt = conn.prepareStatement("INSERT INTO resumen (textoResumen)" +
                    " VALUES(?) ", new String[] { "idresumen" });
            pStmt.setString(1, textoResumen);
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
    
}
