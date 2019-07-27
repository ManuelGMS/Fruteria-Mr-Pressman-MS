package presentacion.command.commandTrabajador;

import negocio.FactoriaNegocio;
import negocio.trabajador.Trabajador;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandBuscarTrabajador implements Command {

	public Contexto execute(Object data) {
		Trabajador trabajador = FactoriaNegocio.getInstance().generateSATrabajador().read((int) data);
		if(trabajador != null)
			return new Contexto(Events.RES_BUSCAR_TRABAJADOR_OK, trabajador);
		else
			return new Contexto(Events.RES_BUSCAR_TRABAJADOR_KO, (int)data);
	}
}