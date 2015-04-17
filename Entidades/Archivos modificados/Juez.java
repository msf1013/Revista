import java.sql.*;
import java.util.Date;
/**
 *
 * @author MarioDiaz
 */
public class Juez {
    //Atributos de conexion
    Connection conn;
    Statement stmt;
    PreparedStatement pStmt;
    
    
    //Metodo constructor
    public Juez(Conexion connect){
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

    public void setNumeroJuez(int numJuez) {
        
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
    
    //asdfsadfsdf
    
    //Metodo que guarda el juez y regresa el idJuez
    public int guardaJuez(int numJuez){
        int idJuez = -1;
        try {
            pStmt = conn.prepareStatement(
                    "INSERT INTO orden (numJuez)" +
                    " VALUES (?) ", new String[] {"idjuez"});
            pStmt.setInt(1, numJuez);
           
            pStmt.executeUpdate();
            
            ResultSet generatedKeys = pStmt.getGeneratedKeys();
            if (null != generatedKeys && generatedKeys.next()) {
                idJuez = generatedKeys.getInt(1);
            }
            return idJuez;
        } catch (SQLException e) {
            System.out.println("Cannot update database" + e);
            return -1;
        }
    }
}
