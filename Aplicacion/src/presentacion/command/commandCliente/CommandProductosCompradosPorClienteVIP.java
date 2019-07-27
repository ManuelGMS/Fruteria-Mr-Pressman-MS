package presentacion.command.commandCliente;

import java.util.ArrayList;

import negocio.FactoriaNegocio;
import negocio.producto.TProducto;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandProductosCompradosPorClienteVIP implements Command {

	@Override
	public Contexto execute(Object data) {
		ArrayList<TProducto> resC = FactoriaNegocio.getInstance().generateSACliente().productosClienteVIP((int) data);
		if(resC != null)
			return new Contexto(Events.RES_PRODUCTOS_MAS_COMPRADOS_VIP_OK, resC);
		else
			return new Contexto(Events.RES_PRODUCTOS_MAS_COMPRADOS_VIP_KO, resC);
	}

}
