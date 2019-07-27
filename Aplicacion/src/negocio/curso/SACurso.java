package negocio.curso;

import java.util.List;

public interface SACurso {

	public int create(Curso curso);

	public int delete(int id);

	public Curso read(int id);

	public List<Curso> readAll();

	public int update(Curso curso);
	
	public int matricularTrabajador(CursoTrabajador cursoTrabajador);
	
	public int desmatricularTrabajador(CursoTrabajador cursoTrabajador);
	
}