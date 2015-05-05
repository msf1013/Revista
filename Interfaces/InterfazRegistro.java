package interfaces;
import controles.*;
import entidades.Suscriptor;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.Date;
//import controles.ControlRegistro;

public class InterfazRegistro extends HttpServlet {
  HttpServletResponse thisResponse;
  HttpServletRequest thisRequest;
  PrintWriter out;
  ControlRegistro cc;
  
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    thisResponse = response;
    thisRequest = request;
    thisResponse.setContentType("text/html");

    HttpSession session = request.getSession(true);

    out = thisResponse.getWriter();
    //Preparar el encabezado de la pagina Web de respuesta
    out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
    out.println("<HTML>");
    out.println("<HEAD>");
    out.println("<META http-equiv=Content-Type content=\"text/html\">");
    out.println("</HEAD>");
    out.println("<BODY>");
    out.println("<TITLE>Revista</TITLE>");
    out.println("<h2>Registro de cuenta</h2>");
    //out.println("<h3>Consultar saldo</h3>");

    String nombre = request.getParameter("nombre");
    String apellido = request.getParameter("apellido");
    String email = request.getParameter("correo");
    String passwd = request.getParameter("contra");
    Date fechaC = new Date();
    boolean esC = false;
    boolean esE = false;
    boolean esN = true;

    if (request.getParameter("tipoC") != null && request.getParameter("tipoC").equals("Corporativa")) {
      esC = true;
      esE = true;
    }

    String tipo = request.getParameter("tipo");
    Date fechaR = new Date();
    Date fechaV = new Date();
    String num = request.getParameter("cuenta");
    String banco = request.getParameter("banco");
    double carga = 1000;
    String dir = request.getParameter("direccion");
    int idE = -1;

        
    String operacion = request.getParameter("operacion");
    if(operacion == "" || operacion == null){ // El menu nos envia un parametro para indicar el inicio de una transaccion 
      desplegarRegistro();
    } else if(operacion.equals("registrar")){
        //String tipo = request.getParameter("tipo");
        //int id = (int)session.getAttribute("idcuenta");
        cc = new ControlRegistro();
        if (request.getParameter("tipoC").equals("Normal")){
           if (cc.crearSuscripcionNormal(nombre,apellido,email,passwd,fechaC,esC,esE,esN,tipo,fechaR,fechaV,num,banco,carga,dir,idE) != -1) {
            out.println("<p>La cuenta ha sido creada exitosamente.</p>");
           } else {
            out.println("<p>Hubo un error, la cuenta no se pudo crear.</p>");
           }    
        } else {

             Vector<Suscriptor> vecSus = new Vector<Suscriptor>();
          vecSus.add(new Suscriptor( request.getParameter("nombreD0"), request.getParameter("apellidoD0"), request.getParameter("correoD0"), 
            passwd,fechaC,esC,!esE,esN,tipo,fechaR,fechaV,num,banco,carga,dir,idE) );
          vecSus.add(new Suscriptor( request.getParameter("nombreD1"), request.getParameter("apellidoD1"), request.getParameter("correoD1"), 
            passwd,fechaC,esC,!esE,esN,tipo,fechaR,fechaV,num,banco,carga,dir,idE) );
          vecSus.add(new Suscriptor( request.getParameter("nombreD2"), request.getParameter("apellidoD2"), request.getParameter("correoD2"), 
            passwd,fechaC,esC,!esE,esN,tipo,fechaR,fechaV,num,banco,carga,dir,idE) );
          vecSus.add(new Suscriptor( request.getParameter("nombreD3"), request.getParameter("apellidoD3"), request.getParameter("correoD3"), 
            passwd,fechaC,esC,!esE,esN,tipo,fechaR,fechaV,num,banco,carga,dir,idE) );
          vecSus.add(new Suscriptor( request.getParameter("nombreD4"), request.getParameter("apellidoD4"), request.getParameter("correoD4"), 
            passwd,fechaC,esC,!esE,esN,tipo,fechaR,fechaV,num,banco,carga,dir,idE) );

           if (cc.crearSuscripcionCorporativa(nombre,apellido,email,passwd,fechaC,esC,esE,esN,tipo,fechaR,fechaV,num,banco,carga,dir,idE,vecSus) == true) {
            out.println("<p>Las cuentas han sido creadas exitosamente.</p>");
           } else {
            out.println("<p>Hubo un error, las cuentas no se pudieron crear.</p>");
           }
        }
        out.println("<a href=\"index.html\">Regresar a Inicio</a>");
        out.println("</BODY>");
        out.println("</HTML>");
    }
    
  }
  
  public void desplegarRegistro(){  
    
    out.println("<form method=\"GET\" action=\"Registro\" id=\"formaRegistro\">");
      out.println("<input type=\"hidden\" name=\"operacion\" value=\"registrar\"/>");     // input escondido

      out.println("<p>Nombre</p>"); // letrero de correo
      out.println("<input type=\"text\" name=\"nombre\"/>"); // input de correo

      out.println("<p>Apellido</p>"); // letrero de correo
      out.println("<input type=\"text\" name=\"apellido\"/>"); // input de correo

      out.println("<p>Correo electronico</p>"); // letrero de passwd
      out.println("<input type=\"text\" name=\"correo\"/>"); // input de passwd

      out.println("<p>Contrasenia</p>"); // letrero de passwd
      out.println("<input type=\"text\" name=\"contra\"/>"); // input de passwd

      out.println("<p>Tipo de cuenta</p>"); // letrero de passwd
      out.println("<select name=\"tipo\" form=\"formaRegistro\">");
        out.println("<option selected value=\"electronica\">Electronica</option>");
        out.println("<option value=\"fisica\">Fisica</option>");
        out.println("<option value=\"ambos\">Ambos</option>");
      out.println("</select>"); // input de passwd

      out.println("<p>Tipo de cuenta (Corporativa o Normal)</p>"); // letrero de passwd
      out.println("<select name=\"tipoC\" form=\"formaRegistro\">");
        out.println("<option selected value=\"Corporativa\">Corporativa</option>");
        out.println("<option value=\"Normal\">Normal</option>");
      out.println("</select>"); // input de passwd

      out.println("<p>Numero de cuenta</p>"); // letrero de passwd
      out.println("<input type=\"text\" name=\"cuenta\"/>"); // input de passwd

      out.println("<p>Banco</p>"); // letrero de passwd
      out.println("<input type=\"text\" name=\"banco\"/>"); // input de passwd

      out.println("<p>Direccion</p>"); // letrero de passwd
      out.println("<input type=\"text\" name=\"direccion\"/>"); // input de passwd

      out.println("<table>");
        out.println("<tr>");
          out.println("<td>Nombre</td>"); // input de passwd
          out.println("<td>Apellido</td>"); // input de passwd
          out.println("<td>Correo</td>"); // input de passwd
        out.println("<tr>");
        out.println("<tr>");
          out.println("<td><input type=\"text\" name=\"nombreD0\"/></td>"); // input de passwd
          out.println("<td><input type=\"text\" name=\"apellidoD0\"/></td>"); // input de passwd
          out.println("<td><input type=\"text\" name=\"correoD0\"/></td>"); // input de passwd
        out.println("<tr>");
        out.println("<tr>");
          out.println("<td><input type=\"text\" name=\"nombreD1\"/></td>"); // input de passwd
          out.println("<td><input type=\"text\" name=\"apellidoD1\"/></td>"); // input de passwd
          out.println("<td><input type=\"text\" name=\"correoD1\"/></td>"); // input de passwd
        out.println("<tr>");
        out.println("<tr>");
          out.println("<td><input type=\"text\" name=\"nombreD2\"/></td>"); // input de passwd
          out.println("<td><input type=\"text\" name=\"apellidoD2\"/></td>"); // input de passwd
          out.println("<td><input type=\"text\" name=\"correoD2\"/></td>"); // input de passwd
        out.println("<tr>");
        out.println("<tr>");
          out.println("<td><input type=\"text\" name=\"nombreD3\"/></td>"); // input de passwd
          out.println("<td><input type=\"text\" name=\"apellidoD3\"/></td>"); // input de passwd
          out.println("<td><input type=\"text\" name=\"correoD3\"/></td>"); // input de passwd
        out.println("<tr>");
        out.println("<tr>");
          out.println("<td><input type=\"text\" name=\"nombreD4\"/></td>"); // input de passwd
          out.println("<td><input type=\"text\" name=\"apellidoD4\"/></td>"); // input de passwd
          out.println("<td><input type=\"text\" name=\"correoD4\"/></td>"); // input de passwd
        out.println("<tr>");
      out.println("</table>");

      out.println("<p><input type=\"submit\" value=\"Enviar\"></p>");
    out.println("</form>");
 
    out.println("</BODY>");
    out.println("</HTML>");    
  }
  
  /*public void validarCuenta(String ema, String psw){  
    cc = new ControlValidacion();
    //La funcion trim() elimina espacios antes y despues del valor
    int idcuenta = cc.validarCuenta(ema,psw);
    if (idcuenta == -1){
       desplegarLogin();    
    } else {
       desplegarInicio();
    }
  }*/ 
   
}
