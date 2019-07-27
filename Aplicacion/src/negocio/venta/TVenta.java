package negocio.venta;

import java.sql.Date;
import java.util.HashMap;


public class TVenta {

	private int id;
	private float precioTotal;
	private Date fecha;
	private int clienteId;
	private boolean estadoAbierta;
	private HashMap<Integer,LineaVenta> lineaVentas;

	/*
	 * Constructor para leer ventas cerradas
	 */
	public TVenta(int id, float precioTotal, Date fecha, int clienteId,
			HashMap<Integer,LineaVenta> lineaVentas, boolean estadoAbierta) {
		this.clienteId     = clienteId;
		this.id            = id;
		this.estadoAbierta = estadoAbierta;
		this.lineaVentas   = lineaVentas;
		this.precioTotal   = precioTotal;
		this.fecha         = fecha;
	}

	/*
	 * Constructor para crear una nueva venta no cerrada
	 */
	public TVenta(int idCliente) {
		this.clienteId     = idCliente;
		this.lineaVentas   = new HashMap<>();
		this.estadoAbierta = true;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPrecioTotal(float precioTotal) {
		this.precioTotal = precioTotal;
	}

	public void setFecha(Date date) {
		this.fecha = date;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

	public void setLineaVentas(HashMap<Integer,LineaVenta> lineaVentas) {
		this.lineaVentas = lineaVentas;
	}

	public void setEstadoAbierta(boolean estadoAbierta) {
		this.estadoAbierta = estadoAbierta;
	}

	public int getId() {
		return this.id;
	}

	public float getPrecioTotal() {
		return this.precioTotal;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public int getClienteID() {
		return this.clienteId;
	}

	public HashMap<Integer,LineaVenta> getLineaVentas() {
		return this.lineaVentas;
	}

	public boolean getEstadoAbierta() {
		return this.estadoAbierta;
	}
}