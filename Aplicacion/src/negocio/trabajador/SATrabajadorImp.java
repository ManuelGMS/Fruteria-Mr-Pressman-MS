package negocio.trabajador;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import negocio.ComprobadorSintactico;
import negocio.departamento.Departamento;

public class SATrabajadorImp implements SATrabajador {
	
	/*
	 * Error:
	 * -1: Trabajador null o departamento null
	 * -2: Error sintactico
	 * -3: El departamento no existe
	 * -4: Ya existe un trabajador con el mismo correo
	 * -5: No se puede reactivar el trabajador porque no es del mismo tipo
	 * -100: Error en la gestión de la BDD.
	 */
	public int create(Trabajador trabajador) {
		int id = -1;
		if(trabajador != null && trabajador.getDepartamento() != null) {
			String nombre = trabajador.getNombre();
			boolean cond1 = ComprobadorSintactico.isName(nombre);
			int idDepartamento = trabajador.getDepartamento().getId();
			boolean cond2 = ComprobadorSintactico.isPositive(idDepartamento);
			boolean cond3, cond4;
			if(trabajador instanceof TiempoCompleto) {
				int sueldo = ((TiempoCompleto) trabajador).getSueldo();
				int antiguedad = ((TiempoCompleto) trabajador).getAntiguedad();
				cond3 = ComprobadorSintactico.isPositive(sueldo);
				cond4 = ComprobadorSintactico.isPositive(antiguedad);
			}
			else  {//es de TiempoParcial
				int horasJornada = ((TiempoParcial) trabajador).getHorasJornada();
				int sueldoHora = ((TiempoParcial) trabajador).getSueldoHora();
				cond3 = ComprobadorSintactico.isPositive(horasJornada);
				cond4 = ComprobadorSintactico.isPositive(sueldoHora);
			}
			
			String correo = trabajador.getCorreo();
			
			
			if(cond1 && cond2 && cond3 && cond4 && !correo.isEmpty()) {
				EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
				try {
					EntityManager entitymanager = emfactory.createEntityManager();
					EntityTransaction entitytransaction = entitymanager.getTransaction();
					entitytransaction.begin();
					Departamento departamentoResult = entitymanager.find(Departamento.class, idDepartamento, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
					if(departamentoResult != null && departamentoResult.getActivo()) {
						trabajador.setDepartamento(departamentoResult);
						TypedQuery<Trabajador> query = entitymanager.createNamedQuery("negocio.trabajador.Trabajador.findBycorreo", Trabajador.class).setParameter("correo", correo);
						List<Trabajador> lista = query.getResultList();
						if(lista.isEmpty()) {
							trabajador.setActivo(true);
							entitymanager.persist(trabajador);
							entitytransaction.commit();
							id = trabajador.getId();
						}	
						else {
							Trabajador trabajadorResult = lista.get(0);
							boolean activo = trabajadorResult.getActivo();
							if(activo) {
								//El trabajador con mismo correo ya existe
								id = -4;
								entitytransaction.rollback();
							}
							else {
								trabajadorResult.setActivo(true);
								trabajadorResult.setCorreo(correo);
								trabajadorResult.setDepartamento(trabajador.getDepartamento());
								if(trabajadorResult instanceof TiempoCompleto && trabajador instanceof TiempoCompleto) {
									int sueldo = ((TiempoCompleto)trabajador).getSueldo();
									int antiguedad = ((TiempoCompleto) trabajador).getAntiguedad();
									((TiempoCompleto)trabajadorResult).setSueldo(sueldo);
									((TiempoCompleto)trabajadorResult).setAntiguedad(antiguedad);
									entitytransaction.commit();
									id = trabajadorResult.getId(); 
								}
								else if(trabajadorResult instanceof TiempoParcial && trabajador instanceof TiempoParcial){
									int horasJornada = ((TiempoParcial) trabajador).getHorasJornada();
									int sueldoHora = ((TiempoParcial) trabajador).getSueldoHora();
									((TiempoParcial)trabajadorResult).setHorasJornada(horasJornada);
									((TiempoParcial)trabajadorResult).setSueldoHora(sueldoHora);

									entitytransaction.commit();
									id = trabajadorResult.getId(); 
								}
								else {
									id = -5;
									entitytransaction.rollback();
								}
								
							}
						}
					}
					else {
						//Se ha dado de alta un trabajador con un departamento que no existe
						id = -3;
						entitytransaction.rollback();
					}
					entitymanager.close();
					emfactory.close();
				} catch(PersistenceException ex) {
					id = -100;
				}
			}
			else
				id = -2;
			
		}
		return id;
	}
	
	/*
	 * Errores:
	 * -1: Error sintactico
	 * -2: Trabajador no encontrado
	 * -3: Trabajador ya esta dado de baja
	 * -4: Trabajador esta matriculado en un curso y no puede darse de baja
	 */
	@Override
	public int delete(int id) {
		boolean cond = ComprobadorSintactico.isPositive(id);
		if(cond) {
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
			try {
				EntityManager entitymanager = emfactory.createEntityManager();
				EntityTransaction entitytransaction = entitymanager.getTransaction();
				entitytransaction.begin();
				Trabajador trabajador = entitymanager.find(Trabajador.class, id);
				if(trabajador != null) {
					boolean cond2 = trabajador.getActivo();
					if(cond2) {
						TypedQuery<Trabajador> query = entitymanager.createNamedQuery("negocio.curso.Realiza.findBytrabajador", Trabajador.class);
						query.setParameter("trabajador", trabajador);
						List<Trabajador> lista = query.getResultList();
						if(lista.isEmpty()) 
						{
							Departamento dept = trabajador.getDepartamento();
							entitymanager.lock(dept, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
							trabajador.setActivo(false);
							entitymanager.persist(trabajador);
							entitytransaction.commit();
						}
						else {
							id = -4;
							entitytransaction.rollback();
						}
					}
					else {
						id = -3;
						entitytransaction.rollback();
					}
				}
				else {
					id = -2;
					entitytransaction.rollback();
				}
				entitymanager.close();
				emfactory.close();
			} catch(PersistenceException ex) {
				id = -100;
			}
		}
		else
			id = -1;
		
		return id;
	}

	@Override
	public Trabajador read(int id) {
		Trabajador trabajador = null;
		boolean cond = ComprobadorSintactico.isPositive(id);
		
		if(cond) {
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
			try {
				EntityManager entitymanager = emfactory.createEntityManager();
				EntityTransaction entitytransaction = entitymanager.getTransaction();
				entitytransaction.begin();
				trabajador = entitymanager.find(Trabajador.class, id);
				
				if(trabajador == null)
					entitytransaction.rollback();
				else
					entitytransaction.commit();
				
				entitymanager.close();
				emfactory.close();
			}
			catch(PersistenceException ex) {}
		}
		return trabajador;
	}

	/*
	 * Error:
	 * -1: Trabajador null o departamento null
	 * -2: Error sintactico
	 * -3: El departamento no existe
	 * -4: El trabajador no existe
	 * -5: El trabajador no esta activo
	 * -6: Ya existe un trabajador con ese correo
	 * -7: El trabajador que se quiere actualizar es de un tipo distinto
	 * -100: Error en la gestión de la BDD.
	 */
	@Override
	public int update(Trabajador trabajador) {
		int idResult = -1;
		if(trabajador != null && trabajador.getDepartamento() != null) {
			int id = trabajador.getId();
			boolean cond1 = ComprobadorSintactico.isPositive(id);
			String nombre = trabajador.getNombre();
			boolean cond2 = ComprobadorSintactico.isName(nombre);
			int idDepartamento = trabajador.getDepartamento().getId();
			boolean cond3 = ComprobadorSintactico.isPositive(idDepartamento);
			boolean cond4, cond5;
			if(trabajador instanceof TiempoCompleto) {
				int sueldo = ((TiempoCompleto) trabajador).getSueldo();
				int antiguedad = ((TiempoCompleto) trabajador).getAntiguedad();
				cond4 = ComprobadorSintactico.isPositive(sueldo);
				cond5 = ComprobadorSintactico.isPositive(antiguedad);
			}
			else  {//es de TiempoParcial
				int horasJornada = ((TiempoParcial) trabajador).getHorasJornada();
				int sueldoHora = ((TiempoParcial) trabajador).getSueldoHora();
				cond4 = ComprobadorSintactico.isPositive(horasJornada);
				cond5 = ComprobadorSintactico.isPositive(sueldoHora);
			}
			
			String correo = trabajador.getCorreo();
			
			
			if(cond1 && cond2 && cond3 && cond4 && cond5 && !correo.isEmpty()) {
				EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
				try {
					EntityManager entitymanager = emfactory.createEntityManager();
					EntityTransaction entitytransaction = entitymanager.getTransaction();
					entitytransaction.begin();
					Departamento departamentoResult = entitymanager.find(Departamento.class, idDepartamento);
					if(departamentoResult != null && departamentoResult.getActivo()) {
						trabajador.setDepartamento(departamentoResult);
						Trabajador trabajadorResult = entitymanager.find(Trabajador.class, id);
						if(trabajadorResult != null) {
							if(trabajadorResult.getActivo()) {
								TypedQuery<Trabajador> query = entitymanager.createNamedQuery("negocio.trabajador.Trabajador.findBycorreo", Trabajador.class).setParameter("correo", correo);
								List<Trabajador> lista = query.getResultList();
								if(lista.isEmpty() || lista.get(0).getId() == id) {
									
									boolean modificado = true;
									if(trabajadorResult instanceof TiempoCompleto && trabajador instanceof TiempoCompleto) {
										((TiempoCompleto) trabajadorResult).setAntiguedad(((TiempoCompleto) trabajador).getAntiguedad());
										((TiempoCompleto) trabajadorResult).setSueldo(((TiempoCompleto) trabajador).getSueldo());
										
									}
									else if(trabajadorResult instanceof TiempoParcial && trabajador instanceof TiempoParcial){
										((TiempoParcial) trabajadorResult).setHorasJornada(((TiempoParcial) trabajador).getHorasJornada());
										((TiempoParcial) trabajadorResult).setSueldoHora(((TiempoParcial) trabajador).getSueldoHora());
									}	
									else {
										//El trabajador con mismo id ya existente no es del mismo tipo que el que se quiere actualizar
										idResult = -7;
										modificado = false;
										entitytransaction.rollback();
									}
									
									if(modificado) {
										trabajadorResult.setNombre(nombre);
										trabajadorResult.setCorreo(correo);
										Departamento departamento = trabajadorResult.getDepartamento();
										if(departamento.getId() != idDepartamento) {
											entitymanager.lock(departamento, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
											trabajadorResult.setDepartamento(departamentoResult);
										}
										entitymanager.lock(departamentoResult, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
										entitytransaction.commit();
										idResult = trabajadorResult.getId(); 
									}
								}
								else {
									//hay otro con el mismo correo
									idResult = -6;
									entitytransaction.rollback();
								}
							}
							else {
								//no activo
								idResult = -5;
								entitytransaction.rollback();
							}
						}
						else {
							idResult = -4;
							entitytransaction.rollback();
						}
					}
					else {
						//no hay ningun departamento con ese id para actualizar
						idResult = -3;
						entitytransaction.rollback();
					}
					entitymanager.close();
					emfactory.close();
				} catch(PersistenceException ex) {
					id = -100;
				}
			}
			else
				idResult = -2;
			
		}
		return idResult;	
	}

	@Override
	public List<Trabajador> readAll() {
		List<Trabajador> lista = null;
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
		try {
			EntityManager entitymanager = emfactory.createEntityManager();
			EntityTransaction entitytransaction = entitymanager.getTransaction();
			entitytransaction.begin();
				
			TypedQuery<Trabajador> query = entitymanager.createNamedQuery("negocio.trabajador.Trabajador.readAll", Trabajador.class);
			lista = query.getResultList();
			entitytransaction.commit();
				
			entitymanager.close();
			emfactory.close();
		} catch(PersistenceException ex) {}
		
		return lista;
	}
	
	
	
}