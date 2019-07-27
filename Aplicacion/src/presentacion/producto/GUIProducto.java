package presentacion.producto;

import javax.swing.JFrame;

import presentacion.GUI;

public abstract class GUIProducto extends JFrame implements GUI {
	
	private static final long serialVersionUID = 2282964712537654368L;
	
	private static GUIProducto instance;

	public static GUIProducto getInstance() {
		
		if(instance == null)
			
			instance = new GUIProductoImp();
		
		instance.setVisible(true);
		
		return instance;
		
	}

}