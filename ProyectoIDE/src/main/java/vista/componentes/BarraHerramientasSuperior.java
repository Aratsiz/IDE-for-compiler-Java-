package vista.componentes;

import javax.swing.*;
import java.awt.*;

public class BarraHerramientasSuperior extends JToolBar {
    public BarraHerramientasSuperior() {
        configurarApariencia();
        agregarBotones();
    }

    private void configurarApariencia() {
        setFloatable(false);
        setBackground(new Color(255, 250, 245));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(200, 100, 100)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void agregarBotones() {
        agregarBoton("新規", "nuevo.png");
        agregarBoton("開く", "abrir.png");
        agregarBoton("保存", "guardar.png");
        addSeparator();
        agregarBoton("実行", "ejecutar.png");
    }

    private void agregarBoton(String texto, String icono) {
        JButton btn = new JButton(texto, new ImageIcon("recursos/icons/" + icono));
        btn.setFont(new Font("MS Gothic", Font.PLAIN, 12));
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        add(btn);
    }
}