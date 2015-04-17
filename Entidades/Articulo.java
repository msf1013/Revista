import java.sql.*;
import java.io.*;
import java.util.Vector;
import java.util.Date;

public class Articulo {
   Connection conn;
   Statement stmt;
   PreparedStatement pStmt;

   public Articulo(Conexion connect) {
      this.conn = connect.conn;
      this.stmt = connect.statem;
   }

   public boolean validar(int idArticulo){
      try {
         stmt.executeQuery ("SELECT idArticulo FROM Articulo WHERE idArticulo = " + idArticulo);
         ResultSet rs = stmt.getResultSet();
         if (rs.next()) { //Va al primer registro si lo hay
            int nArticulo = rs.getInt ("idArticulo");
            rs.close(); 
            return(idArticulo == nArticulo);
         } else {
            return false;
         }
      } catch (SQLException e) {}
      return false;
   }

   public int guardarArticulo(int idRevista, String titulo,
         String textoArticulo, String abstractArticulo, int idArticuloPendiente,
         Date fechaAprobacion){
      try {
         pStmt = conn.prepareStatement(
               "INSERT INTO Articulo (idRevista, titulo, textoArticulo, abstract, idArticuloPendiente, fechaAprobacion)" +
               " VALUES (?, ?, ?, ?, ?, ?) ", new String[] { "idArticulo" });

         pStmt.setInt(1, idRevista);
         pStmt.setString(2, titulo);
         pStmt.setString(3, textoArticulo);
         pStmt.setString(4, abstractArticulo);
         pStmt.setInt(5, idArticuloPendiente);
         pStmt.setDate(6, new java.sql.Date(fechaAprobacion.getTime()));

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
   
   public void setTitulo(int idArticulo, String titulo){
      try {
         String s = "UPDATE Articulo SET titulo = '" + titulo + "' WHERE idArticulo = " + idArticulo;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute disposicion()" + e);
      }
   }

   public String getTitulo(int idArticulo){
      String titulo = "";
      try {
         stmt.executeQuery ("SELECT titulo FROM Articulo WHERE idArticulo = " + idArticulo);
         ResultSet rs = stmt.getResultSet();
         rs.next(); //Va al registro ya validado
         titulo = rs.getString("titulo");
         rs.close();
         return(titulo);
      } catch (SQLException e) {
         System.out.println ("Cannot getTitulo()" + e);
      }
      return titulo;
   }

   public void setTexto(int idArticulo, String texto){
      try {
         String s = "UPDATE Articulo SET texto = '" + texto + "' WHERE idArticulo = " + idArticulo;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute disposicion()" + e);
      }
   }

   public String getTexto(int idArticulo){
      String texto = "";
      try {
         stmt.executeQuery ("SELECT texto FROM Articulo WHERE idArticulo = " + idArticulo);
         ResultSet rs = stmt.getResultSet();
         rs.next(); //Va al registro ya validado
         texto = rs.getString("texto");
         rs.close();
         return(texto);
      } catch (SQLException e) {
         System.out.println ("Cannot getTexto()" + e);
      }
      return texto;
   }

   public void setAbstract(int idArticulo, String abstractArticulo){
      try {
         String s = "UPDATE Articulo SET abstract = '" + abstractArticulo + "' WHERE idArticulo = " + idArticulo;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute disposicion()" + e);
      }
   }

   public String getAbstract(int idArticulo){
      String abstractArticulo = "";
      try {
         stmt.executeQuery ("SELECT abstract FROM Articulo WHERE idArticulo = " + idArticulo);
         ResultSet rs = stmt.getResultSet();
         rs.next(); //Va al registro ya validado
         abstractArticulo = rs.getString("abstract");
         rs.close();
         return(abstractArticulo);
      } catch (SQLException e) {
         System.out.println ("Cannot getAbstract()" + e);
      }
      return abstractArticulo;
   }
   
   public void setFechaAprobacion(int idArticulo, Date fecha){
      try {
         pStmt = conn.prepareStatement(
                 "UPDATE Articulo SET fechaAprobacion = ? WHERE idArticulo = ?");
         pStmt.setDate(1, new java.sql.Date(fecha.getTime()));
         pStmt.setInt(2, idArticulo);
         pStmt.executeUpdate();
      } catch (SQLException e) {
            System.out.println ("Cannot execute setFechaAprobacion(): " + e);
      }
   }

   public Date getFechAprobacion(int idArticulo){
      Date fecha = null;
      try {
         stmt.executeQuery ("SELECT fechaAprobacion FROM Articulo WHERE idArticulo = " + idArticulo);
         ResultSet rs = stmt.getResultSet();
         rs.next(); //Va al registro ya validado
         fecha = rs.getDate("fechaAprobacion");
         rs.close();
         return(fecha);
      } catch (SQLException e) {
         System.out.println ("Cannot getFechAprobacion(): " + e);
      }
      return fecha;
   }

   public void setIdArticuloPendiente(int idArticulo, int idArticuloPendiente){
      try {
         String s = "UPDATE Articulo SET idArticuloPendiente = '" + idArticuloPendiente + "' WHERE idArticulo = " + idArticulo;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute setIdArticuloPendiente(): " + e);
      }
   }

   public int getIdArticuloPendiente(int idArticulo){
      int idArticuloPendiente = -1;
      try {
         stmt.executeQuery ("SELECT idArticuloPendiente FROM Articulo WHERE idArticulo = " + idArticulo);
         ResultSet rs = stmt.getResultSet();
         rs.next(); //Va al registro ya validado
         idArticuloPendiente = rs.getInt("idArticuloPendiente");
         rs.close();
         return(idArticuloPendiente);
      } catch (SQLException e) {
         System.out.println ("Cannot getIdArticuloPendiente(): " + e);
      }
      return idArticuloPendiente;
   }

   public void setIdRevista(int idArticulo, int idRevista){
      try {
         String s = "UPDATE Articulo SET idRevista = '" + idRevista + "' WHERE idArticulo = " + idArticulo;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute setIdRevista(): " + e);
      }
   }

   public int getIdRevista(int idArticulo){
      int idRevista = -1;
      try {
         stmt.executeQuery ("SELECT idRevista FROM Articulo WHERE idArticulo = " + idArticulo);
         ResultSet rs = stmt.getResultSet();
         rs.next(); //Va al registro ya validado
         idRevista = rs.getInt("idRevista");
         rs.close();
         return(idRevista);
      } catch (SQLException e) {
         System.out.println ("Cannot getIdRevista(): " + e);
      }
      return idRevista;
   }

   public void setIdPredecesor(int idArticulo, int idPredecesor){
      try {
         String s = "UPDATE Articulo SET idPredecesor = '" + idPredecesor + "' WHERE idArticulo = " + idArticulo;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute setIdPredecesor(): " + e);
      }
   }

   public int getIdPredecesor(int idArticulo){
      int idPredecesor = -1;
      try {
         stmt.executeQuery ("SELECT idPredecesor FROM Articulo WHERE idArticulo = " + idArticulo);
         ResultSet rs = stmt.getResultSet();
         rs.next(); //Va al registro ya validado
         idPredecesor = rs.getInt("idPredecesor");
         rs.close();
         return(idPredecesor);
      } catch (SQLException e) {
         System.out.println ("Cannot getIdPredecesor(): " + e);
      }
      return idPredecesor;
   }
}
