package negocio.producto;


public class TProducto {

	private int id;
	private String nombre;
	private float precio;
	private int unidades;
	private boolean activo;

	/*
	 * Constructor para leer productos
	 */
	public TProducto(int id, String nombre, float precio, int unidades,
			boolean activo) {
		this.id       = id;
		this.nombre   = nombre;
		this.precio   = precio;
		this.unidades = unidades;
		this.activo   = activo;
	}


	/*
	 * Constructor para crear un producto nuevo
	 */
	public TProducto(String nombre, float precio, int unidades, boolean activo) {
		this.nombre   = nombre;
		this.precio   = precio;
		this.unidades = unidades;
		this.activo   = activo;
	}

	public int getId() {
		return this.id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public float getPrecio() {
		return this.precio;
	}

	public int getUnidades() {
		return this.unidades;
	}

	public boolean getActivo() {
		return this.activo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}