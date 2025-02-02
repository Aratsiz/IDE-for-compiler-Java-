package modelo;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GestorProyectos {
    private static final String[] ESTRUCTURA = {
        "源",       // src (source)
        "箱",       // bin (binary)
        "設定",     // config
        "資源"      // resources
    };

    public GestorProyectos () {
        
    }

    public void crearProyecto(String nombre, String ruta) {
        Path directorioRaiz = Paths.get(ruta, nombre);
        
        // Crear estructura de carpetas con nombres japoneses
        for (String carpeta : ESTRUCTURA) {
            new File(directorioRaiz.toString(), carpeta).mkdirs();
        }
        
        // Crear archivo de configuración básico
        crearArchivoConfiguracion(directorioRaiz.resolve("設定/proyecto.config"));
    }

    private void crearArchivoConfiguracion(Path rutaConfig) {
        String config = """
        # Configuración del Proyecto プロジェクト
        nombre = %s
        version = 1.0.0
        lenguaje = tu_lenguaje
        """;
        // Escribir archivo...
    }

    public DefaultMutableTreeNode generarArbolProyecto(String ruta) {
        File raiz = new File(ruta);
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(new NodoProyecto(raiz));
        generarArbolRecursivo(raiz, nodo);
        return nodo;
    }

    private void generarArbolRecursivo(File directorio, DefaultMutableTreeNode padre) {
        for (File archivo : directorio.listFiles()) {
            DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(new NodoProyecto(archivo));
            if (archivo.isDirectory()) {
                generarArbolRecursivo(archivo, nodo);
            }
            padre.add(nodo);
        }
    }

    public static class NodoProyecto {
        private final File archivo;
        public NodoProyecto(File archivo) { this.archivo = archivo; }
        @Override public String toString() { return archivo.getName(); }
    }
}