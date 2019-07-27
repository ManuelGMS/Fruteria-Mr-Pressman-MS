package negocio.venta;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import integracion.FactoriaIntegracion;
import integracion.cliente.DAOCliente;
import integracion.producto.DAOProducto;
import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;
import integracion.venta.DAOVenta;
import negocio.ComprobadorSintactico;
import negocio.cliente.TCliente;
import negocio.producto.TProducto;


public class SAVentaImp implements SAVenta {

	public TVenta read(int id) {
		
		TVenta tVenta = null;
		
		boolean cond = ComprobadorSintactico.isPositive(id);
		
		if(cond) {
			TransactionManager transactionManager = TransactionManager.getInstance();
			Transaction transaction = transactionManager.nuevaTransaccion();
			if (transaction != null) {
				transaction.start();
				FactoriaIntegracion factoriaIntegracion = FactoriaIntegracion.getInstance();
				DAOVenta daoVenta = factoriaIntegracion.generateDAOVenta();
				tVenta = daoVenta.read(id);
				if(tVenta == null)
					transaction.rollback();
				else
					transaction.commit();
			}
			
			transactionManager.eliminaTransaccion();
			
		}
		
		return tVenta;
		
	}

	public ArrayList<TVenta> readAll() {
		ArrayList<TVenta> listaVentas = null;
		TransactionManager transactionManager = TransactionManager.getInstance();
		Transaction transaction = transactionManager.nuevaTransaccion();
		if (transaction != null) {
			transaction.start();
			FactoriaIntegracion factoriaIntegracion = FactoriaIntegracion.getInstance();
			DAOVenta daoVenta = factoriaIntegracion.generateDAOVenta();
			listaVentas = daoVenta.readAll();
			transaction.commit();
		}
		
		transactionManager.eliminaTransaccion();
		
		return listaVentas;
	}

	/*
	 * Error:
	 * -1: Fallo en la comprobacion sintactica.
	 * -8: Venta no existe.
	 * -9: El producto no existe o no esta activo.
	 * -10: No existe la linea de venta.
	 * -11: Las unidades a devolver superan las de la venta.
	 * -100: Error en la gestión de la BDD.
	 */
	public int devolution(int idVenta, int idProducto, int unidades) {
		
		int id = -1;
		boolean cond1 = ComprobadorSintactico.isPositive(idVenta);
		boolean cond2 = ComprobadorSintactico.isPositive(unidades);
		boolean cond3 = ComprobadorSintactico.isPositive(idProducto);
		
		
		if( cond1 && cond2 && cond3) {
			TransactionManager transactionManager = TransactionManager.getInstance();
			Transaction transaction = transactionManager.nuevaTransaccion();
			if (transaction != null) {
				transaction.start();
				FactoriaIntegracion factoriaIntegracion = FactoriaIntegracion.getInstance(); 
				DAOVenta daoVenta = factoriaIntegracion.generateDAOVenta();
				TVenta tVenta = daoVenta.read(idVenta);
						
				if(tVenta != null) {
					DAOProducto daoProducto = factoriaIntegracion.generateDAOProducto();
					TProducto tProducto = daoProducto.read(idProducto);
					
					if(tProducto != null && tProducto.getActivo()) {
							
						HashMap<Integer,LineaVenta> lineaVentas = tVenta.getLineaVentas();
						LineaVenta lineaVenta = lineaVentas.get(idProducto);
								
						if(lineaVenta != null) {
							
							int unidadesVenta = lineaVenta.getUnidades();
									
							if( unidadesVenta >= unidades ) {
								
								float precioLineaVenta = lineaVenta.getPrecio();
								
								lineaVenta.setUnidades( unidadesVenta - unidades );
								lineaVenta.setPrecio( precioLineaVenta - ( precioLineaVenta / unidadesVenta ) * unidades );
								
								int stock = tProducto.getUnidades();
								
								tProducto.setUnidades( stock + unidades );
								id = daoProducto.update(tProducto);
								
								if(id != -100) {
									
									float precioTotal = tVenta.getPrecioTotal();
								
									tVenta.setPrecioTotal( precioTotal - ( precioLineaVenta / unidadesVenta ) * unidades );
									id = daoVenta.update(tVenta);
									if(id == -100)
										transaction.rollback();
									else
										transaction.commit();
								} 
								else
									transaction.rollback();
							} else {
								id = -11;
								transaction.rollback();
							}
						} else {
							id = -10;
							transaction.rollback();
						}
					} else {
						id = -9;
						transaction.rollback();
					}
				} else {
					id = -8;
					transaction.rollback();
				}
			}
			
			transactionManager.eliminaTransaccion();
			
		} else
			id = -1;
		
		return id;
	}

	public TVenta openSale(int idCliente) {
		
		TVenta tVenta = null;
		boolean cond = ComprobadorSintactico.isPositive(idCliente);
		
		if(cond)
			tVenta = new TVenta(idCliente);
		
		return tVenta;
	}

	/*
	 * Error:
	 * -1: Fallo en la comprobacion sintactica.
	 * -2: Objeto transfer no valido.
	 * -3: La venta no estaba abierta.
	 * -4: No se encontró al cliente en la BDD.
	 * -5: El cliente no esta activo.
	 * -6: La venta no posee una linea de ventas.
	 * -7: La linea de ventas quedo vacia.
	 * -100: Error en la gestión de la BDD.
	 */
	public int closeSale(TVenta tVenta) {
		
		int id = -1;
		
		if(tVenta != null) {
			boolean estadoAbierta = tVenta.getEstadoAbierta();
			
			if(estadoAbierta) {
				TransactionManager transactionManager = TransactionManager.getInstance();
				Transaction transaction = transactionManager.nuevaTransaccion();
				if (transaction != null) {
					transaction.start();
				
					FactoriaIntegracion factoriaIntegracion = FactoriaIntegracion.getInstance();
					DAOCliente daoCliente = factoriaIntegracion.generateDAOCliente();
					int clienteID = tVenta.getClienteID();
					
					TCliente tCliente = daoCliente.read(clienteID);
					
					if(tCliente != null) {
						
						boolean clienteActivo = tCliente.getActivo();
						
						if(clienteActivo) {
							HashMap<Integer,LineaVenta> lineaVentas = tVenta.getLineaVentas();
							
							if(lineaVentas != null) {
								
								Collection<LineaVenta> values = lineaVentas.values();
								Iterator<LineaVenta> iterator = values.iterator();
								
								DAOProducto daoProducto = factoriaIntegracion.generateDAOProducto(); 
								
								LineaVenta lineaVenta;
								int idProducto, idProductoResult;
								TProducto tProducto;
								boolean productoActivo;
								int stock, unidades;
								float precioProducto, precio, precioTotal;
								
								Vector<Integer> eliminarArticulos = new Vector<Integer>();
								
								while( iterator.hasNext() ) {
									
									lineaVenta = iterator.next();
									idProducto = lineaVenta.getIdProducto();
									tProducto  = daoProducto.read(idProducto);
									
									if(tProducto != null) {
										productoActivo = tProducto.getActivo();
										stock = tProducto.getUnidades();
										unidades = lineaVenta.getUnidades();
										
										if( !productoActivo || unidades > stock ) 
											
											eliminarArticulos.add(idProducto); 
										
										else {
											precioProducto = tProducto.getPrecio();
											lineaVenta.setPrecio( precioProducto * unidades );
											precio = lineaVenta.getPrecio();
											precioTotal = tVenta.getPrecioTotal();
											tVenta.setPrecioTotal( precioTotal + precio );
											
											tProducto.setUnidades(stock-unidades);
											idProductoResult = daoProducto.update(tProducto);
											
											if(idProductoResult < 0) 
												
												eliminarArticulos.add(idProducto);
											
										}
										
									} else 
										
										eliminarArticulos.add(idProducto);
								
								}
							
								for( int i : eliminarArticulos ) lineaVentas.remove(i);
								
								if(!lineaVentas.isEmpty()) {
									
									Calendar calendar = Calendar.getInstance();
									long timeInMillis = calendar.getTimeInMillis();
									Date fecha = new Date(timeInMillis);
									
									tVenta.setFecha(fecha);
									tVenta.setEstadoAbierta(false);
									
									DAOVenta daoVenta = factoriaIntegracion.generateDAOVenta();
									id = daoVenta.create(tVenta);
									
									if(id == -100)
										transaction.rollback();
									else
										transaction.commit();
									
								} else {
									id = -7;
									transaction.rollback();
								}
								
							} else {
								id = -6;
								transaction.rollback();
							}
						} else {
							id = -5;
							transaction.rollback();
						}
					} else {
						id = -4;
						transaction.rollback();
					}
				}
				
				transactionManager.eliminaTransaccion();
				
			} else
				id = -3;
			
		} else
			id = -2;
		
		return id;
		
	}
	
}