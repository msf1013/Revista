package controles;
import entidades.Suscriptor;
import entidades.Conexion;
import java.util.*;

public class ControlRegistro {
   Suscriptor cuenta;

   public ControlRegistro(){
     cuenta = new Suscriptor(new Conexion()); //Asume que la instancia persiste durante la sesion
   }
   
   //Valida si la cuenta existe en la base de datos
   public int crearSuscripcionNormal(String nom, String ape, String ema, String pas, 
            Date fecha, boolean esC, boolean esE, boolean esN, String tip,
                Date fechaR, Date fechaV, String num, String ban, double carga,
                    String dir, int idE){            
      return (cuenta.guardaSuscriptor(nom, ape, ema, pas, 
            fecha, esC, esE, esN, tip,
                fechaR, fechaV, num, ban, carga,
                    dir, idE));
   }

   //Valida si la cuenta existe en la base de datos
   public boolean crearSuscripcionCorporativa(String nom, String ape, String ema, String pas, 
            Date fecha, boolean esC, boolean esE, boolean esN, String tip,
                Date fechaR, Date fechaV, String num, String ban, double carga,
                    String dir, int idE, Vector<Suscriptor> vec){            
      int id = (cuenta.guardaSuscriptor(nom, ape, ema, pas, 
            fecha, esC, esE, esN, tip,
                fechaR, fechaV, num, ban, carga,
                    dir, idE));
   		if (id == -1) return false;

   		return cuenta.crearCuentasCorporativasDependientes(id,vec);

   }

}
