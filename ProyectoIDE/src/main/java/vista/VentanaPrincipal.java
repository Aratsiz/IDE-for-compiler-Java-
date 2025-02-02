package vista;

import controlador.ControladorIDE;
import vista.componentes.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    // Componentes principales
    private final PestanasBiombo pestanas = new PestanasBiombo();
    private final ExploradorProyectos explorador = new ExploradorProyectos();
    private final PanelHerramientasDerecho panelDerecho = new PanelHerramientasDerecho();
    private final ConsolaZen consola = new ConsolaZen();
    private final BarraEstadoZen barraEstado = new BarraEstadoZen();
    private final BarraHerramientasSuperior barraSuperior = new BarraHerramientasSuperior();
    private ControladorIDE controlador;

    public VentanaPrincipal() {
        configurarVentana();
        inicializarComponentes();
        configurarControlador();
        aplicarTemaVisual();
    }

    private void configurarVentana() {
        setTitle("IDE Zen - 禅");
        setExtendedState(MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1280, 720));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        // Agregar una pestaña vacía al inicio
        pestanas.agregarPestana("Nuevo archivo", new RSyntaxTextArea());
    
        JScrollPane scrollExplorador = new JScrollPane(explorador);
        scrollExplorador.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(150, 50, 50)));
    
        JSplitPane splitCentral = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitCentral.setTopComponent(pestanas);
        splitCentral.setBottomComponent(consola);
        splitCentral.setDividerLocation(0.8);
        splitCentral.setDividerSize(3);
    
        JSplitPane splitPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPrincipal.setLeftComponent(scrollExplorador);
        splitPrincipal.setRightComponent(panelDerecho);
        splitPrincipal.setDividerLocation(0.15);
    
        JSplitPane splitGlobal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitGlobal.setLeftComponent(splitPrincipal);
        splitGlobal.setRightComponent(splitCentral);
        splitGlobal.setDividerLocation(0.2);
    
        add(barraSuperior, BorderLayout.NORTH);
        add(splitGlobal, BorderLayout.CENTER);
        add(barraEstado, BorderLayout.SOUTH);
    }
    

    private void configurarControlador() {
        controlador = new ControladorIDE(this);
        controlador.vincularExplorador(explorador);
        controlador.vincularPestanas(pestanas);
    }

    private void aplicarTemaVisual() {
        // Paleta de colores Sakura
        Color rojoSakura = new Color(255, 180, 180);
        Color beigeWashi = new Color(255, 250, 240);
        
        UIManager.put("Panel.background", beigeWashi);
        UIManager.put("SplitPane.background", new Color(255, 245, 240));
        pestanas.setBackground(beigeWashi);
        explorador.setBackground(new Color(255, 240, 230));
        consola.setBackground(new Color(50, 20, 20));
    }

    // Métodos de acceso para componentes clave
    public PestanasBiombo getPestanas() { return pestanas; }
    public RSyntaxTextArea getAreaCodigo() {
        RSyntaxTextArea editor = pestanas.getEditorActivo();
        if (editor == null) {
            System.err.println("⚠️ No hay un editor activo. Se devolverá un editor temporal.");
            return new RSyntaxTextArea(); // Devuelve un editor vacío en lugar de null
        }
        return editor;
    }
      
    public ConsolaZen getConsola() { return consola; }

    // Métodos para actualizar la vista
    public void actualizarPosicion(int linea, int columna) {
        barraEstado.actualizarPosicion(linea, columna);  // Método faltante
    }

    public void mostrarError(String mensajeError, int lineaError) {
        // consola.mostrarError(mensajeError, lineaError);  // Método faltante
    }

    public void actualizarConsola(String mensaje) {
        // consola.actualizarConsola(mensaje);  // Método faltante
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
