package entidades;

import java.sql.*;
import java.io.*;
import java.util.Vector;
import java.util.Date;

public class Administrador extends Cuenta {

   /**
    * Constructor de Administardor
    * @param connect Conexion a la base de datos
    */
   public Administrador(Conexion connect) {
      this.conn = connect.conn;
      this.stmt = connect.statem;
   }

   /**
    * Valida la existencia de un registro
    * @param idCuenta ID del registro
    */
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

   /*public int guardarAdministrador(int idCuenta){
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
   }*/

   /**
    * Busca una cuenta de usuario y regresa un vector de ids
    * @param cuenta String con un nombre o apellido de un usuario
    * @return Vector con los ids de los resultados
    */
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

   /**
    * Crea un nuevo usuario de tipo juez
    * @param nombre Nombre del juez
    * @param apellidos Apellidos del juez
    * @param email Correo electronico del juez
    * @param password Contrasena del juez
    * @param fecha Fecha de creación del registro
    * @param numJuez Numero que se le asigna al juez
    * @return ID del juez creado, -1 si hubo error
    */
   public int crearJuez(String nombre, String apellidos, String email, String password, Date fecha, int numJuez){
      try {
         Juez juez = new Juez(new Conexion());
         return juez.guardaJuez(nombre, apellidos, email, password, fecha, numJuez);
      } catch (Exception e) {
         System.out.println("Cannot crearJuez(): " + e);
         return -1;
      }
   }

   /**
    * Elimina una cuenta de usuario
    * @param idCuenta ID de la cuenta a eliminar
    * @return Verdadero si la cuenta se elimino exitosamente, falso si hubo
    * algun error
    */
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

   /**
    * Agrega la carta de editor a una revista
    * @param carta Texto de la carta
    * @param idRevista ID de la revista
    * @return Verdadero si la carta se agrego exitisamente, falso si hubo algun
      error
    */
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

   /**
    * Valida un articulo para ser publicado
    * @param idArticulo ID del articulo
    */
   public void validarArticulo(int idArticulo) {
      ArticuloPendiente articulo = new ArticuloPendiente(new Conexion());
      articulo.setValidado(idArticulo, true);
   }
}
