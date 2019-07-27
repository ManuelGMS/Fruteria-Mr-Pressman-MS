package presentacion.command.commandDepartamento;

import negocio.FactoriaNegocio;
import negocio.departamento.Departamento;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandAltaDepartamento implements Command {

	public Contexto execute(Object data) {
		Departamento dep = (Departamento) data;
		int res = FactoriaNegocio.getInstance().generateSADepartamento().create(dep);
		if(res > 0)
			return new Contexto(Events.RES_ALTA_DEPARTAMENTO_OK, res);
		else
			return new Contexto(Events.RES_ALTA_DEPARTAMENTO_KO, res);
	}
}