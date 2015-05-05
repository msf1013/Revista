package interfaces;

import controles.ControlMostrarContenidoDeRevista;

import controles.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class InterfazArticulos extends HttpServlet {
  HttpServletResponse thisResponse;
  HttpServletRequest thisRequest;
  PrintWriter out;
  ControlMostrarContenidoDeRevista mostrarRevista;

  String titulo;
  
  //Es importante observar que todos los metodos definen la operacion GET para
  //que el metodo doGet sea el que se ejecuta al presionar el boton "Enviar". 
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    mostrarRevista = new ControlMostrarContenidoDeRevista();

    thisResponse = response;
    thisRequest = request;
    thisResponse.setContentType("text/html");
    out = thisResponse.getWriter();

    HttpSession session = request.getSession(true);

    if(!session.getAttribute("tipocuenta").equals("Suscriptor") && !session.getAttribute("tipocuenta").equals("Escritor") && !session.getAttribute("tipocuenta").equals("Juez")){
      out.println("<p>Lo sentimos, usted no tiene permisos.</p>");
      out.println("<a href=\"index.html\">Regresar a Inicio</a>");
    }
    else{
      //Preparar el encabezado de la pagina Web de respuesta
      out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
      out.println("<HTML>");
      out.println("<HEAD>");
      out.println("<META http-equiv=Content-Type content=\"text/html\">");
      out.println("</HEAD>");
      out.println("<BODY>");
      out.println("<table border=\"1\" style=\"width:100%; text-align:center;\">");
      out.println("<tr>");
      out.println("<td style=\"width:12%; vertical-align:middle;\"><h3 style=\"display:inline\">SEB&B</h3></td>");
      out.println("<td style=\"width:12%; vertical-align:middle;\">");
      out.println("<button style=\"width:100%; background-color:transparent\" type=\"button\">Perfil</button>");
      out.println("</td>");
      out.println("<td bgcolor=\"#b9b9b9\" style=\"width:12%; vertical-align:middle;\">");
      out.println("<a href=\"Revista\">");
      out.println("<button style=\"width:100%; background-color:transparent\" type=\"button\"><b>Revista</b></button>");
      out.println("</a>");
      out.println("</td>");
      out.println("<td style=\"width:12%; vertical-align:middle;\">");
      out.println("<a href=\"Solicitudes\">");
      out.println("<button style=\"width:100%; background-color:transparent\" type=\"button\">Solicitudes</button>");
      out.println("</a>");
      out.println("</td>");
      out.println("<td style=\"width:12%; vertical-align:middle;\">");
      out.println("<button style=\"width:100%; background-color:transparent\" type=\"button\">Logout</button>");
      out.println("</td>");
      out.println("<td style=\"width:40%\">");
      out.println("<input name=\"keyword\" type=\"text\" placeholder=\"Search\" style=\"width:50%\">");
      out.println("</td>");
      out.println("</tr>");
      out.println("</table>");

      titulo = request.getParameter("titulo");
      if(titulo == null){
        desplegarSitioWebRevista();
      }
      else{
        desplegarArticulo();
      }
    }
  }

  public void desplegarSitioWebRevista(){
    out.println("<table border=\"1\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td style=\"width:100%\">");
    out.println("<form>");
    out.println("<fieldset>");
    out.println("<legend style=\"text-align:center\"><h3>Articulos</h3></legend>");
    out.println("<table border=\"0\" style=\"width:100%; text-align:left;\">");

    Vector<String> articulos = mostrarRevista.mostrarArticulos();

    for(int i = 0; i < articulos.size(); i += 3){
      out.println("<tr>");
      out.println("<td style=\"width:100%;\">");
      out.println("<form>");
      out.println("<fieldset>");
      out.println("<legend style=\"text-align:center\"><b>" + articulos.elementAt(i) +"</b></legend>");
      out.println("<table border=\"0\" style=\"width:100%;\">");
      out.println("<tr>");
      out.println("<td>");
      out.println("<p><i>" + articulos.elementAt(i + 1) + "</i></p>");
      out.println("<p>Abstract</p>");
      out.println("<p>" + articulos.elementAt(i + 2) + "</p>");
      out.println("</td>");
      out.println("</tr>");
      out.println("<tr>");
      out.println("<td style=\"text-align:center\">");
      out.println("<form method=\"GET\" action=\"Revista\">");
      out.println("<input type=\"hidden\" name=\"titulo\" value=\"" + articulos.elementAt(i) + "\">");
      out.println("<input type=\"submit\" style=\"width:10%\" value=\"Ir a Articulo\">");
      out.println("</form>");
      out.println("</td>");
      out.println("</tr>");
      out.println("</table>");
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
    out.println("<table border=\"1\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td>");
    out.println("<table border=\"0\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td>");
    out.println("<a href=\"menu.html\">Acerca de nosotros</a>");
    out.println("</td>");
    out.println("<td>");
    out.println("<a href=\"menu.html\">Equipo de trabajo</a>");
    out.println("</td>");
    out.println("<td>");
    out.println("<a href=\"menu.html\">Politicas y condiciones</a>");
    out.println("</td>");
    out.println("<td>");
    out.println("<a href=\"menu.html\">Informacion de contacto</a>");
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");
  }

  public void desplegarArticulo(){
    out.println("<table border=\"1\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td style=\"width:100%\">");
    out.println("<fieldset>");
    out.println("<legend style=\"text-align:center\"><h1>" + titulo +"</h2></legend>");
    out.println("<table border=\"0\" style=\"width:100%;\">");
    out.println("<tr>");
    out.println("<td style=\"text-align:center\">");

    Vector<String> articulo = mostrarRevista.mostrarArticulo(titulo);

    out.println("<p><h2>" + articulo.elementAt(0) + "</h2></p>");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td>");
    out.println("<p>" + articulo.elementAt(1) + "</p>");

    out.println("</td>");
    out.println("<tr>");
    out.println("<td style=\"text-align:center\">");
    out.println("<form method=\"GET\" action=\"Revista\">");
    out.println("<input type=\"submit\" style=\"width:10%\" value=\"Regresar\">");
    out.println("</form>");
    out.println("</td>");
    out.println("</tr>");
    out.println("</tr>");
    out.println("</table>");
    out.println("</fieldset>");
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");
  }

}