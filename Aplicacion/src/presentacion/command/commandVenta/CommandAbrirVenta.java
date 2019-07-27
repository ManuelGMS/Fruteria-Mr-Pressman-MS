
package presentacion.command.commandVenta;

import negocio.FactoriaNegocio;
import negocio.venta.TVenta;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;

public class CommandAbrirVenta implements Command {
	
	public Contexto execute(Object data) {
		
		TVenta tV = FactoriaNegocio.getInstance().generateSAVenta().openSale((int)data);
		
		return new Contexto( 
			(tV != null)? Events.RES_ABRIR_VENTA_OK : Events.RES_ABRIR_VENTA_KO ,
			(tV != null)? tV : (int)data
		);
		
	}
	
}