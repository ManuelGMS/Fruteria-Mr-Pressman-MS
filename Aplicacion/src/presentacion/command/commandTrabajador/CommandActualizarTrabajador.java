package presentacion.command.commandTrabajador;

import negocio.FactoriaNegocio;
import negocio.trabajador.Trabajador;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandActualizarTrabajador implements Command {

	public Contexto execute(Object data) {
		Trabajador trabajador = (Trabajador) data;
		int res = FactoriaNegocio.getInstance().generateSATrabajador().update(trabajador);
		if(res > 0)
			return new Contexto(Events.RES_ACTUALIZAR_TRABAJADOR_OK, res);
		else
			return new Contexto(Events.RES_ACTUALIZAR_TRABAJADOR_KO, res);
	}
}