package RDTO.Controles;

public class ClaseJson {
  public String Clave;
  public String Usuario;
  public String Nombre;
  public String Email; 
  
  public String getNombre() {
	return Nombre;
  }  
  public String getUsuario() {
		return Usuario;
	  }
  public String getClave() {
	  return Clave;
  }
  public String getEmail() {
	  return Email;
  }
  public String setUsuario(String Usuario) {
		  return this.Usuario=Usuario;
	  }
  public String setClave(String Clave) {
		  return this.Clave=Clave;
	  }
  public String setCorreo(String Email) {
		  return this.Email=Email;
	  }
    
}

