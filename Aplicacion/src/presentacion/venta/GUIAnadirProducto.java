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

public class GUIAnadirProducto extends JFrame implements GUI {
	
	private static final long serialVersionUID = -9219765534011333182L;

	private String[] labels = { "Id", "Unidades" };
	
	private JTextField[] texts = new JTextField[labels.length];

	public GUIAnadirProducto(){
		super();
		initGUI();
	}
	
	private void initGUI() {

		this.setTitle("Fruteria Mr. Pressman");
		
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		
		JToolBar northPanel = new JToolBar();
		
		northPanel.add(new JLabel("Anadir Producto"));
			
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
			
		JButton button = new JButton("Anadir");
		
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String text = "", textAux;
							
				boolean create = true;
							
				for (int i = 0; i < labels.length && create; ++i) {
								
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
									
						if(id >= 0 &&  unidades > 0 ) {
										
							GUIVenta guiVenta = GUIVenta.getInstance();
										
							TVenta tV = guiVenta.getVenta();
										
							if( tV != null) {
											
								HashMap<Integer, LineaVenta> lV2 = tV.getLineaVentas();
											
								boolean cond = lV2.containsKey(id);
											
								if(!cond) {
												
									LineaVenta lV = new LineaVenta(id, unidades, 0);
												
									lV2.put(id, lV);
											
								} else {
									
									LineaVenta lV = lV2.get(id);
									
									int unidadesVenta = lV.getUnidades();
									
									int unidadesNuevas = unidadesVenta + unidades;
									
									lV.setUnidades(unidadesNuevas);
									
									lV2.put(id, lV);
								
								}
											
								tV.setLineaVentas(lV2);
											
								guiVenta.setVenta(tV);
											
								guiVenta.update(new Contexto(Events.RES_AGREGAR_PRODUCTO_OK, tV));
							
							} else {
								
								guiVenta.update(new Contexto(Events.RES_AGREGAR_PRODUCTO_KO, -2));
							
							}
						
						} else {
							
							GUIVenta.getInstance().update(new Contexto(Events.RES_AGREGAR_PRODUCTO_KO, -1));
							
						}
							
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