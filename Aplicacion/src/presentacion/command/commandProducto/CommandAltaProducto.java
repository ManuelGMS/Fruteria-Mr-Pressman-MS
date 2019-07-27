
package presentacion.command.commandProducto;

import negocio.FactoriaNegocio;
import negocio.producto.TProducto;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandAltaProducto implements Command {
	
	public Contexto execute(Object data) {
		
		int res = FactoriaNegocio.getInstance().generateSAProducto().create((TProducto)data);
		
		return new Contexto( (res > 0)? Events.RES_ALTA_PRODUCTO_OK : Events.RES_ALTA_PRODUCTO_KO , res );
		
	}
	
}