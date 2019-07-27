package presentacion.command.commandDepartamento;

import negocio.FactoriaNegocio;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandCalcularNominaPorDepartamento implements Command {

	public Contexto execute(Object data) {
		int suma = FactoriaNegocio.getInstance().generateSADepartamento().calcularNominaPorDepartamento((int) data);
		if(suma >= 0)
			return new Contexto(Events.RES_CALCULAR_NOMINAS_TRABAJADOR_OK, suma);
		else
			return new Contexto(Events.RES_CALCULAR_NOMINAS_TRABAJADOR_KO, suma);
	}
}