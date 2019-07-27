
package presentacion.command.commandVenta;

import java.util.ArrayList;

import negocio.FactoriaNegocio;
import negocio.venta.TVenta;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandListarVentas implements Command {
	
	public Contexto execute(Object data) {
		
		ArrayList<TVenta> respV = FactoriaNegocio.getInstance().generateSAVenta().readAll();
		
		return new Contexto( 
			(respV != null)? Events.RES_LISTAR_VENTAS_OK : Events.RES_LISTAR_VENTAS_KO , 
			respV
		);
		
	}
	
}