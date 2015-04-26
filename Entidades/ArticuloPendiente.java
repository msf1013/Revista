/**
 * Analisis y modelacion de sistemas de software: Proyecto final
 * Prof. Guillermo Jimenez
 * Equipo #4   
 * @authors Luis Mario Diaz, Humberto Makoto Morimoto,
 * Eduardo Zardain, Mario Sergio Fuentes
 */

package entidades;

import java.sql.*;
import java.io.*;
import java.util.Vector;
import java.util.Date;

/**
 * Clase  ArticuloPendiente
 * Tabla  ArticuloPendiente
 * Campos idArticuloPendiente:integer, titulo:string, textoArticulo:string,
 *        abstract:string,fechaPubEsperada:date, estatus:string,
 *        fechaCreacion:date, validado:boolean, idEscritor:integer
 */
public class ArticuloPendiente {
   Connection conn;
   Statement stmt;
   PreparedStatement pStmt;

   /**
    * Constructor de ArticuloPendiente
    * @param connect Conexion a la base de datos
    */
   public ArticuloPendiente(Conexion connect) {
      this.conn = connect.conn;
      this.stmt = connect.statem;
   }

   /**
    * Valida la existencia de un registro
    * @param idArticuloPendiente ID del registro
    */
   /*
   public boolean validar(int idArticuloPendiente){
      try {
         stmt.executeQuery ("SELECT idArticuloPendiente FROM ArticuloPendiente WHERE idArticuloPendiente = " + idArticuloPendiente);
         ResultSet rs = stmt.getResultSet();
         if (rs.next()) { //Va al primer registro si lo hay
            int nArticulo = rs.getInt ("idArticuloPendiente");
            rs.close(); 
            return(idArticuloPendiente == nArticulo);
         } else {
            return false;
         }
      } catch (SQLException e) {}
      return false;
   }*/

   /**
    * Guarda articulo pendiente en base de datos
    * @param titulo Titulo del articulo
    * @param textoArticulo Texto del articulo
    * @param abstractArticulo Abstarct del articulo
    * @param fechaPubEsperada Fecha en la que el escritor espera se publique el
    *        articulo
    * @param estatus Estatus actual del articulo pendiente
    * @param fechaCreacion Fecha en la que se creo el articulo
    * @param validado Bandera de validación del articulo
    * @return ID del registro nuevo, -1 si hubo algun error
    */
   public int guardarArticuloPendiente(String titulo, String textoArticulo,
         String abstractArticulo, Date fechaPubEsperada, String estatus,
         Date fechaCreacion, boolean validado,
         int idEscritor){
      try {
         pStmt = conn.prepareStatement(
               "INSERT INTO ArticuloPendiente (titulo, textoArticulo, abstract, fechaPubEsperada, estatus, fechaCreacion, validado, idEscritor)" +
               " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ", new String[] { "idArticuloPendiente" });

         pStmt.setString(1, titulo);
         pStmt.setString(2, textoArticulo);
         pStmt.setString(3, abstractArticulo);
         pStmt.setDate(4, new java.sql.Date(fechaPubEsperada.getTime()));
         pStmt.setString(5, estatus);
         pStmt.setDate(6, new java.sql.Date(fechaCreacion.getTime()));
         pStmt.setBoolean(7, validado);
         pStmt.setInt(8, idEscritor);

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
    * Agrega un voto a un articulo pendiente en la base de datos
    * @param idArticuloPendiente ID del articulo al que se le agrega el voto
    * @param idJuez ID del Juez que realiza la votación
    * @param voto Voto del instructor, 1 si es aprobado, 0 si es rechazado
    */
   public void AgregarVoto(int idArticuloPendiente, int idJuez, int voto) {
      try {
         pStmt = conn.prepareStatement("INSERT INTO Votos VALUES (?, ?, ?)");

         pStmt.setInt(1, idArticuloPendiente);
         pStmt.setInt(2, idJuez);
         pStmt.setInt(3, voto);
      } catch (Exception e) {
         System.out.println("Cannot add vote: " + e);
      }
   }

   /**
    * Agrega un comentario a un articulo pendiente en la base de datos
    * @param idArticuloPendiente ID del articulo al que se le agrega el voto
    * @param idJuez ID del Juez que realiza el coementario
    * @param comentario Comentario del juez
    */
   public void AgregarComentario(int idArticuloPendiente, int idJuez, String comentario) {
      try {
         pStmt = conn.prepareStatement("INSERT INTO Comentarios VALUES (?, ?, ?)");

         pStmt.setInt(1, idArticuloPendiente);
         pStmt.setInt(2, idJuez);
         pStmt.setString(3, comentario);
      } catch (Exception e) {
         System.out.println("Cannot add comentario: " + e);
      }
   }

   /**
    * Regresa el conteo de votos a favor de un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @return El total de votos a favor
    */
   public int getVotosAFavor(int idArticuloPendiente) {
      int votos = -1;
      try {
         stmt.executeQuery("SELECT COUNT(voto) FROM Votos WHERE idArticuloPendiente = " + idArticuloPendiente + " AND voto = 1");

         ResultSet rs = stmt.getResultSet();
         if (rs.next()) { //Va al primer registro si lo hay
            votos = rs.getInt ("COUNT(voto)");
            rs.close(); 
         }
      } catch (Exception e) {
         System.out.println("Cannot getVoto(): " + e);
      }
      return votos;
   }

   /**
    * Regresa el conteo de votos en contra de un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @return El total de votos en contra
    */
   public int getVotosEnContra(int idArticuloPendiente) {
      int votos = -1;
      try {
         stmt.executeQuery("SELECT COUNT(voto) FROM Votos WHERE idArticuloPendiente = " + idArticuloPendiente + " AND voto = 0");

         ResultSet rs = stmt.getResultSet();
         if (rs.next()) { //Va al primer registro si lo hay
            votos = rs.getInt ("COUNT(voto)");
            rs.close(); 
         }
      } catch (Exception e) {
         System.out.println("Cannot getVoto(): " + e);
      }
      return votos;
   }

   /**
    * Obtiene un vector con la lista de votos realizados
    * @param idArticuloPendiente ID del articulo pendiente
    * @return Vector con todos los votos
    */
   public Vector<String> getVotos(int idArticuloPendiente) {
      Vector<String> votos = new Vector<String>();
      try {
         pStmt = conn.prepareStatement(
            "SELECT voto FROM Votos WHERE idArticuloPendiente = ?");

         pStmt.setInt(1, idArticuloPendiente);

         ResultSet rs = pStmt.executeQuery();
         while (rs.next()) {
            votos.add(rs.getString("voto"));
         }
      } catch (Exception e) {
         System.out.println ("Cannot getVotos(): " + e );
      }
      return votos;
   }

   /**
    * Obtiene un vector con la lista de comentarios realizados
    * @param idArticuloPendiente ID del articulo pendiente
    * @return Vector con todos los comentarios
    */
   public Vector<String> getComentarios(int idArticuloPendiente) {
      Vector<String> comentarios = new Vector<String>();
      try {
         pStmt = conn.prepareStatement(
            "SELECT comentario FROM Comentarios WHERE idArticuloPendiente = ?");

         pStmt.setInt(1, idArticuloPendiente);

         ResultSet rs = pStmt.executeQuery();
         while (rs.next()) {
            comentarios.add(rs.getString("comentario"));
         }
      } catch (Exception e) {
         System.out.println ("Cannot getComentarios(): " + e );
      }
      return comentarios;
   }
   
   /**
    * Asigna el titulo a un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @param titulo Titulo del articulo
    */
   public void setTitulo(int idArticuloPendiente, String titulo){
      try {
         String s = "UPDATE ArticuloPendiente SET titulo = '" + titulo + "' WHERE idArticuloPendiente = " + idArticuloPendiente;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute disposicion()" + e);
      }
   }

   /**
    * Obtiene el titulo de un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @return  Titulo del articulo
    */
   public String getTitulo(int idArticuloPendiente){
      String titulo = "";
      try {
         stmt.executeQuery ("SELECT titulo FROM ArticuloPendiente WHERE idArticuloPendiente = " + idArticuloPendiente);
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
    * Asigna el cuerpo de texto a un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @param texto Texto del articulo
    */
   public void setTexto(int idArticuloPendiente, String texto){
      try {
         String s = "UPDATE ArticuloPendiente SET texto = '" + texto + "' WHERE idArticuloPendiente = " + idArticuloPendiente;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute disposicion()" + e);
      }
   }

   /**
    * Obtiene el cuerpo de texto de un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @return  Texto del articulo
    */
   public String getTexto(int idArticuloPendiente){
      String texto = "";
      try {
         stmt.executeQuery ("SELECT texto FROM ArticuloPendiente WHERE idArticuloPendiente = " + idArticuloPendiente);
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
    * Asigna el abstract a un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @param abstractArticulo Texto del abstracto
    */
   public void setAbstract(int idArticuloPendiente, String abstractArticulo){
      try {
         String s = "UPDATE ArticuloPendiente SET abstract = '" + abstractArticulo + "' WHERE idArticuloPendiente = " + idArticuloPendiente;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute disposicion()" + e);
      }
   }

   /**
    * Obtiene el abstract de un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @return  Abstract del articulo
    */
   public String getAbstract(int idArticuloPendiente){
      String abstractArticulo = "";
      try {
         stmt.executeQuery ("SELECT abstract FROM ArticuloPendiente WHERE idArticuloPendiente = " + idArticuloPendiente);
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
    * Asigna fecha de publicacion esperada a un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @param fecha Fecha de publicacion
    */
   public void setFechaPubEsperada(int idArticuloPendiente, Date fecha){
      try {
         pStmt = conn.prepareStatement(
                 "UPDATE ArticuloPendiente SET fechaCreacion = ? WHERE idArticuloPendiente = ?");
         pStmt.setDate(1, new java.sql.Date(fecha.getTime()));
         pStmt.setInt(2, idArticuloPendiente);
         pStmt.executeUpdate();
      } catch (SQLException e) {
            System.out.println ("Cannot execute setFechaPubEsperada(): " + e);
      }
   }

   /**
    * Obtiene la fecha de publicacion esperada de un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @return  Fecha de publicacion esperada del articulo pendiente
    */
   public Date getFechPubEsperada(int idArticuloPendiente){
      Date fecha = null;
      try {
         stmt.executeQuery ("SELECT fechaPubEsperada FROM ArticuloPendiente WHERE idArticuloPendiente = " + idArticuloPendiente);
         ResultSet rs = stmt.getResultSet();
         rs.next(); //Va al registro ya validado
         fecha = rs.getDate("fechaPubEsperada");
         rs.close();
         return(fecha);
      } catch (SQLException e) {
         System.out.println ("Cannot getFechPubEsperada(): " + e);
      }
      return fecha;
   }

   /**
    * Asigna estatus a un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @param estatus Estatus del articulo
    */
   public void setEstatus(int idArticuloPendiente, String estatus){
      try {
         String s = "UPDATE ArticuloPendiente SET estatus = '" + estatus + "' WHERE idArticuloPendiente = " + idArticuloPendiente;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute disposicion()" + e);
      }
   }

   /**
    * Obtiene el estatus de un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @return  Estatus del articulo
    */
   public String getEstatus(int idArticuloPendiente){
      String estatus = "";
      try {
         stmt.executeQuery ("SELECT estatus FROM ArticuloPendiente WHERE idArticuloPendiente = " + idArticuloPendiente);
         ResultSet rs = stmt.getResultSet();
         rs.next(); //Va al registro ya validado
         estatus = rs.getString("estatus");
         rs.close();
         return(estatus);
      } catch (SQLException e) {
         System.out.println ("Cannot getEstatus()" + e);
      }
      return estatus;
   }

   /**
    * Asigna fecha de creacion a un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @param fecha Fecha de creación del articulo
    */
   public void setfechaCreacion(int idArticuloPendiente, Date fecha){
      try {
         pStmt = conn.prepareStatement(
                 "UPDATE ArticuloPendiente SET fechaCreacion = ? WHERE idArticuloPendiente = ?");
         pStmt.setDate(1, new java.sql.Date(fecha.getTime()));
         pStmt.setInt(2, idArticuloPendiente);
         pStmt.executeUpdate();
      } catch (SQLException e) {
            System.out.println ("Cannot execute setfechaCreacion(): " + e);
      }
   }

   /**
    * Obtiene la fecha de creacion de un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @return  Fecha de creacion del articulo pendiente
    */
   public Date getFechaCreacion(int idArticuloPendiente){
      Date fecha = null;
      try {
         stmt.executeQuery ("SELECT fechaCreacion FROM ArticuloPendiente WHERE idArticuloPendiente = " + idArticuloPendiente);
         ResultSet rs = stmt.getResultSet();
         rs.next(); //Va al registro ya validado
         fecha = rs.getDate("fechaCreacion");
         rs.close();
         return(fecha);
      } catch (SQLException e) {
         System.out.println ("Cannot getFechaCreacion(): " + e);
      }
      return fecha;
   }

   /**
    * Asigna bandera de validacion al articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @param validado Bandera de validacion
    */
   public void setValidado(int idArticuloPendiente, Boolean validado){
      try {
         String s = "UPDATE ArticuloPendiente SET validado = " + validado + " WHERE idArticuloPendiente = " + idArticuloPendiente;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute setValidado(): " + e);
      }
   }

   /**
    * Obtiene la bandera de validacion de un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @return  Bandera de validacion del articulo pendiente
    */
   public Boolean getValidado(int idArticuloPendiente){
      Boolean validado = false;
      try {
         stmt.executeQuery ("SELECT validado FROM ArticuloPendiente WHERE idArticuloPendiente = " + idArticuloPendiente);
         ResultSet rs = stmt.getResultSet();
         rs.next(); //Va al registro ya validado
         validado = rs.getBoolean("validado");
         rs.close();
         return(validado);
      } catch (SQLException e) {
         System.out.println ("Cannot getValidado(): " + e);
      }
      return validado;
   }

   /**
    * Asigna id de escritor a un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @param idEscritor ID del escritor del articulo
    */
   public void setIdEscritor(int idArticuloPendiente, int idEscritor){
      try {
         String s = "UPDATE ArticuloPendiente SET idEscritor =" + idEscritor + " WHERE idArticuloPendiente = " + idArticuloPendiente;
         stmt.executeUpdate(s);
      } catch (SQLException e) {
         System.out.println ("Cannot execute disposicion(): " + e);
      }
   }

   /**
    * Obtiene el id del escritor de un articulo pendiente
    * @param idArticuloPendiente ID del articulo pendiente
    * @return  ID del escritor del articulo pendiente
    */
   public int getIdEscritor(int idArticuloPendiente){
      int idEscritor = -1;
      try {
         stmt.executeQuery ("SELECT idEscritor FROM ArticuloPendiente WHERE idArticuloPendiente = " + idArticuloPendiente);
         ResultSet rs = stmt.getResultSet();
         rs.next(); //Va al registro ya validado
         idEscritor = rs.getInt("idEscritor");
         rs.close();
         return(idEscritor);
      } catch (SQLException e) {
         System.out.println ("Cannot getIdEscritor(): " + e);
      }
      return idEscritor;
   }
}