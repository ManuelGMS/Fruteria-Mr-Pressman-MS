package presentacion.venta;

import javax.swing.JFrame;

import negocio.venta.TVenta;
import presentacion.Contexto;
import presentacion.GUI;

public abstract class GUIVenta extends JFrame implements GUI {
	
	private static final long serialVersionUID = 8030134574066809715L;
	
	protected TVenta tV;
	
	private static GUIVenta instance;

	public static GUIVenta getInstance() {
		
		if(instance == null)
		
			instance = new GUIVentaImp();
		
		instance.setVisible(true);
	
		return instance;
	
	}
	
	public abstract TVenta getVenta();
	public abstract void setVenta(TVenta tV);
	public abstract void update(Contexto contexto);
	
}