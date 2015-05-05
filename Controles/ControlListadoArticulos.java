package controles;

import entidades.Conexion;
import entidades.Juez;
import entidades.ArticuloPendiente;
import entidades.Cuenta;

import java.sql.*;
import java.io.*;
import java.util.*;

public class ControlListadoArticulos{
  Juez juez;
  Conexion conn;

  public ControlListadoArticulos(){
    conn = new Conexion();
    juez = new Juez(conn);
  }

  public Vector<String> mostrarListaArticulosEvaluar(String idJuez) {
    Vector<Integer> idsArticulos = juez.obtenerArticulosPendientes(Integer.parseInt(idJuez));
    Vector<String> datosArticulos = new Vector<String>();
    try {
	    ArticuloPendiente articulo = new ArticuloPendiente(conn);
	    Cuenta autor = new Cuenta(conn);

	    for (int i = 0; i < idsArticulos.size(); i++) {
	    	int id = idsArticulos.get(i);
	    	int idAutor = articulo.getIdEscritor(id);
	    	String nombre = autor.getNombre(idAutor) + autor.getApellidos(idAutor);
	   		datosArticulos.add(articulo.getTitulo(id));
	   		datosArticulos.add(nombre);
	   		datosArticulos.add(Integer.toString(id));
	    }
	    return datosArticulos;
	  } catch(Exception e){
	  	System.out.println("Cannot execute mostrarListaArticulosEvaluar(): " + e);
			return null;
		}
  }

}