package presentacion.departamento;

import javax.swing.JFrame;

import presentacion.GUI;

public abstract class GUIDepartamento extends JFrame implements GUI {
	
	private static final long serialVersionUID = 1L;
	
	private static GUIDepartamento instance;

	public static GUIDepartamento getInstance() {
		if(instance == null)
			
			instance = new GUIDepartamentoImp();
		
		instance.setVisible(true);
		
		return instance;
	}

}