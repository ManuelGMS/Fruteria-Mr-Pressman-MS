
package presentacion.cliente;

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
import negocio.cliente.TCliente;
import negocio.cliente.TClienteNoVIP;
import negocio.cliente.TClienteVIP;
import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUI;
import presentacion.controlador.Controlador;

public class GUIActualizarCliente extends JFrame implements GUI {

	private static final long serialVersionUID = -4681929282291150241L;
	
	private String[] labels = { "Id", "Nombre", "Telefono", "Direccion", "DNI" };
	private JTextField[] texts = new JTextField[labels.length];
	private JButton button;
	private boolean buscarOrActualizar; // true = buscar; false = actualizar
	private JLabel conditionalLabel;
	private JTextField conditionalTextField;

	public GUIActualizarCliente(){
		super();
		initGUI();
	}
	
	private void initGUI() {
		this.setTitle("Fruteria Mr. Pressman");
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
			JToolBar northPanel = new JToolBar();
			northPanel.add(new JLabel("Actualizar Cliente"));
			
			JPanel centerPanel = new JPanel(new GridLayout(labels.length + 1, 2));
				JLabel label;
				for (int i = 0; i < labels.length; ++i){
					
						label = new JLabel(labels[i] + ": ");
						texts[i] = new JTextField();
					
					centerPanel.add(label);
					centerPanel.add(texts[i]);
				}
				conditionalLabel = new JLabel();
				conditionalLabel.setVisible(false);
				conditionalTextField = new JTextField();
				conditionalTextField.setVisible(false);
				centerPanel.add(conditionalLabel);
				centerPanel.add(conditionalTextField);

			centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
			
			JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			
				button = new JButton("Buscar");
				buscarOrActualizar = false;
				texts[0].setEnabled(true);
				texts[0].setEditable(true);
				for (int i = 1; i < labels.length; ++i) {
					texts[i].setEnabled(false);
					texts[i].setEditable(false);
				}
				button.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						String text = "";
						boolean create = true;
						for (int i = 0; i < labels.length && create; ++i){
							if (texts[i].isEnabled() && texts[i].getText().equalsIgnoreCase("")) create = false;
							text += texts[i].getText() + "\n"; 
						}
						if (create && (!buscarOrActualizar || !conditionalTextField.getText().equalsIgnoreCase(""))) {
							String[] out = text.split("\\n");
							try {
								if (!buscarOrActualizar)
									Controlador.getInstance().accion(new Contexto(Events.BUSCAR_ACTUALIZAR_CLIENTE, Integer.parseInt(out[0])));
								else {
									if (conditionalLabel.getText().equalsIgnoreCase("Cod. Tarjeta"))
									Controlador.getInstance().accion(new Contexto(Events.ACTUALIZAR_CLIENTE, 
											new TClienteVIP(Integer.parseInt(out[0]), out[1], Integer.parseInt(out[2]), out[3], true, out[4], Integer.parseInt(conditionalTextField.getText()))));
									else if (conditionalLabel.getText().equalsIgnoreCase("Lim. Credito"))
										Controlador.getInstance().accion(new Contexto(Events.ACTUALIZAR_CLIENTE, 
												new TClienteNoVIP(Integer.parseInt(out[0]), out[1], Integer.parseInt(out[2]), out[3], true, out[4], Integer.parseInt(conditionalTextField.getText()))));
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
		Dimension windowDim = new Dimension((int) (Main.WINDOW_DIM.getWidth()), (int) (Main.WINDOW_DIM.getHeight() * (labels.length + 3)));
		this.setBounds(
				(int) (dim.getWidth() / 2 - windowDim.getWidth() / 2),
				(int) (dim.getHeight() / 2 - windowDim.getHeight() / 2),
				(int) (windowDim.getWidth()),
				(int) (windowDim.getHeight())
				);
	}

	@Override
	public void update(Contexto contexto) {
		TCliente cliente = (TCliente) contexto.getDato();
		
		texts[0].setText("" + cliente.getId());
		texts[1].setText(cliente.getNombre());
		texts[2].setText("" + cliente.getTelefono());
		texts[3].setText(cliente.getDireccion());
		texts[4].setText(cliente.getDNI());
		if (cliente instanceof TClienteVIP){
			conditionalLabel.setText("Cod. Tarjeta");
			conditionalTextField.setText("" + ((TClienteVIP) cliente).getCodTarjetaOro());
		}
		else if (cliente instanceof TClienteNoVIP){
			conditionalLabel.setText("Lim. Credito");
			conditionalTextField.setText("" + ((TClienteNoVIP) cliente).getLimiteCredito());
		}
	}
	
	public void buscarOrActualizar(boolean busOrAct){
		buscarOrActualizar = busOrAct;
		if (!buscarOrActualizar){
			button.setText("Buscar");
			buscarOrActualizar = false;
			texts[0].setEnabled(true);
			texts[0].setEditable(true);
			for (int i = 1; i < labels.length; ++i){
				texts[i].setEnabled(false);
				texts[i].setEditable(false);
			}
			conditionalLabel.setVisible(false);
			conditionalTextField.setVisible(false);
		}
		else {
			button.setText("Actualizar");
			buscarOrActualizar = true;
			texts[0].setEnabled(false);
			texts[0].setEditable(false);
			for (int i = 1; i < labels.length; ++i){
				texts[i].setEnabled(true);
				texts[i].setEditable(true);
			}
			conditionalLabel.setVisible(true);
			conditionalTextField.setVisible(true);
		}
	}

	public void clearData() {
		for (int i = 0; i < labels.length; ++i)
			texts[i].setText("");
		conditionalLabel.setText("");
	}
}