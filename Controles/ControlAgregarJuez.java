/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controles;

import entidades.Conexion;
import entidades.Cuenta;
import entidades.Juez;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Date;

/**
 *
 * @author MarioDiaz
 */
public class ControlAgregarJuez {
    Juez juez;
    Cuenta cuenta;
    Conexion conn;
   
    
    public ControlAgregarJuez () {
        
        conn = new Conexion();
        juez = new Juez(conn);
        
        
    }
    
    public int agregaJuez(String nom, String ape, String email, String pass,int numJuez){
        Date fecha = new Date();
        int flag = juez.guardaJuez(nom, ape, email, pass,fecha, numJuez);
        return flag;
    }
}
