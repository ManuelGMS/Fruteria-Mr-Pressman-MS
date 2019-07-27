package presentacion.command.commandTrabajador;

import negocio.FactoriaNegocio;
import negocio.trabajador.TiempoParcial;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandAltaTrabajadorTiempoParcial implements Command {

	public Contexto execute(Object data) {
		TiempoParcial tp = (TiempoParcial) data;
		int res = FactoriaNegocio.getInstance().generateSATrabajador().create(tp);
		if(res > 0)
			return new Contexto(Events.RES_ALTA_TRABAJADOR_TIEMPO_PARCIAL_OK, res);
		else
			return new Contexto(Events.RES_ALTA_TRABAJADOR_TIEMPO_PARCIAL_KO, res);
	}
}