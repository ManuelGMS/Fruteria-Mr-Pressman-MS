
package presentacion.command.commandCliente;

import negocio.FactoriaNegocio;
import negocio.cliente.TCliente;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandActualizarCliente implements Command {
	
	public Contexto execute(Object data) {
		TCliente tC = (TCliente) data;
		int res = FactoriaNegocio.getInstance().generateSACliente().update(tC);
		if(res > 0)
			return new Contexto(Events.RES_ACTUALIZAR_CLIENTE_OK, res);
		else
			return new Contexto(Events.RES_ACTUALIZAR_CLIENTE_KO, res);
	}
}