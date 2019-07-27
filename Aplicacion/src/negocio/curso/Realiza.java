package negocio.curso;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import negocio.trabajador.Trabajador;

@Entity
@IdClass(RealizaId.class)
@NamedQueries({
		@NamedQuery(name = "negocio.curso.Realiza.findBytrabajador", query = "select obj from Realiza obj where obj.trabajador = :trabajador and obj.activo = 1"),
		@NamedQuery(name = "negocio.curso.Realiza.findBycurso", query = "select obj from Realiza obj where obj.curso = :curso and obj.activo = 1"),
		@NamedQuery(name = "negocio.curso.Realiza.findByfecha", query = "select obj from Realiza obj where obj.fecha = :fecha") })
public class Realiza implements Serializable {

	private static final long serialVersionUID = 0;

	public Realiza() {
	}
	
	public Realiza(Trabajador trabajador, Curso curso) {
		this.trabajador = trabajador;
		this.curso = curso;
	}

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	private Trabajador trabajador;
	
	@Id
	@ManyToOne
	private Curso curso;
	
	private Date fecha;
	
	private boolean activo;

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
	
	

}