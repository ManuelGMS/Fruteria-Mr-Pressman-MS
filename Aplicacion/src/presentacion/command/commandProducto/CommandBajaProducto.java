
package presentacion.command.commandProducto;

import negocio.FactoriaNegocio;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandBajaProducto implements Command {
	
	public Contexto execute(Object data) {
	
		int res = FactoriaNegocio.getInstance().generateSAProducto().delete((int)data);
		
		return new Contexto( (res > 0)? Events.RES_BAJA_PRODUCTO_OK : Events.RES_BAJA_PRODUCTO_KO , res );
		
	}
	
}