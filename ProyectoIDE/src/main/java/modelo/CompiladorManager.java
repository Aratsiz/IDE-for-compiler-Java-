package modelo;

import modelo.compilador.lexer.AnalizadorLexico;
import java.util.List;

public class CompiladorManager {

    public static ResultadoAnalisis ejecutarLexer(String codigoFuente) {
        try {
            AnalizadorLexico lexer = new AnalizadorLexico();
            List<AnalizadorLexico.Token> tokens = lexer.analizar(codigoFuente);
            return new ResultadoAnalisis(tokens, null, -1);
        } catch (Exception ex) {
            int lineaError = extraerLineaError(ex.getMessage());
            return new ResultadoAnalisis(null, ex.getMessage(), lineaError);
        }
    }

    private static int extraerLineaError(String mensaje) {
        try {
            return Integer.parseInt(mensaje.replaceAll(".*línea (\\d+).*", "$1"));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public record ResultadoAnalisis(
        List<AnalizadorLexico.Token> tokens,
        String mensajeError,
        int lineaError
    ) {
        public boolean hayError() {
            return mensajeError != null;
        }
    }
}