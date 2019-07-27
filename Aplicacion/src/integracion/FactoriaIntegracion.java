
package integracion;

import integracion.cliente.DAOCliente;
import integracion.producto.DAOProducto;
import integracion.venta.DAOVenta;

public abstract class FactoriaIntegracion {

	private static FactoriaIntegracion instance;


	public synchronized static FactoriaIntegracion getInstance() {
		if(instance==null) {
			instance = new FactoriaIntegracionImp();
		}
		
		return instance;
	}

	public abstract DAOProducto generateDAOProducto();

	public abstract DAOVenta generateDAOVenta();

	public abstract DAOCliente generateDAOCliente();
}