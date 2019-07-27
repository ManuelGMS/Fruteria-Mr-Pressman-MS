package negocio.cliente;


public class TClienteNoVIP extends TCliente {

	private int limiteCredito;

	/*
	 * Constructor para leer clientes no vip
	 */
	public TClienteNoVIP(int id, String nombre, int telefono,
			String direccion, boolean activo, String dni, int limiteCredito) {
		super(id,nombre,telefono,direccion,activo,dni);
		this.limiteCredito = limiteCredito;
	}

	/*
	 * Constructor para crear clientes no vip
	 */
	public TClienteNoVIP(String nombre, int telefono, String direccion,
			boolean activo, String dni, int limiteCredito) {
		super(nombre,telefono,direccion,activo,dni);
		this.limiteCredito = limiteCredito;
	}

	public int getLimiteCredito() {
		return this.limiteCredito;
	}

	public void setLimiteCredito(int limiteCredito) {
		this.limiteCredito = limiteCredito;
	}
}
