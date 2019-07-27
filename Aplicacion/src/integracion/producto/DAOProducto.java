package integracion.producto;

import java.util.ArrayList;

import negocio.producto.TProducto;


public interface DAOProducto {

	public int create(TProducto tProducto);

	public TProducto findByName(String nombre);

	public TProducto read(int id);

	public ArrayList<TProducto> readAll();

	public int update(TProducto tProducto);

	public int delete(int id);
}