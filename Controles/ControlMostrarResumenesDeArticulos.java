package controles;

import entidades.Conexion;
import entidades.Resumen;

import java.sql.*;
import java.io.*;
import java.util.*;

public class ControlMostrarResumenesDeArticulos{
	Resumen resumen;
	Conexion conn;

	public ControlMostrarResumenesDeArticulos(){
		conn = new Conexion();
		resumen = new Resumen(conn);
	}

	public Vector<String> mostrarResumenes(){
		return resumen.getResumenes();
	}

}