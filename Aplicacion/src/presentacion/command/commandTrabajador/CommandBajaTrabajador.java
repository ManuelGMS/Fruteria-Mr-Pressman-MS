package presentacion.command.commandTrabajador;

import negocio.FactoriaNegocio;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandBajaTrabajador implements Command {

	public Contexto execute(Object data) {
		int res = FactoriaNegocio.getInstance().generateSATrabajador().delete((int) data);
		if(res > 0)
			return new Contexto(Events.RES_BAJA_TRABAJADOR_OK, res);
		else
			return new Contexto(Events.RES_BAJA_TRABAJADOR_KO, res);
	}
}