/**
 * Analisis y modelacion de sistemas de software: Proyecto final
 * Prof. Guillermo Jimenez
 * Equipo #4   
 * @authors Luis Mario Diaz, Humberto Makoto Morimoto,
 * Eduardo Zardain, Mario Sergio Fuentes
 */

import java.sql.*;
import java.util.Date;
import java.util.Vector;

// Subclase de Cuenta para cuentas de Suscriptores
public class Escritor extends Cuenta {
    
    // Metodo constructor con conexion
    public Escritor(Conexion connect){
        this.conn = connect.conn;
        this.stmt = connect.statem;
    }
    
    // Metodos GET con conexion directa a base de datos
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
    
    // Metodos SET con conexion directa a base de datos
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
    
    // Metodo para guardar Escritor en base de datos.
    // Recibe como parametros todos los atributos de la clase Escritor.
    public int guardaEscritor(String nom, String ape, String ema, String pas, 
            Date fechaC, Date fechaU){
        try {
            
            // Insertar en Cuenta y obtener ID generado         
            int nuevoId=guardaCuenta(nom,ape,ema,pas,fechaC,"Escritor");
            ResultSet generatedKeys = pStmt.getGeneratedKeys();
            if (null != generatedKeys && generatedKeys.next()) {
                nuevoId = generatedKeys.getInt(1);
            }
            
            // Si no se pudo insertar Cuenta,
            // se devuelve -1 como valor default
            if(nuevoId == -1){
                return -1;
            }                
            System.out.println("id: " + nuevoId);
            
            // Insertar en tabla de Suscriptor
            pStmt = conn.prepareStatement(
                "INSERT INTO escritor (idcuenta,fechaUltimaPublicacion)" +
                    " VALUES (?, ?) ");
            pStmt.setInt(1,nuevoId);
            pStmt.setDate(2,new java.sql.Date(fechaU.getTime()));
            pStmt.executeUpdate();
            // Devolver ID generado
            return nuevoId;
        } catch (Exception e) {
            System.out.println ("Cannot update database" + e );
            // Devolver valor default de error
            return -1;
        }   
    }
        
    // Metodo que devuelve los IDs de todos los articulos pendientes
    // creados por una cuenta Escritor
    public Vector<Integer> obtenerArticulosPendientes(int idcuenta){
        Vector<Integer> vecIds = new Vector<Integer>();
        int aux;
        try {
            ResultSet rs = stmt.executeQuery ("SELECT idArticuloPendiente FROM articulopendiente WHERE idescritor = " + idcuenta);
            // Iteracion en result set
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