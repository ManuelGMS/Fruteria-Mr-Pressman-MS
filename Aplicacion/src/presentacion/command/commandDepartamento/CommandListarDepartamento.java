package presentacion.command.commandDepartamento;

import java.util.List;

import negocio.FactoriaNegocio;
import negocio.departamento.Departamento;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandListarDepartamento implements Command {

	public Contexto execute(Object data) {
		List<Departamento> resD = FactoriaNegocio.getInstance().generateSADepartamento().readAll();
		if(resD != null)
			return new Contexto(Events.RES_LISTAR_DEPARTAMENTO_OK, resD);
		else
			return new Contexto(Events.RES_LISTAR_DEPARTAMENTO_KO, resD);
	}
}