package presentacion.departamento;

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

public class GUIDepartamentoImp extends GUIDepartamento {
	
	private static final long serialVersionUID = 1L;
	
	protected String[] module = {"Alta", "Buscar", "Listar", "Actualizar", "Baja" , "Calcular Nomina por Departamento"};
	
	protected int numModule;
	
	protected JComboBox<?> comboBox;
	
	private GUIAltaDepartamento gUIAltaDepartamento;
	
	private GUIBajaDepartamento gUIBajaDepartamento;
	
	private GUIActualizarDepartamento gUIActualizarDepartamento;
	
	private GUIBuscarDepartamento gUIBuscarDepartamento;
	
	private GUIListarDepartamento gUIListarDepartamento;
	
	private GUICalcularNominaPorDepartamento gUICalcularNominaPorDepartamento;

	public GUIDepartamentoImp() {
		super();
		gUIAltaDepartamento = new GUIAltaDepartamento();
		gUIBuscarDepartamento = new GUIBuscarDepartamento();
		gUIListarDepartamento = new GUIListarDepartamento();
		gUIActualizarDepartamento = new GUIActualizarDepartamento();
		gUIBajaDepartamento = new GUIBajaDepartamento();
		gUICalcularNominaPorDepartamento = new GUICalcularNominaPorDepartamento();
		initGUI();
	}
	
	private void initGUI() {
		
		this.setTitle("Fruteria Mr. Pressman");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
			
		JToolBar northPanel = new JToolBar();
			
		comboBox = new JComboBox<String>(GUIMainImp.module);
		
		comboBox.setSelectedIndex(3);
			
		comboBox.addActionListener(new ActionListener() {
				
			@SuppressWarnings("unchecked")	
			@Override
			public void actionPerformed(ActionEvent e){
					
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
					
				switch ((String)cb.getSelectedItem()) {
					case "Ventas": 
						Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_VENTA, null)); 
					break;
					case "Clientes": 
						Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_CLIENTE, null)); 
					break;
					case "Productos": 
						Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_PRODUCTO, null)); 
					break;
					case "Cursos":
						Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_CURSO, null)); 
					break;
					case "Trabajadores": 
						Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_TRABAJADOR, null)); 
					break;
				}
					
				if (GUIMainImp.module[3] != (String)cb.getSelectedItem()) 
					
					dispose();
				
			}
			
		});
			
		northPanel.add(new JLabel("Gestion de Departamentos"));
			
		northPanel.add(comboBox);
			
		JPanel centerPanel = new JPanel(new GridLayout(module.length, 1));
			
		for (int i = 0; i < module.length; ++i) {
				
			final int j = i;
				
			JButton button = new JButton(module[j]);
				
			button.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
						
					switch (module[j]) {
						case "Alta": 
							gUIAltaDepartamento.clearData();
							gUIAltaDepartamento.setVisible(true); 
						break;
						case "Buscar": 
							gUIBuscarDepartamento.clearData(); 
							gUIBuscarDepartamento.setVisible(true); 
						break;
						case "Listar": 
							gUIListarDepartamento.clearData(); 
							gUIListarDepartamento.setVisible(true); 
						break;
						case "Actualizar": 
							gUIActualizarDepartamento.clearData(); 
							gUIActualizarDepartamento.setVisible(true); 
						break;
						case "Baja": 
							gUIBajaDepartamento.clearData(); 
							gUIBajaDepartamento.setVisible(true); 
							break;
						case "Calcular Nomina por Departamento": 
							gUICalcularNominaPorDepartamento.clearData(); 
							gUICalcularNominaPorDepartamento.setVisible(true); 
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
		
		switch(contexto.getEvent()) {
			case Events.RES_ALTA_DEPARTAMENTO_OK:
				gM.showMessage("Se ha dado de alta correctamente al departamento con id: "
				+ (int) contexto.getDato(), "ALTA DEPARTAMENTO", false);
			break;
			case Events.RES_ALTA_DEPARTAMENTO_KO:
			
				switch((int)contexto.getDato()){
					case -1:
						gM.showMessage("El departamento es nulo","ALTA DEPARTAMENTO", true);
						break;
					case -2:
						gM.showMessage("Error sintáctico","ALTA DEPARTAMENTO", true);
					break;
					case -3:
						gM.showMessage("El departamento ya está activo","ALTA DEPARTAMENTO", true);
					break;
					case -100:
						gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde", "ALTA DEPARTAMENTO", true);
					break;
				}
			
			break;
			case Events.RES_ACTUALIZAR_DEPARTAMENTO_OK:
				gM.showMessage("Se ha actualizado correctamente el departamento con id: " + (int) contexto.getDato(),"ACTUALIZAR DEPARTAMENTO", false);
			break;
			case Events.RES_ACTUALIZAR_DEPARTAMENTO_KO:
			
				switch((int)contexto.getDato()) {
					case -1:
						gM.showMessage("El departamento es nulo","ACTUALIZAR DEPARTAMENTO", true);
					break;
					case -2:
						gM.showMessage("Error sintáctico","ACTUALIZAR DEPARTAMENTO", true);
					break;
					case -3:
						gM.showMessage("No existe el departamento a actualizar","ACTUALIZAR DEPARTAMENTO", true);
					break;
					case -4:
						gM.showMessage("El departamento no está activo","ACTUALIZAR DEPARTAMENTO", true);
					break;
					case -5:
						gM.showMessage("Se ha querido actualizar el nombre a otro ya existente","ACTUALIZAR DEPARTAMENTO", true);
					break;
					case -100:
						gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde","ACTUALIZAR DEPARTAMENTO", true);
					break;
				}
			
			break;
			case Events.RES_BUSCAR_DEPARTAMENTO_OK:
				this.gUIBuscarDepartamento.update(contexto);
			break;
			case Events.RES_BUSCAR_DEPARTAMENTO_KO:
				gM.showMessage("No se encontro el departamento con id: "+ contexto.getDato() , "BUSCAR DEPARTAMENTO", true);
			break;
			case Events.RES_LISTAR_DEPARTAMENTO_OK:
				this.gUIListarDepartamento.update(contexto);
			break;
			case Events.RES_LISTAR_DEPARTAMENTO_KO:
				gM.showMessage( "No se pudieron listar los departamentos", "LISTAR DEPARTAMENTO",true);	
			break;
			case Events.RES_BAJA_DEPARTAMENTO_OK:
				gM.showMessage("Se dio de baja correctamente el departamento con id: " + contexto.getDato(), "BAJA DEPARTAMENTO", false);
			break;
			case Events.RES_BAJA_DEPARTAMENTO_KO:
			
				switch((int)contexto.getDato()){
					case -1:
						gM.showMessage("Error sintáctico","BAJA DEPARTAMENTO", true);
					break;
					case -2:
						gM.showMessage("El departamento a dar de baja no existe","BAJA DEPARTAMENTO", true);
					break;
					case -3:
						gM.showMessage("El departamento a borrar ya ha sido borrado","BAJA DEPARTAMENTO", true);
					break;
					case -4:
						gM.showMessage("El departamento a dar de baja tiene algun trabajador asociado", "BAJA DEPARTAMENTO", true);
					break;
					case -100:
						gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde", "ALTA DEPARTAMENTO", true);
					break;
				}
			
			break;
			case Events.RES_CALCULAR_NOMINAS_TRABAJADOR_OK:
				gM.showMessage("La suma de las nominas es: "
						+ (int)contexto.getDato(), "CALCULAR NOMINAS", false);
				break;
				
			case Events.RES_CALCULAR_NOMINAS_TRABAJADOR_KO:
				switch((int)contexto.getDato()){
				case -1:
					gM.showMessage("Error sintáctico"
					, "CALCULAR NOMINAS", true);
					break;
				case -2:
					gM.showMessage("El departamento no existe"
							, "CALCULAR NOMINAS", true);
					break;
				case -100:
					gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde"
							, "CALCULAR NOMINAS", true);
					break;
				}
				break;
			case Events.CREAR_GUI_DEPARTAMENTO:
				comboBox.setSelectedIndex(3);
			break;
		}
	}
}