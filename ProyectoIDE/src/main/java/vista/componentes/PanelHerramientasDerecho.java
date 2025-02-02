package vista.componentes;

import javax.swing.*;
import java.awt.*;

public class PanelHerramientasDerecho extends JPanel {
    public PanelHerramientasDerecho() {
        configurarApariencia();
        agregarComponentes();
    }

    private void configurarApariencia() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(255, 245, 240));
        setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, new Color(200, 100, 100)));
    }

    private void agregarComponentes() {
        add(crearSeccion("⚙️ 道具 (Herramientas)", new String[]{"検索", "置換", "デバッグ"}));
        add(Box.createVerticalStrut(20));
        add(crearSeccion("ℹ️ 情報 (Información)", new String[]{"変数", "依存関係", "統計"}));
    }

    private JPanel crearSeccion(String titulo, String[] items) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 240, 230));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("MS Gothic", Font.BOLD, 12));
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(5));

        for (String item : items) {
            JButton btn = new JButton("・ " + item);
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
            panel.add(btn);
            panel.add(Box.createVerticalStrut(3));
        }

        return panel;
    }
}