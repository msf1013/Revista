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

    /*Obtener Articulos pendientes

    select idArticuloPendiente
    from ArticuloPendiente
    where idArticuloPendiente not in (
        select idArticuloPendiente
        from Votos join Juez
        where idJuez = 13
    )*/

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
        try {
            // Insertar en Cuenta         
            int nuevoId=guardaCuenta(nom,ape,ema,pas,fecha,"Juez");
            ResultSet generatedKeys = pStmt.getGeneratedKeys();
            if (null != generatedKeys && generatedKeys.next()) {
                nuevoId = generatedKeys.getInt(1);
            }
            // Se verifica que la cuenta se guardo adecuadamente
            if(nuevoId == -1){
                return -1;
            }                
            
            // Insertar en Juez
            pStmt = conn.prepareStatement(
                "INSERT INTO Juez (idcuenta,numJuez)" +
                    " VALUES (?, ?) ");
            pStmt.setInt(1,nuevoId);
            pStmt.setInt(2,numJuez);
            pStmt.executeUpdate();
            return nuevoId;
        } catch (Exception e) {
            System.out.println ("Cannot update database" + e );
            return -1;
        }   
    }
    
}

