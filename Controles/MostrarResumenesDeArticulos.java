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
		int idArticuloPendiente;
		int idEscritor;
		String nombre;

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
				conn.statem.executeQuery("SELECT * FROM articulo WHERE idArticulo = " + idArticulos.elementAt(i));
				rs = conn.statem.getResultSet();
				rs.next();
				titulo = rs.getString("titulo");
				resumenes.add(titulo);

				idArticuloPendiente = rs.getInt("idArticuloPendiente");
				conn.statem.executeQuery("SELECT idEscritor FROM articulopendiente WHERE idArticuloPendiente = " + idArticuloPendiente);
				rs = conn.statem.getResultSet();
				rs.next();

				idEscritor = rs.getInt("idEscritor");
				conn.statem.executeQuery("SELECT CONCAT(nombre, ' ', apellidos) AS nombre FROM cuenta WHERE idCuenta = " + idEscritor);
				rs = conn.statem.getResultSet();
				rs.next();

				nombre = rs.getString("nombre");
				resumenes.add(nombre);

				textoResumen = resumen.getResumen(idResumenes.elementAt(i));
				resumenes.add(textoResumen);
			}
			return resumenes;
		}
		catch(SQLException e){
			System.out.println("UP");
			return null;
		}
	}

}