package RDTO.Servelt;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import RDTO.Controles.BaseDatos;
import RDTO.Controles.ClaseJson;
import RDTO.Controles.Ecriptar;
import RDTO.Controles.Propiedades;
import RDTO.Controles.Respuesta;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private  static Propiedades prop= new Propiedades();
    private Ecriptar ecrip;
    private BaseDatos bd = new BaseDatos();
       
    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper objMapper = new ObjectMapper();
		HttpSession session;
		PreparedStatement stat = null;
		String loginQuery = prop.getValue("query_logIn");
		ClaseJson ClaseJson = objMapper.readValue(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), ClaseJson.class);
		Respuesta<ClaseJson> resp = new Respuesta<>();
        try { 
        	
        	String usuario =ClaseJson.getUsuario();
        	String clave =ClaseJson.getClave();
        	
        	ecrip = new Ecriptar(clave);
        	
        	stat = BaseDatos.getConnection().prepareStatement(loginQuery);
        	stat.setString(1, usuario);
            stat.setString(2, ecrip.returnEncrypt());
            ResultSet result = stat.executeQuery();
            if(result.next()) {
            	
            	int type_id = result.getInt("type_id");
            	if(checkUserType(type_id)) {
            		System.out.println("es un admin");
            		session = request.getSession();
            		session.setAttribute("usr", usuario);
            		session.setAttribute("tusr", "admin");
            		resp.setMessage("Login Exitoso");
                    resp.setStatus(200);
                    resp.setData(ClaseJson);
            	} else {
            		System.out.println("es un usuario");
            		session = request.getSession();
            		session.setAttribute("usr", usuario);
            		session.setAttribute("tusr", "user");
            		resp.setMessage("Login Exitoso");
                    resp.setStatus(404);
                    resp.setData(ClaseJson);
            	}
            } else {
            	resp.setMessage("Clave invalida");
                resp.setStatus(500);
                resp.setData(ClaseJson);
            }
            String res = objMapper.writeValueAsString(resp);
            System.out.println(objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resp));
            response.getWriter().print(res);
        } catch (SQLException e) {
           System.out.println("Error: "+e.getMessage());
        }
	}
	public boolean checkUserType(int type_id) {
        boolean isAdmin = false;
        isAdmin = ((type_id == 1) ? true : false);
        return isAdmin;
    }

}
