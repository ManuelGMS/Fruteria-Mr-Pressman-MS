package negocio.trabajador;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import negocio.departamento.Departamento;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.trabajador.TiempoCompleto.findByid", query = "select obj from TiempoCompleto obj where obj.id = :id"),
		@NamedQuery(name = "negocio.trabajador.TiempoCompleto.findBysueldo", query = "select obj from TiempoCompleto obj where obj.sueldo = :sueldo"),
		@NamedQuery(name = "negocio.trabajador.TiempoCompleto.findByantiguedad", query = "select obj from TiempoCompleto obj where obj.antiguedad = :antiguedad") })
public class TiempoCompleto extends Trabajador implements Serializable {

	private static final long serialVersionUID = 0;
	
	public TiempoCompleto() {}

	public TiempoCompleto(int id, String nombre, String correo, Departamento departamento, boolean activo, int sueldo, int antiguedad) {
		super(id, nombre, correo, departamento, activo);
		this.sueldo = sueldo;
		this.antiguedad = antiguedad;
	}
	
	public TiempoCompleto(String nombre, String correo, Departamento departamento, boolean activo, int sueldo, int antiguedad) {
		super(nombre, correo, departamento, activo);
		this.sueldo = sueldo;
		this.antiguedad = antiguedad;
	}

	private int sueldo;
	
	public int getSueldo() {
		return sueldo;
	}
	
	public void setSueldo(int sueldo) {
		this.sueldo = sueldo;
	}

	private int antiguedad;
	
	public int getAntiguedad() {
		return antiguedad;
	}

	public void setAntiguedad(int antiguedad) {
		this.antiguedad = antiguedad;
	}

	@Override
	public int calcularNomina() {
		return sueldo;
	}
}