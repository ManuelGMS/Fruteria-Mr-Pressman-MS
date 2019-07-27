package presentacion.command.commandTrabajador;

import java.util.List;

import negocio.FactoriaNegocio;
import negocio.trabajador.Trabajador;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandListarTrabajador implements Command {
	
	public Contexto execute(Object data) {
		List<Trabajador> resT = FactoriaNegocio.getInstance().generateSATrabajador().readAll();
		if(resT != null)
			return new Contexto(Events.RES_LISTAR_TRABAJADOR_OK, resT);
		else
			return new Contexto(Events.RES_LISTAR_TRABAJADOR_KO, resT);
	}
}