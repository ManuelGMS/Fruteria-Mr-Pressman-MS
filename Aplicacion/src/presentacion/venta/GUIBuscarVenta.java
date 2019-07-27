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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import main.Main;
import negocio.venta.LineaVenta;
import negocio.venta.TVenta;
import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUI;
import presentacion.controlador.Controlador;

public class GUIBuscarVenta extends JFrame implements GUI {
	
	private static final long serialVersionUID = 5528410396658182194L;
	
	private String[] labels = { "Id", "PrecioTotal", "Fecha", "ClienteId" };
	
	private JTextField[] texts = new JTextField[labels.length];
	
	private JTable table;
	
	private DefaultTableModel tableModel;

	public GUIBuscarVenta(){
		super();
		initGUI();
	}
	
	private void initGUI() {

		this.setTitle("Fruteria Mr. Pressman");
		
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		
		JToolBar northPanel = new JToolBar();
		
		northPanel.add(new JLabel("Buscar Venta"));
			
		JPanel centerPanel = new JPanel(new BorderLayout());
			
		JPanel centerLeftPanel = new JPanel(new GridLayout(labels.length, 2));
		
		JLabel label;
		
		for (int i = 0; i < labels.length; ++i) {
					
			label = new JLabel(labels[i] + ": ");
						
			texts[i] = new JTextField();
						
			if (!labels[i].equalsIgnoreCase("Id")) {
			
				texts[i].setEditable(false);
					
				texts[i].setEnabled(false);
						
			}
					
			centerLeftPanel.add(label);
					
			centerLeftPanel.add(texts[i]);
				
		}
				
		table = new JTable();
				
		table.setVisible(false);
				
		tableModel = new DefaultTableModel() {
			
			private static final long serialVersionUID = -6998574125227593444L;

			@Override
			public boolean isCellEditable(int row, int column) {
				       
				return false;
				    
			}
				
		};
				
		JPanel centerRightPanel = new JPanel(new BorderLayout());
			
		tableModel.setColumnCount(0);
		
		tableModel.addColumn("#");
		
		tableModel.addColumn("IdProducto");
		
		tableModel.addColumn("Precio");
		
		tableModel.addColumn("Unidades");
		
		table.setModel(tableModel);
				
		centerRightPanel.add(new JLabel("Linea Ventas: "), BorderLayout.NORTH);
				
		centerRightPanel.add(new JScrollPane(table), BorderLayout.CENTER);
				
		centerRightPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
					
		centerPanel.add(centerLeftPanel, BorderLayout.CENTER);
			
		centerPanel.add(centerRightPanel, BorderLayout.EAST);
			
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
							
						Controlador.getInstance().accion(new Contexto(Events.BUSCAR_VENTA, Integer.parseInt(out[0])));
							
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
	public void update (Contexto contexto){
		TVenta venta = (TVenta) contexto.getDato();
		
		texts[0].setText("" + venta.getId());
		
		texts[1].setText("" + venta.getPrecioTotal());
		
		texts[2].setText("" + venta.getFecha().toString());
		
		texts[3].setText("" + venta.getClienteID());	
		
		for (int i = 0; i < labels.length; ++i){
			
			if (!labels[i].equalsIgnoreCase("Id"))
				
				texts[i].setEnabled(true);
		}
		
		tableModel.setRowCount(0);
		
		int i = 0;
		
		for (LineaVenta v : venta.getLineaVentas().values()) {
		
			tableModel.insertRow(i, new Object[] { i, v.getIdProducto(), v.getPrecio(), v.getUnidades() });
			
			++i;
		 
		}
		
		table.setModel(tableModel);
		
		table.setVisible(true);
		
		toFront();
	
	}

	public void clearData() {
		
		for (int i = 0; i < labels.length; ++i)
			
			texts[i].setText("");
		
		table.setVisible(false);
	
	}
	
}