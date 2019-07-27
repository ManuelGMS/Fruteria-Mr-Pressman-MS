package presentacion.venta;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import main.Main;
import negocio.venta.LineaVenta;
import negocio.venta.TVenta;
import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUI;
import presentacion.controlador.Controlador;

public class GUICerrarVenta extends JFrame implements GUI {
	
	private static final long serialVersionUID = 5332987732036602545L;

	private JTable table;
	
	private DefaultTableModel tableModel;

	public GUICerrarVenta(){
		super();
		initGUI();
	}
	
	private void initGUI() {
		
		this.setTitle("Fruteria Mr. Pressman");
		
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
			
		JToolBar northPanel = new JToolBar();
			
		northPanel.add(new JLabel("Cerrar Venta"));
				
		JPanel centerPanel = new JPanel(new BorderLayout());
				
		table = new JTable();
				
		table.setVisible(false);
				
		tableModel = new DefaultTableModel(){
			
			private static final long serialVersionUID = 5124180431632699975L;

			@Override
			public boolean isCellEditable(int row, int column) {
				       
				return false;
			
			}
					
		};
						
		tableModel.setColumnCount(0);
				
		tableModel.addColumn("#");
		
		tableModel.addColumn("IdProducto");
		
		tableModel.addColumn("Unidades");
		
		table.setModel(tableModel);
		
		centerPanel.add(new JLabel("Linea Ventas: "), BorderLayout.NORTH);
			
		centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);
			
		centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
				
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
					
		JButton button = new JButton("Cerrar");
				
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
						
				int n = JOptionPane.showOptionDialog(new JFrame(),		
						"Estas seguro de que no quieres gastarte mas dinero por Pressman?", "Cerrar",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
						
				if (n == 0) {
							
					Controlador.getInstance().accion(new Contexto(Events.CERRAR_VENTA, GUIVenta.getInstance().getVenta()));
							
					toFront();
				}
					
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
		
		Dimension windowDim = new Dimension((int) (Main.WINDOW_DIM.getWidth()), (int) (Main.WINDOW_DIM.getHeight() * (5)));
		
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
		
		tableModel.setRowCount(0);
		
		int i = 0;
		
		for (LineaVenta v : venta.getLineaVentas().values()) {
			 
			tableModel.insertRow(i, new Object[] { i, v.getIdProducto(), v.getUnidades() });
			 
			++i;
		 
		}
		 
		table.setModel(tableModel);
		 
		table.setVisible(true);
		 
		toFront();
	
	}

	public void clearData() {
		
		tableModel.setRowCount(0);
	
	}
	
}