package interfaces;

import controles.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import controles.ControlValidacion;

public class InterfazAprobarArticulo extends HttpServlet {
  HttpServletResponse thisResponse;
  HttpServletRequest thisRequest;
  PrintWriter out;
  ControlAprobarArticulo controlAprobarArticulo;
  
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
    int idArticulo = Integer.parseInt(thisRequest.getParameter("articulo").trim());
    controlAprobarArticulo = new ControlAprobarArticulo();
    HashMap<String,String> articulo = controlAprobarArticulo.getArticulo(idArticulo);

    String titulo = articulo.get("titulo");
    String abstractArticulo = articulo.get("abstract");
    String textoArticulo = articulo.get("texto");

    out.println("<div class=\"col-md-9\">");
    out.println("<h2>" + titulo + "</h2>");
    out.println("<p>" + abstractArticulo + "</p>");
    out.println("<p>" + textoArticulo + "</p>");
    out.println("</div>");


    out.println("<div class=\"col-md-3\">");
    out.println("<form method=post action=\"AprobarArticulo\">");
    out.println("<input type=\"hidden\" name=\"idArticulo\" value=\"" + idArticulo + "\"/>");
    out.println("<div class=\"form-group\">");
    out.println("<label>Aprobar:</label>"); // letrero de correo
    out.println("<select class=\"form-control\" name=\"aprobacion\">"); // input de correo
    out.println("<option>Aceptado</option>"); // letrero de passwd
    out.println("<option>Rechazado</option>"); // letrero de passwd
    out.println("</select>");
    out.println("</div>");
    out.println("<div class=\"form-group\">");
    out.println("<input type=\"submit\" value=\"Guardar\" class=\"btn btn-primary\">");
    out.println("</div>");
    out.println("</form>");
 
    out.println("<form method=\"GET\" action=\"ValidarPublicacion\">");
    out.println("<input type=\"submit\" value=\"Cancelar\" class=\"btn btn-default\">");
    out.println("</form>");
    out.println("</div>");
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();

        int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
        String aprobacion = request.getParameter("aprobacion");
        int aprobado = (aprobacion.equals("Aceptado")) ? 1 : 0;

        controlAprobarArticulo = new ControlAprobarArticulo();
        if (aprobado == 1) {
            controlAprobarArticulo.realizarAprobacion(idArticulo);
        }

        response.sendRedirect("ValidarPublicacion");
    }

}
