package entidades;

import java.sql.*;
import java.io.*;
import java.util.Vector;
import java.util.Date;

public class Articulo {
   Connection conn;
   Statement stmt;
   PreparedStatement pStmt;

   /**
    * Constructor de Articulo
    * @param connect Conexion a la base de datos
    */
   public Articulo(Conexion connect) {
      this.conn = connect.conn;
      this.stmt = connect.statem;
   }

   /**
    * Valida la existencia de un registro
    * @param idArticulo ID del registro
    */
   /*
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
   }*/

   /**
    * Guarda articulo en la base de datos
    * @param idRevista ID de la revista a la que pertenece
    * @param titulo Titulo del articulo
    * @param textoArticulo Texto del articulo
    * @param abstractArticulo Abstract del articulo
    * @param idArticuloPendiente ID del articulo pendiente e el que se basa
    * @param fechaAprobacion Fecha en la que se aprobo el articulo
    * @return ID del registro nuevo, -1 si hubo algun error
    */
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

   /**
    * Asigna el titulo a un articulo
    * @param idArticulo ID del articulo
    * @param titulo Titulo del articulo
    */
   public void setTitulo(int idArticulo, String titulo){
      try {
         String s = "UPDATE Articulo SET titulo = '" + titulo + "' WHERE idArticulo = " + idArticulo;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute disposicion()" + e);
      }
   }

   /**
    * Obtiene el titulo de un articulo
    * @param idArticulo ID del articulo
    * @return  Titulo del articulo
    */
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

   /**
    * Asigna el texto a un articulo
    * @param idArticulo ID del articulo
    * @param texto Texto del articulo
    */
   public void setTexto(int idArticulo, String texto){
      try {
         String s = "UPDATE Articulo SET texto = '" + texto + "' WHERE idArticulo = " + idArticulo;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute disposicion()" + e);
      }
   }

   /**
    * Obtiene el texto de un articulo
    * @param idArticulo ID del articulo
    * @return  Texto del articulo
    */
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

   /**
    * Asigna el abstract del articulo
    * @param idArticulo ID del articulo
    * @param abstractArticulo Texto del articulo
    */
   public void setAbstract(int idArticulo, String abstractArticulo){
      try {
         String s = "UPDATE Articulo SET abstract = '" + abstractArticulo + "' WHERE idArticulo = " + idArticulo;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute disposicion()" + e);
      }
   }

   /**
    * Obtiene el abstract de un articulo
    * @param idArticulo ID del articulo
    * @return  Abstract del articulo
    */
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
   
   /**
    * Asigna la fecha de aprobacion del articulo
    * @param idArticulo ID del articulo
    * @param abstractArticulo Texto del articulo
    */
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

   /**
    * Obtiene la fecha de aporbacion de un articulo
    * @param idArticulo ID del articulo
    * @return  Fecha de aprobacion del articulo
    */
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

   /**
    * Asigna el articulo pendiente del articulo
    * @param idArticulo ID del articulo
    * @param idArticuloPendiente ID del articulo pendiente al que pertenece
    */
   public void setIdArticuloPendiente(int idArticulo, int idArticuloPendiente){
      try {
         String s = "UPDATE Articulo SET idArticuloPendiente = '" + idArticuloPendiente + "' WHERE idArticulo = " + idArticulo;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute setIdArticuloPendiente(): " + e);
      }
   }

   /**
    * Obtiene el id del articulo pendiente en el que se basa un articulo
    * @param idArticulo ID del articulo
    * @return  ID del articulo pendiente del articulo
    */
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

   /**
    * Asigna el id de la revista del articulo
    * @param idArticulo ID del articulo
    * @param idRevista ID de la revista
    */
   public void setIdRevista(int idArticulo, int idRevista){
      try {
         String s = "UPDATE Articulo SET idRevista = '" + idRevista + "' WHERE idArticulo = " + idArticulo;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute setIdRevista(): " + e);
      }
   }

   /**
    * Obtiene el id de la revista a la que pertenece un articulo
    * @param idArticulo ID del articulo
    * @return  ID de la revista
    */
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

   /**
    * Asigna el abstract del articulo
    * @param idArticulo ID del articulo
    * @param idPredecesor ID del articulo predecesor al articulo
    */
   public void setIdPredecesor(int idArticulo, int idPredecesor){
      try {
         String s = "UPDATE Articulo SET idPredecesor = '" + idPredecesor + "' WHERE idArticulo = " + idArticulo;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute setIdPredecesor(): " + e);
      }
   }

   /**
    * Obtiene el id del predecesor de un articulo
    * @param idArticulo ID del articulo
    * @return  ID del predecesor del articulo
    */
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
