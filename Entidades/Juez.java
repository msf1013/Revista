/**
 * Analisis y modelacion de sistemas de software: Proyecto final
 * Prof. Guillermo Jimenez
 * Equipo #4   
 * @authors Luis Mario Diaz, Humberto Makoto Morimoto,
 * Eduardo Zardain, Mario Sergio Fuentes
 */

package entidades;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

// Subclase de Cuenta para cuentas de Jueces
public class Juez extends Cuenta {
    //Atributos de conexion
    Connection conn;
    Statement stmt;
    PreparedStatement pStmt;
    
    
    // Metodo constructor con conexion
    public Juez(Conexion connect) {
        this.conn = connect.conn;
        this.stmt = connect.statem;
    }
    
    public ArrayList selectJueces(){
        
        ArrayList<CuentaAuxiliar> cuentas = new ArrayList<CuentaAuxiliar>();
        
        try{
            stmt.executeQuery("SELECT * FROM cuenta WHERE tipoUsuario = 'Juez'");
            ResultSet rs = stmt.getResultSet();
            
            while(rs.next()){
                CuentaAuxiliar cuentaAux = new CuentaAuxiliar();
                
                cuentaAux.setIdCuenta(rs.getInt("idCuenta"));
                cuentaAux.setNombre(rs.getString("nombre"));
                cuentaAux.setApellidos(rs.getString("apellidos"));
                cuentaAux.setEmail(rs.getString("email"));
                cuentaAux.setPasswd(rs.getString("passwd"));
                cuentaAux.setTipoUsuario(rs.getString("tipoUsuario"));
                
                cuentas.add(cuentaAux);
            }
        }
        catch(SQLException e){
            return null;
        }
        return cuentas;
    }
    
    //Getters
    public int getNumeroJuez(int idJuez) {
        int iNumJuez = 0;
        try {
            stmt.executeQuery("SELECT numJuez FROM juez WHERE idjuez = " 
                + idJuez);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            iNumJuez = rs.getInt("numJuez");
            rs.close();

            return(iNumJuez); 
        } catch (SQLException e) {
            System.out.println("Cannot getNumeroJuez()" + e);
        }
        return iNumJuez;
    }

    //Setters
    public void setNumeroJuez(int idJuez, int numJuez) {
        try {
            String sqlString = "UPDATE juez SET numJuez = '" + numJuez + 
                    "' WHERE idorden = " + idJuez;
            stmt.executeUpdate(sqlString);

        } catch(SQLException e) {
            System.out.println("Cannot execute setNumeroJuez()" + e);
        }
    }

    public Vector<Integer> obtenerArticulosPendientes(int idJuez) {
        Vector<Integer> vecArticulos = new Vector<Integer>();
        int aux;
        try {
            ResultSet rs = stmt.executeQuery("SELECT idArticuloPendiente FROM ArticuloPendiente " +
                "WHERE idArticuloPendiente not in (SELECT idArticuloPendiente FROM Votos join Juez WHERE idJuez = " + idJuez +")");

            while(rs.next()) {
                aux = rs.getInt(1);
                vecArticulos.add(aux);
            }
            return vecArticulos;
        } catch(SQLException e) {  
            System.out.println("Cannot execute obtenerArticulosPendientes()" + e);
        }
        return vecArticulos;
    }
    
    // Metodo para guardar Juez en base de datos.
    // Recibe como parametros todos los atributos de la clase
    public int guardaJuez(String nom, String ape, String ema, String pas, 
            Date fecha, int numJuez){
            String tipJuez = "Juez";
        try {
            pStmt = conn.prepareStatement(
                "INSERT INTO cuenta (nombre,apellidos,email,passwd,fechaDeCreacion,tipoUsuario)" +
                    " VALUES (?, ?, ?, ?, ?, ?) ",new String[] { "idcuenta" });
            pStmt.setString(1,nom);
            pStmt.setString(2,ape);
            pStmt.setString(3,ema);
            pStmt.setString(4,pas);
            pStmt.setDate(5,new java.sql.Date(fecha.getTime()));
            pStmt.setString(6,tipJuez);
            
            pStmt.executeUpdate();
            
            // obtener ID de cuenta insertada
            int nuevoId=-1;
            ResultSet generatedKeys = pStmt.getGeneratedKeys();
            if (null != generatedKeys && generatedKeys.next()) {
                nuevoId = generatedKeys.getInt(1);
            }        
            
            System.out.println(nuevoId);
            
            // Insertar en Juez
            pStmt = conn.prepareStatement(
                "INSERT INTO juez (idCuenta,numJuez)" +
                    " VALUES (?, ?) ");
            pStmt.setInt(1,nuevoId);
            pStmt.setInt(2,numJuez);
            pStmt.executeUpdate();
            return nuevoId;
        } catch (Exception e) {
            System.out.println ("Cannot update Juez" + e );
            return -1;
        }   
    }
    
}

