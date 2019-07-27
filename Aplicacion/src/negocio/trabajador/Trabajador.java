package negocio.trabajador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import negocio.curso.Realiza;
import negocio.departamento.Departamento;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.trabajador.Trabajador.findBycorreo", query = "select obj from Trabajador obj where obj.correo = :correo"),
		@NamedQuery(name = "negocio.trabajador.Trabajador.findBydepartamento", query = "select obj from Trabajador obj where obj.departamento = :departamento and obj.activo = 1"),
		@NamedQuery(name = "negocio.trabajador.Trabajador.readAll", query = "select obj from Trabajador obj where obj.activo = 1"),
		@NamedQuery(name = "negocio.trabajador.Trabajador.findByrealiza", query = "select obj from Trabajador obj where obj.realiza = :realiza")
		})
public abstract class Trabajador implements Serializable {

	private static final long serialVersionUID = 0;

	public Trabajador() {}

	public Trabajador(int id, String nombre, String correo, Departamento departamento, boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.departamento = departamento;
		this.activo = activo;
		this.realiza = new ArrayList<>();
	}
	
	public Trabajador(String nombre, String correo, Departamento departamento, boolean activo) {
		this.nombre = nombre;
		this.correo = correo;
		this.departamento = departamento;
		this.activo = activo;
		this.realiza = new ArrayList<>();
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Version
	private int version;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String nombre;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private String correo;

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	@ManyToOne
	private Departamento departamento;

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@OneToMany(mappedBy = "trabajador")
	private List<Realiza> realiza;

	public List<Realiza> getRealiza() {
		return realiza;
	}

	public void setRealiza(List<Realiza> realiza) {
		this.realiza = realiza;
	}
	
	private boolean activo;
	
	public boolean getActivo() {
		return activo;
	}
	
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	abstract public int calcularNomina();
}