package negocio.trabajador;

import java.util.List;


public interface SATrabajador {

	public int create(Trabajador trabajador);

	public int delete(int id);

	public Trabajador read(int id);

	public int update(Trabajador trabajador);

	public List<Trabajador> readAll();
	
		
}