package presentacion.command.commandCurso;

import negocio.FactoriaNegocio;
import negocio.curso.CursoTrabajador;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandDesmatricularTrabajador implements Command {
	
	public Contexto execute(Object data) {
		
		int id = FactoriaNegocio.getInstance().generateSACurso().desmatricularTrabajador(((CursoTrabajador)data));
		
		if (id > 0)
			return new Contexto(Events.RES_DESMATRICULAR_TRABAJADOR_OK, id);
		else
			return new Contexto(Events.RES_DESMATRICULAR_TRABAJADOR_KO, id);
		
	}
	
}