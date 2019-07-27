package negocio.departamento;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import negocio.trabajador.Trabajador;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.departamento.Departamento.readAll", query = "select obj from Departamento obj where obj.activo = 1"),
		@NamedQuery(name = "negocio.departamento.Departamento.findBynombre", query = "select obj from Departamento obj where obj.nombre = :nombre") })
public class Departamento implements Serializable {

	private static final long serialVersionUID = 0;

	public Departamento() {};
	
	public Departamento(Integer id, String nombre, int fondos,  boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.fondos = fondos;
		this.activo = activo;
	}
	
	public Departamento(String nombre, int fondos,  boolean activo) {
		this.nombre = nombre;
		this.fondos = fondos;
		this.activo = activo;
	}


	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Version
	private int version;
	
	private int fondos;

	private String nombre;
	
	@OneToMany(mappedBy = "departamento")
	private List<Trabajador> trabajador;
	
	private boolean activo;

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getFondos() {
		return this.fondos;
	}

	public void setFondos(int fondos) {
		this.fondos = fondos;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean getActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public int calcularNomina() {
		int suma = 0;
		Iterator<Trabajador> it = this.trabajador.iterator();
		Trabajador trabajador;
		while(it.hasNext()) {
			trabajador = it.next();
			boolean cond = trabajador.getActivo();
			if(cond)
				suma += trabajador.calcularNomina();
		}
		return suma;
	}
}