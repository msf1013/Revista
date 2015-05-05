package interfaces;
import controles.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.Date;
//import controles.ControlRegistro;

public class InterfazEnvioArticulo extends HttpServlet {
  HttpServletResponse thisResponse;
  HttpServletRequest thisRequest;
  PrintWriter out;
  ControlEnvioArticulo cc;
  
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
    out.println("<h2>Envio de articulo</h2>");
    //out.println("<h3>Consultar saldo</h3>");

    String titulo = request.getParameter("titulo");
    String texto = request.getParameter("texto");
    String abstra = request.getParameter("abstra");
    int dia=0, mes=0, anio=0;
    if (request.getParameter("dia") != "" && request.getParameter("dia") != null)
      dia = Integer.parseInt(request.getParameter("dia"));
    if (request.getParameter("mes") != "" && request.getParameter("mes") != null)
      mes = Integer.parseInt(request.getParameter("mes")) - 1;
    if (request.getParameter("anio") != "" && request.getParameter("anio") != null)
      anio = Integer.parseInt(request.getParameter("anio")) - 1900;
            
    String operacion = request.getParameter("operacion");
    if(operacion == "" || operacion == null){ // El menu nos envia un parametro para indicar el inicio de una transaccion 
      desplegarEnvioArticulo();
    } else if(operacion.equals("enviar")){
        //String tipo = request.getParameter("tipo");
        int id = (int)session.getAttribute("idcuenta");
        cc = new ControlEnvioArticulo();
        if (session.getAttribute("tipocuenta").equals("Escritor")){
              if ( cc.crearArticulo(titulo,texto,abstra,new Date(anio,mes,dia),"Pendiente",new Date(),false,id) != -1 ) {
                out.println("<p>Gracias, su articulo ha sido propuesto.</p>");
              } else {
                out.println("<p>Lo sentimos, el articulo no pudo ser enviado.</p>");
              }
              out.println("<a href=\"index_escritor.html\">Regresar a Pantalla de Escritor</a>");
        } else {
          out.println("<p>Lo sentimos, usted no tiene permiso de envio de articulo.</p>");
          out.println("<a href=\"index.html\">Regresar a Inicio</a>");
        }
        out.println("</BODY>");
        out.println("</HTML>");
    }
    
  }
  
  public void desplegarEnvioArticulo(){  
    
    out.println("<form method=\"GET\" action=\"EnvioArticulo\" id=\"formaEnvioArticulo\">");
      out.println("<input type=\"hidden\" name=\"operacion\" value=\"enviar\"/>");     // input escondido

      out.println("<p>Titulo</p>"); // letrero de correo
      out.println("<input type=\"text\" name=\"titulo\"/>"); // input de correo

      out.println("<p>Abtract</p>"); // letrero de correo
      out.println("<textarea rows=\"5\" cols=\"50\" name=\"abstra\"></textarea>"); // input de correo

      out.println("<p>Texto</p>"); // letrero de correo
      out.println("<textarea rows=\"10\" cols=\"50\" name=\"texto\"></textarea>");

      out.println("<p>Anio de publicacion</p>"); // letrero de passwd
      out.println("<input type=\"text\" name=\"anio\"/>"); // input de passwd

      out.println("<p>Mes de publicacion (numero)</p>"); // letrero de passwd
      out.println("<input type=\"text\" name=\"mes\"/>"); // input de passwd

      out.println("<p>Dia de publicacion</p>"); // letrero de passwd
      out.println("<input type=\"text\" name=\"dia\"/>"); // input de passwd

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
