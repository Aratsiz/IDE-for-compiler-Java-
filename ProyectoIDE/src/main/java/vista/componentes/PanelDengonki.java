package vista.componentes;

import javax.swing.*;
import java.awt.*;

public class PanelDengonki extends JPanel {
    private final JToggleButton[] botonesHerramientas;

    public PanelDengonki() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(60, 30, 30));
        setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        String[] herramientas = {
            "🗡️",  // Katana - Editor
            "📜",  // Pergamino - Archivos
            "🎎",  // Muñecos - Debug
            "🔍"   // Lupa - Buscar
        };

        botonesHerramientas = new JToggleButton[herramientas.length];
        for (int i = 0; i < herramientas.length; i++) {
            botonesHerramientas[i] = crearBotonHerramienta(herramientas[i]);
            add(botonesHerramientas[i]);
            add(Box.createVerticalStrut(10));
        }
    }

    private JToggleButton crearBotonHerramienta(String icono) {
        JToggleButton btn = new JToggleButton(icono);
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        btn.setPreferredSize(new Dimension(50, 50));
        btn.setBackground(new Color(80, 40, 40));
        return btn;
    }
}