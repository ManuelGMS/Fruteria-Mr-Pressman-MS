package presentacion;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GUIMensaje extends JDialog {
	
	private static final long serialVersionUID = -5561244042688324871L;

	public void showMessage(String message1, String message2, boolean error) {
		
		if(!error)
		
			JOptionPane.showMessageDialog(new JFrame(),message1	, message2, JOptionPane.INFORMATION_MESSAGE);
		
		else 
			
			JOptionPane.showMessageDialog(new JFrame(),message1	, message2, JOptionPane.ERROR_MESSAGE);

	}
	
}