package presentacion.command.commandDepartamento;

import negocio.FactoriaNegocio;
import negocio.departamento.Departamento;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandBuscarDepartamento implements Command {

	public Contexto execute(Object data) {
		Departamento dep = FactoriaNegocio.getInstance().generateSADepartamento().read((int) data);
		if(dep != null)
			return new Contexto(Events.RES_BUSCAR_DEPARTAMENTO_OK, dep);
		else
			return new Contexto(Events.RES_BUSCAR_DEPARTAMENTO_KO, (int)data);
	}
}