package RDTO.Servelt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.net.ssl.SSLEngineResult.Status;
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
    private Ecriptar ecrip;
    private BaseDatos bd = new BaseDatos();
    
	 
    public Registro() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		/*	ObjectMapper objMapper = new ObjectMapper();
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
		
		*/
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int m = 0;
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
        //response.getWriter().print(res);
        System.out.print(ClaseJson.Clave+" "+ClaseJson.Nombre+" "+ClaseJson.Email+" "+ClaseJson.Usuario);
       
        
            PreparedStatement stat = null;
        	String signupQuery = prop.getValue("query_NuevoUsuario");
        	stat = BaseDatos.getConnection().prepareStatement(signupQuery);
        	PreparedStatement stats = BaseDatos.getConnection().prepareStatement(prop.getValue("query_chekNU"));
        	ecrip = new Ecriptar(clave);
        	ResultSet n = stats.executeQuery();
        	
        	
        	for (; n.next();) {
        		m =  ((Number) n.getObject(1)).intValue();
        		m++;
        		}
        	
        	stat.setInt(1, m);
        	stat.setString(2,ecrip.returnEncrypt());
        	stat.setString(3, usuario);
        	stat.setString(4, nombre);
        	stat.setTimestamp(5, getTiempo() );
        	bd.checkEmail(email);
        	boolean status= bd.state;
        	if(status==true) {
        	stat.setString(6, email);
        	stat.executeUpdate();
        	}else {
        		System.out.println("  el email yaa existe");
                resp.setMessage("Error con Email");
                stat.close();
        	}
        
        	
        	//if(stat.) {
        		//n++;
        	//}
           // resp.setMessage("Exito en en registro");
            System.out.print(resp);
            response.getWriter().print(resp.getMessage());
            
            
            
	}catch(SQLException e){
		System.out.print("ERROR EN DOPOST= "+e.getMessage());
	}
		
	}
	private static java.sql.Timestamp getTiempo() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}
}
