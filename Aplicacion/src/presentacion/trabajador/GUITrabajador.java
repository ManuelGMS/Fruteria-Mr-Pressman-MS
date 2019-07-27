package presentacion.trabajador;

import javax.swing.JFrame;

import presentacion.GUI;

public abstract class GUITrabajador extends JFrame implements GUI {

	private static final long serialVersionUID = 1L;
	private static GUITrabajador instance;

	public static GUITrabajador getInstance() {
		if(instance == null)
			
			instance = new GUITrabajadorImp();
		
		instance.setVisible(true);
		
		return instance;
	}

}