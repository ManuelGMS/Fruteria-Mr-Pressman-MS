
package presentacion.cliente;

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
import presentacion.Contexto;
import presentacion.Events;
import presentacion.GUIMainImp;
import presentacion.GUIMensaje;
import presentacion.controlador.Controlador;

public class GUIClienteImp extends GUICliente {
	
	private static final long serialVersionUID = -8059655462780060375L;
	
	private GUIAltaClienteVIP gUIAltaClienteVIP;
	private GUIAltaClienteNoVIP gUIAltaClienteNoVIP;
	private GUIActualizarCliente gUIActualizarCliente;
	private GUIBuscarCliente gUIBuscarCliente;
	private GUIBajaCliente gUIBajaCliente;
	private GUIListarClientes gUIListarClientes;
	private GUIProductoMasComprado gUIProductoMasComprado;
	private GUIProductosCompradosPorClienteVIP gUIProductosCompradosPorClienteVIP;
	
	private String[] module = {
		"Alta Cliente VIP", "Alta Cliente No VIP", "Buscar", 
		"Listar", "Actualizar", "Baja", "Producto mas comprado",
		"Productos comprados por Cliente VIP"
	};
	
	private JComboBox<?> comboBox;

	public GUIClienteImp(){
		super();
		gUIAltaClienteVIP = new GUIAltaClienteVIP();
		gUIAltaClienteNoVIP = new GUIAltaClienteNoVIP();
		gUIBuscarCliente = new GUIBuscarCliente();
		gUIListarClientes = new GUIListarClientes();
		gUIActualizarCliente = new GUIActualizarCliente();
		gUIBajaCliente = new GUIBajaCliente();
		gUIProductoMasComprado = new GUIProductoMasComprado();
		gUIProductosCompradosPorClienteVIP = new GUIProductosCompradosPorClienteVIP();
		initGUI();
	}
	
	private void initGUI() {
		
		this.setTitle("Fruteria Mr. Pressman");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
			
		JToolBar northPanel = new JToolBar();
			
			comboBox = new JComboBox<String>(GUIMainImp.module);
			
			comboBox.setSelectedIndex(2);
			
			comboBox.addActionListener(new ActionListener() {
				@SuppressWarnings("unchecked")
				@Override
				public void actionPerformed(ActionEvent e){
					JComboBox<String> cb = (JComboBox<String>)e.getSource();
					switch ((String)cb.getSelectedItem()) {
					case "Productos": Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_PRODUCTO, null)); break;
					case "Ventas": Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_VENTA, null)); break;
					case "Departamentos":Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_DEPARTAMENTO, null)); break;
					case "Cursos": Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_CURSO, null)); break;
					case "Trabajadores": Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_TRABAJADOR, null)); break;
					}
					if (GUIMainImp.module[2] != (String)cb.getSelectedItem()) dispose();
				}
			});
			northPanel.add(new JLabel("Gestion de Clientes"));
			northPanel.add(comboBox);
		
			JPanel centerPanel = new JPanel(new GridLayout(module.length, 1));
			for (int i = 0; i < module.length; ++i){
				final int j = i;
				JButton button = new JButton(module[j]);
				button.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						switch (module[j]) {
						case "Alta Cliente VIP": gUIAltaClienteVIP.clearData(); gUIAltaClienteVIP.setVisible(true); break;
						case "Alta Cliente No VIP": gUIAltaClienteNoVIP.clearData(); gUIAltaClienteNoVIP.setVisible(true); break;
						case "Buscar": gUIBuscarCliente.clearData(); gUIBuscarCliente.setVisible(true); break;
						case "Listar": gUIListarClientes.clearData(); gUIListarClientes.setVisible(true); break;
						case "Actualizar": gUIActualizarCliente.clearData(); gUIActualizarCliente.setVisible(true); gUIActualizarCliente.buscarOrActualizar(false); break;
						case "Baja": gUIBajaCliente.clearData(); gUIBajaCliente.setVisible(true); break;
						case "Producto mas comprado": gUIProductoMasComprado.clearData(); gUIProductoMasComprado.setVisible(true); break;
						case "Productos comprados por Cliente VIP": gUIProductosCompradosPorClienteVIP.clearData(); gUIProductosCompradosPorClienteVIP.setVisible(true); break;
						}
					}
					
				});
				centerPanel.add(button);
			}
			mainPanel.add(northPanel, BorderLayout.NORTH);
			mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.setOpaque(true);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		Dimension windowDim = new Dimension(
			(int) Main.WINDOW_DIM.getWidth(), 
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

	@Override
	public void update(Contexto contexto) {
		
		GUIMensaje gM = new GUIMensaje();
		switch(contexto.getEvent()){
		case Events.RES_ALTA_CLIENTE_NO_VIP_OK:
			gM.showMessage("Se ha dado de alta correctamente al cliente no vip con id: "
			+ (int)contexto.getDato(), "ALTA CLIENTE NO VIP", false);
			break;
		case Events.RES_ALTA_CLIENTE_NO_VIP_KO:
			switch((int)contexto.getDato()){
			case -1:
				gM.showMessage("Error sintáctico"
				, "ALTA CLIENTE NO VIP", true);
				break;
			case -2:
				gM.showMessage("Cliente no válido"
						, "ALTA CLIENTE NO VIP", true);
				break;
			case -3:
				gM.showMessage("El cliente ya estaba dado de alta"
						, "ALTA CLIENTE NO VIP", true);
				break;
			case -4:
				gM.showMessage("Para reactivar cliente deber ser un cliente vip"
						, "ALTA CLIENTE NO VIP", true);
				break;
			case -100:
				gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde"
						, "ALTA CLIENTE NO VIP", true);
				break;
			}
			
			break;
		case Events.RES_ALTA_CLIENTE_VIP_OK:
			gM.showMessage("Se ha dado de alta correctamente al cliente vip con id: "
			+ (int)contexto.getDato(), "ALTA CLIENTE VIP", false);
			break;
		case Events.RES_ALTA_CLIENTE_VIP_KO:
			switch((int)contexto.getDato()){
			case -1:
				gM.showMessage("Error sintáctico"
				, "ALTA CLIENTE VIP", true);
				break;
			case -2:
				gM.showMessage("Cliente no válido"
						, "ALTA CLIENTE VIP", true);
				break;
			case -3:
				gM.showMessage("El cliente ya estaba dado de alta"
						, "ALTA CLIENTE VIP", true);
				break;
			case -4:
				gM.showMessage("Para reactivar cliente deber ser un cliente no vip"
						, "ALTA CLIENTE VIP", true);
				break;
			case -100:
				gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde"
						, "ALTA CLIENTE VIP", true);
				break;
			}
			break;
		case Events.RES_ACTUALIZAR_CLIENTE_OK:
			this.gUIActualizarCliente.buscarOrActualizar(false);
			this.gUIActualizarCliente.clearData();
			gM.showMessage("Se ha actualizado correctamente el cliente con id: "
			+ (int)contexto.getDato()	, "ACTUALIZAR CLIENTE", false);
			break;
		
		case Events.RES_ACTUALIZAR_CLIENTE_KO:
			this.gUIActualizarCliente.buscarOrActualizar(true);
			switch((int)contexto.getDato()){
			case -1:
				gM.showMessage("Error sintáctico"
				, "ACTUALIZAR CLIENTE", true);
				break;
			case -2:
				gM.showMessage("Cliente no válido"
						, "ACTUALIZAR CLIENTE", true);
				break;
			case -3:
				gM.showMessage("No se ha encontrado el cliente"
						, "ACTUALIZAR CLIENTE", true);
				break;
			case -4:
				gM.showMessage("El cliente que se encontró no estaba activo"
						, "ACTUALIZAR CLIENTE", true);
				break;
			case -5:
				gM.showMessage("El cliente solicitado no estaba activo"
						, "ACTUALIZAR CLIENTE", true);
				break;
			case -6:
				gM.showMessage("Se ha querido actualizar el DNI a uno ya existente"
						, "ACTUALIZAR CLIENTE", true);
				break;
			case -100:
				gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde"
						, "ACTUALIZAR CLIENTE", true);
				break;

			}
			break;
		case Events.RES_BUSCAR_ACTUALIZAR_CLIENTE_OK:
			
			this.gUIActualizarCliente.buscarOrActualizar(true);
			this.gUIActualizarCliente.update(new Contexto(Events.RES_BUSCAR_ACTUALIZAR_CLIENTE_OK,contexto.getDato()));
			
			break;
		case Events.RES_BUSCAR_ACTUALIZAR_CLIENTE_KO:
			this.gUIActualizarCliente.buscarOrActualizar(false);
			gM.showMessage("No se encontro el cliente con id: "
					+ contexto.getDato(), "BUSCAR CLIENTE", true);
			break;
		case Events.RES_BUSCAR_CLIENTE_OK:
			
			this.gUIBuscarCliente.update(new Contexto(Events.RES_BUSCAR_CLIENTE_OK,contexto.getDato()));
		
			break;
		case Events.RES_BUSCAR_CLIENTE_KO:
			gM.showMessage("No se encontro el cliente con id: "
			+ contexto.getDato(), "BUSCAR CLIENTE", true);
			break;
		case Events.RES_LISTAR_CLIENTES_OK:
			
			this.gUIListarClientes.update(new Contexto(Events.RES_LISTAR_CLIENTES_OK,contexto.getDato()));
			
			break;
		case Events.RES_LISTAR_CLIENTES_KO:
			gM.showMessage("No se pudieron listar los clientes", "LISTAR CLIENTES",
					true);	
			break;
		case Events.RES_BAJA_CLIENTE_OK:
			gM.showMessage("Se dio de baja correctamente al cliente con id: "
					+ (int)contexto.getDato(), "BAJA CLIENTE", false);
			break;
			
		case Events.RES_BAJA_CLIENTE_KO:
			switch((int)contexto.getDato()){
			case -1:
				gM.showMessage("Error sintáctico"
				, "ACTUALIZAR CLIENTE", true);
				break;
			case -2:
				gM.showMessage("El cliente no existe"
						, "ACTUALIZAR CLIENTE", true);
				break;
			case -100:
				gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde"
						, "ACTUALIZAR CLIENTE", true);
				break;
			}
			break;
		case Events.RES_PRODUCTO_MAS_COMPRADO_OK:
			this.gUIProductoMasComprado.update(contexto);
			break;
		case Events.RES_PRODUCTO_MAS_COMPRADO_KO:
			gM.showMessage("No se pudo encontrar producto más comprado", "PRODUCTO MAS COMPRADO",
					true);	
			break;
		case Events.RES_PRODUCTOS_MAS_COMPRADOS_VIP_OK:
			this.gUIProductosCompradosPorClienteVIP.update(contexto);
			break;
		case Events.RES_PRODUCTOS_MAS_COMPRADOS_VIP_KO:
			gM.showMessage("No se pudo realizar la operación correctamente, revise los datos", "PRODUCTOS MAS COMPRADOS VIP",
					true);	
			break;

		case Events.CREAR_GUI_CLIENTE:
			comboBox.setSelectedIndex(2);
			break;
		
		}

		
	}
	
}