package presentacion.command.commandCurso;

import java.util.List;

import negocio.FactoriaNegocio;
import negocio.curso.Curso;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandListarCurso implements Command {
	
	public Contexto execute(Object data) {
		List<Curso> resC = FactoriaNegocio.getInstance().generateSACurso().readAll();
		if(resC != null)
			return new Contexto(Events.RES_LISTAR_CURSO_OK, resC);
		else
			return new Contexto(Events.RES_LISTAR_CURSO_KO, resC);
	}
}