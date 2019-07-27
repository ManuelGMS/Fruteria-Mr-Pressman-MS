package presentacion.command.commandTrabajador;

import negocio.FactoriaNegocio;
import negocio.trabajador.Trabajador;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandBuscarActualizarTrabajador implements Command{

	@Override
	public Contexto execute(Object data) {
		Trabajador tr = FactoriaNegocio.getInstance().generateSATrabajador().read((int)data);
		if(tr != null)
			return new Contexto(Events.RES_BUSCAR_ACTUALIZAR_TRABAJADOR_OK, tr);
		else
			return new Contexto(Events.RES_BUSCAR_ACTUALIZAR_TRABAJADOR_KO, (int)data);
	}

}
