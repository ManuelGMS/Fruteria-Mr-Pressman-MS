package negocio.cliente;


public class TCliente {

	private int id;
	private String nombre;
	private int telefono;
	private String direccion;
	private boolean activo;
	private String dni;

	/*
	 * Constructor para leer clientes
	 */
	public TCliente(int id, String nombre, int telefono, String direccion,
			boolean activo, String dni) {
		this.id        = id;
		this.nombre    = nombre;
		this.telefono  = telefono;
		this.direccion = direccion;
		this.activo    = activo;
		this.dni       = dni;
	}

	/*
	 * Constructor para crear clientes
	 */
	public TCliente(String nombre, int telefono, String direccion,
			boolean activo, String dni) {
		this.nombre    = nombre;
		this.telefono  = telefono;
		this.direccion = direccion;
		this.activo    = activo;
		this.dni       = dni;
	}

	public int getId() {
		return this.id;
	}

	public String getDNI() {
		return this.dni;
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getTelefono() {
		return this.telefono;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public boolean getActivo() {
		return this.activo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDNI(String dni) {
		this.dni = dni;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
