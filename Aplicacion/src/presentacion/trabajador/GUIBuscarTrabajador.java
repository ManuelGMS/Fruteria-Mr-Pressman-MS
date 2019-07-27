package presentacion.trabajador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import main.Main;
import negocio.curso.Curso;
import negocio.curso.Realiza;
import negocio.trabajador.TiempoCompleto;
import negocio.trabajador.TiempoParcial;
import negocio.trabajador.Trabajador;
import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUI;
import presentacion.controlador.Controlador;

public class GUIBuscarTrabajador extends JFrame implements GUI{

	private static final long serialVersionUID = 1L;
	private String[] labels = { "Id", "Nombre", "Correo",
			"Id departamento", "Sueldo/Hora",  "Hora/Jornada",
			"Sueldo", "Antiguedad" };
	private JTextField[] texts = new JTextField[labels.length];
	private JTable table;
	private String[] columnNames = { "#", "Id", "Nombre","Horas", "Plazas"  };
	private DefaultTableModel tableModel;
	
	public GUIBuscarTrabajador() {
		super();
		initGUI();
	}

	private void initGUI() {
		this.setTitle("Fruteria Mr. Pressman");
		
		JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		mainPanel.setResizeWeight(0.5);
		
			JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
					
				JToolBar northPanel = new JToolBar();
				northPanel.add(new JLabel("Buscar Trabajador"));
						
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
									clearData();
									Controlador.getInstance().accion(
										new Contexto(
											Events.BUSCAR_TRABAJADOR, 
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
				 		
			leftPanel.add(northPanel, BorderLayout.PAGE_START);		
			leftPanel.add(centerPanel, BorderLayout.CENTER);
			leftPanel.add(southPanel, BorderLayout.PAGE_END);
		
			table = new JTable();
			
			tableModel = new DefaultTableModel(){
				
				private static final long serialVersionUID = -179371584696260945L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
				
			};
			tableModel.setColumnCount(0);
			for (int i = 0; i < columnNames.length; ++i)
				tableModel.addColumn(columnNames[i]);
			table.setModel(tableModel);
			
		mainPanel.add(leftPanel);
		mainPanel.add(new JScrollPane(table));
			
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
		Trabajador trabajador = (Trabajador) contexto.getDato();		
		texts[0].setText("" + trabajador.getId());
		texts[1].setText("" + trabajador.getNombre());
		texts[2].setText("" + trabajador.getCorreo());
		texts[3].setText("" + trabajador.getDepartamento().getId());
		if (trabajador instanceof TiempoParcial) {
			texts[4].setText("" + ((TiempoParcial) trabajador).getSueldoHora());
			texts[5].setText("" + ((TiempoParcial) trabajador).getHorasJornada());
		}
		else if (trabajador instanceof TiempoCompleto){
			texts[6].setText("" + ((TiempoCompleto) trabajador).getSueldo());
			texts[7].setText("" + ((TiempoCompleto) trabajador).getAntiguedad());
		}
		for (int i = 0; i < labels.length; ++i){
			if (!labels[i].equalsIgnoreCase("Id"))
				texts[i].setEnabled(true);
		}
		
		List<Realiza> l = trabajador.getRealiza();
		tableModel.setRowCount(0);
		
		Curso c;
		int cont = 0;
		for (int i = 0; i < l.size(); i++){
			c = l.get(i).getCurso();
			boolean activo = l.get(i).getActivo();
			if(activo) {
				tableModel.insertRow(
						cont,
					new Object[] { 
						i,
						c.getId(),
						c.getNombre(),
						c.getHoras(),
						c.getPlazas()
					}
				);
				++cont;
			}
		}
		table.setModel(tableModel);
	}

	public void clearData() {
		for (int i = 0; i < labels.length; ++i)			
			texts[i].setText("");
		tableModel.setRowCount(0);
		table.setModel(tableModel);
	}
}