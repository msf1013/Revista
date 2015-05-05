package controles;
import entidades.Escritor;
import entidades.ArticuloPendiente;
import entidades.Cuenta;
import entidades.Conexion;
import java.util.*;

public class ControlEnvioArticulo {
   ArticuloPendiente articulo;
   Cuenta cuenta;

   public ControlEnvioArticulo(){
     cuenta = new Cuenta(new Conexion()); //Asume que la instancia persiste durante la sesion
     articulo = new ArticuloPendiente(new Conexion()); //Asume que la instancia persiste durante la sesion
   }
   
   //Valida si la cuenta existe en la base de datos
   public String getTipoUsuario(int id){            
      return (cuenta.getTipoUsuario(id));
   }

   public int crearArticulo(String titulo, String textoArticulo,
         String abstractArticulo, Date fechaPubEsperada, String estatus,
         Date fechaCreacion, boolean validado,
         int idEscritor) {
      return articulo.guardarArticuloPendiente(titulo, textoArticulo,
         abstractArticulo, fechaPubEsperada, estatus,
         fechaCreacion, validado,
         idEscritor);
   }
   
}
