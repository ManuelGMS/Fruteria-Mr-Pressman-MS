package presentacion.command.commandDepartamento;

import negocio.FactoriaNegocio;
import negocio.departamento.Departamento;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandActualizarDepartamento implements Command {

	public Contexto execute(Object data) {
		Departamento dep = (Departamento) data;
		int res = FactoriaNegocio.getInstance().generateSADepartamento().update(dep);
		if(res > 0)
			return new Contexto(Events.RES_ACTUALIZAR_DEPARTAMENTO_OK, res);
		else
			return new Contexto(Events.RES_ACTUALIZAR_DEPARTAMENTO_KO, res);
	}
}