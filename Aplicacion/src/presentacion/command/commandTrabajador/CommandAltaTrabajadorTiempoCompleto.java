
package presentacion.command.commandTrabajador;

import negocio.FactoriaNegocio;
import negocio.trabajador.TiempoCompleto;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandAltaTrabajadorTiempoCompleto implements Command {
	
	public Contexto execute(Object data) {
		TiempoCompleto tc = (TiempoCompleto) data;
		int res = FactoriaNegocio.getInstance().generateSATrabajador().create(tc);
		if(res > 0)
			return new Contexto(Events.RES_ALTA_TRABAJADOR_TIEMPO_COMPLETO_OK, res);
		else
			return new Contexto(Events.RES_ALTA_TRABAJADOR_TIEMPO_COMPLETO_KO, res);
	}
}