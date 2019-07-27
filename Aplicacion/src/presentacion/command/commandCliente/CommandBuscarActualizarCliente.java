package presentacion.command.commandCliente;

import negocio.FactoriaNegocio;
import negocio.cliente.TCliente;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandBuscarActualizarCliente implements Command{

	@Override
	public Contexto execute(Object data) {
		TCliente tC = FactoriaNegocio.getInstance().generateSACliente().read((int)data);
		if(tC != null)
			return new Contexto(Events.RES_BUSCAR_ACTUALIZAR_CLIENTE_OK, tC);
		else
			return new Contexto(Events.RES_BUSCAR_ACTUALIZAR_CLIENTE_KO, (int)data);
	}

}
