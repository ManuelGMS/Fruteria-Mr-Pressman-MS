package negocio.trabajador;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import negocio.departamento.Departamento;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.trabajador.TiempoParcial.findByid", query = "select obj from TiempoParcial obj where obj.id = :id"),
		@NamedQuery(name = "negocio.trabajador.TiempoParcial.findByhorasJornada", query = "select obj from TiempoParcial obj where obj.horasJornada = :horasJornada"),
		@NamedQuery(name = "negocio.trabajador.TiempoParcial.findBysueldoHora", query = "select obj from TiempoParcial obj where obj.sueldoHora = :sueldoHora") })
public class TiempoParcial extends Trabajador implements Serializable {

	private static final long serialVersionUID = 0;

	public TiempoParcial() {}

	public TiempoParcial(int id, String nombre, String correo, Departamento departamento, boolean activo, int sueldo, int horas) {
		super(id, nombre, correo, departamento, activo);
		this.horasJornada = horas;
		this.sueldoHora = sueldo;
	}

	public TiempoParcial(String nombre, String correo, Departamento departamento, boolean activo, int sueldo, int horas) {
		super(nombre, correo, departamento, activo);
		this.horasJornada = horas;
		this.sueldoHora = sueldo;
	}

	private int horasJornada;
	
	public int getHorasJornada() {
		return horasJornada;
	}
	public void setHorasJornada(int horasJornada) {
		this.horasJornada = horasJornada;
	}

	private int sueldoHora;
	
	public int getSueldoHora() {
		return sueldoHora;
	}
	public void setSueldoHora(int sueldoHora) {
		this.sueldoHora = sueldoHora;
	}

	@Override
	public int calcularNomina() {
		return this.sueldoHora*this.horasJornada*30;
	}
}