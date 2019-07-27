package integracion.query;

import integracion.query.imp.ProductoMasCompradoPorUnCliente;
import integracion.query.imp.ProductosCompradosPorClienteVIP;


public class FactoriaQueryImp extends FactoriaQuery {
	
	public ProductoMasCompradoPorUnCliente generateQueryProductoMasCompradoPorUnCliente() {
		return new ProductoMasCompradoPorUnCliente();
	}

	public ProductosCompradosPorClienteVIP generateQueryProductosCompradosPorClienteVIP() {
		return new ProductosCompradosPorClienteVIP();
	}
}