package integracion.query;

import integracion.query.imp.ProductoMasCompradoPorUnCliente;
import integracion.query.imp.ProductosCompradosPorClienteVIP;


public abstract class FactoriaQuery {

	private static FactoriaQuery instance;

	public static FactoriaQuery getInstance() {
		if(instance == null){
			instance = new FactoriaQueryImp();
		}
		return instance;
	}

	public abstract ProductoMasCompradoPorUnCliente generateQueryProductoMasCompradoPorUnCliente();

	public abstract ProductosCompradosPorClienteVIP generateQueryProductosCompradosPorClienteVIP();
}