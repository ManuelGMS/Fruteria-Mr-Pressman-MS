package presentacion.venta;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import negocio.venta.TVenta;
import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUI;
import presentacion.controlador.Controlador;

public class GUIListarVentas extends JFrame implements GUI {
	
	private static final long serialVersionUID = -4997390746465934262L;

	private JTable table;
	
	private String[] columnNames = { "#", "Id", "Fecha", "Precio Total",
			"IdCliente" };

	private DefaultTableModel tableModel;
	
	private ArrayList<Integer> ids;

	public GUIListarVentas(){
		super();
		initGUI();
	}
	
	private void initGUI() {
		
		this.setTitle("Fruteria Mr. Pressman");
		
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
			
		JToolBar northPanel = new JToolBar();
			
		northPanel.add(new JLabel("Listar Ventas"));
			
		ids = new ArrayList<>();
			
		table = new JTable();
		
		table.addMouseListener(new MouseAdapter() {
		
			public void mouseClicked(MouseEvent e) {
					
				if (e.getClickCount() == 2){
						
					JTable target = (JTable)e.getSource();
						
					int row = target.getSelectedRow();
						
					int column = target.getSelectedColumn();
						
					if (column == columnNames.length - 1)
							
						Controlador.getInstance().accion(new Contexto(Events.BUSCAR_VENTA, ids.get(row)));
					
				}
				
			}
			
		});
				
		tableModel = new DefaultTableModel(){
			
			private static final long serialVersionUID = 2560854130017229568L;

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
				
				Controlador.getInstance().accion(new Contexto(Events.LISTAR_VENTAS, null));
					
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
	public void update (Contexto contexto) {
		@SuppressWarnings("unchecked")
		ArrayList<TVenta> res = (ArrayList<TVenta>) contexto.getDato();
		 
		tableModel.setRowCount(0);
		 
		ids.clear();
		 
		for (int i = 0; i < res.size(); i++) {
			 
			tableModel.insertRow(i, new Object[] 
					 
					{ i, res.get(i).getId(), res.get(i).getFecha().toString(), 
					res.get(i).getPrecioTotal(), res.get(i).getClienteID() });
		
			ids.add(res.get(i).getId());
		 
		 }
	
		 table.setModel(tableModel);
	
	}

	public void clearData() {
	
		tableModel.setRowCount(0);
		
		table.setModel(tableModel);
	
	}
	
}