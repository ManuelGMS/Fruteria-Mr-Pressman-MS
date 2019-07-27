
package presentacion.command.commandCliente;

import negocio.FactoriaNegocio;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandBajaCliente implements Command {

	public Contexto execute(Object data) {
		int res = FactoriaNegocio.getInstance().generateSACliente().delete((int)data);
		if(res > 0)
			return new Contexto(Events.RES_BAJA_CLIENTE_OK, res);
		else
			return new Contexto(Events.RES_BAJA_CLIENTE_KO, res);
	}
}