package presentacion.venta;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import main.Main;
import negocio.venta.TVenta;
import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUIMainImp;
import presentacion.GUIMensaje;
import presentacion.controlador.Controlador;

public class GUIVentaImp extends GUIVenta {
	
	private static final long serialVersionUID = -2412667319856915005L;

	private GUIAbrirVenta gUIAbrirVenta;
	
	private GUIAnadirProducto gUIAnadirProducto;
	
	private GUIBuscarVenta gUIBuscarVenta;
	
	private GUICerrarVenta gUICerrarVenta;
	
	private GUIDevolucion gUIDevolucion;
	
	private GUIListarVentas gUIListarVentas;
	
	private GUIModificarProducto gUIModificarProducto;
	
	protected String[] module = { 
		"Abrir Venta", "Buscar", "Listar", "Cerrar Venta",
		"Anadir Producto", "Modificar Producto", "Devolucion"
	};
	
	protected JComboBox<?> comboBox;
	
	protected JButton[] button = new JButton[module.length];

	public GUIVentaImp(){
		super();
		gUIAbrirVenta = new GUIAbrirVenta();
		gUIBuscarVenta = new GUIBuscarVenta();
		gUIListarVentas = new GUIListarVentas();
		gUICerrarVenta = new GUICerrarVenta();
		gUIAnadirProducto = new GUIAnadirProducto();
		gUIModificarProducto = new GUIModificarProducto();
		gUIDevolucion = new GUIDevolucion();
		initGUI();
	}
	
	public void initGUI() {

		this.setTitle("Fruteria Mr. Pressman");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JToolBar northPanel = new JToolBar();
		
		comboBox = new JComboBox<String>(GUIMainImp.module);
		
		comboBox.setSelectedIndex(1);
		
		comboBox.addActionListener(new ActionListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e){
				
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
				
				switch ((String)cb.getSelectedItem()) {
					case "Productos": 
						Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_PRODUCTO, null)); 
					break;
					case "Clientes": 
						Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_CLIENTE, null)); 
					break;
					case "Departamentos": 
						Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_DEPARTAMENTO, null)); 
					break;
					case "Cursos":
						Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_CURSO, null)); 
					break;
					case "Trabajadores": 
						Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_TRABAJADOR, null)); 
					break;
				}
				
				if (GUIMainImp.module[1] != (String)cb.getSelectedItem()) dispose();
			
			}
			
		});
		
		northPanel.add(new JLabel("Gestion de Ventas"));
		
		northPanel.add(comboBox);
		
		JPanel centerPanel = new JPanel(new GridLayout(module.length, 1));
			
		for (int i = 0; i < module.length; ++i) {
				
			final int j = i;
			
			button[i] = new JButton(module[j]);
			
			button[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
						
					switch (module[j]) {
						
						case "Abrir Venta": 
							gUIAbrirVenta.clearData(); gUIAbrirVenta.setVisible(true); 
						break;
						case "Buscar": 
							gUIBuscarVenta.clearData(); gUIBuscarVenta.setVisible(true); 
						break;
						case "Listar": 
							gUIListarVentas.clearData(); gUIListarVentas.setVisible(true); 
						break;
						case "Cerrar Venta": 
							gUICerrarVenta.clearData(); gUICerrarVenta.setVisible(true);
							GUIVenta.getInstance().update(new Contexto(Events.CERRAR_VENTA_UPDATE_LINEA_VENTA, getVenta()));
						break;
						case "Anadir Producto": 
							gUIAnadirProducto.clearData(); gUIAnadirProducto.setVisible(true); 
						break;
						case "Modificar Producto": 
							gUIModificarProducto.clearData(); gUIModificarProducto.setVisible(true); 
						break;
						case "Devolucion": 
							gUIDevolucion.clearData(); gUIDevolucion.setVisible(true); 
						break;
						
					}
					
				}
					
			});
				
			centerPanel.add(button[i]);
			
		}
		
		setEnaDis(false, "Cerrar Venta\nAnadir Producto\nModificar Producto");
		
		mainPanel.add(northPanel, BorderLayout.NORTH);
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		mainPanel.setOpaque(true);
		
		this.setContentPane(mainPanel);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		Dimension windowDim = new Dimension(
			(int) (Main.WINDOW_DIM.getWidth()), 
			(int) (Main.WINDOW_DIM.getHeight() * (module.length + 1))
		);
		
		this.setBounds(
			(int) (dim.getWidth() / 2 - windowDim.getWidth() / 2),
			(int) (dim.getHeight() / 2 - windowDim.getHeight() / 2),
			(int) (windowDim.getWidth()),
			(int) (windowDim.getHeight())
		);
	
		
		this.setVisible(true);
		
	}

	public void setEnaDis(boolean enaDis, String text) {

		String[] texts = text.split("\\n");
		
		for (int i = 0; i < module.length; ++i)
			
			for (int j = 0; j < texts.length; ++j)
				
				if (module[i].equalsIgnoreCase(texts[j]))
					
					button[i].setEnabled(enaDis);

	}

	@Override
	public TVenta getVenta() {
		return this.tV;
	}

	@Override
	public void setVenta(TVenta tV) {
		this.tV = tV;
	}

	@Override
	public void update(Contexto contexto) {
		GUIMensaje gM = new GUIMensaje();
		
		switch(contexto.getEvent()){
		case Events.RES_ABRIR_VENTA_OK:
			this.tV = (TVenta) contexto.getDato();
			setEnaDis(false, "Abrir Venta");
			setEnaDis(true, "Cerrar Venta\nAnadir Producto\nModificar Producto");
			gM.showMessage("Se ha abierto correctamente la venta"
			, "ABRIR VENTA", false);
			break;
		case Events.RES_ABRIR_VENTA_KO:
			gM.showMessage("No se pudo abrir la venta", "ABRIR VENTA", true);
			break;
		case Events.RES_AGREGAR_PRODUCTO_OK:
			tV = (TVenta) contexto.getDato();
			gM.showMessage("Se ha agregado el producto correctamente a la venta", "AGREGAR PRODUCTO", false);
			break;
		case Events.RES_AGREGAR_PRODUCTO_KO:
			switch((int)contexto.getDato()){
				case -1:
					gM.showMessage("Error sintáctico"
					, "AGREGAR PRODUCTO", true);
					break;
				case -2:
					gM.showMessage("La venta no está abierta"
					, "AGREGAR PRODUCTO", true);
					break;
			}
			break;
		case Events.RES_MODIFICAR_PRODUCTO_OK:
			tV = (TVenta) contexto.getDato();
			gM.showMessage("Se modifico correctamente el producto a la venta", "MODIFICAR PRODUCTO", false);
			break;
		case Events.RES_MODIFICAR_PRODUCTO_KO:
			switch((int)contexto.getDato()){
			case -1:
				gM.showMessage("La venta en la que se quiere modificar un producto es nula"
					, "MODIFICAR_PRODUCTO", true);
				break;
			case -2:
				gM.showMessage("El producto a modificar no existe en la venta"
						, "MODIFICAR_PRODUCTO", true);
				break;
			case -3:
				gM.showMessage("No se admiten unidades negativas"
						, "MODIFICAR_PRODUCTO", true);
				break;
			}
			
			break;
		case Events.RES_CERRAR_VENTA_OK:
			setEnaDis(true, "Abrir Venta");
			setEnaDis(false, "Cerrar Venta\nAnadir Producto\nModificar Producto");
			gM.showMessage("Se cerro correctamente la venta con id: "
			+ (int) contexto.getDato()	, "CERRAR VENTA", false);
			this.gUICerrarVenta.clearData();
			break;
			
		case Events.RES_CERRAR_VENTA_KO:
			setEnaDis(true, "Abrir Venta");
			setEnaDis(false, "Cerrar Venta\nAnadir Producto\nModificar Producto");
			switch((int)contexto.getDato()){
			case -1:
				gM.showMessage("Error sintáctico"
					, "CERRAR VENTA", true);
				break;
			case -2:
				gM.showMessage("La venta a cerrar no es válida"
						, "CERRAR VENTA", true);
				break;
			case -3:
				gM.showMessage("La venta a cerrar no estaba abierta"
						, "CERRAR VENTA", true);
				break;
			case -4:
				gM.showMessage("No se ha encontrado al cliente "
						, "CERRAR VENTA", true);
				break;
			case -5:
				gM.showMessage("El cliente no está activo"
						, "CERRAR VENTA", true);
				break;
			case -6:
				gM.showMessage("La venta no posee una línea de ventas"
						, "CERRAR VENTA", true);
				break;
			case -7:
				gM.showMessage("La línea de ventas quedó vacía"
						, "CERRAR VENTA", true);
				break;
			case -100:
				gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde"
						, "CERRAR VENTA", true);
				break;
			}
			
			break;
		case Events.RES_BUSCAR_VENTA_OK:
			this.gUIBuscarVenta.update(contexto);
			break;
		case Events.RES_BUSCAR_VENTA_KO:
			gM.showMessage("No se encontro la venta con id: "
			+(int)contexto.getDato(), "BUSCAR VENTA", true);
			break;
		case Events.RES_LISTAR_VENTAS_OK:
			this.gUIListarVentas.update(contexto);
			break;
		case Events.RES_LISTAR_VENTAS_KO:
			gM.showMessage( "No se pudieron listar las ventas", "LISTAR VENTAS",
					true);	
			break;
		case Events.RES_DEVOLUCION_VENTA_OK:
			gM.showMessage("Se realizo correctamente la devolucion de la venta con id: "
					+ (int) contexto.getDato()	, "DEVOLUCION VENTA", false);
			break;
		
		case Events.RES_DEVOLUCION_VENTA_KO:
			switch((int)contexto.getDato()){
			case -1:
				gM.showMessage("Error sintáctico"
				, "DEVOLUCION VENTA", true);
				break;
			case -8:
				gM.showMessage("La venta no existe"
				, "DEVOLUCION VENTA", true);
				break;
			case -9:
				gM.showMessage("El producto no existe o no está activo"
				, "DEVOLUCION VENTA", true);
				break;
			case -10:
				gM.showMessage("No existe la línea de venta"
				, "DEVOLUCION VENTA", true);
				break;
			case -11:
				gM.showMessage("Las unidades a devolver superan las de la venta"
				, "DEVOLUCION VENTA", true);
				break;
			case -100:
				gM.showMessage("Errar en la gestión de la Base de Datos"
				, "DEVOLUCION VENTA", true);
				break;
			}
			
			break;
		case Events.CREAR_GUI_VENTA:
			comboBox.setSelectedIndex(1);
			break;
		case Events.CERRAR_VENTA_UPDATE_LINEA_VENTA:
			this.gUICerrarVenta.update(contexto);
			break;
		}
		
	}
	
}