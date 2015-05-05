package interfaces;

import controles.ControlCrearOrdenDeEnvio;

import controles.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class InterfazSolicitudNumerosAnteriores extends HttpServlet {
  HttpServletResponse thisResponse;
  HttpServletRequest thisRequest;
  PrintWriter out;
  ControlCrearOrdenDeEnvio ordenEnvio;
  boolean bAceptado;
  
  //Es importante observar que todos los metodos definen la operacion GET para
  //que el metodo doGet sea el que se ejecuta al presionar el boton "Enviar". 
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ordenEnvio = new ControlCrearOrdenDeEnvio();

    thisResponse = response;
    thisRequest = request;
    thisResponse.setContentType("text/html");
    out = thisResponse.getWriter();

    HttpSession session = request.getSession(true);

    if(!(session.getAttribute("tipocuenta")).equals("Suscriptor")){
      out.println("<p>Lo sentimos, usted no tiene permisos de Suscriptor.</p>");
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
        out.println("<td style=\"width:12%; vertical-align:middle;\">");
        out.println("<a href=\"Revista\">");
        out.println("<button style=\"width:100%; background-color:transparent\" type=\"button\">Revista</button>");
        out.println("</a>");
        out.println("</td>");
        out.println("<td bgcolor=\"#b9b9b9\" style=\"width:12%; vertical-align:middle;\">");
        out.println("<a href=\"Solicitudes\">");
        out.println("<button style=\"width:100%; background-color:transparent\" type=\"button\"><b>Solicitudes</b></button>");
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

        desplegarSolicitudNumeros();
    }
  }

  //Es importante observar que todos los metodos definen la operacion POST para
  //que el metodo doGet sea el que se ejecuta al presionar el boton "Enviar". 
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ordenEnvio = new ControlCrearOrdenDeEnvio();
    bAceptado = true;

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
    out.println("<td style=\"width:12%; vertical-align:middle;\"><h3 style=\"display:inline\">SEB&B</h3></td>");
    out.println("<td style=\"width:12%; vertical-align:middle;\">");
    out.println("<button style=\"width:100%; background-color:transparent\" type=\"button\">Perfil</button>");
    out.println("</td>");
    out.println("<td style=\"width:12%; vertical-align:middle;\">");
    out.println("<a href=\"Revista\">");
    out.println("<button style=\"width:100%; background-color:transparent\" type=\"button\">Revista</button>");
    out.println("</a>");
    out.println("</td>");
    out.println("<td bgcolor=\"#b9b9b9\" style=\"width:12%; vertical-align:middle;\">");
    out.println("<a href=\"Solicitudes\">");
    out.println("<button style=\"width:100%; background-color:transparent\" type=\"button\"><b>Solicitudes</b></button>");
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

    String numeroCuenta = request.getParameter("numeroCuenta");
    String nip = request.getParameter("NIP");
    String direccion = request.getParameter("direccion");
    if(numeroCuenta == null || numeroCuenta.equals("") || nip == null || nip.equals("") || direccion == null || direccion.equals("")){
      bAceptado = false;
    }

    desplegarSolicitudRealizada();
  }

  public void desplegarSolicitudNumeros(){
    out.println("<table border=\"1\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td style=\"width:100%\">");
    out.println("<form method=\"POST\" action=\"Solicitudes\">");
    out.println("<fieldset>");
    out.println("<legend style=\"text-align:center\"><h1>Solicitar numeros de revista</h1></legend>");
    out.println("<table border=\"0\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td style=\"width:30%\">");
    out.println("</td>");
    out.println("<td style=\"width:40%\">");
    out.println("<table border=\"1\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td style=\"width:50%\">");
    out.println("<b>Edicion</b>");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<b>Numero de copias</b>");
    out.println("</td>");
    out.println("</tr>");

    Vector<String> revistas = ordenEnvio.obtenerRevistas();

    for(int i = 0; i < revistas.size(); i += 4){
      out.println("<tr>");
      out.println("<td style=\"width:50%; text-align:left\">");
      out.println(revistas.elementAt(i + 1) + " de " + revistas.elementAt(i + 2));
      out.println("</td>");
      out.println("<td style=\"width:50%\">");
      out.println("<input name=\"" + revistas.elementAt(i) + "\" type=\"number\" style=\"width:15%\" min=\"0\" max=\"10\" value=\"0\">");
      out.println("</td>");
      out.println("</tr>");
    }

    out.println("</table>");
    out.println("</td>");
    out.println("<td style=\"width:30%\">");
    out.println("</td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<td style=\"width:30%\">");
    out.println("</td>");
    out.println("<td>");
    out.println("<table border=\"0\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td style=\"width:50%; text-align:right\">");
    out.println("<p></p>");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<p></p>");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td style=\"width:50%\">");
    out.println("<input type=\"radio\" name=\"card\" value=\"MasterCard\" checked>MasterCard");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<input type=\"radio\" name=\"card\" value=\"Visa\">Visa");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td style=\"width:50%; text-align:right\">");
    out.println("<p></p>");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<p></p>");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td style=\"width:50%; text-align:right\">");
    out.println("Numero de cuenta:");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<input name=\"numeroCuenta\" type=\"text\" style=\"width:95%\" placeholder=\"56182289901\" maxlength=\"16\">");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td style=\"width:50%; text-align:right\">");
    out.println("NIP:");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<input name=\"NIP\" type=\"password\" style=\"width:95%\" placeholder=\"****\" maxlength=\"4\">");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td style=\"width:50%; text-align:right\">");
    out.println("Direccion de Entrega:");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<input name=\"direccion\" type=\"text\" style=\"width:95%\" placeholder=\"Av. Eugenio Garza Sada No. 1500\">");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td style=\"width:50%; text-align:right\">");
    out.println("<p></p>");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<p></p>");
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");
    out.println("<td style=\"width:30%\">");
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");

    out.println("<table border=\"0\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td>");
    out.println("<input type=\"submit\" style=\"width:10%\" value=\"Confirmar\">");
    out.println("</input>");
    out.println("</td>");
    out.println("</tr>");
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

  public void desplegarSolicitudRealizada(){
    out.println("<table border=\"1\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td style=\"width:100%\">");
    out.println("<form method=\"POST\" action=\"Solicitudes\">");
    out.println("<fieldset>");
    out.println("<legend style=\"text-align:center\"><h1>Solicitar numeros de revista</h1></legend>");
    out.println("<table border=\"0\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td style=\"width:30%\">");
    out.println("</td>");
    out.println("<td style=\"width:40%\">");
    out.println("<table border=\"1\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td style=\"width:50%\">");
    out.println("<b>Edicion</b>");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<b>Numero de copias</b>");
    out.println("</td>");
    out.println("</tr>");

    Vector<String> revistas = ordenEnvio.obtenerRevistas();
    Vector<java.sql.Date> vecFecha = new Vector<java.sql.Date>();
    Vector<Integer> vecCant = new Vector<Integer>();
    int cantidad;

    for(int i = 0; i < revistas.size(); i += 4){
      out.println("<tr>");
      out.println("<td style=\"width:50%; text-align:left\">");
      out.println(revistas.elementAt(i + 1) + " de " + revistas.elementAt(i + 2));
      out.println("</td>");
      out.println("<td style=\"width:50%\">");
      out.println("<input name=\"" + revistas.elementAt(i) + "\" type=\"number\" style=\"width:15%\" min=\"0\" max=\"10\" value=\"0\">");
      out.println("</td>");
      out.println("</tr>");

      vecFecha.add(java.sql.Date.valueOf(revistas.elementAt(i + 3)));
    }

    int totalPedidas = 0;
    for(int i = 0; i < revistas.size(); i += 4){
      cantidad = Integer.parseInt(thisRequest.getParameter(revistas.elementAt(i)));
      totalPedidas += cantidad;
      vecCant.add(cantidad);
    }

    ordenEnvio.crearOrden(5, vecFecha, vecCant, totalPedidas * 50);

    out.println("</table>");
    out.println("</td>");
    out.println("<td style=\"width:30%\">");
    out.println("</td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<td style=\"width:30%\">");
    out.println("</td>");
    out.println("<td>");
    out.println("<table border=\"0\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td style=\"width:50%; text-align:right\">");
    out.println("<p></p>");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<p></p>");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td style=\"width:50%\">");
    out.println("<input type=\"radio\" name=\"card\" value=\"MasterCard\" checked>MasterCard");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<input type=\"radio\" name=\"card\" value=\"Visa\">Visa");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td style=\"width:50%; text-align:right\">");
    out.println("<p></p>");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<p></p>");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td style=\"width:50%; text-align:right\">");
    out.println("Numero de cuenta:");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<input name=\"numeroCuenta\" type=\"text\" style=\"width:95%\" placeholder=\"56182289901\" maxlength=\"16\">");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td style=\"width:50%; text-align:right\">");
    out.println("NIP:");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<input name=\"NIP\" type=\"password\" style=\"width:95%\" placeholder=\"****\" maxlength=\"4\">");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td style=\"width:50%; text-align:right\">");
    out.println("Direccion de Entrega:");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<input name=\"direccion\" type=\"text\" style=\"width:95%\" placeholder=\"Av. Eugenio Garza Sada No. 1500\">");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td style=\"width:50%; text-align:right\">");
    out.println("<p></p>");
    out.println("</td>");
    out.println("<td style=\"width:50%\">");
    out.println("<p></p>");
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");
    out.println("<td style=\"width:30%\">");
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");

    out.println("<table border=\"0\" style=\"width:100%; text-align:center;\">");
    out.println("<tr>");
    out.println("<td>");
    out.println("<input type=\"submit\" style=\"width:10%\" value=\"Confirmar\">");
    out.println("</input>");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td>");
    out.println("<p></p>");
    out.println("</td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td>");
    if(!bAceptado || totalPedidas == 0){
      out.println("<b><font color=\"red\">No se pudo realizar pedido porque falta informacion.</font></b>");
    }
    else{
      out.println("<b><font color=\"green\">Pedido realizado exitosamente</font></b>");
    }
    out.println("</td>");
    out.println("</tr>");
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

}