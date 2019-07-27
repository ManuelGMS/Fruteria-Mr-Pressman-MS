package negocio;

import negocio.cliente.SACliente;
import negocio.curso.SACurso;
import negocio.departamento.SADepartamento;
import negocio.producto.SAProducto;
import negocio.trabajador.SATrabajador;
import negocio.venta.SAVenta;


public abstract class FactoriaNegocio {

	private static FactoriaNegocio instance;

	public synchronized static FactoriaNegocio getInstance() {
		if(instance == null) instance = new FactoriaNegocioImp();
		return instance;
	}

	public abstract SAProducto generateSAProducto();

	public abstract SAVenta generateSAVenta();

	public abstract SACliente generateSACliente();
	
	public abstract SADepartamento generateSADepartamento();
	
	public abstract SACurso generateSACurso();
	
	public abstract SATrabajador generateSATrabajador();
}