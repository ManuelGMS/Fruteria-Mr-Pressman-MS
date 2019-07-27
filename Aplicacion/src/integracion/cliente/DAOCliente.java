package integracion.cliente;

import java.util.ArrayList;

import negocio.cliente.TCliente;


public interface DAOCliente {

	public int create(TCliente tCliente);

	public TCliente read(int id);
	
	public TCliente findClienteByDNI(String dni);

	public ArrayList<TCliente> readAll();

	public int update(TCliente tCliente);

	public int delete(int id);
}