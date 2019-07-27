package negocio.cliente;

public class ClienteMes {
	
	private int idCliente;
	private int mes;
	
	public ClienteMes(int idCliente, int mes) {
		this.idCliente = idCliente;
		this.mes = mes;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}
	
}
