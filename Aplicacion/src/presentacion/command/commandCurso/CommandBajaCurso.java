package presentacion.command.commandCurso;

import negocio.FactoriaNegocio;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandBajaCurso implements Command {

	public Contexto execute(Object data) {
		int res = FactoriaNegocio.getInstance().generateSACurso().delete((int) data);
		if(res > 0)
			return new Contexto(Events.RES_BAJA_CURSO_OK, res);
		else
			return new Contexto(Events.RES_BAJA_CURSO_KO, res);
	}
}