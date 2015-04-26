/**
 * Analisis y modelacion de sistemas de software: Proyecto final
 * Prof. Guillermo Jimenez
 * Equipo #4   
 * @authors Luis Mario Diaz, Humberto Makoto Morimoto,
 * Eduardo Zardain, Mario Sergio Fuentes
 */

package entidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

// Clase que modela un objeto de conexion con una base de datos de MySQL
// a raves del conector JDBC
public class Conexion {
    // atributos de conexion
    public Connection conn;
    public Statement statem;
    
    // Constructor
    public Conexion(){
        try {
            String userName = "root";
            String password = "";
            // direccion de base de datos en entorno de pruebas
            String url = 
                    "jdbc:mysql://localhost:3306/revista?zeroDateTimeBehavior=convertToNull";
            Class.forName ("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection (url, userName, password);    
            statem = conn.createStatement();
        }catch (Exception e) {
            System.out.println ("Cannot connect to database server");
        }
    }    
}
