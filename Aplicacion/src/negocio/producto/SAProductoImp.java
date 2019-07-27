package negocio.producto;

import java.util.ArrayList;

import integracion.FactoriaIntegracion;
import integracion.producto.DAOProducto;
import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;
import negocio.ComprobadorSintactico;


public class SAProductoImp implements SAProducto {

	/*
	 * TABLA DE ERRORES
	 * 
	 * -1 - tProducto == null
	 * -2 - Validez sintáctica
	 * -5 - El producto ya está activo y no se puede volver a crear
	 * -100 - Error de Base de Datos
	 */
	public int create(TProducto tProducto) {
		
		int id = -1;
		if (tProducto != null) {
			String nombre = tProducto.getNombre();
			int unidades = tProducto.getUnidades();
			float precio = tProducto.getPrecio();
			if(ComprobadorSintactico.isName(nombre) && ComprobadorSintactico.isPositive(unidades) 
					&& ComprobadorSintactico.isPositive(precio)) {
				TransactionManager transactionManager = TransactionManager.getInstance();
				Transaction transaction = transactionManager.nuevaTransaccion();
				if (transaction != null) {
					transaction.start();
					DAOProducto daoProducto = FactoriaIntegracion.getInstance().generateDAOProducto();
	
					TProducto tProductoResult = daoProducto.findByName(nombre);
					tProducto.setActivo(true);
					if (tProductoResult == null) {
						id = daoProducto.create(tProducto);
						if(id == -100)
							transaction.rollback();
						else
							transaction.commit();
					}
					else {
						if (!tProductoResult.getActivo()) {
							tProducto.setId(tProductoResult.getId());
							id = daoProducto.update(tProducto);
							if(id == -100)
								transaction.rollback();
							else
								transaction.commit();
						}
						else {
							id = -5;
							transaction.rollback();
						}
					}
				}
				
				transactionManager.eliminaTransaccion();
				
			}
			else
				id = -2;
		}
		
		return id;
		
	}

	/*
	 * TABLA DE ERRORES
	 * 
	 * null - Dependencia sintáctica
	 */
	public TProducto read(int id) {
		TProducto tProducto = null;
		if(ComprobadorSintactico.isPositive(id)){
			TransactionManager transactionManager = TransactionManager.getInstance();
			Transaction transaction = transactionManager.nuevaTransaccion();
			if (transaction != null) {
				transaction.start();
				DAOProducto daoProducto = FactoriaIntegracion.getInstance().generateDAOProducto();
				tProducto = daoProducto.read(id);
				if(tProducto == null)
					transaction.rollback();
				else
					transaction.commit();
			}
			
			transactionManager.eliminaTransaccion();
			
		}
		return tProducto;
	}

	public ArrayList<TProducto> readAll() {
		ArrayList<TProducto> listaProductos = null;
		
		TransactionManager transactionManager = TransactionManager.getInstance();
		Transaction transaction = transactionManager.nuevaTransaccion();
		
		if (transaction != null) {
			transaction.start();
			DAOProducto daoProducto = FactoriaIntegracion.getInstance().generateDAOProducto();
			listaProductos = daoProducto.readAll();
			transaction.commit();
		}
		
		transactionManager.eliminaTransaccion();
		
		return listaProductos;
	}

	/*
	 * TABLA DE ERRORES
	 * 
	 * -1 - tProducto == null
	 * -2 - Validez sintáctica
	 * -3 - No existe el producto que se quiere actualizar
	 * -4 - El producto no está activo
	 * -7 - Se ha querido actualizar el nombre al de otro ya existente
	 * -100 - Error de Base de Datos
	 */
	public int update(TProducto tProducto) {
		
		int id = -1;
		if (tProducto != null) {
			int idProducto = tProducto.getId();
			String nombre = tProducto.getNombre();
			float precio = tProducto.getPrecio();
			int unidades = tProducto.getUnidades();
			if(ComprobadorSintactico.isPositive(idProducto) && ComprobadorSintactico.isName(nombre) 
					&& ComprobadorSintactico.isPositive(precio) && ComprobadorSintactico.isPositive(unidades)) {
				TransactionManager transactionManager = TransactionManager.getInstance();
				Transaction transaction = transactionManager.nuevaTransaccion();
				if (transaction != null) {
					transaction.start();
					DAOProducto daoProducto = FactoriaIntegracion.getInstance().generateDAOProducto();
					TProducto tProductoResult = daoProducto.read(idProducto);
					if (tProductoResult != null) {
						if (!tProducto.getNombre().equalsIgnoreCase(tProductoResult.getNombre())) {
							TProducto tProductoResult1 = daoProducto.findByName(tProducto.getNombre());
							if (tProductoResult1 != null) id = -7;
						}
						if (id != -7){
							boolean activoResult = tProductoResult.getActivo();
							boolean activo = tProducto.getActivo();
							if(activoResult && activo){
								id = daoProducto.update(tProducto);
								if(id == -100)
									transaction.rollback();
								else
									transaction.commit();
							}
							else {
								id = -4;
								transaction.rollback();
							}
						}
						else
							transaction.rollback();
					}
					else {
						id = -3;
						transaction.rollback();
					}
				}
				
				transactionManager.eliminaTransaccion();
				
			}
			else id = -2;
		}	
		return id;
	}

	/*
	 * TABLA DE ERRORES
	 * 
	 * -1 - Validez sintáctica
	 * -2 - El producto que se quiere borrar no existe
	 * -3 - El producto que se quiere borrar ya está borrado
	 * -100 - Error de Base de Datos
	 */
	public int delete(int id) {
		
		int idRetorno = -1;
		DAOProducto daoProducto = FactoriaIntegracion.getInstance().generateDAOProducto();
		if(ComprobadorSintactico.isPositive(id)){
			TransactionManager transactionManager = TransactionManager.getInstance();
			Transaction transaction = transactionManager.nuevaTransaccion();
			if (transaction != null) {
				transaction.start();
				TProducto tProductoResult = daoProducto.read(id);
				if(tProductoResult != null) {
					if (tProductoResult.getActivo()) {
						idRetorno = daoProducto.delete(id);
						if(id == -100)
							transaction.rollback();
						else
							transaction.commit();
					}
					else {
						idRetorno = -3;
						transaction.rollback();
					}
				}
				else {
					idRetorno = -2;
					transaction.rollback();
				}
			}
			
			transactionManager.eliminaTransaccion();
			
		}
		return idRetorno;
	}
}