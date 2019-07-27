package negocio.departamento;

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

public class SADepartamentoImp implements SADepartamento {
	
	public int create(Departamento departamento) {
		int id = -1;
		if(departamento != null) {
			String nombre = departamento.getNombre();
			boolean cond1 = ComprobadorSintactico.isName(nombre);
			int fondos = departamento.getFondos();
			boolean cond2 = ComprobadorSintactico.isPositive(fondos);
			if(cond1 && cond2) {
				EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
				try {
					EntityManager entitymanager = emfactory.createEntityManager();
					EntityTransaction entitytransaction = entitymanager.getTransaction();
					entitytransaction.begin();
					TypedQuery<Departamento> query = entitymanager.createNamedQuery("negocio.departamento.Departamento.findBynombre", Departamento.class).setParameter("nombre", nombre);
					List<Departamento> lista = query.getResultList();
					if(lista.isEmpty()) {
						departamento.setActivo(true);
						entitymanager.persist(departamento);
						entitytransaction.commit();
						id = departamento.getId();
					}
					else {
						Departamento departamentoResult = lista.get(0);
						boolean activo = departamentoResult.getActivo();
						if(activo) {
							id = -3;
							entitytransaction.rollback();
						}
						else {
							//reactivacion
							departamentoResult.setActivo(true);
							departamentoResult.setFondos(fondos);
							entitytransaction.commit();
							id = departamentoResult.getId(); 
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
				Departamento departamento = entitymanager.find(Departamento.class, id);
				if(departamento != null) {
					boolean cond2 = departamento.getActivo();
					if(cond2) {
						TypedQuery<Trabajador> query = entitymanager.createNamedQuery("negocio.trabajador.Trabajador.findBydepartamento", Trabajador.class).setParameter("departamento", departamento);
						List<Trabajador> lista = query.getResultList();
						if(lista.isEmpty()) {
							departamento.setActivo(false);
							entitymanager.persist(departamento);
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

	public Departamento read(int id) {
		Departamento departamento = null;
		boolean cond = ComprobadorSintactico.isPositive(id);
		if(cond) {
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
			try {
				EntityManager entitymanager = emfactory.createEntityManager();
				EntityTransaction entitytransaction = entitymanager.getTransaction();
				entitytransaction.begin();
				
				departamento = entitymanager.find(Departamento.class, id);
				
				if(departamento == null)
					entitytransaction.rollback();
				else
					entitytransaction.commit();
				
				entitymanager.close();
				emfactory.close();
			} catch(PersistenceException ex) {}
		}
		
		return departamento;
	}

	public int update(Departamento departamento) {
		int idResult = -1;
		if(departamento != null) {
			int id = departamento.getId();
			boolean cond1 = ComprobadorSintactico.isPositive(id);
			String nombre = departamento.getNombre();
			boolean cond2 = ComprobadorSintactico.isName(nombre);
			int fondos = departamento.getFondos();
			boolean cond3 = ComprobadorSintactico.isPositive(fondos);
			if(cond1 && cond2 && cond3) {
				EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
				try {
					EntityManager entitymanager = emfactory.createEntityManager();
					EntityTransaction entitytransaction = entitymanager.getTransaction();
					entitytransaction.begin();
					Departamento departamentoResult = entitymanager.find(Departamento.class, id);
					if(departamentoResult != null) {
						if(departamentoResult.getActivo()) {
							TypedQuery<Departamento> query = entitymanager.createNamedQuery("negocio.departamento.Departamento.findBynombre", Departamento.class);
							query.setParameter("nombre", nombre);
							List<Departamento> lista = query.getResultList();
							if(lista.isEmpty() || lista.get(0).getId() == id) {
								departamentoResult.setNombre(nombre);
								departamentoResult.setFondos(fondos);
								entitymanager.persist(departamentoResult);
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

	public List<Departamento> readAll() {
		List<Departamento> lista = null;
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
		try {
			EntityManager entitymanager = emfactory.createEntityManager();
			EntityTransaction entitytransaction = entitymanager.getTransaction();
			entitytransaction.begin();
				
			TypedQuery<Departamento> query = entitymanager.createNamedQuery("negocio.departamento.Departamento.readAll", Departamento.class);
			lista = query.getResultList();
			entitytransaction.commit();
				
			entitymanager.close();
			emfactory.close();
		} catch(PersistenceException ex) {}
		
		return lista;
	}
	public int calcularNominaPorDepartamento(int id) {
		int suma = -1;
		boolean cond = ComprobadorSintactico.isPositive(id);
		if(cond) {
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("FruteriaMrPressmanCode");
			try {
				EntityManager entitymanager = emfactory.createEntityManager();
				EntityTransaction entitytransaction = entitymanager.getTransaction();
				entitytransaction.begin();
				Departamento departamento = entitymanager.find(Departamento.class, id, LockModeType.OPTIMISTIC);
				if(departamento != null) {
					suma = departamento.calcularNomina();
					entitytransaction.commit();
				}
				else {
					suma = -2;
					entitytransaction.rollback();
				}
				
					
				entitymanager.close();
				emfactory.close();
			} catch(PersistenceException ex) {
				suma = -100;
			}
		}
		
		
		return suma;
	}
}