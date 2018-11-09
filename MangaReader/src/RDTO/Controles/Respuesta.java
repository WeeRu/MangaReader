package RDTO.Controles;

public class Respuesta<T> {
	private String mensaje;
	private Integer estado;
	private T data;

	public Object getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return mensaje;
	}

	public void setMessage(String mensaje) {
		this.mensaje = mensaje;
	}

	public Integer getStatus() {
		return estado;
	}

	public void setStatus(Integer status) {
		this.estado = status;
	}

}
