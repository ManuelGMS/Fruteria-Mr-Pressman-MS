/**
 * 
 */
package negocio.venta;

import java.util.ArrayList;


public interface SAVenta {

	public TVenta read(int id);

	public ArrayList<TVenta> readAll();

	public int devolution(int idVenta, int idProducto, int unidades);

	public TVenta openSale(int idCliente);

	public int closeSale(TVenta tVenta);
}