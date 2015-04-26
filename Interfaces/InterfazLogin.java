package interfaces;
import controles.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import controles.ControlValidacion;

public class InterfazConsulta extends HttpServlet {
  HttpServletResponse thisResponse;
  HttpServletRequest thisRequest;
  PrintWriter out;
  ControlValidacion cc;
  
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
    out.println("<h2>Login</h2>");
    //out.println("<h3>Consultar saldo</h3>");

    String email = session.getAttribute("email").toString();
    String passwd = session.getAttribute("passwd").toString();

        
    String operacion = request.getParameter("operacion");
    if(operacion == null){ // El menu nos envia un parametro para indicar el inicio de una transaccion
      desplegarLogin();  
    }else if(operacion.equals("validar")){
       cc = new ControlValidacion();
        //La funcion trim() elimina espacios antes y despues del valor
        int idcuenta = cc.validarCuenta(email,passwd);
        if (idcuenta == -1){
           desplegarLogin();    
        } else {
            session.setAttribute("idcuenta", idcuenta);
           desplegarInicio();
        }
    } 
    
  }
  
  public void desplegarLogin(){  
    
    out.println("<form method=\"GET\" action=\"Login\">");
    out.println("<input type=\"hidden\" name=\"operacion\" value=\"validar\"/>");     // input escondido
    out.println("<p>Ingrese su correo electronico</p>"); // letrero de correo
    out.println("<input type=\"text\" name=\"email\"/>"); // input de correo
    out.println("<p>Ingrese su contrasenia</p>"); // letrero de passwd
    out.println("<input type=\"text\" name=\"passwd\"/>"); // input de passwd
    out.println("<p><input type=\"submit\" value=\"Enviar\"></p>");
    out.println("</form>");
 
    out.println("<form method=\"GET\" action=\"menu.html\">");
    out.println("<p><input type=\"submit\" value=\"Cancelar\"></p>");
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
  
  public void desplegarInicio(){ 

    out.println("<p>"+session.getAttribute("idcuenta").toString();+"</p>");
    out.println("</BODY>");
    out.println("</HTML>");
        
  } 
   
   
}
