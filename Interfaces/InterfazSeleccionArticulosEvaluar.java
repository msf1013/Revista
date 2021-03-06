package interfaces;

import controles.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import controles.ControlValidacion;

public class InterfazSeleccionArticulosEvaluar extends HttpServlet {
  HttpServletResponse thisResponse;
  HttpServletRequest thisRequest;
  PrintWriter out;
  ControlListadoArticulos controlListado;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    thisResponse = response;
    thisRequest = request;
    thisResponse.setContentType("text/html");

    HttpSession session = request.getSession(true);
    out = thisResponse.getWriter();

    if(session.getAttribute("idcuenta") == null || !session.getAttribute("tipocuenta").equals("Juez")) {
      out.println("<p>Lo sentimos, usted no tiene permisos de Juez.</p>");
      
      if (session.getAttribute("tipocuenta").equals("Escritor")) {
        out.println("<a href=\"index_escritor.html\">Regresar a Inicio</a>");
       }
       else if (session.getAttribute("tipocuenta").equals("Administrador")) {
        out.println("<a href=\"index_admin.html\">Regresar a Inicio</a>");
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
      out.println("<link href=\"//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css\" rel=\"stylesheet\">");
      out.println("<script src=\"//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>");
      out.println("</HEAD>");
      out.println("<BODY>");
      out.println("<TITLE>Revista: Articulos Pendientes</TITLE>");
      out.println("<h2>Articulos a Evaluar</h2>");
      out.println("<h3>Bienvenido a la revista</h3>");

      String id = session.getAttribute("idcuenta").toString();
      controlListado = new ControlListadoArticulos();

      Vector<String> articulosEvaluar = new Vector<String>();
      articulosEvaluar= controlListado.mostrarListaArticulosEvaluar(id);

      out.println("<div class=\"table-responsive\">");
      out.println("<table class=\"table table-striped\">");
      
      out.println("<thead>");
      out.println("<tr>");
      out.println("<th class=\"text-center\"> Nombre </th>");
      out.println("<th class=\"text-center\"> Autor </th>");
      out.println("<th></th>");
      out.println("</tr>");
      out.println("</thead>");

      out.println("<tbody>");
      for (int i = 0; i < articulosEvaluar.size(); i+=3) {
        out.println("<tr>");
        out.println("<td class=\"text-center\">" + articulosEvaluar.elementAt(i) + "</td>");
        out.println("<td class=\"text-center\">" + articulosEvaluar.elementAt(i + 1) + "</td>");
        out.println("<td><a class=\"btn btn-default btn-sm\" href=\"/Revista/VotarArticulo?articulo=" + articulosEvaluar.elementAt(i + 2) + "\">Mostrar</a></td>");
        out.println("</tr>");
      }

      out.println("</tbody>");
      out.println("</table>");
      out.println("</div>");
    }
  } 
}
