
package presentacion.trabajador;

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
import negocio.trabajador.TiempoCompleto;
import negocio.trabajador.TiempoParcial;
import negocio.trabajador.Trabajador;
import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUI;
import presentacion.controlador.Controlador;

public class GUIActualizarTrabajador extends JFrame implements GUI {

	private static final long serialVersionUID = -4681929282291150241L;
	
	private String[] labels = { "Id", "Nombre", "Correo","Id departamento" };
	private JTextField[] texts = new JTextField[labels.length];
	private JButton button;
	private boolean buscarOrActualizar; // true = buscar; false = actualizar
	private JLabel conditionalLabel, conditionalLabel2;
	private JTextField conditionalTextField, conditionalTextField2;

	public GUIActualizarTrabajador(){
		super();
		initGUI();
	}
	
	private void initGUI() {
		this.setTitle("Fruteria Mr. Pressman");
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
			JToolBar northPanel = new JToolBar();
			northPanel.add(new JLabel("Actualizar Trabajador"));
			
			JPanel centerPanel = new JPanel(new GridLayout(labels.length + 2, 2));
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
				
				conditionalLabel2 = new JLabel();
				conditionalLabel2.setVisible(false);
				conditionalTextField2 = new JTextField();
				conditionalTextField2.setVisible(false);
				centerPanel.add(conditionalLabel2);
				centerPanel.add(conditionalTextField2);

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
						if (create && (!buscarOrActualizar || (!conditionalTextField.getText().equalsIgnoreCase("") && !conditionalTextField2.getText().equalsIgnoreCase("")))) {
							String[] out = text.split("\\n");
							try {
								if (!buscarOrActualizar)
									Controlador.getInstance().accion(new Contexto(Events.BUSCAR_ACTUALIZAR_TRABAJADOR, Integer.parseInt(out[0])));
								else {
									Departamento departamento = new Departamento();
									departamento.setId(Integer.parseInt(out[3]));
									if (conditionalLabel.getText().equalsIgnoreCase("Sueldo/Hora") && conditionalLabel2.getText().equalsIgnoreCase("Hora/Jornada"))
										Controlador.getInstance().accion(new Contexto(Events.ACTUALIZAR_TRABAJADOR, 
												new TiempoParcial(
														Integer.parseInt(out[0]), 
														out[1],
														out[2],
														departamento,
														true,
														Integer.parseInt(conditionalTextField.getText()), 
														Integer.parseInt(conditionalTextField2.getText())
														)));
									else if (conditionalLabel.getText().equalsIgnoreCase("Sueldo") && conditionalLabel2.getText().equalsIgnoreCase("Antiguedad"))
										Controlador.getInstance().accion(new Contexto(Events.ACTUALIZAR_TRABAJADOR, 
												new TiempoCompleto(
														Integer.parseInt(out[0]), 
														out[1],
														out[2],
														departamento,
														true,
														Integer.parseInt(conditionalTextField.getText()), 
														Integer.parseInt(conditionalTextField2.getText())
														)));
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
		Trabajador trabajador = (Trabajador) contexto.getDato();
		
		texts[0].setText("" + trabajador.getId());
		texts[1].setText(trabajador.getNombre());
		texts[2].setText(trabajador.getCorreo());
		texts[3].setText("" + trabajador.getDepartamento().getId());
		if (trabajador instanceof TiempoParcial){
			conditionalLabel.setText("Sueldo/Hora");
			conditionalTextField.setText("" + ((TiempoParcial) trabajador).getSueldoHora());
			conditionalLabel2.setText("Hora/Jornada");
			conditionalTextField2.setText("" + ((TiempoParcial) trabajador).getHorasJornada());
		}
		else if (trabajador instanceof TiempoCompleto){
			conditionalLabel.setText("Sueldo");
			conditionalTextField.setText("" + ((TiempoCompleto) trabajador).getSueldo());
			conditionalLabel2.setText("Antiguedad");
			conditionalTextField2.setText("" + ((TiempoCompleto) trabajador).getAntiguedad());
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
			conditionalLabel2.setVisible(false);
			conditionalTextField2.setVisible(false);
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
			conditionalLabel2.setVisible(true);
			conditionalTextField2.setVisible(true);
		}
	}

	public void clearData() {
		for (int i = 0; i < labels.length; ++i)
			texts[i].setText("");
		conditionalLabel.setText("");
		conditionalLabel2.setText("");
	}
}