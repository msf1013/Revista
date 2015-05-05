package interfaces;

import controles.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import controles.ControlValidacion;

public class InterfazPublicarCarta extends HttpServlet {
   HttpServletResponse thisResponse;
   HttpServletRequest thisRequest;
   PrintWriter out;
   ControlCarta controlCarta;

   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      thisResponse = response;
      thisRequest = request;
      thisResponse.setContentType("text/html");
      HttpSession session = request.getSession(true);
      out = thisResponse.getWriter();

      if(session.getAttribute("idcuenta") == null || !session.getAttribute("tipocuenta").equals("Administrador")) {
         out.println("<p>Lo sentimos, usted no tiene permisos de Administrador.</p>");
      
      if (session.getAttribute("tipocuenta").equals("Escritor")) {
        out.println("<a href=\"index_escritor.html\">Regresar a Inicio</a>");
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
         out.println("<link href=\"//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css\" rel=\"stylesheet\">");
         out.println("<script src=\"//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>");
         out.println("</HEAD>");
         out.println("<BODY>");
         out.println("<TITLE>Revista: Articulos Pendientes</TITLE>");
         out.println("<h2>Carta</h2>");

         String id = session.getAttribute("idcuenta").toString();

         out.println("<p>Eres el usuario numero: " + id + "</p>");

         out.println("<h2>Carta del editor</h2>");
         out.println("<form method=post action=\"PublicarCarta\">");
         out.println("<div class=\"col-md-9\">"); 
         out.println("<div class=\"form-group\">");
         out.println("<textarea type=\"text\" name=\"carta\" class=\"form-control\"></textarea>");
         out.println("</div>");
         out.println("</div>");

         out.println("<div class=\"col-md-3\">");
         out.println("<input type=\"hidden\" name=\"idAdmin\" value=\"" + id + "\"/>");

         out.println("<div class=\"form-group\">");
         out.println("<label>Dia:</label>");
         out.println("<select class=\"form-control\" name=\"dia\">");
         for (int i = 1; i <= 31; i++) {
            out.println("<option>" + i + "</option>");
         }
         out.println("</select>");
         out.println("</div>");

         out.println("<div class=\"form-group\">");
         out.println("<label>Mes:</label>"); 
         out.println("<select class=\"form-control\" name=\"mes\">");
         for (int i = 1; i <= 12; i++) {
            out.println("<option>" + i + "</option>");
         }
         out.println("</select>");
         out.println("</div>");

         out.println("<div class=\"form-group\">");
         out.println("<label>Año:</label>");
         out.println("<select class=\"form-control\" name=\"anio\">");
         for (int i = 2015; i <= 2030; i++) {
            out.println("<option>" + i + "</option>");
         }
         out.println("</select>");
         out.println("</div>");

         out.println("<div class=\"form-group\">");
         out.println("<input type=\"submit\" value=\"Enviar\" class=\"btn btn-primary\">");
         out.println("</div>");
         out.println("</form>");

         out.println("<form method=\"GET\" action=\"Revista\">");
         out.println("<input type=\"submit\" value=\"Cancelar\" class=\"btn btn-default\">");
         out.println("</form>");
         out.println("</div>");
      }
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html");  
      PrintWriter out = response.getWriter();

      int dia = Integer.parseInt(request.getParameter("dia"));
      int mes = Integer.parseInt(request.getParameter("mes"));
      int anio = Integer.parseInt(request.getParameter("anio"));
      String carta = request.getParameter("carta").trim();

      java.sql.Date fecha = new java.sql.Date(anio, mes, dia);

      controlCarta = new ControlCarta();
      controlCarta.publicarCarta(fecha, carta);
      response.sendRedirect("Revista");

   }

}
