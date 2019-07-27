package negocio.curso;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class RealizaId implements Serializable {

	private static final long serialVersionUID = 0;

	public RealizaId() {
	}
	
	public RealizaId(int trabajador, int curso) {
		this.trabajador = trabajador;
		this.curso = curso;
	}

	private int curso;

	private int trabajador;

	public int getCurso() {
		return curso;
	}

	public void setCurso(int curso) {
		this.curso = curso;
	}

	public int getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(int trabajador) {
		this.trabajador = trabajador;
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof RealizaId))
			return false;
		RealizaId pk = (RealizaId) obj;
		if (!(curso == pk.curso))
			return false;
		if (!(trabajador == pk.trabajador))
			return false;
		return true;
	}

	public int hashCode() {
		int hashcode = 0;
		hashcode += curso;
		hashcode += trabajador;
		return hashcode;
	}
}