package presentacion.venta;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import main.Main;
import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUI;
import presentacion.controlador.Controlador;

public class GUIAbrirVenta extends JFrame implements GUI {
	
	private static final long serialVersionUID = 1141696264922282747L;

	private String[] labels = { "Cliente" };
	
	private JTextField[] texts = new JTextField[labels.length];

	public GUIAbrirVenta(){
		super();
		initGUI();
	}
	
	private void initGUI() {
	
		this.setTitle("Fruteria Mr. Pressman");
		
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		
		JToolBar northPanel = new JToolBar();
		
		northPanel.add(new JLabel("Abrir Venta"));
			
		JPanel centerPanel = new JPanel(new GridLayout(labels.length, 2));
		
		JLabel label;
		
		for (int i = 0; i < labels.length; ++i) {
					
			label = new JLabel(labels[i] + ": ");
			
			texts[i] = new JTextField();
					
			centerPanel.add(label);
			
			centerPanel.add(texts[i]);
		
		}
				
		centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
			
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			
		JButton button = new JButton("Crear");
			
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String text = "";
							
				boolean create = true;
							
				for (int i = 0; i < labels.length && create; ++i) {
							
					if (texts[i].getText().equalsIgnoreCase("")) 
									
						create = false;
								
						text += texts[i].getText() + "\n"; 
							
				}
							
				if (create) {
								
					String[] out = text.split("\\n");
								
					try {
								
						Controlador.getInstance().accion(new Contexto(Events.ABRIR_VENTA, Integer.parseInt(out[0])));
								
					} catch (Exception ex) {
									
						JOptionPane.showMessageDialog(new JFrame(), "Informacion Erronea", "Error", JOptionPane.ERROR_MESSAGE);
								
					}
							
				}
							
				toFront();
						
			}
							
		});
				
		southPanel.add(button);
		 		
		mainPanel.add(northPanel, BorderLayout.PAGE_START);
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		mainPanel.add(southPanel, BorderLayout.PAGE_END);
		
		mainPanel.setOpaque(true);
		
		this.setContentPane(mainPanel);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		Dimension windowDim = new Dimension((int) (Main.WINDOW_DIM.getWidth()), (int) (Main.WINDOW_DIM.getHeight() * (labels.length + 2)));
		
		this.setBounds(
				(int) (dim.getWidth() / 2 - windowDim.getWidth() / 2),
				(int) (dim.getHeight() / 2 - windowDim.getHeight() / 2),
				(int) (windowDim.getWidth()),
				(int) (windowDim.getHeight())
				);
		
	}

	public void clearData() {

		for (int i = 0; i < labels.length; ++i)
			
			texts[i].setText("");
		
	}

	@Override
	public void update(Contexto contexto) {}

}