package RDTO.Controles;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Propiedades {
	
	private static Propiedades propr = new Propiedades();
	private Properties prop = new Properties();
	private InputStream input = null;

	
	public Propiedades() {
		try {
			input = new FileInputStream("C:\\Users\\Maven\\Eclipse-workspace\\ProyectoW2MR\\WebContent\\config.properties");
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Propiedades getInstance () {
		return propr;
	}
	public String getValue(String key){
		return prop.getProperty(key);
	}
}	
