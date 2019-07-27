
package presentacion.command.commandCliente;

import negocio.FactoriaNegocio;
import negocio.cliente.TClienteVIP;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandAltaClienteVip implements Command {
	
	public Contexto execute(Object data) {
		TClienteVIP tCV = (TClienteVIP) data;
		int res = FactoriaNegocio.getInstance().generateSACliente().create(tCV);
		if(res > 0)
			return new Contexto(Events.RES_ALTA_CLIENTE_VIP_OK, res);
		else
			return new Contexto(Events.RES_ALTA_CLIENTE_VIP_KO, res);
	}
}