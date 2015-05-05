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
  ArticuloPendiente articulo;
  Conexion conn;

  public ControlListadoArticulos(){
    conn = new Conexion();
    juez = new Juez(conn);
    articulo = new ArticuloPendiente(conn);
  }

  public Vector<String> mostrarListaArticulosEvaluar(String idJuez) {
    Vector<Integer> idsArticulos = juez.obtenerArticulosPendientes(Integer.parseInt(idJuez));
    Vector<String> datosArticulos = new Vector<String>();
    try {
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

  public Vector<HashMap<String,String>> mostrarListaArticulosPendientes() {
    Vector<Integer> idsArticulos = articulo.obtenerArticulos();
    Vector<HashMap<String,String>> articulos = new Vector<HashMap<String,String>>();
    try {
    	Cuenta autor = new Cuenta(conn);
	    for (int i = 0; i < idsArticulos.size(); i++) {
	    	int id = idsArticulos.get(i);
	    	int idAutor = articulo.getIdEscritor(id);
	    	String nombre = autor.getNombre(idAutor) + autor.getApellidos(idAutor);
	    	String fechaPropuesta = articulo.getFechaPubEsperada(id).toString();
	    	HashMap<String,String> articuloHash = new HashMap<String,String>();
	    	articuloHash.put("id", Integer.toString(id));
	    	articuloHash.put("titulo", articulo.getTitulo(id));
	    	articuloHash.put("autor", nombre);
	    	articuloHash.put("fechaEsperada", fechaPropuesta);
	    	articulos.add(articuloHash);
	    }
	    return articulos;
	  } catch(Exception e){
	  	System.out.println("Cannot execute mostrarListaArticulosPendientes(): " + e);
			return null;
		}
  }

}