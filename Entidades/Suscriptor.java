/**
 * AMSS: Proyecto final
 * Prof. Guillermo Jimenez
 * @author Mario Sergio Fuentes Juarez
 */

import java.sql.*;
import java.util.Date;
import java.util.Vector;
 
// Clase hija de Cuenta para Suscriptores de la revista 
public class Suscriptor extends Cuenta {
    
    // Clase auxiliar de tipo Suscriptor  
    private String nombre;
    private String apellidos;
    private String email;
    private String passwd;
    private Date fechaDeCreacion;
    // private String tipoUsuario;
    
    private boolean esCorporativa;
    private boolean esEncargada;
    private boolean esNuevo;
    private String tipo; // fisico o electronico o ambos
    private Date fechaDeRenovacion;
    private Date fechaDeVencimiento;
    private String numeroCuentaPago;
    private String banco;
    private double cargaMensual;
    private String direccion;
    private int idEncargado;
    
    public Suscriptor(Conexion connect) {
        this.conn = connect.conn;
        this.stmt = connect.statem;
    }
    
    public Suscriptor(String nom, String ape, String ema, String pas, Date fechaC,
        boolean esC, boolean esE, boolean esN, String tip, Date fechaR, Date fechaV,
                String num, String ban, double carga, String dir, int idE) {
        nombre = nom;
        apellidos = ape;
        email = ema;
        passwd = pas;
        fechaDeCreacion = fechaC;
        esCorporativa = esC;
        esEncargada = esE;
        esNuevo = esN;
        tipo = tip;
        fechaDeRenovacion = fechaR;
        fechaDeVencimiento = fechaV;
        numeroCuentaPago = num;
        banco = ban;
        cargaMensual = carga;
        direccion = dir;
        idEncargado = idE;
    }
    
    // Metodos GET
    public String getNombre() {
        return nombre;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPasswd() {
        return passwd;
    }
    
    public Date getFechaDeCreacion() {
        return fechaDeCreacion;
    }
    
    public boolean getEsCorporativa() {
        return esCorporativa;
    }
    
    public boolean getEsEncargada() {
       return esEncargada;
    }
    
    public boolean getEsNuevo() {
        return esNuevo;
    }
    
    public String getTipoSuscripcion() {
        return tipo;
    }
    
    public Date getFechaDeRenovacion() {
        return fechaDeRenovacion;
    }
    
    public Date getFechaDeVencimiento() {
        return fechaDeVencimiento;
    }
    
    public String getNumeroCuentaPago() {
        return numeroCuentaPago;
    }
    
    public String getBanco() {
        return banco;
    }
    
    public double getCargaMensual() {
        return cargaMensual;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public int getIdEncargado() {
        return idEncargado;
    }
    
    // Metodos SET
    public void setNombre(String nom){
        nombre = nom;
    }
    
    public void setApellidos(String ape){
        apellidos = ape;
    }
    
    public void setEmail(String ema){
        email = ema;
    }
    
    public void setPasswd(String pas){
        passwd = pas;
    }
    
    public void setFechaDeCreacion(Date fecha){
        fechaDeCreacion = fecha;
    }
    
    public void setEsCorporativa(boolean es){
        esCorporativa = es;
    }
    
    public void setEsEncargada(boolean es){
       esEncargada = es;
    }
    
    public void setEsNuevo(boolean es){
        esNuevo = es;
    }
    
    public void setTipoSuscripcion(String tip){
        tipo = tip;
    }
    
    public void setFechaDeRenovacion(Date fecha){
        fechaDeRenovacion = fecha;
    }
    
    public void setFechaDeVencimiento(Date fecha){
        fechaDeVencimiento = fecha;
    }
    
    public void setNumeroCuentaPago(String num){
        numeroCuentaPago = num;
    }
    
    public void setBanco(String ban){
        banco = ban;
    }
    
    public void setCargaMensual(double carga){
        cargaMensual = carga;
    }
    
    public void setDireccion(String dir){
        direccion = dir;
    }
    
    public void setIdEncargado(int idE){
        idEncargado = idE;
    }
    
    // Metodos GET
    public boolean getEsCorporativa(int idcuenta) {
        boolean esC = false; 
        try {
            stmt.executeQuery ("SELECT esCorporativa FROM suscriptor WHERE idcuenta = " + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next(); //Va al registro ya validado
            esC = rs.getBoolean("esCorporativa");
            rs.close();
            return (esC);
        } catch (SQLException e) {
            System.out.println ("Cannot getEsCorporativa()" + e);
        }
        return esC;
    }
    
    public boolean getEsEncargada(int idcuenta) {
        boolean esE = false; 
        try {
            stmt.executeQuery ("SELECT esEncargada FROM suscriptor WHERE idcuenta = " + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next(); //Va al registro ya validado
            esE = rs.getBoolean("esEncargada");
            rs.close();
            return (esE);
        } catch (SQLException e) {
            System.out.println ("Cannot getEsEncargada()" + e);
        }
        return esE;
    }
    
    public boolean getEsNuevo(int idcuenta) {
        boolean esN = false; 
        try {
            stmt.executeQuery ("SELECT esNuevo FROM suscriptor WHERE idcuenta = " + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next(); //Va al registro ya validado
            esN = rs.getBoolean("esNuevo");
            rs.close();
            return (esN);
        } catch (SQLException e) {
            System.out.println ("Cannot getEsNuevo()" + e);
        }
        return esN;
    }
    
    public String getTipoSuscripcion(int idcuenta) {
        String sT = ""; 
        try {
            stmt.executeQuery ("SELECT tipo FROM suscriptor WHERE idcuenta = " + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next(); //Va al registro ya validado
            sT = rs.getString("tipo");
            rs.close();
            return (sT);
        } catch (SQLException e) {
            System.out.println ("Cannot getTipoSuscripcion()" + e);
        }
        return sT;
    }
    
    public Date getFechaDeRenovacion(int idcuenta) {
        Date dFecha = null; 
        try {
            stmt.executeQuery ("SELECT fechaDeRenovacion FROM suscriptor WHERE idcuenta = " + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next(); //Va al registro ya validado
            dFecha = rs.getDate("fechaDeRenovacion");
            rs.close();
            return (dFecha);
        } catch (SQLException e) {
            System.out.println ("Cannot getFechaDeRenovacion()" + e);
        }
        return dFecha;
    }
    
    public Date getFechaDeVencimiento(int idcuenta) {
        Date dFecha = null; 
        try {
            stmt.executeQuery ("SELECT fechaDeVencimiento FROM suscriptor WHERE idcuenta = " + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next(); //Va al registro ya validado
            dFecha = rs.getDate("fechaDeVencimiento");
            rs.close();
            return (dFecha);
        } catch (SQLException e) {
            System.out.println ("Cannot getFechaDeVencimiento()" + e);
        }
        return dFecha;
    }
    
    public String getNumeroCuentaPago(int idcuenta) {
        String sCuenta = ""; 
        try {
            stmt.executeQuery ("SELECT numeroCuentaPago FROM suscriptor WHERE idcuenta = " + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next(); //Va al registro ya validado
            sCuenta = rs.getString("numeroCuentaPago");
            rs.close();
            return (sCuenta);
        } catch (SQLException e) {
            System.out.println ("Cannot getNumeroCuentaPago()" + e);
        }
        return sCuenta;
    }
    
    public String getBanco(int idcuenta) {
        String sB = ""; 
        try {
            stmt.executeQuery ("SELECT banco FROM suscriptor WHERE idcuenta = " + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next(); //Va al registro ya validado
            sB = rs.getString("banco");
            rs.close();
            return (sB);
        } catch (SQLException e) {
            System.out.println ("Cannot getBanco()" + e);
        }
        return sB;
    }
    
    public double getCargaMensual(int idcuenta) {
        double carga = 0.0; 
        try {
            stmt.executeQuery ("SELECT cargaMensual FROM suscriptor WHERE idcuenta = " + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next(); //Va al registro ya validado
            carga = rs.getDouble("cargaMensual");
            rs.close();
            return (carga);
        } catch (SQLException e) {
            System.out.println ("Cannot getCargaMensual()" + e);
        }
        return carga;
    }
    
    public String getDireccion(int idcuenta) {
        String sDir = ""; 
        try {
            stmt.executeQuery ("SELECT direccion FROM suscriptor WHERE idcuenta = " + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next(); //Va al registro ya validado
            sDir = rs.getString("direccion");
            rs.close();
            return (sDir);
        } catch (SQLException e) {
            System.out.println ("Cannot getDireccion()" + e);
        }
        return sDir;
    }
    
    public int getIdEncargado(int idcuenta) {
        int idE = -1; 
        try {
            stmt.executeQuery ("SELECT idEncargado FROM suscriptor WHERE idcuenta = " + idcuenta);
            ResultSet rs = stmt.getResultSet();
            rs.next(); //Va al registro ya validado
            idE = rs.getInt("idEncargado");
            rs.close();
            if(idE == 0) idE = -1;
            return (idE);
        } catch (SQLException e) {
            System.out.println ("Cannot getIdEncargado()" + e);
        }
        return idE;
    }
    
    // Metodos SET
    public void setEsCorporativa(int idcuenta, boolean es){
        try {
            String s = "UPDATE suscriptor SET esCorporativa = '" + es + "' WHERE idcuenta = " + idcuenta;
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println ("Cannot execute setEsCorporativa()" + e);
        }
    }
    
    public void setEsEncargada(int idcuenta, boolean es){
        try {
            String s = "UPDATE suscriptor SET esEncargada = '" + es + "' WHERE idcuenta = " + idcuenta;
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println ("Cannot execute setEsEncargada()" + e);
        }
    }
    
    public void setEsNuevo(int idcuenta, boolean es){
        try {
            String s = "UPDATE suscriptor SET esNuevo = '" + es + "' WHERE idcuenta = " + idcuenta;
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println ("Cannot execute setEsNuevo()" + e);
        }
    }
    
    public void setTipoSuscripcion(int idcuenta, String tip){
        try {
            String s = "UPDATE suscriptor SET tipo = '" + tip + "' WHERE idcuenta = " + idcuenta;
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println ("Cannot execute setTipoSuscripcion()" + e);
        }
    }
    
    public void setFechaDeRenovacion(int idcuenta, Date fecha){
        try {
            pStmt = conn.prepareStatement(
                    "UPDATE suscriptor SET fechaDeRenovacion = ? WHERE idcuenta = ?");
            pStmt.setDate(1,new java.sql.Date(fecha.getTime()));
            pStmt.setInt(2,idcuenta);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println ("Cannot execute setFechaDeRenovacion()" + e);
        }
    }
    
    public void setFechaDeVencimiento(int idcuenta, Date fecha){
        try {
            pStmt = conn.prepareStatement(
                    "UPDATE suscriptor SET fechaDeVencimiento = ? WHERE idcuenta = ?");
            pStmt.setDate(1,new java.sql.Date(fecha.getTime()));
            pStmt.setInt(2,idcuenta);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println ("Cannot execute setFechaDeVencimiento()" + e);
        }
    }
    
    public void setNumeroCuentaPago(int idcuenta, String num){
        try {
            String s = "UPDATE suscriptor SET numeroCuentaPago = '" + num + "' WHERE idcuenta = " + idcuenta;
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println ("Cannot execute setNumeroCuentaPago()" + e);
        }
    }
    
    public void setBanco(int idcuenta, String ban){
        try {
            String s = "UPDATE suscriptor SET banco = '" + ban + "' WHERE idcuenta = " + idcuenta;
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println ("Cannot execute setBanco()" + e);
        }
    }
    
    public void setCargaMensual(int idcuenta, double carga){
        try {
            String s = "UPDATE suscriptor SET cargaMensual = " + carga + " WHERE idcuenta = " + idcuenta;
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println ("Cannot execute setCargaMensual()" + e);
        }
    }
    
    public void setDireccion(int idcuenta, String dir){
        try {
            String s = "UPDATE suscriptor SET direccion = '" + dir + "' WHERE idcuenta = " + idcuenta;
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            System.out.println ("Cannot execute setDireccion()" + e);
        }
    }
    
    public void setIdEncargado(int idcuenta, int idE){
        try {
            pStmt = conn.prepareStatement(
                "UPDATE suscriptor SET idEncargado = ? WHERE idcuenta = ?");
            if(idE != -1){
                pStmt.setInt(1,idE);
            } else {
                pStmt.setNull(1,java.sql.Types.NULL);
            }
            pStmt.setInt(2,idcuenta);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println ("Cannot execute setIdEncargado()" + e);
        }
    }
    
    // Metodo para guardar Suscriptor en base de datos
    public int guardaSuscriptor(String nom, String ape, String ema, String pas, 
            Date fecha, boolean esC, boolean esE, boolean esN, String tip,
                Date fechaR, Date fechaV, String num, String ban, double carga,
                    String dir, int idE){
        try {
            // insertar en Cuenta          
            
            int nuevoId=guardaCuenta(nom,ape,ema,pas,fecha,"Suscriptor");
            ResultSet generatedKeys = pStmt.getGeneratedKeys();
            if (null != generatedKeys && generatedKeys.next()) {
                nuevoId = generatedKeys.getInt(1);
            }
            // Se verifica que la cuenta se guardo adecuadamente
            if(nuevoId == -1){
                return -1;
            }                
            
            // insertar en Suscriptor
            pStmt = conn.prepareStatement(
                "INSERT INTO suscriptor (idcuenta,esCorporativa,esEncargada,esNuevo,tipo,fechaDeRenovacion,fechaDeVencimiento,numeroCuentaPago,banco,cargaMensual,direccion,idEncargado)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
            pStmt.setInt(1,nuevoId);
            pStmt.setBoolean(2,esC);
            pStmt.setBoolean(3,esE);
            pStmt.setBoolean(4,esN);
            pStmt.setString(5,tip);
            pStmt.setDate(6,new java.sql.Date(fechaR.getTime()));
            pStmt.setDate(7,new java.sql.Date(fechaV.getTime()));
            pStmt.setString(8,num);
            pStmt.setString(9,ban);
            pStmt.setDouble(10,carga);
            pStmt.setString(11,dir);
            if(idE != -1){
                pStmt.setInt(12,idE);
            } else {
                pStmt.setNull(12,java.sql.Types.NULL);
            }
            pStmt.executeUpdate();
            return nuevoId;
        } catch (Exception e) {
            System.out.println ("Cannot update database" + e );
            return -1;
        }   
    }
    
    // Metodo que actualiza la fecha de renovacion al pagar la suscripcion
    public boolean realizarPagoSuscripcion(int idcuenta, Date fechaR, Date fechaV){
        try {
            pStmt = conn.prepareStatement(
                "UPDATE suscriptor SET fechaDeRenovacion = ? , fechaDeVencimiento = ? WHERE idcuenta = ? or idencargado = ?");
            pStmt.setDate(1,new java.sql.Date(fechaR.getTime()));
            pStmt.setDate(2,new java.sql.Date(fechaV.getTime()));
            pStmt.setInt(3,idcuenta);
            pStmt.setInt(4,idcuenta);
            pStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println ("Cannot execute realizarPagoSuscripcion()" + e);
            return false;
        }
    }
    
    // Metodo de validacion de cuenta de pago
    // Compara el numero de cuenta y el banco con los almacenados en la cuenta
    // del usuario
    public boolean validarCuentaPago(int idcuenta, String num, String ban){
        try {
            stmt.executeQuery ("SELECT numeroCuentaPago, banco FROM suscriptor WHERE idcuenta = " + idcuenta);
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) { //Va al primer registro si lo hay
                String sNum = rs.getString ("numeroCuentaPago");
                String sBan = rs.getString ("banco");
                rs.close(); 
                return( sNum.equals(num) && sBan.equals(ban)  );
            } else {
                return false;
            }
        } catch (SQLException e) {
          System.out.println ("Cannot execute validarCuentaPago()" + e);
        }
        return false;
    }
    
    // Metodo que genera un conjunto de cuentas corporativas dependientes
    // a aprtir de una cuenta corporativa encargada
    public boolean crearCuentasCorporativasDependientes(int idcuenta,
            Vector<Suscriptor> vecSuscriptor){
        
        // validar que la cuenta recibida es corporativa y encargada
        try {
            stmt.executeQuery ("SELECT esCorporativa, esEncargada FROM suscriptor WHERE idcuenta = " + idcuenta);
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) { //Va al primer registro si lo hay
                boolean esC = rs.getBoolean ("esCorporativa");
                boolean esE = rs.getBoolean ("esEncargada");
                rs.close(); 
                if ( !(esC && esE) ){
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
          System.out.println ("Cannot execute crearCuentasCorporativasDependientes()" + e);
        }
        
        Suscriptor sAux;
        for (int i=0; i<vecSuscriptor.size(); i++){
            sAux = vecSuscriptor.get(i);
            if(guardaSuscriptor(sAux.getNombre(),sAux.getApellidos(),sAux.getEmail(), sAux.getPasswd(),
                sAux.getFechaDeCreacion(), sAux.getEsCorporativa(),
                    sAux.getEsEncargada(), sAux.getEsNuevo(), sAux.getTipoSuscripcion(),
                        sAux.getFechaDeRenovacion(), sAux.getFechaDeVencimiento(),
                                sAux.getNumeroCuentaPago(),sAux.getBanco(),
                                    sAux.getCargaMensual(), sAux.getDireccion(),
                                        idcuenta) == -1){
                return false;
            }
        } 
        return true;
    }
    
    // Metodo que devuelve los IDs de todas las ordenes vinculadas
    // a una cuenta de tipo Suscriptor
    public Vector<Integer> obtenerOrdenes(int idcuenta){
        Vector<Integer> vecIds = new Vector<Integer>();
        int aux;
        try {
            ResultSet rs = stmt.executeQuery ("SELECT idOrden FROM orden WHERE idsuscriptor = " + idcuenta);
            while( rs.next() ){
                aux = rs.getInt(1);
                vecIds.add(aux);
            }
            return vecIds;
        } catch (SQLException e) {
          System.out.println ("Cannot execute obtenerOrdenes()" + e);
        }        
        return null;
    }
    
    // Metodo que devuelve los IDs de todas las cuentas dependientes
    // a una cuenta corporativa encargada de tipo Suscriptor
    public Vector<Integer> obtenerCuentasDependientes(int idcuenta){
        Vector<Integer> vecIds = new Vector<Integer>();
        int aux;
        try {
            ResultSet rs = stmt.executeQuery ("SELECT idcuenta FROM suscriptor WHERE idencargado = " + idcuenta);
            while( rs.next() ){
                aux = rs.getInt(1);
                vecIds.add(aux);
            }
            return vecIds;
        } catch (SQLException e) {
          System.out.println ("Cannot execute obtenerCuentasDependientes()" + e);
        }        
        return null;
    }
    
}