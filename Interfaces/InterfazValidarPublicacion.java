package interfaces;

import controles.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import controles.ControlValidacion;

public class InterfazValidarPublicacion extends HttpServlet {
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

    Vector<HashMap<String,String>> articulos = controlListado.mostrarListaArticulosPendientes();

    out.println("<div class=\"table-responsive\">");
    out.println("<table class=\"table table-striped\">");
    
    out.println("<thead>");
    out.println("<tr>");
    out.println("<th class=\"text-center\"> Nombre </th>");
    out.println("<th class=\"text-center\"> Autor </th>");
    out.println("<th class=\"text-center\"> Fecha Esperada </th>");
    out.println("<th></th>");
    out.println("</tr>");
    out.println("</thead>");

    out.println("<tbody>");
    for (int i = 0; i < articulos.size(); i++) {
      HashMap<String,String> articulo = articulos.elementAt(i);
      out.println("<tr>");
      out.println("<td class=\"text-center\">" + articulo.get("titulo") + "</td>");
      out.println("<td class=\"text-center\">" + articulo.get("autor") + "</td>");
      out.println("<td class=\"text-center\">" + articulo.get("fechaEsperada") + "</td>");
      out.println("<td><a class=\"btn btn-default btn-sm\" href=\"/Revista/AprobarArticulo?articulo=" + articulo.get("id") + "\">Mostrar</a></td>");
      out.println("</tr>");
    }

    out.println("</tbody>");
    out.println("</table>");
    out.println("</div>");
  } 
}
