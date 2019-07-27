
package integracion;

import integracion.cliente.DAOCliente;
import integracion.cliente.DAOClienteImp;
import integracion.producto.DAOProducto;
import integracion.producto.DAOProductoImp;
import integracion.venta.DAOVenta;
import integracion.venta.DAOVentaImp;


public class FactoriaIntegracionImp extends FactoriaIntegracion {

	public DAOProducto generateDAOProducto() {
		return new DAOProductoImp();
	}

	public DAOVenta generateDAOVenta() {
		return new DAOVentaImp();
	}

	public DAOCliente generateDAOCliente() {
		return new DAOClienteImp();
	}
}