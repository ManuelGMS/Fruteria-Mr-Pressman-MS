package presentacion.command.commandCliente;

import java.util.ArrayList;

import negocio.FactoriaNegocio;
import negocio.cliente.ClienteMes;
import negocio.producto.TProducto;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandProductoMasComprado implements Command {

	@Override
	public Contexto execute(Object data) {
		ArrayList<TProducto> resC = FactoriaNegocio.getInstance().generateSACliente().productoMasComprado((ClienteMes) data);
		if(resC != null)
			return new Contexto(Events.RES_PRODUCTO_MAS_COMPRADO_OK, resC);
		else
			return new Contexto(Events.RES_PRODUCTO_MAS_COMPRADO_KO, resC);
	}

}
