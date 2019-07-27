package presentacion.command.commandCurso;

import negocio.FactoriaNegocio;
import negocio.curso.CursoTrabajador;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandMatricularTrabajador implements Command {
	
	public Contexto execute(Object data) {
		
		int id = FactoriaNegocio.getInstance().generateSACurso().matricularTrabajador(((CursoTrabajador)data));
		
		if (id > 0)
			return new Contexto(Events.RES_MATRICULAR_TRABAJADOR_OK, id);
		else
			return new Contexto(Events.RES_MATRICULAR_TRABAJADOR_KO, id);
	}
}