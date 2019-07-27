
package presentacion.trabajador;

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

public class GUITrabajadorImp extends GUITrabajador {
	
	private static final long serialVersionUID = -8059655462780060375L;
	
	private GUIAltaTrabajadorTiempoParcial guiAltaTrabajadorTiempoParcial;
	private GUIAltaTrabajadorTiempoCompleto guiAltaTrabajadorTiempoCompleto;
	private GUIActualizarTrabajador guiActualizarTrabajador;
	private GUIBuscarTrabajador guiBuscarTrabajador;
	private GUIBajaTrabajador guiBajaTrabajador;
	private GUIListarTrabajador guiListarTrabajador;

	
	
	private String[] module = {
		"Alta Trabajador Tiempo Parcial", "Alta Trabajador Tiempo Completo", "Buscar", 
		"Listar", "Actualizar", "Baja"
	};
	
	private JComboBox<?> comboBox;

	public GUITrabajadorImp(){
		super();
		guiAltaTrabajadorTiempoParcial = new GUIAltaTrabajadorTiempoParcial();
		guiAltaTrabajadorTiempoCompleto = new GUIAltaTrabajadorTiempoCompleto();
		guiBuscarTrabajador = new GUIBuscarTrabajador();
		guiListarTrabajador = new GUIListarTrabajador();
		guiActualizarTrabajador = new GUIActualizarTrabajador();
		guiBajaTrabajador = new GUIBajaTrabajador();
		
		initGUI();
	}
	
	private void initGUI() {
		
		this.setTitle("Fruteria Mr. Pressman");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
			
		JToolBar northPanel = new JToolBar();
			
			comboBox = new JComboBox<String>(GUIMainImp.module);
			
			comboBox.setSelectedIndex(5);
			
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
					case "Clientes": Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_CLIENTE, null)); break;
					}
					if (GUIMainImp.module[5] != (String)cb.getSelectedItem()) dispose();
				}
			});
			northPanel.add(new JLabel("Gestion de Trabajadores"));
			northPanel.add(comboBox);
		
			JPanel centerPanel = new JPanel(new GridLayout(module.length, 1));
			for (int i = 0; i < module.length; ++i){
				final int j = i;
				JButton button = new JButton(module[j]);
				button.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						switch (module[j]) {
						case "Alta Trabajador Tiempo Parcial": 
							guiAltaTrabajadorTiempoParcial.clearData(); 
							guiAltaTrabajadorTiempoParcial.setVisible(true);
						break;
						case "Alta Trabajador Tiempo Completo": 
							guiAltaTrabajadorTiempoCompleto.clearData(); 
							guiAltaTrabajadorTiempoCompleto.setVisible(true); break;
						case "Buscar": 
							guiBuscarTrabajador.clearData(); 
							guiBuscarTrabajador.setVisible(true); 
						break;
						case "Listar": 
							guiListarTrabajador.clearData(); 
							guiListarTrabajador.setVisible(true); 
						break;
						case "Actualizar": 
							guiActualizarTrabajador.clearData(); 
							guiActualizarTrabajador.setVisible(true); 
							guiActualizarTrabajador.buscarOrActualizar(false); 
						break;
						case "Baja": 
							guiBajaTrabajador.clearData(); 
							guiBajaTrabajador.setVisible(true); 
						break;
						
						
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
		case Events.RES_ALTA_TRABAJADOR_TIEMPO_PARCIAL_OK:
			gM.showMessage("Se ha dado de alta correctamente al trabajador a tiempo parcial con id: "
			+ (int)contexto.getDato(), "ALTA TRABAJADOR TIEMPO PARCIAL", false);
			break;
		case Events.RES_ALTA_TRABAJADOR_TIEMPO_PARCIAL_KO:
			switch((int)contexto.getDato()){
			case -1:
				gM.showMessage("Los datos no son correctos"
				, "ALTA TRABAJADOR TIEMPO PARCIAL", true);
				break;
			case -2:
				gM.showMessage("Error sintáctico"
						, "ALTA TRABAJADOR TIEMPO PARCIAL", true);
				break;
			case -3:
				gM.showMessage("El departamento no existe"
						, "ALTA TRABAJADOR TIEMPO PARCIAL", true);
				break;
			case -4:
				gM.showMessage("Ya existe un trabajador con el mismo correo"
						, "ALTA TRABAJADOR TIEMPO PARCIAL", true);
				break;
			case -5:
				gM.showMessage("No se puede reactivar el trabajador porque no es del mismo tipo"
						, "ALTA TRABAJADOR TIEMPO PARCIAL", true);
				break;
			case -100:
				gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde"
						, "ALTA TRABAJADOR TIEMPO PARCIAL", true);
				break;
			}
			
			break;
		case Events.RES_ALTA_TRABAJADOR_TIEMPO_COMPLETO_OK:
			gM.showMessage("Se ha dado de alta correctamente al trabajador a tiempo completo con id: "
			+ (int)contexto.getDato(), "ALTA TRABAJADOR TIEMPO COMPLETO", false);
			break;
		case Events.RES_ALTA_TRABAJADOR_TIEMPO_COMPLETO_KO:
			switch((int)contexto.getDato()){
			case -1:
				gM.showMessage("Los datos no son correctos"
				, "ALTA TRABAJADOR TIEMPO COMPLETO", true);
				break;
			case -2:
				gM.showMessage("Error sintáctico"
						, "ALTA TRABAJADOR TIEMPO COMPLETO", true);
				break;
			case -3:
				gM.showMessage("El departamento no existe"
						, "ALTA TRABAJADOR TIEMPO COMPLETO", true);
				break;
			case -4:
				gM.showMessage("Ya existe un trabajador con el mismo correo"
						, "ALTA TRABAJADOR TIEMPO COMPLETO", true);
				break;
			case -5:
				gM.showMessage("No se puede reactivar el trabajador porque no es del mismo tipo"
						, "ALTA TRABAJADOR TIEMPO COMPLETO", true);
				break;
			case -100:
				gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde"
						, "ALTA TRABAJADOR TIEMPO COMPLETO", true);
				break;
			}
			break;
		case Events.RES_ACTUALIZAR_TRABAJADOR_OK:
			this.guiActualizarTrabajador.buscarOrActualizar(false);
			this.guiActualizarTrabajador.clearData();
			gM.showMessage("Se ha actualizado correctamente el trabajador con id: "
			+ (int)contexto.getDato()	, "ACTUALIZAR TRABAJADOR", false);
			break;
		
		case Events.RES_ACTUALIZAR_TRABAJADOR_KO:
			this.guiActualizarTrabajador.buscarOrActualizar(true);
			switch((int)contexto.getDato()){
			case -1:
				gM.showMessage("Los datos no son correctos"
				, "ACTUALIZAR TRABAJADOR", true);
				break;
			case -2:
				gM.showMessage("Error sintáctico"
				, "ACTUALIZAR TRABAJADOR", true);
				break;
			case -3:
				gM.showMessage("Departamento no existe"
						, "ACTUALIZAR TRABAJADOR", true);
				break;
			case -4:
				gM.showMessage("No se ha encontrado el trabajador"
						, "ACTUALIZAR TRABAJADOR", true);
				break;
			case -5:
				gM.showMessage("El trabajador que se encontró no estaba activo"
						, "ACTUALIZAR TRABAJADOR", true);
				break;
			case -6:
				gM.showMessage("Ya existe un trabajador con ese correo"
						, "ACTUALIZAR TRABAJADOR", true);
				break;
			case -7:
				gM.showMessage("El trabajador que se quiere actualizar no es de ese tipo"
						, "ACTUALIZAR TRABAJADOR", true);
				break;
			case -100:
				gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde"
						, "ACTUALIZAR TRABAJADOR", true);
				break;

			}
			break;
		case Events.RES_BUSCAR_ACTUALIZAR_TRABAJADOR_OK:
			
			this.guiActualizarTrabajador.buscarOrActualizar(true);
			this.guiActualizarTrabajador.update(new Contexto(Events.RES_BUSCAR_ACTUALIZAR_TRABAJADOR_OK, contexto.getDato()));
			
			break;
		case Events.RES_BUSCAR_ACTUALIZAR_TRABAJADOR_KO:
			this.guiActualizarTrabajador.buscarOrActualizar(false);
			gM.showMessage("No se encontro el trabajador con id: "
					+ contexto.getDato(), "BUSCAR TRABAJADOR", true);
			break;
		case Events.RES_BUSCAR_TRABAJADOR_OK:
			
			this.guiBuscarTrabajador.update(new Contexto(Events.RES_BUSCAR_TRABAJADOR_OK,contexto.getDato()));
		
			break;
		case Events.RES_BUSCAR_TRABAJADOR_KO:
			gM.showMessage("No se encontro el trabajador con id: "
			+ contexto.getDato(), "BUSCAR TRABAJADOR", true);
			break;
		case Events.RES_LISTAR_TRABAJADOR_OK:
			
			this.guiListarTrabajador.update(new Contexto(Events.RES_LISTAR_TRABAJADOR_OK,contexto.getDato()));
			
			break;
		case Events.RES_LISTAR_TRABAJADOR_KO:
			gM.showMessage("No se pudieron listar los trabajadores", "LISTAR TRABAJADOR",
					true);	
			break;
		case Events.RES_BAJA_TRABAJADOR_OK:
			gM.showMessage("Se dio de baja correctamente al trabajador con id: "
					+ (int)contexto.getDato(), "BAJA TRABAJADOR", false);
			break;
			
		case Events.RES_BAJA_TRABAJADOR_KO:
			switch((int)contexto.getDato()){
			case -1:
				gM.showMessage("Error sintáctico"
				, "ACTUALIZAR TRABAJADOR", true);
				break;
			case -2:
				gM.showMessage("El trabajador no existe"
						, "ACTUALIZAR TRABAJADOR", true);
				break;
			case -3:
				gM.showMessage("El trabajador ya esta dado de baja"
						, "ACTUALIZAR TRABAJADOR", true);
				break;
			case -4:
				gM.showMessage("El trabajador esta matriculado en un curso"
						, "ACTUALIZAR TRABAJADOR", true);
				break;
			case -100:
				gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde"
						, "ACTUALIZAR TRABAJADOR", true);
				break;
			}
			break;
		
			case Events.CREAR_GUI_TRABAJADOR:
			comboBox.setSelectedIndex(5);
			break;
		
		}

		
	}
	
}