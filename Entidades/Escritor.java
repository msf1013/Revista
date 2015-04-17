/**
 * AMSS: Proyecto final
 * Prof. Guillermo Jimenez
 * @author Mario Sergio Fuentes Juarez
 */

import java.sql.*;
import java.util.Date;
import java.util.Vector;

// Clase hija de Cuenta para Escritores de la revista
public class Escritor extends Cuenta {
    
    // metodo constructor
    public Escritor(Conexion connect){
        this.conn = connect.conn;
        this.stmt = connect.statem;
    }
    
    // Metodos GET
    public Date getFechaUltimaPublicacion(int idcuenta) {
        Date dFecha = null; 
        try {
            stmt.executeQuery ("SELECT fechaUltimaPublicacion FROM escritor WHERE idcuenta = " + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next(); //Va al registro ya validado
            dFecha = rs.getDate("fechaUltimaPublicacion");
            rs.close();
            return (dFecha);
        } catch (SQLException e) {
            System.out.println ("Cannot getFechaUltimaPublicacion()" + e);
        }
        return dFecha;
    }
    
    // Metodos SET
    public void setFechaUltimaPublicacion(int idcuenta, Date fecha){
        try {
            pStmt = conn.prepareStatement(
                    "UPDATE escritor SET fechaUltimaPublicacion = ? WHERE idcuenta = ?");
            pStmt.setDate(1,new java.sql.Date(fecha.getTime()));
            pStmt.setInt(2,idcuenta);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println ("Cannot execute setFechaUltimaPublicacion()" + e);
        }
    }
    
    // Metodo para guardar Escritor en base de datos
    public int guardaEscritor(String nom, String ape, String ema, String pas, 
            Date fechaC, Date fechaU){
        try {
            // insertar en Cuenta          
            
            int nuevoId=guardaCuenta(nom,ape,ema,pas,fechaC,"Escritor");
            ResultSet generatedKeys = pStmt.getGeneratedKeys();
            if (null != generatedKeys && generatedKeys.next()) {
                nuevoId = generatedKeys.getInt(1);
            }
            
            if(nuevoId == -1){
                return -1;
            }                
            System.out.println("id: " + nuevoId);
            // insertar en Suscriptor
            pStmt = conn.prepareStatement(
                "INSERT INTO escritor (idcuenta,fechaUltimaPublicacion)" +
                    " VALUES (?, ?) ");
            pStmt.setInt(1,nuevoId);
            pStmt.setDate(2,new java.sql.Date(fechaU.getTime()));
            
            pStmt.executeUpdate();
            return nuevoId;
        } catch (Exception e) {
            System.out.println ("Cannot update database" + e );
            return -1;
        }   
    }
        
    // Metodo que devuelve los IDs de todos los articulos pendientes
    // creador por una cuenta Escritor
    public Vector<Integer> obtenerArticulosPendientes(int idcuenta){
        Vector<Integer> vecIds = new Vector<Integer>();
        int aux;
        try {
            ResultSet rs = stmt.executeQuery ("SELECT idArticuloPendiente FROM articulopendiente WHERE idescritor = " + idcuenta);
            while( rs.next() ){
                aux = rs.getInt(1);
                vecIds.add(aux);
            }
            return vecIds;
        } catch (SQLException e) {
          System.out.println ("Cannot execute obtenerArticulosPendientes()" + e);
        }        
        return null;
    }
    
}