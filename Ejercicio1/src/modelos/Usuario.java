package modelos;

public class Usuario {
	private int idusuario;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String dni;
	private boolean activo;
  
	public Usuario() {
		
	}

	public Usuario( int idusuario,String nombre, String apellido1, String apellido2, String dni) {
		super();
		this.idusuario = idusuario;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.dni = dni;
	}
	
	public Usuario(String nombre, String apellido1, String apellido2, String dni) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.dni = dni;
    }
	
	
	public int getIdusuario() {
		return idusuario;
	}
	
	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido1() {
		return apellido1;
	}
	
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	
	public String getApellido2() {
		return apellido2;
	}
	
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	} 
	
	public boolean isActivo() { // Método requerido por el test
        return activo;
    }

    public void setActivo(boolean activo) { //Método requerido por el test
        this.activo = activo;
    }
	
	
	
	@Override
	public String toString() {
		return "Usuario [idusuario=" + idusuario + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2="
				+ apellido2 + ", dni=" + dni + "]";
	}
	  
	  
	}
