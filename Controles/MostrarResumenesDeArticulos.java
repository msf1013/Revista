package controles;

import entidades.Conexion;
import entidades.Resumen;

import java.sql.*;
import java.io.*;
import java.util.*;

public class MostrarResumenesDeArticulos{
	Resumen resumen;
	Conexion conn;

	public MostrarResumenesDeArticulos(){
		conn = new Conexion();
		resumen = new Resumen(conn);
	}

	public Vector<String> mostrarResumenes(){
		Vector<String> resumenes = new Vector<String>();
		Vector<Integer> idResumenes = new Vector<Integer>();
		Vector<Integer> idArticulos = new Vector<Integer>();
		String textoResumen;
		String titulo;

		try{
			conn.statem.executeQuery("SELECT * FROM resumen");
			ResultSet rs = conn.statem.getResultSet();
			while(rs.next()){
				int idResumen = rs.getInt("idResumen");
				idResumenes.add(idResumen);
				int idArticulo = rs.getInt("idArticulo");
				idArticulos.add(idArticulo);
			}
			for(int i = 0; i < idResumenes.size(); i++){
				conn.statem.executeQuery("SELECT titulo FROM articulo WHERE idArticulo = " + idArticulos.elementAt(i));
				rs = conn.statem.getResultSet();
				rs.next();
				titulo = rs.getString("titulo");
				resumenes.add(titulo);

				textoResumen = resumen.getResumen(idResumenes.elementAt(i));
				resumenes.add(textoResumen);
			}
			return resumenes;
		}
		catch(SQLException e){
			return null;
		}
	}

}