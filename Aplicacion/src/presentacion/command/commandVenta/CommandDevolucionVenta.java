package presentacion.command.commandVenta;

import java.util.ArrayList;

import negocio.FactoriaNegocio;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandDevolucionVenta implements Command {

	@Override
	public Contexto execute(Object data) {
		@SuppressWarnings("unchecked")
		ArrayList<Integer> datos = (ArrayList<Integer>)data;
		int res = FactoriaNegocio.getInstance().generateSAVenta().devolution(datos.get(0),datos.get(1),datos.get(2));
		if(res > 0)
			return new Contexto(Events.RES_DEVOLUCION_VENTA_OK, res);
		else
			return new Contexto(Events.RES_DEVOLUCION_VENTA_KO, res);
		
	}

}
