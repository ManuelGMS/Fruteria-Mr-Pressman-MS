package presentacion.curso;

import javax.swing.JFrame;

import presentacion.GUI;

public abstract class GUICurso extends JFrame implements GUI {
	
	private static final long serialVersionUID = 1L;
	private static GUICurso instance;

	public static GUICurso getInstance() {
		if(instance == null)
			
			instance = new GUICursoImp();
		
		instance.setVisible(true);
		
		return instance;
	}

}