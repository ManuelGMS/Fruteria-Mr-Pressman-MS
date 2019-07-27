package presentacion.command.commandProducto;

import java.util.ArrayList;

import negocio.FactoriaNegocio;
import negocio.producto.TProducto;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandListarProductos implements Command {

	public Contexto execute(Object data) {

		ArrayList<TProducto> resPr = FactoriaNegocio.getInstance().generateSAProducto().readAll();
		
		return new Contexto( (resPr != null)? Events.RES_LISTAR_PRODUCTO_OK : Events.RES_LISTAR_PRODUCTO_KO , resPr );
		
	}
	
}