package presentacion;

import javax.swing.JFrame;

public abstract class GUIMain extends JFrame implements GUI {
	
	private static final long serialVersionUID = 4289860831452699322L;
	
	private static GUIMain instance;

	public static GUIMain getInstance() {
		
		if(instance == null)
			
			instance = new GUIMainImp();
		
		return instance;
		
	}
	
}