package presentacion.cliente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import negocio.cliente.ClienteMes;
import negocio.producto.TProducto;
import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUI;
import presentacion.controlador.Controlador;

public class GUIProductoMasComprado extends JFrame implements GUI{
	
	private static final long serialVersionUID = 5938548500368793983L;
	
	private String[] labels = { "Id" };
	private JTextField[] texts = new JTextField[labels.length];
	private JTable table;
	private String[] columnNames = { "#", "Id", "Nombre" };
	@SuppressWarnings("rawtypes")
	private JComboBox jcb;
	private String[] months = { "Choose your month", "January", "February", "March", "April", "May", "June", 
			"July", "August", "September", "October", "November", "December" };
	private DefaultTableModel tableModel;
	
	public GUIProductoMasComprado(){
		super();
		initGUI();
	}
	
	private void initGUI() {
		this.setTitle("Fruteria Mr. Pressman");
		
		JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		mainPanel.setResizeWeight(0.5);
		
			JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
			
				JToolBar northPanel = new JToolBar();
				northPanel.add(new JLabel("Producto mas comprado"));
				
				jcb = new JComboBox<String>(months);
				jcb.setSelectedIndex(0);
				
				JPanel centerPanel = new JPanel(new GridLayout(labels.length + 1, 2));
					JLabel label = new JLabel("Month: ");
					centerPanel.add(label);
					centerPanel.add(jcb);
					
					for (int i = 0; i < labels.length; ++i){
						
							label = new JLabel(labels[i] + ": ");
							texts[i] = new JTextField();
						
						centerPanel.add(label);
						centerPanel.add(texts[i]);
					}
	
				centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
				
				JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
				
					JButton button = new JButton("Buscar");
					button.addActionListener(new ActionListener(){
	
						@Override
						public void actionPerformed(ActionEvent e) {
							String text = "";
							boolean create = jcb.getSelectedIndex() != 0;
							for (int i = 0; i < labels.length && create; ++i){
								if (texts[i].getText().equalsIgnoreCase("")) create = false;
								text += texts[i].getText() + "\n"; 
							}
							if (create) {
								String[] out = text.split("\\n");
								try {
								Controlador.getInstance().accion(new Contexto(Events.PRODUCTO_MAS_COMPRADO, 
										new ClienteMes(Integer.parseInt(out[0]), jcb.getSelectedIndex())));
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

	public void clearData() {
		jcb.setSelectedIndex(0);
		for (int i = 0; i < labels.length; ++i)
			texts[i].setText("");
		tableModel.setRowCount(0);
		table.setModel(tableModel);
	}

	@Override
	public void update(Contexto contexto) {
		@SuppressWarnings("unchecked")
		ArrayList<TProducto> res = (ArrayList<TProducto>) contexto.getDato();
		tableModel.setRowCount(0);
		
		for (int i = 0; i < res.size(); i++)
			tableModel.insertRow(
				i, 
				new Object[] { 
					i, 
					res.get(i).getId(), 
					res.get(i).getNombre()
				}
		);
		table.setModel(tableModel);
	}
}
