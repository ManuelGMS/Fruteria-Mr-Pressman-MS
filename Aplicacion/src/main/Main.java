package main;

import java.awt.Dimension;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import presentacion.Contexto;
import presentacion.Events;
import presentacion.controlador.Controlador;

public class Main {

	public static Dimension WINDOW_DIM = new Dimension(1000, 70);
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				
				} catch (InstantiationException e) {
					
					e.printStackTrace();
				
				} catch (IllegalAccessException e) {
					
					e.printStackTrace();
				
				} catch (UnsupportedLookAndFeelException e) {
				
					e.printStackTrace();
				
				}
				
				Controlador.getInstance().accion(new Contexto(Events.GUI_MOSTRAR,null));
				
			}
			
		});
		
	}

}