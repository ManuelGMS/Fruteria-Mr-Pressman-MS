package negocio.cliente;

import java.util.ArrayList;

import integracion.FactoriaIntegracion;
import integracion.cliente.DAOCliente;
import integracion.query.FactoriaQuery;
import integracion.transaction.Transaction;
import integracion.transaction.transactionManager.TransactionManager;
import negocio.ComprobadorSintactico;
import negocio.producto.TProducto;


public class SAClienteImp implements SACliente {

	/*
	 * Error:
	 * -1: La validez sintactica no es correcta.
	 * -2: Objeto transfer no valido.
	 * -3: El cliente ya estaba dado de alta y por tanto no se puede volver a crear.
	 * -4: El tipo de cliente es incompatible para la reactivacion
	 * -100: Error en la gestión de la BDD.
	 */
	public int create(TCliente tCliente) {
		
		int id = -1;
		
		if(tCliente != null) {					
			if( ComprobadorSintactico.isPositive(tCliente.getId()) && ComprobadorSintactico.isName(tCliente.getNombre()) 
					&& ComprobadorSintactico.isPositive(tCliente.getTelefono())) {
				TransactionManager transactionManager = TransactionManager.getInstance();
				Transaction transaction = transactionManager.nuevaTransaccion();
				if (transaction != null) {
					transaction.start();
					if(tCliente instanceof TClienteVIP) {
						int codigoTarjeta = ((TClienteVIP) tCliente).getCodTarjetaOro();
						if (ComprobadorSintactico.isPositive(codigoTarjeta)) {
							FactoriaIntegracion factoriaIntegracion = FactoriaIntegracion.getInstance();
							
							DAOCliente daoCliente = factoriaIntegracion.generateDAOCliente();
									
							TCliente tClienteResult = daoCliente.findClienteByDNI(tCliente.getDNI());
							
							tCliente.setActivo(true);
									
							if(tClienteResult == null) {
										
								id = daoCliente.create(tCliente);
								if(id == -100)
									transaction.rollback();
								else
									transaction.commit();
										
							} else {
								
								if(!tClienteResult.getActivo()) {
									if(tClienteResult instanceof TClienteVIP) {
										tCliente.setId(tClienteResult.getId());
												
										id = daoCliente.update(tCliente);
										if(id == -100)
											transaction.rollback();
										else
											transaction.commit();
									}
									else {
										id = -4;
										transaction.rollback();
									}
											
								} else {
									id = -3;
									transaction.rollback();		
								}
										
							}
						}	
					} else {
						int limiteCredito = ((TClienteNoVIP) tCliente).getLimiteCredito();
						if(ComprobadorSintactico.isPositive(limiteCredito)) {
							FactoriaIntegracion factoriaIntegracion = FactoriaIntegracion.getInstance();
							
							DAOCliente daoCliente = factoriaIntegracion.generateDAOCliente();
									
							TCliente tClienteResult = daoCliente.findClienteByDNI(tCliente.getDNI());
									
							if(tClienteResult == null) {
										
								tCliente.setActivo(true);
								id = daoCliente.create(tCliente);
								
								if(id == -100)
									transaction.rollback();
								else
									transaction.commit();
										
							} else {
								
								if(!tClienteResult.getActivo()) {
									if(tClienteResult instanceof TClienteNoVIP) {
										tCliente.setActivo(true);
										tCliente.setId(tClienteResult.getId());
										id = daoCliente.update(tCliente);
										
										if(id == -100)
											transaction.rollback();
										else
											transaction.commit();
									}
									else {
										id = -4;
										transaction.rollback();
									}
											
								} else {
									id = -3;
									transaction.rollback();
								}
										
							}
						}
					}
				}	
				
				transactionManager.eliminaTransaccion();
				
			}
					
		}
		
		return id;
		
	}

	public TCliente read(int id) {
		
		TCliente tCliente = null;
		
		if(ComprobadorSintactico.isPositive(id)) {
			TransactionManager transactionManager = TransactionManager.getInstance();
			Transaction transaction = transactionManager.nuevaTransaccion();
			if (transaction != null) {
				transaction.start();
				tCliente = FactoriaIntegracion.getInstance().generateDAOCliente().read(id);
				if(tCliente == null)
					transaction.rollback();
				else
					transaction.commit();
			}
			
			transactionManager.eliminaTransaccion();
			
		}
		
		return tCliente;
		
	}

	public ArrayList<TCliente> readAll() {
		ArrayList<TCliente> clientes = null;
		TransactionManager transactionManager = TransactionManager.getInstance();
		Transaction transaction = transactionManager.nuevaTransaccion();
		
		if (transaction != null) {
			transaction.start();
			DAOCliente daoCliente = FactoriaIntegracion.getInstance().generateDAOCliente();
			clientes = daoCliente.readAll();
			transaction.commit();
		}
		
		transactionManager.eliminaTransaccion();
		return clientes;
	}

	/*
	 * Error:
	 * -1: No se cumple la validez sintactica.
	 * -2: Objeto transfer no valido.
	 * -3: No se encontró al cliente en la BDD.
	 * -4: El cliente buscado en a BDD no estaba activo.
	 * -5: El cliente solicitado no estaba activo.
	 * -6: Se ha querido actualizar el DNI al de otro ya existente
	 * -100: Error en la gestión de la BDD. 
	 */
	public int update(TCliente tCliente) {
		int id = -1;
		
		if(tCliente != null) {
		
			if(ComprobadorSintactico.isPositive(tCliente.getId()) && ComprobadorSintactico.isName(tCliente.getNombre())
					&& ComprobadorSintactico.isPositive(tCliente.getTelefono())) {
				TransactionManager transactionManager = TransactionManager.getInstance();
				Transaction transaction = transactionManager.nuevaTransaccion();
				if (transaction != null) {
					transaction.start();
			
					if(tCliente instanceof TClienteVIP) {
						int codigoTarjeta = ((TClienteVIP) tCliente).getCodTarjetaOro();
						if (ComprobadorSintactico.isPositive(codigoTarjeta)) { 
							FactoriaIntegracion factoriaIntegracion = FactoriaIntegracion.getInstance();
							
							DAOCliente daoCliente = factoriaIntegracion.generateDAOCliente();
							
							TCliente tClienteResult = daoCliente.read(tCliente.getId());
							
							if(tClienteResult != null) {
								if (!tCliente.getDNI().equalsIgnoreCase(tClienteResult.getDNI()) ) {
									TCliente tClienteResult1 = daoCliente.findClienteByDNI(tCliente.getDNI());
									if (tClienteResult1 != null) {
										id = -6;
										transaction.rollback();
									}
								}
								if (id != -6) {
									if(tClienteResult.getActivo() && tCliente.getActivo()) {
										
										id = daoCliente.update(tCliente);
										if(id == -100)
											transaction.rollback();
										else
											transaction.commit();
										
									} else {
										
										if(!tClienteResult.getActivo())
											id = -4;
										else
											id = -5;
										
										transaction.rollback();
										
									}
								}
							} else {
								id = -3;
								transaction.rollback();
							}
						} 
					}
					else {
						int limiteCredito = ((TClienteNoVIP) tCliente).getLimiteCredito();
						if(ComprobadorSintactico.isPositive(limiteCredito)) {
							FactoriaIntegracion factoriaIntegracion = FactoriaIntegracion.getInstance();
							
							DAOCliente daoCliente = factoriaIntegracion.generateDAOCliente();
							
							TCliente tClienteResult = daoCliente.read(tCliente.getId());
							
							if(tClienteResult != null) {
								if (!tCliente.getDNI().equalsIgnoreCase(tClienteResult.getDNI())) {
									TCliente tClienteResult1 = daoCliente.findClienteByDNI(tCliente.getDNI());
									if (tClienteResult1 != null) {
										id = -6;
										transaction.rollback();
									}
								}
								if (id != -6) {
									if(tClienteResult.getActivo() && tCliente.getActivo()) {
										id = daoCliente.update(tCliente);
										
										if(id == -100)
											transaction.rollback();
										else
											transaction.commit();
									
									} else {
										
										if(!tClienteResult.getActivo())
											id = -4;
										else
											id = -5;
										
										transaction.rollback();
										
									}
								}
							} else {
								id = -3;
								transaction.rollback();
							}
						}
					}
				}
				
				transactionManager.eliminaTransaccion();
				
			}	
		} else {		
			id = -2;	
		}
		
		return id;
		
	}

	/*
	 * Error:
	 * -1: No se cumple la validez sintactica.
	 * -2: El cliente no existe y no se le puede dar de baja.
	 * -100: Error en la gestión de la BDD.
	 */
	public int delete(int id) {
		if(ComprobadorSintactico.isPositive(id)) {
			TransactionManager transactionManager = TransactionManager.getInstance();
			Transaction transaction = transactionManager.nuevaTransaccion();
			if (transaction != null) {
				transaction.start();
			
				FactoriaIntegracion factoriaIntegracion = FactoriaIntegracion.getInstance();
				
				DAOCliente daoCliente = factoriaIntegracion.generateDAOCliente();
				
				TCliente tClienteResult = daoCliente.read(id);
				
				if(tClienteResult != null) {
					id = daoCliente.delete(id);
					if(id == -100)
						transaction.rollback();
					else
						transaction.commit();
				}
				else {
					id = -2;
					transaction.rollback();
				}
				
			}
			
			transactionManager.eliminaTransaccion();
			
		} else {
			id = -1;
		}
		
		return id;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<TProducto> productoMasComprado(ClienteMes clienteMes) {
		ArrayList<TProducto> productos = null;
		
		if(ComprobadorSintactico.isPositive(clienteMes.getIdCliente()) && ComprobadorSintactico.isPositive(clienteMes.getMes()) 
				&& clienteMes.getMes() <= 12) {
			TransactionManager transactionManager = TransactionManager.getInstance();
			Transaction transaction = transactionManager.nuevaTransaccion();
			if (transaction != null) {
				transaction.start();
				TCliente cliente = FactoriaIntegracion.getInstance().generateDAOCliente().read(clienteMes.getIdCliente());
				if(cliente != null) {
					productos = (ArrayList<TProducto>) FactoriaQuery.getInstance().generateQueryProductoMasCompradoPorUnCliente().execute(clienteMes);
					transaction.commit();
				}
				else transaction.rollback(); 
			}
			
			transactionManager.eliminaTransaccion();
			
		}
		
		return productos;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<TProducto> productosClienteVIP(int idCliente) {
		ArrayList<TProducto> productos = null;

		if (ComprobadorSintactico.isPositive(idCliente)) {
			TransactionManager transactionManager = TransactionManager.getInstance();
			Transaction transaction = transactionManager.nuevaTransaccion();
			
			if (transaction != null) {
				transaction.start();
				TCliente cliente = FactoriaIntegracion.getInstance().generateDAOCliente().read(idCliente);
				if(cliente instanceof TClienteVIP) {
					productos = (ArrayList<TProducto>) FactoriaQuery.getInstance().generateQueryProductosCompradosPorClienteVIP().execute(idCliente);
					transaction.commit();
				}
				else {
					transaction.rollback();
				}
			}
			
			transactionManager.eliminaTransaccion();
		}

		return productos;
	}
}
