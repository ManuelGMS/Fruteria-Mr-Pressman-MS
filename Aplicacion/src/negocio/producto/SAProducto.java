
package negocio.producto;

import java.util.ArrayList;


public interface SAProducto {

	public int create(TProducto tProducto);

	public TProducto read(int id);

	public ArrayList<TProducto> readAll();

	public int update(TProducto tProducto);

	public int delete(int id);
}