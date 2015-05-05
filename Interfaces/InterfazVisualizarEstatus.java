package interfaces;
import controles.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.Date;
//import controles.ControlRegistro;

public class InterfazVisualizarEstatus extends HttpServlet {
  HttpServletResponse thisResponse;
  HttpServletRequest thisRequest;
  PrintWriter out;
  ControlVisualizarEstatus cc;
  
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    thisResponse = response;
    thisRequest = request;
    thisResponse.setContentType("text/html");

    HttpSession session = request.getSession(true);
    out = thisResponse.getWriter();

    if(session.getAttribute("idcuenta") == null || !session.getAttribute("tipocuenta").equals("Escritor")) {
      out.println("<p>Lo sentimos, usted no tiene permisos de Escritor.</p>");
      
      if (session.getAttribute("tipocuenta").equals("Administrador")) {
        out.println("<a href=\"index_admin.html\">Regresar a Inicio</a>");
       }
       else if (session.getAttribute("tipocuenta").equals("Juez")) {
        out.println("<a href=\"index_juez.html\">Regresar a Inicio</a>");
       }
       else if (session.getAttribute("tipocuenta").equals("Suscriptor")) {
        out.println("<a href=\"index_suscriptor.html\">Regresar a Inicio</a>");
       }
       else {
        out.println("<a href=\"index.html\">Regresar a Inicio</a>");
       }
    } else {

      //Preparar el encabezado de la pagina Web de respuesta
      out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
      out.println("<HTML>");
      out.println("<HEAD>");
      out.println("<META http-equiv=Content-Type content=\"text/html\">");
      out.println("</HEAD>");
      out.println("<BODY>");
      out.println("<TITLE>Revista</TITLE>");
      out.println("<h2>Estatus de articulos enviados</h2>");
      //out.println("<h3>Consultar saldo</h3>");

      int id = (int)session.getAttribute("idcuenta");
      out.println(id);
          
      cc = new ControlVisualizarEstatus();
      if ((session.getAttribute("tipocuenta")).equals("Escritor")){
            out.println(cc.verArticulos(id));
            out.println("<a href=\"index_escritor.html\">Regresar a Pantalla de Escritor</a>");
      } else {
        out.println("<p>Lo sentimos, usted no tiene permisos de Escritor.</p>");
        out.println("<a href=\"index.html\">Regresar a Inicio</a>");
      }
      out.println("</BODY>");
      out.println("</HTML>");
    }
    
  }
     
}
