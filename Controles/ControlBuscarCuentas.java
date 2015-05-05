/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controles;

import entidades.Conexion;
import entidades.Cuenta;
import entidades.CuentaAuxiliar;
import entidades.Juez;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author MarioDiaz
 */
public class ControlBuscarCuentas {
    Cuenta cuenta;
    Conexion conn;
    String stringHTML;
    
    
    public ControlBuscarCuentas () {
        conn = new Conexion();
        cuenta = new Cuenta(conn);
        stringHTML = "";
        
    }
    
    
    public String verCuentas(String nom){
        ArrayList<CuentaAuxiliar> cuentas = cuenta.buscarCuentasPorNombre(nom);
        
        for(CuentaAuxiliar cuentaAux : cuentas) {
            stringHTML = "<tr>";
            stringHTML = stringHTML + "<td style=\"width:100%;\">";
            stringHTML = stringHTML + "<form method=\"POST\" action=\"BuscarCuentas\">";
            
            stringHTML = stringHTML + "<fieldset>";
            stringHTML = stringHTML + "<legend style=\"text-align:center\"><b>*"+ cuentaAux.getTipoUsuario() +"* Id de Cuenta: " + cuentaAux.getIdCuenta() + "</b></legend>";
            stringHTML = stringHTML + "<p><i>" + cuentaAux.getNombre()+ " " + cuentaAux.getApellidos() + "</i></p>";
            stringHTML = stringHTML + "<p>" + cuentaAux.getEmail() + "</p>";
            //stringHTML = stringHTML + "<button type=\"submit\" name=\"botonEliminar\" value="+cuentaAux.getIdCuenta()+" >Eliminar cuenta</button>";
            stringHTML = stringHTML + "</fieldset>";
            stringHTML = stringHTML + "</form>";
            stringHTML = stringHTML + "</td>";
            stringHTML = stringHTML + "</tr>";
        }
        return stringHTML;
    }
    
    
    
    
    
}
