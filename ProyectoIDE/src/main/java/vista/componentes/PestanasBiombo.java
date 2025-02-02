package vista.componentes;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import javax.swing.*;
import java.awt.*;

public class PestanasBiombo extends JTabbedPane {
    private static final Color COLOR_ACTIVO = new Color(255, 230, 230);
    
    public PestanasBiombo() {
        configurarApariencia();
    }
    
    private void configurarApariencia() {
        setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
        setBackground(new Color(255, 250, 245));
        setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(200, 100, 100)));
    }
    
    public void agregarPestana(String titulo, RSyntaxTextArea editor) {
        JPanel panel = new JPanel(new BorderLayout()) {{
            setBackground(COLOR_ACTIVO);
            add(new JScrollPane(editor));
        }};
        
        JLabel lblTitulo = new JLabel(titulo) {{
            setFont(new Font("MS Gothic", Font.BOLD, 12));
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 30));
        }};
        
        addTab(null, panel);
        setTabComponentAt(getTabCount()-1, lblTitulo);
    }
    
    public RSyntaxTextArea getEditorActivo() {
        Component componenteSeleccionado = getSelectedComponent();
        
        if (componenteSeleccionado == null) {
            return null; // No hay ninguna pestaña seleccionada
        }
    
        if (componenteSeleccionado instanceof JPanel) {
            JPanel panel = (JPanel) componenteSeleccionado;
            if (panel.getComponentCount() > 0 && panel.getComponent(0) instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) panel.getComponent(0);
                if (scrollPane.getViewport().getView() instanceof RSyntaxTextArea) {
                    return (RSyntaxTextArea) scrollPane.getViewport().getView();
                }
            }
        }
        
        return null; // Si no es un panel con un JScrollPane conteniendo un RSyntaxTextArea, devolver null
    }    
}