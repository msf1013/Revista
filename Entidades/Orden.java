import java.sql.*;
import java.util.Date;
import java.util.Vector;
/**
 *
 * @author MarioDiaz
 */
public class Orden {
    //Atributos de conexion
    Connection conn;
    Statement stmt;
    PreparedStatement pStmt;
    
    
    //Metodo constructor
    public Orden(Conexion connect){
        this.conn = connect.conn;
        this.stmt = connect.statem;
    }
    
    //Getters
    public Date getFechaDeOrden(int idOrden){
        Date dFechaOrden = null;
        try {
            stmt.executeQuery ("SELECT fechaDeOrden FROM orden WHERE idorden = " 
                    + idOrden);
            ResultSet rs = stmt.getResultSet();
            rs.next(); //Va al registro ya validado
            dFechaOrden = rs.getDate("fechaDeOrden");
            rs.close();
            return (dFechaOrden);
        } catch (SQLException e) {
            System.out.println ("Cannot getFechaDeOrden()" + e);
        }
        return dFechaOrden;
    }
    
    public double getCargoTotal(int idOrden) {
        double doCargoTotal = 0;
        try {
            stmt.executeQuery("SELECT cargoTotal FROM orden WHERE idorden = " 
                    + idOrden);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            doCargoTotal = rs.getDouble("cargoTotal");
            rs.close();
            return(doCargoTotal);
            
        } catch (SQLException e) {
            System.out.println("Cannot getCargoTotal()" + e);
        }
        return doCargoTotal;
    }
    
    public int getOrdenCompletada(int idOrden) {
        int iOrdenCompletada = 0;
        try {
            stmt.executeQuery("SELECT ordenCompletada FROM orden WHERE idorden = " 
                    + idOrden);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            iOrdenCompletada = rs.getInt("ordenCompletada");
            rs.close();
            return(iOrdenCompletada);
            
        } catch (SQLException e) {
            System.out.println("Cannot getOrdenCompletada()" + e);
        }
        return iOrdenCompletada;
    }
    
    public int getOrdenEnviada(int idOrden) {
        int iOrdenEnv = 0;
        try {
            stmt.executeQuery("SELECT ordenEnviada FROM orden WHERE idorden = " 
                    + idOrden);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            iOrdenEnv = rs.getInt("ordenEnviada");
            rs.close();
            return(iOrdenEnv);
            
        } catch (SQLException e) {
            System.out.println("Cannot getOrdenCompletada()" + e);
        }
        return iOrdenEnv;
    }
    
    public int getNumUnidades(int idOrden) {
        int iNumUni = 0;
        try {
            stmt.executeQuery("SELECT numUnidades FROM orden WHERE idorden = " 
                    + idOrden);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            iNumUni = rs.getInt("numUnidades");
            rs.close();
            return(iNumUni);
            
        } catch (SQLException e) {
            System.out.println("Cannot getOrdenCompletada()" + e);
        }
        return iNumUni;
    }
    public int getIdSuscriptor(int idOrden) {
        int idSus = 0;
        try {
            stmt.executeQuery("SELECT idSuscriptor FROM orden WHERE idorden = " 
                    + idOrden);
            ResultSet rs = stmt.getResultSet();
            rs.next();
            idSus = rs.getInt("idSuscriptor");
            rs.close();
            return(idSus);
            
        } catch (SQLException e) {
            System.out.println("Cannot getOrdenCompletada()" + e);
        }
        return idSus;
    }
    
    //Metodos set
    public void setCargoTotal(int idOrden, int cargo) {
        try {
            String sqlString = "UPDATE orden SET cargoTotal = '" + cargo + 
                    "' WHERE idorden = " + idOrden;
            stmt.executeUpdate(sqlString);
        } catch(SQLException e) {
            System.out.println("Cannot exceute setCargoTotal()" + e);
        }
    }
    
    public void setOrdenCompletada(int idOrden, int completada) {
        try {
            String sqlString = "UPDATE orden SET ordenCompletada = '" + completada + 
                    "' WHERE idorden = " + idOrden;
            stmt.executeUpdate(sqlString);
        } catch(SQLException e) {
            System.out.println("Cannot exceute setOrdenCompletada()" + e);
        }
    }
    
    public void setOrdenEnviada(int idOrden, int enviada) {
        try {
            String sqlString = "UPDATE orden SET ordenEnviada = '" + enviada + 
                    "' WHERE idorden = " + idOrden;
            stmt.executeUpdate(sqlString);
        } catch(SQLException e) {
            System.out.println("Cannot exceute setOrdenEnviada()" + e);
        }
    }
    
    public void setNumUnidades(int idOrden, int uni) {
        try {
            String sqlString = "UPDATE orden SET numUnidades = '" + uni + 
                    "' WHERE idorden = " + idOrden;
            stmt.executeUpdate(sqlString);
        } catch(SQLException e) {
            System.out.println("Cannot exceute setNumUnidades()" + e);
        }
    }
    
    public void setiDSuscriptor(int idOrden, int idSus) {
        try {
            String sqlString = "UPDATE orden SET idSuscriptor = '" + idSus + 
                    "' WHERE idorden = " + idOrden;
            stmt.executeUpdate(sqlString);
        } catch(SQLException e) {
            System.out.println("Cannot exceute setIdSuscriptor()" + e);
        }
    }
    
    //Metodo para calcular el cargo de la orden
    private double calculaCargo() {
        double cargo = 0;
        
        return cargo; 
    }


    //Metodo para guardar las revistas del pedido
    private void guardaRevistaOrden(Vector<Date> vecFecha, Vector<Integer> vecCant, int idOrden) {
        Revista revista = new Revista(new Conexion());

        try {

            for(int i=0; i<vecFecha.size(); i++){

                int unidadesRevista = vecCant.get(i);
                Date fechaRevista = vecFecha.get(i);
                int idRevista = revista.getIdRevista(fechaRevista);

                pStmt = conn.prepareStatement(
                    "INSERT INTO revistaOrden (idRevista, idOrden, cantidad)" +
                    " VALUES (?,?,?)");
                pStmt.setInt(1,idRevista);
                pStmt.setInt(2,idOrden);
                pStmt.setInt(3,unidadesRevista);

                pStmt.executeUpdate();
            }

        } catch(SQLException e) {
            System.out.println("Error guardando las revistas" + e);
        }
    }
    
    
    //Metodo que guarda la orden y regresa el idOrden
    public int guardaOrden(double cargo, int ordComp, int numUni, int idSus){
        int idOrden = -1;
        boolean ordEnv = true;
        
        try {
            pStmt = conn.prepareStatement(
                    "INSERT INTO orden (cargoTotal,ordenCompletada,ordenEnviada,numUnidades,idSuscriptor)" +
                    " VALUES (?,?,?,?,?) ", new String[] {"idorden"});
            pStmt.setDouble(1, cargo);
            pStmt.setInt(2, ordComp);
            pStmt.setBoolean(3, ordEnv);
            pStmt.setInt(4, numUni);
            pStmt.setInt(5, idSus);
            
            pStmt.executeUpdate();
            
            ResultSet generatedKeys = pStmt.getGeneratedKeys();
            if (null != generatedKeys && generatedKeys.next()) {
                idOrden = generatedKeys.getInt(1);
            }
            return idOrden;
        } catch (SQLException e) {
            System.out.println("Cannot update database" + e);
            return -1;
        }
    }
}
