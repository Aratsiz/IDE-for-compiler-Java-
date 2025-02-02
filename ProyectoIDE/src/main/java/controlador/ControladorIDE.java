package controlador;

import modelo.CompiladorManager;
import vista.VentanaPrincipal;
import vista.componentes.ExploradorProyectos;
import vista.componentes.PestanasBiombo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorIDE {
    private final VentanaPrincipal vista;
    private final Timer timerAnalisis;

    public ControladorIDE(VentanaPrincipal vista) {
        this.vista = vista;
        this.timerAnalisis = new Timer(500, new AccionAnalisis());
        timerAnalisis.setRepeats(false);
        configurarListeners();
    }

    private void configurarListeners() {
    RSyntaxTextArea areaCodigo = vista.getAreaCodigo();
    
    if (areaCodigo == null) {
        System.err.println("⚠️ No hay editor activo, no se configuran los listeners.");
        return;
    }

    areaCodigo.getDocument().addDocumentListener(new DocumentListener() {
        @Override public void insertUpdate(DocumentEvent e) { actualizarEstado(); }
        @Override public void removeUpdate(DocumentEvent e) { actualizarEstado(); }
        @Override public void changedUpdate(DocumentEvent e) {}
    });
}


    private void actualizarEstado() {
        try {
            int caretPos = vista.getAreaCodigo().getCaretPosition();
            int linea = vista.getAreaCodigo().getLineOfOffset(caretPos) + 1;
            int columna = caretPos - vista.getAreaCodigo().getLineStartOffset(linea - 1) + 1;
            vista.actualizarPosicion(linea, columna);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
        
        timerAnalisis.restart();
    }

    private class AccionAnalisis implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String codigo = vista.getAreaCodigo().getText();
            CompiladorManager.ResultadoAnalisis resultado = CompiladorManager.ejecutarLexer(codigo);
            
            if (resultado.hayError()) {
                vista.mostrarError(resultado.mensajeError(), resultado.lineaError());
            } else {
                vista.actualizarConsola("✓ Análisis léxico exitoso (" + resultado.tokens().size() + " tokens)");
            }
        }
    }

    // Métodos de vinculación con los componentes de la vista
    public void vincularExplorador(ExploradorProyectos explorador) {
        // Lógica para vincular el explorador
        // this.vista.getExplorador().setExplorador(explorador);  // Método faltante en la vista
    }

    public void vincularPestanas(PestanasBiombo pestanas) {
        // Lógica para vincular las pestañas
        // this.vista.getPestanas().setPestanas(pestanas);  // Método faltante en la vista
    }
}
