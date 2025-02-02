package vista.componentes;

import javax.swing.*;
import java.awt.*;

public class BarraEstadoZen extends JPanel {
    private final JLabel lblLineaColumna = new JLabel(" Línea: 1, Columna: 1 ");
    private final JLabel lblModo = new JLabel(" Modo: 標準 ");
    
    public BarraEstadoZen() {
        configurarApariencia();
    }
    
    private void configurarApariencia() {
        setLayout(new BorderLayout());
        setBackground(new Color(100, 40, 40));
        setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(150, 50, 50)));
        
        // Componentes
        lblLineaColumna.setForeground(Color.WHITE);
        lblModo.setForeground(new Color(255, 180, 180));
        
        JPanel panelOeste = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 3));
        panelOeste.setOpaque(false);
        panelOeste.add(lblLineaColumna);
        
        JPanel panelEste = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 3));
        panelEste.setOpaque(false);
        panelEste.add(lblModo);
        
        add(panelOeste, BorderLayout.WEST);
        add(panelEste, BorderLayout.EAST);
    }
    
    public void actualizarPosicion(int linea, int columna) {
        lblLineaColumna.setText(String.format(" 行: %d, 列: %d ", linea, columna));
    }
}