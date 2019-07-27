package presentacion.venta;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import main.Main;
import negocio.venta.LineaVenta;
import negocio.venta.TVenta;
import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUI;

public class GUIModificarProducto extends JFrame implements GUI {
	
	private static final long serialVersionUID = 7872117712827312681L;

	private String[] labels = { "Id", "Unidades" };
	
	private JTextField[] texts = new JTextField[labels.length];

	public GUIModificarProducto(){
		super();
		initGUI();
	}
	
	private void initGUI() {
		
		this.setTitle("Fruteria Mr. Pressman");
		
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
			
		JToolBar northPanel = new JToolBar();
			
		northPanel.add(new JLabel("Modificar Producto"));
			
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
			
		JButton button = new JButton("Modificar");
		
		button.addActionListener(new ActionListener() {

			@Override		
			public void actionPerformed(ActionEvent e) {
						
				String text = "", textAux;
						
				boolean create = true;
						
				for (int i = 0; i < labels.length && create; ++i){
							
					textAux = texts[i].getText();
							
					if (textAux.equalsIgnoreCase("")) 
						
						create = false;
							
					text += textAux + "\n";
						
				}
						
				if (create) {
							
					String[] out = text.split("\\n");
							
					try {
										
						int id = Integer.parseInt(out[0]);
								
						int unidades = Integer.parseInt(out[1]);
								
						GUIVenta guiVenta = GUIVenta.getInstance();
								
						if(id > 0) {
									
							TVenta tV = guiVenta.getVenta();
									
							if( tV != null) {
										
								HashMap<Integer, LineaVenta> lV = tV.getLineaVentas();
										
								boolean cond = lV.containsKey(id);
										
								if(cond) {
											
									if(unidades > 0) {
												
										LineaVenta lVEncontrada = lV.get(id);
												
										lVEncontrada.setUnidades(unidades);
												
										lV.put(id, lVEncontrada);
												
										tV.setLineaVentas(lV);
												
										guiVenta.setVenta(tV);
												
										guiVenta.update(new Contexto(Events.RES_MODIFICAR_PRODUCTO_OK, tV));
											
									} else if(unidades == 0) {
												
										lV.remove(id);
												
										tV.setLineaVentas(lV);
												
										guiVenta.setVenta(tV);
												
										guiVenta.update(new Contexto(Events.RES_MODIFICAR_PRODUCTO_OK, tV));
											
									
									} else
												
										guiVenta.update(new Contexto(Events.RES_MODIFICAR_PRODUCTO_KO, -3));		
									
								} else
									
									guiVenta.update(new Contexto(Events.RES_MODIFICAR_PRODUCTO_KO, -2));
									
							} else
										
								guiVenta.update(new Contexto(Events.RES_MODIFICAR_PRODUCTO_KO, -1));
								
						} else
							
							guiVenta.update(new Contexto(Events.RES_MODIFICAR_PRODUCTO_KO, -3));
							
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