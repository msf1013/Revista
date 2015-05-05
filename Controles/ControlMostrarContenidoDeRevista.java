package controles;

import entidades.Conexion;
import entidades.Articulo;

import java.sql.*;
import java.io.*;
import java.util.*;

public class ControlMostrarContenidoDeRevista{
	Articulo articulo;
	Conexion conn;

	public ControlMostrarContenidoDeRevista(){
		conn = new Conexion();
		articulo = new Articulo(conn);
	}

	public Vector<String> mostrarArticulos(){
		return articulo.getArticulos();
	}

	public Vector<String> mostrarArticulo(String titulo){
		return articulo.getInformacionArticulo(titulo);
	}

}