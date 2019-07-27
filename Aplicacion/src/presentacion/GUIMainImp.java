package presentacion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Main;
import presentacion.controlador.Controlador;

public class GUIMainImp extends GUIMain {
	
	private static final long serialVersionUID = 9036387712550963949L;

	public static String[] module = {"Productos", "Ventas", "Clientes", "Departamentos", "Cursos", "Trabajadores"};

	public GUIMainImp() {
		super();
		initGUI();
	}

	private void initGUI() {

		JPanel mainPanel = new JPanel(new BorderLayout());
		
		mainPanel.add(new JLabel("Gestion de Fruteria"), BorderLayout.PAGE_START);
		
			JPanel centerPanel = new JPanel(new GridLayout(module.length, 1));
			
			for (int i = 0; i < module.length; ++i) {
				
				final int j = i;
				
				JButton button = new JButton(module[j]);
				
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						switch (module[j]) {
							case "Productos": 
								Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_PRODUCTO, null)); 
							break;
							case "Ventas": 
								Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_VENTA, null)); 
							break;
							case "Clientes": 
								Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_CLIENTE, null)); 
							break;
							case "Departamentos": 
								Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_DEPARTAMENTO, null)); 
							break;
							case "Cursos":
								Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_CURSO, null)); 
							break;
							case "Trabajadores": 
								Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_TRABAJADOR, null)); 
							break;
						}
						
					}
					
				});
				
				centerPanel.add(button);
				
			}
		
			mainPanel.add(centerPanel, BorderLayout.CENTER);
		
			mainPanel.setOpaque(true);
		
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
			Dimension windowDim = new Dimension((int) (Main.WINDOW_DIM.getWidth()), (int) (Main.WINDOW_DIM.getHeight() * (module.length + 1)));
		
			this.setBounds(
				(int) (dim.getWidth() / 2 - windowDim.getWidth() / 2),
				(int) (dim.getHeight() / 2 - windowDim.getHeight() / 2),
				(int) (windowDim.getWidth()),
				(int) (windowDim.getHeight())
			);

			this.setContentPane(mainPanel);
			this.setTitle("Fruteria Mr. Pressman");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);

	}

	public void update(Contexto contexto) {
		initGUI();
	}
	
}