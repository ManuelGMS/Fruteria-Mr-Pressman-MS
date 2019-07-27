
package presentacion.command.commandVenta;

import negocio.FactoriaNegocio;
import negocio.venta.TVenta;
import presentacion.Command;
import presentacion.Contexto;
import presentacion.Events;
import presentacion.venta.GUIVenta;

public class CommandCerrarVenta implements Command {
	
	public Contexto execute(Object data) {
		
		int res = FactoriaNegocio.getInstance().generateSAVenta().closeSale((TVenta)data);
		
		GUIVenta.getInstance().setVenta(null);
		
		return new Contexto( (res > 0)? Events.RES_CERRAR_VENTA_OK : Events.RES_CERRAR_VENTA_KO , res );
		
	}
	
}