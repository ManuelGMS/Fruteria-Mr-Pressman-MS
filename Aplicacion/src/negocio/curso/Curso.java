package negocio.curso;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.curso.Curso.readAll", query = "select obj from Curso obj where obj.activo = 1"),
		@NamedQuery(name = "negocio.curso.Curso.findBynombre", query = "select obj from Curso obj where obj.nombre = :nombre")})
public class Curso implements Serializable {

	private static final long serialVersionUID = 0;

	public Curso() {}

	public Curso(int id, String nombre, int horas, int plazas, boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.horas = horas;
		this.plazas = plazas;
		this.activo = activo;
	}
	
	public Curso(String nombre, int horas, int plazas, boolean activo) {
		this.nombre = nombre;
		this.horas = horas;
		this.plazas = plazas;
		this.activo = activo;
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Version
	private int version;
	
	private String nombre;
	
	private int horas;

	private int plazas;
	
	private boolean activo;
	
	@OneToMany(mappedBy = "curso", fetch = FetchType.EAGER)
	private List<Realiza> realiza;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public int getPlazas() {
		return plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}

	public List<Realiza> getRealiza() {
		return realiza;
	}

	public void setRealiza(List<Realiza> realiza) {
		this.realiza = realiza;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
	
	
}