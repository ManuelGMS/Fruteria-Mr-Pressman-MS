package presentacion.command.commandProducto;

import negocio.FactoriaNegocio;
import negocio.producto.TProducto;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandBuscarProducto implements Command {
	
	public Contexto execute(Object data) {
	
		TProducto tPr = FactoriaNegocio.getInstance().generateSAProducto().read((int)data);
		
		return new Contexto( 
			(tPr != null)? Events.RES_BUSCAR_PRODUCTO_OK : Events.RES_BUSCAR_PRODUCTO_KO , 
			(tPr != null)? tPr : ((int)data)
		);
		
	}
	
}