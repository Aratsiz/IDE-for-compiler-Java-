package vista.componentes;

import javax.swing.*;
import java.awt.*;

public class ConsolaZen extends JTextArea {
    public ConsolaZen() {
        configurarApariencia();
    }
    
    private void configurarApariencia() {
        setEditable(false);
        setFont(new Font("MS Gothic", Font.PLAIN, 12));
        setForeground(new Color(255, 200, 200));
        setBackground(new Color(50, 20, 20));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(150, 50, 50)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
    
    public void agregarSalida(String texto, Color color) {
        setForeground(color);
        append(texto + "\n");
        setCaretPosition(getDocument().getLength());
    }
}