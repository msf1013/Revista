package controles;

import entidades.Conexion;
import entidades.Juez;
import entidades.ArticuloPendiente;
import entidades.Cuenta;

import java.sql.*;
import java.io.*;
import java.util.*;

public class ControlVoto{
  Juez juez;
  Conexion conn;

  public ControlVoto(){
    conn = new Conexion();
    juez = new Juez(conn);
  }

  public HashMap<String,String> getArticulo(int idArticulo) {
    try {
      ArticuloPendiente articulo = new ArticuloPendiente(conn);
      HashMap<String,String> datosArticulo = new HashMap<String,String>();

      datosArticulo.put("titulo", articulo.getTitulo(idArticulo));
      datosArticulo.put("abstract", articulo.getAbstract(idArticulo));
      datosArticulo.put("texto", articulo.getTexto(idArticulo));

      return datosArticulo;
    } catch(Exception e){
      System.out.println("Cannot execute mostrarListaArticulosEvaluar(): " + e);
      return null;
    }
  }

  public void enviarVoto(int idArticulo, int idJuez, int voto) {
      ArticuloPendiente articulo = new ArticuloPendiente(conn);
      articulo.agregarVoto(idArticulo, idJuez, voto);
  }

  public void enviarComentario(int idArticulo, int idJuez, String comentario) {
      ArticuloPendiente articulo = new ArticuloPendiente(conn);
      articulo.agregarComentario(idArticulo, idJuez, comentario);
  }

}