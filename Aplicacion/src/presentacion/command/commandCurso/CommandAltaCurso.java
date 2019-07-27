package presentacion.command.commandCurso;

import negocio.FactoriaNegocio;
import negocio.curso.Curso;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandAltaCurso implements Command {

	public Contexto execute(Object data) {
		Curso curso = (Curso) data;
		int res = FactoriaNegocio.getInstance().generateSACurso().create(curso);
		if(res > 0)
			return new Contexto(Events.RES_ALTA_CURSO_OK, res);
		else
			return new Contexto(Events.RES_ALTA_CURSO_KO, res);
	}
}