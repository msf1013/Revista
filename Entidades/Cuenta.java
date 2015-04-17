/**
 * Analisis y modelacion de sistemas de software: Proyecto final
 * Prof. Guillermo Jimenez
 * Equipo #4   
 * @authors Luis Mario Diaz, Humberto Makoto Morimoto,
 * Eduardo Zardain, Mario Sergio Fuentes
 */

import java.sql.*;
import java.util.Date;

// Clase base de cuentas de usuario
public class Cuenta {
    
    // Atributos para conexion con base de datos
    Connection conn;
    Statement stmt;
    PreparedStatement pStmt;
    
    // Metodo constructor con conexion
    public Cuenta(Conexion connect){
        this.conn = connect.conn;
        this.stmt = connect.statem;
    }
    
    // Metodo constructor vacio
    public Cuenta(){
      
    }
    
    // Metodos GET con acceso directo a base de datos
    public String getNombre(int idcuenta) {
        String sNombre = ""; 
        try {
            stmt.executeQuery("SELECT nombre FROM cuenta WHERE idcuenta = " 
                    + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            sNombre = rs.getString("nombre");
            rs.close();
            return (sNombre);
        } catch (SQLException e) {
            System.out.println ("Cannot getNombre()" + e);
        }
        return sNombre;
    }
    
    public String getApellidos(int idcuenta) {
        String sApellidos = ""; 
        try {
            stmt.executeQuery ("SELECT apellidos FROM cuenta WHERE idcuenta = " 
                    + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            sApellidos = rs.getString("apellidos");
            rs.close();
            return (sApellidos);
        } catch (SQLException e) {
            System.out.println ("Cannot getApellidos()" + e);
        }
        return sApellidos;
    }
    
    public String getEmail(int idcuenta) {
        String sEmail = ""; 
        try {
            stmt.executeQuery ("SELECT email FROM cuenta WHERE idcuenta = " 
                    + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next(); 
            sEmail = rs.getString("email");
            rs.close();
            return (sEmail);
        } catch (SQLException e) {
            System.out.println ("Cannot getEmail()" + e);
        }
        return sEmail;
    }
    
    public String getPasswd(int idcuenta) {
        String sPasswd = ""; 
        try {
            stmt.executeQuery ("SELECT passwd FROM cuenta WHERE idcuenta = " 
                    + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            sPasswd = rs.getString("passwd");
            rs.close();
            return (sPasswd);
        } catch (SQLException e) {
            System.out.println ("Cannot getPasswd()" + e);
        }
        return sPasswd;
    }
    
    public Date getFechaDeCreacion(int idcuenta) {
        Date dFecha = null; 
        try {
            stmt.executeQuery ("SELECT fechaDeCreacion FROM cuenta WHERE idcuenta = " 
                    + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next(); 
            dFecha = rs.getDate("fechaDeCreacion");
            rs.close();
            return (dFecha);
        } catch (SQLException e) {
            System.out.println ("Cannot getFechaDeCreacion()" + e);
        }
        return dFecha;
    }
    
    public String getTipoUsuario(int idcuenta) {
        String sTipo = ""; 
        try {
            stmt.executeQuery ("SELECT tipoUsuario FROM cuenta WHERE idcuenta = " 
                    + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            sTipo = rs.getString("tipoUsuario");
            rs.close();
            return (sTipo);
        } catch (SQLException e) {
            System.out.println ("Cannot getTipoUsuario()" + e);
        }
        return sTipo;
    }
    
    // Metodos SET con acceso directo a base de datos
    public void setNombre(int idcuenta, String nom){
        try {
            String s = "UPDATE cuenta SET nombre = '" + nom + "' WHERE idcuenta = " 
                    + idcuenta;
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println ("Cannot execute setNombre()" + e);
        }
    }
    
    public void setApellidos(int idcuenta, String ape){
        try {
            String s = "UPDATE cuenta SET apellidos = '" + ape + "' WHERE idcuenta = " 
                    + idcuenta;
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println ("Cannot execute setApellidos()" + e);
        }
    }
    
    public void setEmail(int idcuenta, String ema){
        try {
            String s = "UPDATE cuenta SET email = '" + ema + "' WHERE idcuenta = " 
                    + idcuenta;
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println ("Cannot execute setEmail()" + e);
        }
    }
    
    public void setPasswd(int idcuenta, String pas){
        try {
            String s = "UPDATE cuenta SET passwd = '" + pas + "' WHERE idcuenta = " 
                    + idcuenta;
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println ("Cannot execute setPasswd()" + e);
        }
    }
    
    public void setFechaDeCreacion(int idcuenta, Date fecha){
        try {
            pStmt = conn.prepareStatement(
                    "UPDATE cuenta SET fechaDeCreacion = ? WHERE idcuenta = ?");
            pStmt.setDate(1,new java.sql.Date(fecha.getTime()));
            pStmt.setInt(2,idcuenta);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println ("Cannot execute setFechaDeCreacion()" + e);
        }
    }
    
    public void setTipoUsuario(int idcuenta, String sTipo){
        try {
            String s = "UPDATE cuenta SET tipoUsuario = '" 
                    + sTipo + "' WHERE idcuenta = " + idcuenta;
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println ("Cannot execute setTipoUsuario()" + e);
        }
    }
    
    // Metodo de validacion de existencia de cuenta a partir de un
    // email y passwd dados, que devuelve el id (llave primaria) de la cuenta
    public int validarCuenta(String ema, String pas){
        int idcuenta = -1;
        try {
            stmt.executeQuery("SELECT idcuenta, passwd FROM cuenta WHERE email = '" 
                    + ema +"'");
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) { //Va al primer registro si lo hay
                idcuenta = rs.getInt("idcuenta");
                String sPasswd = rs.getString ("passwd");
                rs.close(); 
                if( sPasswd.equals(pas) ){
                    return idcuenta;
                } else {
                    return -1;
                }
            } else {
                return -1; // devuelve -1 como valor de error default
            }
        } catch (SQLException e) {
          System.out.println ("Cannot execute validarCuenta()" + e);
        }
        return -1;
    }
    
    // Metodo para guardar Cuenta en base de datos.
    // Recibe como parametros todos los atributos de Cuenta.
    // Devuelve el id de la Cuenta guardada, y -1 si la operacion
    // no se pudo realizar.
    public int guardaCuenta(String nom, String ape, String ema, String pas, 
            Date fecha, String tip){
        try {
                        
            pStmt = conn.prepareStatement(
                "INSERT INTO cuenta (nombre,apellidos,email,passwd,fechaDeCreacion,tipoUsuario)" +
                    " VALUES (?, ?, ?, ?, ?, ?) ",new String[] { "idcuenta" });
            pStmt.setString(1,nom);
            pStmt.setString(2,ape);
            pStmt.setString(3,ema);
            pStmt.setString(4,pas);
            pStmt.setDate(5,new java.sql.Date(fecha.getTime()));
            pStmt.setString(6,tip);
            
            pStmt.executeUpdate();
            
            // obtener ID de cuenta insertada
            int nuevoId=-1;
            ResultSet generatedKeys = pStmt.getGeneratedKeys();
            if (null != generatedKeys && generatedKeys.next()) {
                nuevoId = generatedKeys.getInt(1);
            }
            return nuevoId;
            
        } catch (SQLException e) {
            System.out.println ("Cannot update database" + e );
            return -1;
        }   
    }  
        
}

