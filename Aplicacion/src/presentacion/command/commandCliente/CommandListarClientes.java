
package presentacion.command.commandCliente;

import java.util.ArrayList;

import negocio.FactoriaNegocio;
import negocio.cliente.TCliente;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandListarClientes implements Command {
	
	public Contexto execute(Object data) {
		ArrayList<TCliente> resC = FactoriaNegocio.getInstance().generateSACliente().readAll();
		if(resC != null)
			return new Contexto(Events.RES_LISTAR_CLIENTES_OK, resC);
		else
			return new Contexto(Events.RES_LISTAR_CLIENTES_KO, resC);
	}
}