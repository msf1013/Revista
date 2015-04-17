import java.sql.*;
import java.io.*;
import java.util.Vector;
import java.util.Date;

public class Administrador extends Cuenta {

   public Administrador(Conexion connect) {
      this.conn = connect.conn;
      this.stmt = connect.statem;
   }

   public boolean validar(int idCuenta){
      try {
         stmt.executeQuery ("SELECT idCuenta FROM Administrador WHERE idCuenta = " + idCuenta);
         ResultSet rs = stmt.getResultSet();
         if (rs.next()) { //Va al primer registro si lo hay
            int nAdministrador = rs.getInt ("idCuenta");
            rs.close(); 
            return(idCuenta == nAdministrador);
         } else {
            return false;
         }
      } catch (SQLException e) {}
      return false;
   }

   public int guardarAdministrador(int idCuenta){
      try {
         pStmt = conn.prepareStatement(
               "INSERT INTO Administrador(idCuenta) VALUES (?)");

         pStmt.setInt(1, idCuenta);

         pStmt.executeUpdate();

         int nuevoId = -1;
         ResultSet generatedKeys = pStmt.getGeneratedKeys();
         if (null != generatedKeys && generatedKeys.next()) {
            nuevoId = generatedKeys.getInt(1);
         }
         return nuevoId;

      } catch (Exception e) {
         System.out.println ("Cannot update database: " + e );
         return -1;
      }   
   }

   public Vector<Integer> buscarCuenta(String cuenta){
      Vector<Integer> cuentas = new Vector<Integer>();
      try {
         pStmt = conn.prepareStatement(
            "SELECT idCuenta FROM Cuenta WHERE nombre LIKE ? OR apellidos LIKE ?");

         pStmt.setString(1, cuenta + '%');
         pStmt.setString(2, cuenta + '%');

         ResultSet rs = pStmt.executeQuery();
         while (rs.next()) {
            cuentas.add(rs.getInt("idCuenta"));
         }
      } catch (Exception e) {
         System.out.println ("Cannot buscarCuenta(): " + e );
      }
      return cuentas;
   }

   
   public boolean crearJuez(String nombre, String apellidos, String email, String password, int numJuez){
      try {
         Cuenta cuenta = new Cuenta(new Conexion());
         int id = cuenta.guardaCuenta(nombre, apellidos, email, password, new Date(), "juez");
         Juez juez = new Juez(new Conexion());
         juez.guardaJuez(numJuez);
         return true;
      } catch (Exception e) {
         System.out.println("Cannot crearJuez(): " + e);
         return false;
      }
   }

   public boolean eliminarCuenta(int idCuenta) {
      try {
         pStmt = conn.prepareStatement(
            "DELETE FROM Cuenta WHERE idCuenta = ?");

         pStmt.setInt(1, idCuenta);
         pStmt.executeQuery();

         return true;
      } catch (Exception e) {
         System.out.println ("Cannot eliminarCuenta(): " + e );
         return false;
      }
   }

   
   public boolean publicarCarta(String carta, int idRevista) {
      try {
         Revista revista = new Revista(new Conexion());
         revista.setCartaDeEditor(idRevista, carta);
         return true;
      } catch (Exception e) {
         System.out.println("Cannot publicarCarta(): " + e);
         return false;
      }
   }

   public void validarArticulo(int idArticulo) {
      ArticuloPendiente articulo = new ArticuloPendiente(new Conexion());
      articulo.setValidado(idArticulo, true);
   }
}
