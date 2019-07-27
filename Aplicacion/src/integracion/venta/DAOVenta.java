
package integracion.venta;

import java.util.ArrayList;

import negocio.venta.TVenta;


public interface DAOVenta {
	
	public int create(TVenta tVenta);

	public TVenta read(int id);

	public ArrayList<TVenta> readAll();

	public int update(TVenta tVenta);
}