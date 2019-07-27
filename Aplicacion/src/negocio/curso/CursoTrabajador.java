package negocio.curso;

public class CursoTrabajador {
	
	private int idCurso;
	private int idTrabajador;
	
	public CursoTrabajador(int idCurso, int idTrabajador) {
		this.idCurso = idCurso;
		this.idTrabajador = idTrabajador;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public int getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(int idTrabajador) {
		this.idTrabajador = idTrabajador;
	}
	
}
