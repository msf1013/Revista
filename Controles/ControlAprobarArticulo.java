package controles;

import entidades.Conexion;
import entidades.Articulo;
import entidades.ArticuloPendiente;
import entidades.Cuenta;

import java.sql.*;
import java.io.*;
import java.util.*;

public class ControlAprobarArticulo{
  ArticuloPendiente articuloPendiente;
  Articulo articulo;
  Conexion conn;

  public ControlAprobarArticulo(){
    conn = new Conexion();
    articuloPendiente = new ArticuloPendiente(conn);
    articulo = new Articulo(conn);
  }

  public HashMap<String,String> getArticulo(int idArticuloPendiente) {
    try {
      HashMap<String,String> datosArticulo = new HashMap<String,String>();

      datosArticulo.put("titulo", articuloPendiente.getTitulo(idArticuloPendiente));
      datosArticulo.put("abstract", articuloPendiente.getAbstract(idArticuloPendiente));
      datosArticulo.put("texto", articuloPendiente.getTexto(idArticuloPendiente));

      return datosArticulo;
    } catch(Exception e){
      System.out.println("Cannot execute mostrarListaArticulosEvaluar(): " + e);
      return null;
    }
  }

  public void realizarAprobacion(int idArticuloPendiente) {
      String titulo = articuloPendiente.getTitulo(idArticuloPendiente);
      String textoArticulo = articuloPendiente.getTexto(idArticuloPendiente);
      String abstractArticulo = articuloPendiente.getAbstract(idArticuloPendiente);
      java.util.Date fechaAprobacion = new java.util.Date();
      articuloPendiente.setValidado(idArticuloPendiente);
      articulo.guardarArticulo(titulo, textoArticulo, abstractArticulo, idArticuloPendiente, fechaAprobacion);
  }

}