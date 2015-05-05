package controles;

import entidades.Conexion;
import entidades.Revista;

import java.sql.*;
import java.io.*;
import java.util.*;

public class ControlCarta{
  Revista revista;
  Conexion conn;

  public ControlCarta(){
    conn = new Conexion();
    revista = new Revista(conn);
  }

  public void publicarCarta(java.sql.Date fecha, String carta) {
    try {
      revista.guardaRevista(fecha, carta);
    } catch(Exception e){
      System.out.println("Cannot execute publicarCarta(): " + e);
    }
  }
}