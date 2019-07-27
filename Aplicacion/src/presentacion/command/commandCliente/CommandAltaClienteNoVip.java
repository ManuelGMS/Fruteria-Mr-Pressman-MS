
package presentacion.command.commandCliente;

import negocio.FactoriaNegocio;
import negocio.cliente.TClienteNoVIP;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandAltaClienteNoVip implements Command {
	
	public Contexto execute(Object data) {
		TClienteNoVIP tCnV = (TClienteNoVIP) data;
		int res = FactoriaNegocio.getInstance().generateSACliente().create(tCnV);
		if(res > 0)
			return new Contexto(Events.RES_ALTA_CLIENTE_NO_VIP_OK, res);
		else
			return new Contexto(Events.RES_ALTA_CLIENTE_NO_VIP_KO, res);
	}
}