package RDTO.Controles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDatos {
  private  static Connection conn = null;
  private  static Propiedades prop= new Propiedades();
 
  
  public static Connection getConnection() {
	  try {
		  Class.forName(prop.getValue("dbDriver"));
          conn = DriverManager.getConnection(prop.getValue("dbUrl"), prop.getValue("dbUser"), prop.getValue("dbPassword"));
	  }catch (SQLException | ClassNotFoundException e) {
          System.out.println("Error: " + e.getMessage());
      }
	return conn;
  }
 
}
