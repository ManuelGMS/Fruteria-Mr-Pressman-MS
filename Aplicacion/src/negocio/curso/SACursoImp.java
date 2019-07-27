package negocio.curso;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import negocio.ComprobadorSintactico;
import negocio.trabajador.Trabajador;

public class SACursoImp implements SACurso {

	public int create(Curso curso) {
		int id = -1;
		if(curso != null) {
			String nombre = curso.getNombre();
			boolean cond1 = ComprobadorSintactico.isName(nombre);
			int horas = curso.getHoras();
			boolean cond2 = ComprobadorSintactico.isPositive(horas);
			int plazas = curso.getPlazas();
			boolean cond3 = ComprobadorSintactico.isPositive(plazas);
			if(cond1 && cond2 && cond3) {
				EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
				try {
					EntityManager entitymanager = emfactory.createEntityManager();
					EntityTransaction entitytransaction = entitymanager.getTransaction();
					entitytransaction.begin();
					TypedQuery<Curso> query = entitymanager.createNamedQuery("negocio.curso.Curso.findBynombre", Curso.class).setParameter("nombre", nombre);
					List<Curso> lista = query.getResultList();
					if(lista.isEmpty()) {
						curso.setActivo(true);
						entitymanager.persist(curso);
						entitytransaction.commit();
						id = curso.getId();
					}
					else {
						Curso cursoResult = lista.get(0);
						boolean activo = cursoResult.getActivo();
						if(activo) {
							id = -3;
							entitytransaction.rollback();
						}
						else {
							cursoResult.setActivo(true);
							cursoResult.setHoras(horas);
							cursoResult.setPlazas(plazas);
							entitytransaction.commit();
							id = cursoResult.getId(); 
						}
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

	public int delete(int id) {
		boolean cond = ComprobadorSintactico.isPositive(id);
		if(cond) {
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
			try {
				EntityManager entitymanager = emfactory.createEntityManager();
				EntityTransaction entitytransaction = entitymanager.getTransaction();
				entitytransaction.begin();
				Curso curso = entitymanager.find(Curso.class, id);
				if(curso != null) {
					boolean cond2 = curso.getActivo();
					if(cond2) {
						TypedQuery<Realiza> query = entitymanager.createNamedQuery("negocio.curso.Realiza.findBycurso", Realiza.class);
						query.setParameter("curso", curso);
						List<Realiza> listaRealiza = query.getResultList();
						if(listaRealiza.isEmpty()) {
							curso.setActivo(false);
							entitymanager.persist(curso);
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

	public Curso read(int id) {
		Curso curso = null;
		boolean cond = ComprobadorSintactico.isPositive(id);
		if(cond) {
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
			try {
				EntityManager entitymanager = emfactory.createEntityManager();
				EntityTransaction entitytransaction = entitymanager.getTransaction();
				entitytransaction.begin();
				
				curso = entitymanager.find(Curso.class, id);
				
				if(curso == null)
					entitytransaction.rollback();
				else
					entitytransaction.commit();
				
				entitymanager.close();
				emfactory.close();
			} catch(PersistenceException ex) {}
		}
		
		return curso;
	}

	public List<Curso> readAll() {
		List<Curso> lista = null;
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
		try {
			EntityManager entitymanager = emfactory.createEntityManager();
			EntityTransaction entitytransaction = entitymanager.getTransaction();
			entitytransaction.begin();
				
			TypedQuery<Curso> query = entitymanager.createNamedQuery("negocio.curso.Curso.readAll", Curso.class);
			lista = query.getResultList();
			entitytransaction.commit();
				
			entitymanager.close();
			emfactory.close();
		} catch(PersistenceException ex) {}
		
		return lista;
	}

	public int update(Curso curso) {
		int idResult = -1;
		if(curso != null) {
			int id = curso.getId();
			boolean cond1 = ComprobadorSintactico.isPositive(id);
			String nombre = curso.getNombre();
			boolean cond2 = ComprobadorSintactico.isName(nombre);
			int horas = curso.getHoras();
			boolean cond3 = ComprobadorSintactico.isPositive(horas);
			int plazas = curso.getPlazas();
			boolean cond4 = ComprobadorSintactico.isPositive(plazas);
			if(cond1 && cond2 && cond3 && cond4) {
				EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
				try {
					EntityManager entitymanager = emfactory.createEntityManager();
					EntityTransaction entitytransaction = entitymanager.getTransaction();
					entitytransaction.begin();
					Curso cursoResult = entitymanager.find(Curso.class, id);
					if(cursoResult != null) {
						if(cursoResult.getActivo()) {
							TypedQuery<Curso> query = entitymanager.createNamedQuery("negocio.curso.Curso.findBynombre", Curso.class);
							query.setParameter("nombre", nombre);
							List<Curso> lista = query.getResultList();
							if(lista.isEmpty() || lista.get(0).getId() == id) {
								cursoResult.setNombre(nombre);
								cursoResult.setHoras(horas);
								cursoResult.setPlazas(plazas);
								entitymanager.persist(cursoResult);
								entitytransaction.commit();
								idResult = id;
							}
							else {
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

	/*
	 * ERROR
	 * -1: Error datos null
	 * -2: Error sintactico
	 * -3: Curso no existe
	 * -4: Trabajador no existe
	 * -5: Curso no activo
	 * -6: Trabajador no activo
	 * -7: No hay plazas libres
	 * -8: Ya esta matriculado
	 */
	public int matricularTrabajador(CursoTrabajador cursoTrabajador) {
		int id = -1;
		
		if(cursoTrabajador != null) {
			int idCurso = cursoTrabajador.getIdCurso();
			boolean cond1 = ComprobadorSintactico.isPositive(idCurso);
			int idTrabajador = cursoTrabajador.getIdTrabajador();
			boolean cond2 = ComprobadorSintactico.isPositive(idTrabajador);
			if(cond1 && cond2) {
				EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
				try {
					EntityManager entitymanager = emfactory.createEntityManager();
					EntityTransaction entitytransaction = entitymanager.getTransaction();
					entitytransaction.begin();
					Curso cursoResult = entitymanager.find(Curso.class, idCurso);
					Trabajador trabajadorResult = entitymanager.find(Trabajador.class, idTrabajador);
					
					if(cursoResult != null)  {
						if(trabajadorResult != null) {
							if(cursoResult.getActivo()) {
								if(trabajadorResult.getActivo()) {
									int plazas = cursoResult.getPlazas();
									if(plazas>0) {
										RealizaId realizaId = new RealizaId(idTrabajador, idCurso);
										Realiza realizaResult = entitymanager.find(Realiza.class, realizaId);
										
										if(realizaResult == null) {
											cursoResult.setPlazas(plazas-1);
											entitymanager.persist(cursoResult);
											entitymanager.lock(trabajadorResult, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
											Realiza realiza = new Realiza(trabajadorResult, cursoResult);											realiza.setFecha(new Date());
											realiza.setFecha(new Date());
											realiza.setActivo(true);
											entitymanager.persist(realiza);
											entitytransaction.commit();
											id = realizaId.hashCode();
										} else {
											if(realizaResult.getActivo()) {
												entitytransaction.rollback();
												id = -8;
											} else {
												cursoResult.setPlazas(plazas-1);
												entitymanager.persist(cursoResult);
												entitymanager.lock(trabajadorResult, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
												realizaResult.setActivo(true);
												entitymanager.persist(realizaResult);
												entitytransaction.commit();
												id = realizaId.hashCode();
											}
										}
									}
									else {
										id = -7;
									}
								}
								else {
									id = -6;
								}

							}
							else {
								id = -5;
							}
						}
						else {
							entitytransaction.rollback();
							id = -4;
						}
					}
					else {
						entitytransaction.rollback();
						id = -3;
					}
						
					
					entitymanager.close();
					
				} catch(PersistenceException ex) {
					id = -100;
				}

				emfactory.close();
			}
			else
				id = -2;
		}
		
		return id;
	}

	/*
	 * ERROR
	 * -1: Error datos null
	 * -2: Error sintactico
	 * -3: Curso no existe
	 * -4: Trabajador no existe
	 * -5: Curso no activo
	 * -6: Trabajador no activo
	 * -7: Matricula no encontrada
	 * -8: ya esta desmatriculado
	 */
	public int desmatricularTrabajador(CursoTrabajador cursoTrabajador) {
		int id = -1;
		
		if(cursoTrabajador != null) {
			int idCurso = cursoTrabajador.getIdCurso();
			boolean cond1 = ComprobadorSintactico.isPositive(idCurso);
			int idTrabajador = cursoTrabajador.getIdTrabajador();
			boolean cond2 = ComprobadorSintactico.isPositive(idTrabajador);
			if(cond1 && cond2) {
				EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
				try {
					EntityManager entitymanager = emfactory.createEntityManager();
					EntityTransaction entitytransaction = entitymanager.getTransaction();
					entitytransaction.begin();
					Curso cursoResult = entitymanager.find(Curso.class, idCurso);
					Trabajador trabajadorResult = entitymanager.find(Trabajador.class, idTrabajador);
					
					if(cursoResult != null) {
						if(trabajadorResult != null) {
							if(cursoResult.getActivo()) {
								if(trabajadorResult.getActivo()) {
									RealizaId realizaId = new RealizaId(idTrabajador, idCurso);
									Realiza realizaResult = entitymanager.find(Realiza.class, realizaId);
									
									if(realizaResult == null) {
										entitytransaction.rollback();
										id = -7;
									} else {
										if(realizaResult.getActivo()) {
											int plazas = cursoResult.getPlazas();
											cursoResult.setPlazas(plazas+1);
											entitymanager.persist(cursoResult);
											entitymanager.lock(trabajadorResult, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
											realizaResult.setActivo(false);
											entitymanager.persist(realizaResult);
											entitytransaction.commit();
											id = realizaId.hashCode();
											
										} else {
											entitytransaction.rollback();
											id = -8;
										}
									}
								} else {
									entitytransaction.rollback();
									id = -6;
								}
	
							} else {
								entitytransaction.rollback();
								id = -5;
							}
						} else {
							entitytransaction.rollback();
							id = -4;
						}
					} else {
						entitytransaction.rollback();
						id = -3;
					}
					
					entitymanager.close();
					
				} catch(PersistenceException ex) {
					id = -100;
				}

				emfactory.close();
			} else {
				id = -2;
			}
		}
		
		return id;
	}
}