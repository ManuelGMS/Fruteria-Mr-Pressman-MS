package negocio.departamento;

import java.util.List;

public interface SADepartamento {

	public int create(Departamento departamento);

	public int delete(int id);

	public Departamento read(int id);

	public int update(Departamento departamento);

	public List<Departamento> readAll();
	
	public int calcularNominaPorDepartamento(int id);

}