package controles;
import entidades.Cuenta;
import entidades.Conexion;

public class ControlValidacion {
   Cuenta cuenta;

   public ControlValidacion(){
     cuenta = new Cuenta(new Conexion()); //Asume que la instancia persiste durante la sesion
   }
   
   //Valida si la cuenta existe en la base de datos
   public int validarCuenta(String email, String passwd){            
      return(cuenta.validarCuenta(email,passwd));
   }

}
