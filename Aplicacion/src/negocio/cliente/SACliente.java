package negocio.cliente;

import java.util.ArrayList;

import negocio.producto.TProducto;


public interface SACliente {
	
	public int create(TCliente tCliente);
	
	public TCliente read(int id);

	public ArrayList<TCliente> readAll();

	public int update(TCliente tCliente);

	public int delete(int id);

	public ArrayList<TProducto> productoMasComprado(ClienteMes clienteMes);

	public ArrayList<TProducto> productosClienteVIP(int idCliente);

}
