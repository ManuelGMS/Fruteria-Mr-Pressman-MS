
package presentacion.cliente;

import javax.swing.JFrame;

import presentacion.GUI;

public abstract class GUICliente extends JFrame implements GUI {
	
	private static final long serialVersionUID = 6856595862731334871L;
	
	private static GUICliente instance;

	public static GUICliente getInstance() {
		if(instance == null)
			instance = new GUIClienteImp();
		instance.setVisible(true);
		return instance;
	}
}