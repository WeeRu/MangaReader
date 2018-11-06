package RDTO.Servelt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import RDTO.Controles.*;

@WebServlet("/Registro")
public class Registro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private  static Propiedades prop= new Propiedades();
    private  static BaseDatos BD= new BaseDatos();
	 
    public Registro() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	

		ObjectMapper objMapper = new ObjectMapper();
    try {
    																																																																																																		Respuesta<ClaseJson> resp = new Respuesta<>();
    	ClaseJson ClaseJson = objMapper.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), ClaseJson.class);
        resp.setData(ClaseJson);
        String res = objMapper.writeValueAsString(resp);
        System.out.println(objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resp));
        response.getWriter().print(res);
        
     	}catch(IOException e){
		 System.out.print("ERROR EN DOGET"+e.getMessage());
	     }
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ObjectMapper objMapper = new ObjectMapper();
    try {
    	//variables
    	Respuesta<ClaseJson> resp = new Respuesta<>();
    	
    	//Mapeado ---   recibe la respuesta-lee por lineas--crea un colector-- los agregas a ese colector --- y la separa por lineas-- los ingresa a la clasejson
    	ClaseJson ClaseJson = objMapper.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), ClaseJson.class);
        resp.setData(ClaseJson);
        String clave =ClaseJson.getClave();
        String nombre =ClaseJson.getNombre();
        String usuario =ClaseJson.getUsuario();
        String email =ClaseJson.getEmail();
     
        String res = objMapper.writeValueAsString(resp);
        
        System.out.println(objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resp));
        response.getWriter().print(res);
        System.out.print(ClaseJson.Clave+" "+ClaseJson.Nombre+" "+ClaseJson.Email+" "+ClaseJson.Usuario);
       
        
            PreparedStatement stat = null;
        	String signupQuery = prop.getValue("query_NuevoUsuario");
        	stat = BaseDatos.getConnection().prepareStatement(signupQuery);
        	stat.setString(1,clave);
        	stat.setString(2, usuario);
        	stat.setString(3, nombre);
        	stat.setTimestamp(4, getTiempo() );
        	stat.setString(5, email);
        	stat.executeUpdate();
            resp.setMessage("Exito en en registro");
            System.out.print(resp);
	}catch(SQLException e){
		System.out.print("ERROR EN DOPOST= "+e.getMessage());
	}
		
	}
	private static java.sql.Timestamp getTiempo() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}
}
