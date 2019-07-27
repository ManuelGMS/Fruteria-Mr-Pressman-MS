package presentacion.trabajador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import main.Main;
import negocio.trabajador.TiempoCompleto;
import negocio.trabajador.TiempoParcial;
import negocio.trabajador.Trabajador;
import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUI;
import presentacion.controlador.Controlador;

public class GUIListarTrabajador extends JFrame implements GUI {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private String[] columnNames = { "#", "Id", "Nombre", "Correo", "Departamento", "Sueldo", "Antiguedad", "Sueldo Horas", "Horas Jornada"};
	private DefaultTableModel tableModel;
	
	public GUIListarTrabajador() {
		super();
		initGUI();
	}

	private void initGUI() {
		this.setTitle("Fruteria Mr. Pressman");
		
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		
			JToolBar northPanel = new JToolBar();
			northPanel.add(new JLabel("Listar Trabajadores"));
					
			table = new JTable();	
			tableModel = new DefaultTableModel() {
				private static final long serialVersionUID = -9119961641231539692L;
	
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tableModel.setColumnCount(0);
				
			for (int i = 0; i < columnNames.length; ++i)	
				tableModel.addColumn(columnNames[i]);
			table.setModel(tableModel);
						
			JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			
				JButton button = new JButton("Listar");	
				button.addActionListener(new ActionListener(){
		
					@Override
					public void actionPerformed(ActionEvent e) {
						Controlador.getInstance().accion(new Contexto(Events.LISTAR_TRABAJADOR, null));
						toFront();
					}		
				});			
			southPanel.add(button);
			
		mainPanel.add(northPanel, BorderLayout.PAGE_START);
		mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		mainPanel.setOpaque(true);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension windowDim = new Dimension((int) (Main.WINDOW_DIM.getWidth()), (int) (Main.WINDOW_DIM.getHeight() * (5)));
		this.setBounds(
			(int) (dim.getWidth() / 2 - windowDim.getWidth() / 2),
			(int) (dim.getHeight() / 2 - windowDim.getHeight() / 2),
			(int) (windowDim.getWidth()),
			(int) (windowDim.getHeight())
		);
	}
	
	@Override
	public void update(Contexto contexto) {
		@SuppressWarnings("unchecked")
		List<Trabajador> res = (List<Trabajador>) contexto.getDato();
		
		tableModel.setRowCount(0);
		for (int i = 0; i < res.size(); i++)
			if (res.get(i) instanceof TiempoParcial) {
				tableModel.insertRow(
						i, 
						new Object[] { 
							i, 
							res.get(i).getId(), 
							res.get(i).getNombre(), 
							res.get(i).getCorreo(),
							res.get(i).getDepartamento().getId(),
							"",
							"",
							((TiempoParcial)res.get(i)).getSueldoHora(),
							((TiempoParcial)res.get(i)).getHorasJornada()
						}
					);
			}
			else {
				tableModel.insertRow(
						i, 
						new Object[] { 
							i, 
							res.get(i).getId(), 
							res.get(i).getNombre(), 
							res.get(i).getCorreo(),
							res.get(i).getDepartamento().getId(),
							((TiempoCompleto) res.get(i)).getSueldo(),
							((TiempoCompleto) res.get(i)).getAntiguedad(),
							"",
							""
						}
					);
			}
		table.setModel(tableModel);
	}
	
	public void clearData() {
		tableModel.setRowCount(0);
		table.setModel(tableModel);
	}
}