package RDTO.Controles;

import java.sql.Connection;	
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.ResultSet;


public class BaseDatos {
	
  private  static Connection conn = null;
  private  static Propiedades prop= new Propiedades();
  private PreparedStatement pstmt;
  public boolean state = false;

  
  public static Connection getConnection() {
	  try {
		  Class.forName(prop.getValue("dbDriver"));
          conn = DriverManager.getConnection(prop.getValue("dbUrl"), prop.getValue("dbUser"), prop.getValue("dbPassword"));
	  }catch (SQLException | ClassNotFoundException e) {
          System.out.println("Error: " + e.getMessage());
      }
	return conn;
  }
  
  public boolean checkEmail(String email) {
		
		try {
			this.pstmt = conn.prepareStatement(prop.getValue("query_checkusersign"));
			this.pstmt.setString(1, email);
		    ResultSet rs = pstmt.executeQuery();
			boolean state1 = rs.next();
			if(state1) {
				System.out.println("  Email existes");
			} else{
				state = true;
				System.out.println("  No problema");
			}
		} catch (SQLException e) {
			e.getStackTrace();
		}
		return state;
	}
 
}
