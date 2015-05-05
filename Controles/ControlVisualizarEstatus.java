package controles;
import entidades.Escritor;
import entidades.ArticuloPendiente;
import entidades.Cuenta;
import entidades.Escritor;
import entidades.Conexion;
import java.util.*;

public class ControlVisualizarEstatus {
   ArticuloPendiente articulo;
   Cuenta cuenta;
   Escritor escritor;

   public ControlVisualizarEstatus(){
     cuenta = new Cuenta(new Conexion()); //Asume que la instancia persiste durante la sesion
     articulo = new ArticuloPendiente(new Conexion()); //Asume que la instancia persiste durante la sesion
      escritor = new Escritor(new Conexion());
   }
   
   //Valida si la cuenta existe en la base de datos
   public String getTipoUsuario(int id){            
      return (cuenta.getTipoUsuario(id));
   }

   public String verArticulos(int id) {
      Vector<Integer> vecArticulos = escritor.obtenerArticulosPendientes(id);

      String respuesta = "";

      for (int i = 0; i<vecArticulos.size(); i++) {
        respuesta += "</br>";
        respuesta += "<table>";
            respuesta += "<tr>";
                respuesta += "<td>";
                respuesta += "Titulo";
                respuesta += "</td>";
                respuesta += "<td>";
                respuesta += articulo.getTitulo(vecArticulos.get(i));
                respuesta += "</td>";
            respuesta += "</tr>";
            respuesta += "<tr>";
                respuesta += "<td>";
                respuesta += "Fecha de creacion";
                respuesta += "</td>";
                respuesta += "<td>";
                respuesta += articulo.getFechaCreacion(vecArticulos.get(i)).toString();
                respuesta += "</td>";
            respuesta += "</tr>";
            respuesta += "<tr>";
                respuesta += "<td>";
                respuesta += "Estatus";
                respuesta += "</td>";
                respuesta += "<td>";
                respuesta += articulo.getEstatus(vecArticulos.get(i));
                respuesta += "</td>";
            respuesta += "</tr>";
            respuesta += "<tr>";
                respuesta += "<td>";
                respuesta += "Votos a favor";
                respuesta += "</td>";
                respuesta += "<td>";
                respuesta += articulo.getVotosAFavor(vecArticulos.get(i));
                respuesta += "</td>";
            respuesta += "</tr>";
            respuesta += "<tr>";
                respuesta += "<td>";
                respuesta += "Votos en contra";
                respuesta += "</td>";
                respuesta += "<td>";
                respuesta += articulo.getVotosEnContra(vecArticulos.get(i));
                respuesta += "</td>";
            respuesta += "</tr>";
            Vector<String> vecComentarios = articulo.getComentarios(vecArticulos.get(i));
            for (int j = 0; j < vecComentarios.size(); j++) {
              respuesta += "<tr>";
                respuesta += "<td>";
                respuesta += "Comentario " + (j+1);
                respuesta += "</td>";
                respuesta += "<td>";
                respuesta += vecComentarios.get(j);
                respuesta += "</td>";
              respuesta += "</tr>";
            }
        respuesta += "<table>";
      }
      return respuesta;

   }
   
}
