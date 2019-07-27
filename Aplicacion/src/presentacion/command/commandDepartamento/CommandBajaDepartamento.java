package presentacion.command.commandDepartamento;

import negocio.FactoriaNegocio;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandBajaDepartamento implements Command {

	public Contexto execute(Object data) {
		int res = FactoriaNegocio.getInstance().generateSADepartamento().delete((int) data);
		if(res > 0)
			return new Contexto(Events.RES_BAJA_DEPARTAMENTO_OK, res);
		else
			return new Contexto(Events.RES_BAJA_DEPARTAMENTO_KO, res);
	}
}