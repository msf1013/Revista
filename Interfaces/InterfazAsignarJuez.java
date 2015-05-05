package interfaces;

import controles.*;
import entidades.CuentaAuxiliar;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class InterfazAsignarJuez extends HttpServlet {
    HttpServletResponse thisResponse;
    HttpServletRequest thisRequest;
    PrintWriter out;
    ControlGetJueces busquedaJueces;
    ControlAgregarJuez agregarJuez;
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
         thisResponse = response;
        thisRequest = request;
        thisResponse.setContentType("text/html");
        out = thisResponse.getWriter();
        
        agregarJuez = new ControlAgregarJuez();
        String nom = request.getParameter("nombreUsu");
        String ape = request.getParameter("apellidoUsu");
        String email = request.getParameter("emailUsu");
        String pass = request.getParameter("passUsu");
        
        String numJz = request.getParameter("numJuez");
        int numJuez = Integer.parseInt(numJz);
                
        int flag = agregarJuez.agregaJuez(nom, ape, email, pass, numJuez);
        
        if(flag>1){
            out.println("Se agrego el Juez correctamente");
        }
        else {
            out.println("Fallo la insercion del juez");
        }
       
    }
  
    //Es importante observar que todos los metodos definen la operacion GET para
    //que el metodo doGet sea el que se ejecuta al presionar el boton "Enviar".
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        //inicializando controlador
        busquedaJueces = new ControlGetJueces();
        
        
        thisResponse = response;
        thisRequest = request;
        thisResponse.setContentType("text/html");
        out = thisResponse.getWriter();
        
        HttpSession session = request.getSession(true);

        if(session.getAttribute("idcuenta") == null || !session.getAttribute("tipocuenta").equals("Administrador")) {
            out.println("<p>Lo sentimos, usted no tiene permisos de administrador.</p>");
            
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
        out.println("</HEAD>");
        out.println("<BODY>");
        out.println("<table border=\"1\" style=\"width:100%; text-align:center;\">");
        out.println("<tr>");
        out.println("<td style=\"width:20%; vertical-align:middle;\"><h3 style=\"display:inline\">SEB&B</h3></td>");
        out.println("<td bgcolor=\"#b9b9b9\" style=\"width:20%; vertical-align:middle;\">");
        out.println("<button style=\"width:100%; background-color:transparent\" type=\"button\"><b>Home</b></button>");
        out.println("</td>");
        out.println("<td style=\"width:20%; vertical-align:middle;\">");
        out.println("<button style=\"width:100%; background-color:transparent\" type=\"button\">Login</button>");
        out.println("</td>");
        out.println("<td>");
        out.println("<form method=\"GET\" action=\"\"><td>");
        out.println("<input name=\"keyword\" type=\"text\" placeholder=\"Buscar usuarios\" style=\"width:50%\">");
        out.println("</td></form>");
        out.println("</tr>");
        out.println("</table>");
        
        String juecesString = busquedaJueces.verJueces();
        desplegarInterfazSitioWeb(juecesString);
            
        }
        
        
         
               
    }
    
    public void desplegarInterfazSitioWeb(String jueces){
        out.println("<table border=\"1\" style=\"width:100%; text-align:center;\">");
        out.println("<tr>");
        out.println("<td style=\"width:100%\">");
        out.println("<fieldset>");
        out.println("<legend style=\"text-align:center\"><h3>ADMIN DE INSERTAR JUEZ</h3></legend>");
        out.println("<table border=\"0\" style=\"width:100%; text-align:left;\">");
        
        out.println("<tr>");
        out.println("<td style=\"width:100%;\">");
        out.println("<form method=\"POST\" action=\"AsignarJuez\">");
        out.println("<fieldset>");
        out.println("<input name=\"nombreUsu\" type=\"text\" placeholder=\"Nombre\" style=\"width:50%\">");
        out.println("<input name=\"apellidoUsu\" type=\"text\" placeholder=\"Apellidos\" style=\"width:50%\">");
        out.println("<input name=\"emailUsu\" type=\"text\" placeholder=\"Email\" style=\"width:50%\">");
        out.println("<input name=\"passUsu\" type=\"password\" placeholder=\"password\" style=\"width:50%\">");
        out.println("<input name=\"numJuez\" type=\"text\" placeholder=\"Numero de Juez\" style=\"width:50%\">");
        out.println("<button name=\"submit\" type=\"submit\" style=\"width:50%\">Agregar Juez</button>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</td>");
        out.println("</tr>");

        
        out.println(jueces);
        
               
        out.println("</table>");
        out.println("</fieldset>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
    }
    
}