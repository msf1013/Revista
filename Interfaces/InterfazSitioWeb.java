package interfaces;

import controles.ControlMostrarResumenesDeArticulos;

import controles.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class InterfazSitioWeb extends HttpServlet {
  HttpServletResponse thisResponse;
  HttpServletRequest thisRequest;
  PrintWriter out;
  ControlMostrarResumenesDeArticulos mostrarResumenes;
  
  //Es importante observar que todos los metodos definen la operacion GET para
  //que el metodo doGet sea el que se ejecuta al presionar el boton "Enviar". 
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    mostrarResumenes = new ControlMostrarResumenesDeArticulos();

    thisResponse = response;
    thisRequest = request;
    thisResponse.setContentType("text/html");
    out = thisResponse.getWriter();

    //Preparar el encabezado de la pagina Web de respuesta
    out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
    out.println("<HTML>");
    out.println("<HEAD>");
    out.println("<META http-equiv=Content-Type content=\"text/html\">");
    out.println("</HEAD>");
    out.println("<BODY>");
    out.println("<table border=\"1\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td style=\"width:20%; vertical-align:middle;\"><h3 style=\"display:inline\">SEB&B</h3></td>");
    out.println("<td bgcolor=\"#b9b9b9\" style=\"width:20%; vertical-align:middle;\">");
    out.println("<button style=\"width:100%; background-color:transparent\" type=\"button\"><b>Resumenes</b></button>");
    out.println("</td>");
    out.println("<td style=\"width:20%; vertical-align:middle;\">");
    out.println("<button style=\"width:100%; background-color:transparent\" type=\"button\">Login</button>");
    out.println("</td>");
    out.println("<td>");
    out.println("<input name=\"keyword\" type=\"text\" placeholder=\"Search\" style=\"width:50%\">");
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");

    desplegarInterfazSitioWeb();
  }

  public void desplegarInterfazSitioWeb(){
    out.println("<table border=\"1\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td style=\"width:100%\">");
    out.println("<form>");
    out.println("<fieldset>");
    out.println("<legend style=\"text-align:center\"><h3>Articulos</h3></legend>");
    out.println("<table border=\"0\" style=\"width:100%; text-align:left;\">");

    Vector<String> resumenes = mostrarResumenes.mostrarResumenes();

    for(int i = 0; i < resumenes.size(); i += 3){
      out.println("<tr>");
      out.println("<td style=\"width:100%;\">");
      out.println("<form>");
      out.println("<fieldset>");
      out.println("<legend style=\"text-align:center\"><b>" + resumenes.elementAt(i) +"</b></legend>");
      out.println("<p><i>" + resumenes.elementAt(i + 1) + "</i></p>");
      out.println("<p>" + resumenes.elementAt(i + 2) + "</p>");
      out.println("</fieldset>");
      out.println("</form>");
      out.println("</td>");
      out.println("</tr>");
    }

    out.println("</table>");
    out.println("</fieldset>");
    out.println("</form>");
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");
  }

}