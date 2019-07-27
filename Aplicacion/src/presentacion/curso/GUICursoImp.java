/**
 * 
 */
package presentacion.curso;

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

public class GUICursoImp extends GUICurso {

	private static final long serialVersionUID = 1L;
	protected String[] module = {"Alta", "Buscar", "Listar", "Actualizar", "Baja", "Matricular", "Desmatricular"};
	protected int numModule;
	@SuppressWarnings("rawtypes")
	protected JComboBox comboBox;
	private GUIAltaCurso gUIAltaCurso;
	private GUIBajaCurso gUIBajaCurso;
	private GUIActualizarCurso gUIActualizarCurso;
	private GUIBuscarCurso gUIBuscarCurso;
	private GUIListarCurso gUIListarCurso;
	private GUIMatricularTrabajador gUIMatricularTrabajador;
	private GUIDesmatricularTrabajador gUIDesmatricularTrabajador;

	public GUICursoImp() {
		super();
		gUIAltaCurso = new GUIAltaCurso();
		gUIBuscarCurso = new GUIBuscarCurso();
		gUIListarCurso = new GUIListarCurso();
		gUIActualizarCurso = new GUIActualizarCurso();
		gUIBajaCurso = new GUIBajaCurso();
		gUIMatricularTrabajador = new GUIMatricularTrabajador();
		gUIDesmatricularTrabajador = new GUIDesmatricularTrabajador();
		initGUI();
	}

	private void initGUI() {
		this.setTitle("Fruteria Mr. Pressman");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
				
			JToolBar northPanel = new JToolBar();
			
				comboBox = new JComboBox<String>(GUIMainImp.module);
				comboBox.setSelectedIndex(4);
				comboBox.addActionListener(new ActionListener() {
						
					@SuppressWarnings("unchecked")	
					@Override
					public void actionPerformed(ActionEvent e){
						JComboBox<String> cb = (JComboBox<String>)e.getSource();
						switch ((String)cb.getSelectedItem()) {
							case "Ventas": Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_VENTA, null)); break;
							case "Clientes": Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_CLIENTE, null)); break;
							case "Productos": Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_PRODUCTO, null)); break;
							case "Departamentos": Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_DEPARTAMENTO, null)); break;
							case "Trabajadores": Controlador.getInstance().accion(new Contexto(Events.CREAR_GUI_TRABAJADOR, null)); break;
						}
						if (GUIMainImp.module[4] != (String)cb.getSelectedItem()) 
							dispose();	
					}
				});
					
			northPanel.add(new JLabel("Gestion de Cursos"));
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
									gUIAltaCurso.clearData();
									gUIAltaCurso.setVisible(true); 
								break;
								case "Buscar": 
									gUIBuscarCurso.clearData(); 
									gUIBuscarCurso.setVisible(true); 
								break;
								case "Listar": 
									gUIListarCurso.clearData(); 
									gUIListarCurso.setVisible(true); 
								break;
								case "Actualizar": 
									gUIActualizarCurso.clearData(); 
									gUIActualizarCurso.setVisible(true); 
								break;
								case "Baja": 
									gUIBajaCurso.clearData(); 
									gUIBajaCurso.setVisible(true); 
								break;
								case "Matricular": 
									gUIMatricularTrabajador.clearData(); 
									gUIMatricularTrabajador.setVisible(true); 
								break;
								case "Desmatricular": 
									gUIDesmatricularTrabajador.clearData(); 
									gUIDesmatricularTrabajador.setVisible(true); 
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
			case Events.RES_ALTA_CURSO_OK:
				gM.showMessage("Se ha dado de alta correctamente al curso con id: "
				+ (int) contexto.getDato(), "ALTA CURSO", false);
			break;
			case Events.RES_ALTA_CURSO_KO:
			
				switch((int)contexto.getDato()){
					case -1:
						gM.showMessage("El curso es nulo","ALTA CURSO", true);
						break;
					case -2:
						gM.showMessage("Error sintactico","ALTA CURSO", true);
					break;
					case -3:
						gM.showMessage("El curso ya esta activo","ALTA CURSO", true);
					break;
					case -100:
						gM.showMessage("Error en la gestion de la Base de Datos", "ALTA CURSO", true);
					break;
				}
			
			break;
			case Events.RES_ACTUALIZAR_CURSO_OK:
				gM.showMessage("Se ha actualizado correctamente el curso con id: " + (int) contexto.getDato(),"ACTUALIZAR CURSO", false);
			break;
			case Events.RES_ACTUALIZAR_CURSO_KO:
			
				switch((int)contexto.getDato()) {
					case -1:
						gM.showMessage("El curso es nulo","ACTUALIZAR CURSO", true);
					break;
					case -2:
						gM.showMessage("Error sintactico","ACTUALIZAR CURSO", true);
					break;
					case -3:
						gM.showMessage("No existe el curso a actualizar","ACTUALIZAR CURSO", true);
					break;
					case -4:
						gM.showMessage("El curso no esta activo","ACTUALIZAR CURSO", true);
					break;
					case -5:
						gM.showMessage("Se ha querido actualizar el nombre a otro ya existente","ACTUALIZAR CURSO", true);
					break;
					case -100:
						gM.showMessage("Error en la gestion de la Base de Datos","ACTUALIZAR CURSO", true);
					break;
				}
			
			break;
			case Events.RES_BUSCAR_CURSO_OK:
				this.gUIBuscarCurso.update(contexto);
			break;
			case Events.RES_BUSCAR_CURSO_KO:
				gM.showMessage("No se encontro el curso con id: "+ contexto.getDato() , "BUSCAR CURSO", true);
			break;
			case Events.RES_LISTAR_CURSO_OK:
				this.gUIListarCurso.update(contexto);
			break;
			case Events.RES_LISTAR_CURSO_KO:
				gM.showMessage( "No se pudieron listar los cursos", "LISTAR CURSO",true);	
			break;
			case Events.RES_BAJA_CURSO_OK:
				gM.showMessage("Se dio de baja correctamente el curso con id: " + contexto.getDato(), "BAJA CURSO", false);
			break;
			case Events.RES_BAJA_CURSO_KO:
			
				switch((int)contexto.getDato()){
					case -1:
						gM.showMessage("Error sintactico","BAJA CURSO", true);
					break;
					case -2:
						gM.showMessage("El curso a dar de baja no existe","BAJA CURSO", true);
					break;
					case -3:
						gM.showMessage("El curso a borrar ya ha sido borrado","BAJA CURSO", true);
					break;
					case -4:
						gM.showMessage("El curso tiene trabajadores matriculados","BAJA CURSO", true);
					break;
					case -100:
						gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde", "ALTA CURSO", true);
					break;
				}
			
			break;
			case Events.RES_MATRICULAR_TRABAJADOR_OK:
				gM.showMessage("Se matriculo correctamente el trabajador", "MATRICULAR TRABAJADOR", false);
				break;
			case Events.RES_MATRICULAR_TRABAJADOR_KO:
				switch((int)contexto.getDato()){
					case -1:
						gM.showMessage("Error en los datos","MATRICULAR TRABAJADOR", true);
					break;
					case -2:
						gM.showMessage("Error sintactico","MATRICULAR TRABAJADOR", true);
					break;
					case -3:
						gM.showMessage("El curso no existe","MATRICULAR TRABAJADOR", true);
					break;
					case -4:
						gM.showMessage("El trabajador no existe","MATRICULAR TRABAJADOR", true);
					break;
					case -5:
						gM.showMessage("El curso no esta activo","MATRICULAR TRABAJADOR", true);
					break;
					case -6:
						gM.showMessage("El trabajador no esta activo","MATRICULAR TRABAJADOR", true);
					break;
					case -7:
						gM.showMessage("No hay plazas libres en el curso","MATRICULAR TRABAJADOR", true);
					break;
					case -8:
						gM.showMessage("El trabajador ya estaba matriculado","MATRICULAR TRABAJADOR", true);
					break;
					case -100:
						gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde", "MATRICULAR TRABAJADOR", true);
					break;
				}
				break;
				case Events.RES_DESMATRICULAR_TRABAJADOR_OK:
					gM.showMessage("Se desmatriculo correctamente el trabajador", "DESMATRICULAR TRABAJADOR", false);
					break;
					 
				case Events.RES_DESMATRICULAR_TRABAJADOR_KO:
					switch((int)contexto.getDato()){
					case -1:
						gM.showMessage("Error en los datos","DESMATRICULAR TRABAJADOR", true);
					break;
					case -2:
						gM.showMessage("Error sintactico","DESMATRICULAR TRABAJADOR", true);
					break;
					case -3:
						gM.showMessage("El curso no existe","DESMATRICULAR TRABAJADOR", true);
					break;
					case -4:
						gM.showMessage("El trabajador no existe","DESMATRICULAR TRABAJADOR", true);
					break;
					case -5:
						gM.showMessage("El curso no esta activo","DESMATRICULAR TRABAJADOR", true);
					break;
					case -6:
						gM.showMessage("El trabajador no esta activo","DESMATRICULAR TRABAJADOR", true);
					break;
					case -7:
						gM.showMessage("No se ha encontrado la matricula","DESMATRICULAR TRABAJADOR", true);
					break;
					case -8:
						gM.showMessage("El trabajador ya estaba matriculado","DESMATRICULAR TRABAJADOR", true);
					break;
					case -100:
						gM.showMessage("Hemos tenido problemas, por favor inténtelo más tarde", "DESMATRICULAR TRABAJADOR", true);
					break;
					}
					break;
			case Events.CREAR_GUI_CURSO:
				comboBox.setSelectedIndex(4);
				
			break;
		}
	}
}