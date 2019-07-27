
package presentacion.command.commandProducto;

import negocio.FactoriaNegocio;
import negocio.producto.TProducto;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandActualizarProducto implements Command {
	
	public Contexto execute(Object data) {
	
		int res = FactoriaNegocio.getInstance().generateSAProducto().update((TProducto)data);
		
		return new Contexto( (res > 0)? Events.RES_ACTUALIZAR_PRODUCTO_OK : Events.RES_ACTUALIZAR_PRODUCTO_KO , res );
	
	}
	
}