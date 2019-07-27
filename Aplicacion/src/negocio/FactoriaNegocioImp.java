package negocio;

import negocio.cliente.SACliente;
import negocio.cliente.SAClienteImp;
import negocio.curso.SACurso;
import negocio.curso.SACursoImp;
import negocio.departamento.SADepartamento;
import negocio.departamento.SADepartamentoImp;
import negocio.producto.SAProducto;
import negocio.producto.SAProductoImp;
import negocio.trabajador.SATrabajador;
import negocio.trabajador.SATrabajadorImp;
import negocio.venta.SAVenta;
import negocio.venta.SAVentaImp;


public class FactoriaNegocioImp extends FactoriaNegocio {
	
	public SAProducto generateSAProducto() {
		return new SAProductoImp();
	}

	public SAVenta generateSAVenta() {
		return new SAVentaImp();
	}

	public SACliente generateSACliente() {
		return new SAClienteImp();
	}
	
	public SADepartamento generateSADepartamento(){
		return new SADepartamentoImp();
	}

	public SACurso generateSACurso() {
		return new SACursoImp();
	}
	
	public SATrabajador generateSATrabajador() {
		return new SATrabajadorImp();
	}
	
}