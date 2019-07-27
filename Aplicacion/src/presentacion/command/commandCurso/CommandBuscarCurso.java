package presentacion.command.commandCurso;

import negocio.FactoriaNegocio;
import negocio.curso.Curso;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandBuscarCurso implements Command {

	public Contexto execute(Object data) {
		Curso curso = FactoriaNegocio.getInstance().generateSACurso().read((int) data);
		if(curso != null)
			return new Contexto(Events.RES_BUSCAR_CURSO_OK, curso);
		else
			return new Contexto(Events.RES_BUSCAR_CURSO_KO, (int)data);
	}
}