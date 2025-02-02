package vista.temas;

import javax.swing.UIManager;
import java.awt.Color;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;

public class TemaEstaciones {
    public enum Estacion {
        SAKURA,    // Primavera
        NATSU,     // Verano
        AKI,       // Otoño
        FUYU       // Invierno
    }

    public static void cambiarTema(Estacion estacion) {
        FlatAnimatedLafChange.showSnapshot();
        
        switch(estacion) {
                    case SAKURA -> aplicarTemaSakura();
                    // case NATSU -> aplicarTemaNatsu();
                    // ... Implementar otras estaciones
                    default -> throw new IllegalArgumentException("Unexpected value: " + estacion);
        }
        
        FlatLaf.updateUI();
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    private static void aplicarTemaSakura() {
        FlatLaf.registerCustomDefaultsSource("temas.sakura");
        UIManager.put("TextArea.background", new Color(255, 240, 245));
        UIManager.put("ScrollBar.thumb", new Color(255, 180, 180));
    }
}
