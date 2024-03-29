
package presentacion.cliente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import main.Main;
import negocio.cliente.TCliente;
import negocio.cliente.TClienteNoVIP;
import negocio.cliente.TClienteVIP;
import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUI;
import presentacion.controlador.Controlador;

public class GUIListarClientes extends JFrame implements GUI {
	
	private static final long serialVersionUID = -4682162064787298680L;
	
	private JTable table;
	private String[] columnNames = { "#", "Id", "Nombre", "Telefono", "Direccion", "DNI", "Cod. Tarjeta Oro", "Lim. Credito" }; 
	private DefaultTableModel tableModel;
	
	public GUIListarClientes(){
		super();
		initGUI();
	}
	
	private void initGUI() {
		this.setTitle("Fruteria Mr. Pressman");
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
			JToolBar northPanel = new JToolBar();
			northPanel.add(new JLabel("Listar Clientes"));
			
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
				
			JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			
			JButton button = new JButton("Listar");
			button.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					Controlador.getInstance().accion(new Contexto(Events.LISTAR_CLIENTES, null));
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

	public void clearData() {
		tableModel.setRowCount(0);
		table.setModel(tableModel);
	}

	@Override
	public void update(Contexto contexto) {
		
		@SuppressWarnings("unchecked")
		ArrayList<TCliente> res = (ArrayList<TCliente>) contexto.getDato();
		
		 tableModel.setRowCount(0);
		 
		 for (int i = 0; i < res.size(); i++) {
			if (res.get(i) instanceof TClienteVIP){
				tableModel.insertRow(i, new Object[] 
						{ i, res.get(i).getId(), res.get(i).getNombre(), res.get(i).getTelefono(), res.get(i).getDireccion(), res.get(i).getDNI(), ((TClienteVIP) res.get(i)).getCodTarjetaOro() });
			}
			else if (res.get(i) instanceof TClienteNoVIP){
				tableModel.insertRow(i, new Object[] 
						{ i, res.get(i).getId(), res.get(i).getNombre(), res.get(i).getTelefono(), res.get(i).getDireccion(), res.get(i).getDNI(), "",((TClienteNoVIP) res.get(i)).getLimiteCredito() });
			}
		}
		
		 table.setModel(tableModel);
		
	}
	
}