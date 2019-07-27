package negocio.venta;


public class LineaVenta {

	private int idProducto;
	private int unidades;
	private float precio;

	/** 
	 * Constructor para LineaVenta
	 */
	public LineaVenta(int idProducto, int unidades, float precio) {
		this.idProducto = idProducto;
		this.unidades = unidades;
		this.precio = precio;
	}

	public void setIdProducto(int producto) {
		this.idProducto = producto;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getIdProducto() {
		return this.idProducto;
	}

	public int getUnidades() {
		return this.unidades;
	}

	public float getPrecio() {
		return this.precio;
	}
}