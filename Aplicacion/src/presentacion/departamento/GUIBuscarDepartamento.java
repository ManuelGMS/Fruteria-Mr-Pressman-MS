package presentacion.departamento;

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
import negocio.departamento.Departamento;
import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUI;
import presentacion.controlador.Controlador;

public class GUIBuscarDepartamento extends JFrame implements GUI{
	
	private static final long serialVersionUID = 1L;
	
	private String[] labels = { "Id", "Nombre", "Fondos" };
	
	private JTextField[] texts = new JTextField[labels.length];

	public GUIBuscarDepartamento() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		
		this.setTitle("Fruteria Mr. Pressman");
		
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
			
		JToolBar northPanel = new JToolBar();
			
		northPanel.add(new JLabel("Buscar Departamento"));
				
		JPanel centerPanel = new JPanel(new GridLayout(labels.length, 2));
				
		JLabel label;
				
		for (int i = 0; i < labels.length; ++i) {
					
			label = new JLabel(labels[i] + ": ");
						
			texts[i] = new JTextField();
						
			if (!labels[i].equalsIgnoreCase("Id")) {
							
				texts[i].setEditable(false);
							
				texts[i].setEnabled(false);
						
			}
					
			centerPanel.add(label);
					
			centerPanel.add(texts[i]);
				
		}

		centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
			
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			
		JButton button = new JButton("Buscar");
				
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String text = "";
						
				boolean create = true;
						
				for (int i = 0; i < 1 && create; ++i) {
							
					if (texts[i].getText().equalsIgnoreCase("")) 
						
						create = false;
							
					text += texts[i].getText() + "\n"; 
						
				}
						
				if (create) {
							
					String[] out = text.split("\\n");
							
					try {
							
						Controlador.getInstance().accion(
							new Contexto(
								Events.BUSCAR_DEPARTAMENTO, 
								Integer.parseInt(out[0])
							)
						);
							
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

	@Override
	public void update(Contexto contexto) {
		Departamento departamento = (Departamento) contexto.getDato();
		
		texts[0].setText("" + departamento.getId());
		
		texts[1].setText("" + departamento.getNombre());
		
		texts[2].setText("" + departamento.getFondos());
		
		for (int i = 0; i < labels.length; ++i) {
			
			if (!labels[i].equalsIgnoreCase("Id"))
				
				texts[i].setEnabled(true);
		
		}
	}
	
	public void clearData() {
		for (int i = 0; i < labels.length; ++i)
			
			texts[i].setText("");
	}
}