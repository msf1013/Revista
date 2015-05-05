package controles;

import entidades.Conexion;
import entidades.Revista;
import entidades.Orden;

import java.sql.*;
import java.io.*;
import java.util.*;

public class ControlCrearOrdenDeEnvio{
	Revista revista;
	Orden orden;
	Conexion conn;

	public ControlCrearOrdenDeEnvio(){
		conn = new Conexion();
		revista = new Revista(conn);
		orden = new Orden(conn);
	}

	public Vector<String> obtenerRevistas(){
		return revista.getRevistas();
	}

	public boolean crearOrden(int idSus, Vector<java.sql.Date> vecFecha, Vector<Integer> vecCant, double costoRevista){
		return orden.guardaOrden(idSus, vecFecha, vecCant, costoRevista);
	}

}