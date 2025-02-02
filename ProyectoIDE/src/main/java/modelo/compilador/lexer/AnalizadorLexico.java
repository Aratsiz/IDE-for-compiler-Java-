package modelo.compilador.lexer;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Analizador léxico para lenguaje tipo C++ con palabras reservadas en romaji
 * Autor: [Zephyllo]
 * Fecha: [30-01-2025]
 */
public class AnalizadorLexico {

    // Tipos de tokens
    public enum TipoToken {
        // Tipos de datos
        ENTERO,         // seisuu (entero)
        FLOTANTE,       // shousuu (flotante)
        DOBLE,          // nijuu_shousuu (doble)
        BOOLEANO,       // bulean (booleano)
        CARACTER,       // moji (carácter)
        CADENA,         // mojiretsu (cadena)
        ARREGLO,        // hairetsu (arreglo)
        ENUMERACION,    // enumu (enumeración)
        ESTRUCTURA,     // shikumi (estructura)
        FUNCION,        // kansuu (función)

        // Estructuras de control
        SI,             // moshi (si)
        SINO,           // de_nakereba (sino)
        CASO,           // keesu (caso)
        DEFECTO,        // deforuto (defecto)
        PARA,           // tame (para)
        MIENTRAS,       // nagara (mientras)
        HACER,          // yaru (hacer)
        ROMPER,         // breku (romper)
        CONTINUAR,      // tsuzuku (continuar)
        RETORNAR,       // modoru (retornar)

        // Operadores
        OPERADOR,
        OPERADOR_LOGICO,
        OPERADOR_BIT,
        INCREMENTO,
        DESPLAZAMIENTO,
        CONDICIONAL,

        // Literales y otros
        IDENTIFICADOR,
        NUMERO,
        HEXADECIMAL,
        BINARIO,
        OCTAL,
        LITERAL_CADENA,
        LITERAL_CARACTER,
        COMENTARIO,
        DELIMITADOR,
        SEPARADOR,
        FIN_ARCHIVO,
        ERROR
    }

    public AnalizadorLexico() {
        // Constructor necesario para instanciación desde CompiladorManager
    }

    /**
     * Clase que representa un token identificado
     */
    public record Token(TipoToken tipo, String valor, int linea, int columna) {
        @Override
        public String toString() {
            return String.format("%-15s %-20s (Línea: %2d, Columna: %2d)",
                    tipo, valor, linea, columna);
        }
    }

    // Mapa de palabras reservadas (romaji -> tipo token)
    private static final Map<String, TipoToken> PALABRAS_RESERVADAS = new HashMap<>();
    static {
        // Tipos de datos
        PALABRAS_RESERVADAS.put("seisuu", TipoToken.ENTERO);        // entero
        PALABRAS_RESERVADAS.put("shousuu", TipoToken.FLOTANTE);     // flotante
        PALABRAS_RESERVADAS.put("nijuu_shousuu", TipoToken.DOBLE);  // doble
        PALABRAS_RESERVADAS.put("bulean", TipoToken.BOOLEANO);      // booleano
        PALABRAS_RESERVADAS.put("moji", TipoToken.CARACTER);        // carácter
        PALABRAS_RESERVADAS.put("mojiretsu", TipoToken.CADENA);     // cadena
        PALABRAS_RESERVADAS.put("hairetsu", TipoToken.ARREGLO);     // arreglo
        PALABRAS_RESERVADAS.put("enumu", TipoToken.ENUMERACION);    // enumeración
        PALABRAS_RESERVADAS.put("shikumi", TipoToken.ESTRUCTURA);   // estructura
        PALABRAS_RESERVADAS.put("kansuu", TipoToken.FUNCION);       // función

        // Estructuras de control
        PALABRAS_RESERVADAS.put("moshi", TipoToken.SI);             // si
        PALABRAS_RESERVADAS.put("de_nakereba", TipoToken.SINO);     // sino
        PALABRAS_RESERVADAS.put("keesu", TipoToken.CASO);           // caso
        PALABRAS_RESERVADAS.put("deforuto", TipoToken.DEFECTO);     // defecto
        PALABRAS_RESERVADAS.put("tame", TipoToken.PARA);            // para
        PALABRAS_RESERVADAS.put("nagara", TipoToken.MIENTRAS);      // mientras
        PALABRAS_RESERVADAS.put("yaru", TipoToken.HACER);           // hacer
        PALABRAS_RESERVADAS.put("breku", TipoToken.ROMPER);         // romper
        PALABRAS_RESERVADAS.put("tsuzuku", TipoToken.CONTINUAR);    // continuar
        PALABRAS_RESERVADAS.put("modoru", TipoToken.RETORNAR);      // retornar
    }

    // Patrones de expresiones regulares
    private static final Pattern COMENTARIO = Pattern.compile("//.*|/\\*.*?\\*/", Pattern.DOTALL);
    private static final Pattern CADENA = Pattern.compile("\"(?:\\\\.|[^\"\\\\])*\"");
    private static final Pattern CARACTER = Pattern.compile("'(\\\\['\"tnr\\\\]|[^'])'");
    private static final Pattern NUMERO = Pattern.compile(
            "-?(?:\\d+\\.\\d*|\\.\\d+|\\d+)(?:[eE][+-]?\\d+)?|" +  // Decimal
                    "0x[0-9a-fA-F]+|" +    // Hexadecimal (0x1A3F)
                    "0b[01]+|" +           // Binario (0b1010)
                    "0[0-7]+"              // Octal (0777)
    );
    private static final Pattern OPERADOR = Pattern.compile(
            "\\+\\+|--|" +         // Incremento/decremento
                    "==|!=|<=|>=|&&|\\|\\||" + // Operadores lógicos
                    "[+\\-*/%&|^<>!=]=?|" +    // Operadores con asignación
                    "->|::|\\?\\?|\\?|\\.|~|%" // Operadores especiales
    );
    private static final Pattern DELIMITADORES = Pattern.compile("[(){}\\[\\]]");
    private static final Pattern SEPARADORES = Pattern.compile("[,;:]");
    private static final Pattern IDENTIFICADOR = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");

    /**
     * Método principal que realiza el análisis léxico
     * @param entrada Código fuente a analizar
     * @return Lista de tokens identificados
     */
    public List<Token> analizar(String entrada) {
        List<Token> tokens = new ArrayList<>();
        int posicion = 0;
        int linea = 1;
        int columna = 1;

        while (posicion < entrada.length()) {
            char caracterActual = entrada.charAt(posicion);

            // Manejo de espacios y saltos de línea
            if (Character.isWhitespace(caracterActual)) {
                if (caracterActual == '\n') {
                    linea++;
                    columna = 1;
                } else {
                    columna++;
                }
                posicion++;
                continue;
            }

            // Detección de comentarios
            Matcher coincidenciaComentario = COMENTARIO.matcher(entrada.substring(posicion));
            if (coincidenciaComentario.lookingAt()) {
                String comentario = coincidenciaComentario.group();
                agregarToken(tokens, TipoToken.COMENTARIO, comentario, linea, columna);
                posicion += comentario.length();
                columna += comentario.length();
                continue;
            }

            // Detección de cadenas
            Matcher coincidenciaCadena = CADENA.matcher(entrada.substring(posicion));
            if (coincidenciaCadena.lookingAt()) {
                String cadena = coincidenciaCadena.group();
                agregarToken(tokens, TipoToken.LITERAL_CADENA, cadena, linea, columna);
                posicion += cadena.length();
                columna += cadena.length();
                continue;
            }

            // Detección de caracteres
            Matcher coincidenciaCaracter = CARACTER.matcher(entrada.substring(posicion));
            if (coincidenciaCaracter.lookingAt()) {
                String caracter = coincidenciaCaracter.group();
                agregarToken(tokens, TipoToken.LITERAL_CARACTER, caracter, linea, columna);
                posicion += caracter.length();
                columna += caracter.length();
                continue;
            }

            // Detección de números
            Matcher coincidenciaNumero = NUMERO.matcher(entrada.substring(posicion));
            if (coincidenciaNumero.lookingAt()) {
                String numero = coincidenciaNumero.group();
                TipoToken tipoNumero = determinarTipoNumero(numero);
                agregarToken(tokens, tipoNumero, numero, linea, columna);
                posicion += numero.length();
                columna += numero.length();
                continue;
            }

            // Detección de operadores
            Matcher coincidenciaOperador = OPERADOR.matcher(entrada.substring(posicion));
            if (coincidenciaOperador.lookingAt()) {
                String operador = coincidenciaOperador.group();
                TipoToken tipoOperador = clasificarOperador(operador);
                agregarToken(tokens, tipoOperador, operador, linea, columna);
                posicion += operador.length();
                columna += operador.length();
                continue;
            }

            // Detección de delimitadores
            Matcher coincidenciaDelimitador = DELIMITADORES.matcher(entrada.substring(posicion));
            if (coincidenciaDelimitador.lookingAt()) {
                String delimitador = coincidenciaDelimitador.group();
                agregarToken(tokens, TipoToken.DELIMITADOR, delimitador, linea, columna);
                posicion += delimitador.length();
                columna += delimitador.length();
                continue;
            }

            // Detección de separadores
            Matcher coincidenciaSeparador = SEPARADORES.matcher(entrada.substring(posicion));
            if (coincidenciaSeparador.lookingAt()) {
                String separador = coincidenciaSeparador.group();
                agregarToken(tokens, TipoToken.SEPARADOR, separador, linea, columna);
                posicion += separador.length();
                columna += separador.length();
                continue;
            }

            // Detección de identificadores y palabras reservadas
            Matcher coincidenciaIdentificador = IDENTIFICADOR.matcher(entrada.substring(posicion));
            if (coincidenciaIdentificador.lookingAt()) {
                String identificador = coincidenciaIdentificador.group();
                TipoToken tipo = PALABRAS_RESERVADAS.getOrDefault(identificador, TipoToken.IDENTIFICADOR);
                agregarToken(tokens, tipo, identificador, linea, columna);
                posicion += identificador.length();
                columna += identificador.length();
                continue;
            }

            throw new RuntimeException("Error léxico: Carácter no reconocido '" + caracterActual +
                    "' en línea " + linea + ", columna " + columna);
        }

        agregarToken(tokens, TipoToken.FIN_ARCHIVO, "", linea, columna);
        return tokens;
    }

    // Métodos auxiliares
    private void agregarToken(List<Token> tokens, TipoToken tipo, String valor, int linea, int columna) {
        tokens.add(new Token(tipo, valor, linea, columna));
    }

    private TipoToken determinarTipoNumero(String numero) {
        if (numero.startsWith("0x")) return TipoToken.HEXADECIMAL;
        if (numero.startsWith("0b")) return TipoToken.BINARIO;
        if (numero.startsWith("0") && numero.length() > 1 && !numero.contains(".")) return TipoToken.OCTAL;
        if (numero.contains(".") || numero.toLowerCase().contains("e")) return TipoToken.FLOTANTE;
        return TipoToken.NUMERO;
    }

    private TipoToken clasificarOperador(String operador) {
        return switch (operador) {
            case "&&", "||", "!", "==", "!=" -> TipoToken.OPERADOR_LOGICO;
            case "<<", ">>", ">>>", "&", "|", "^", "~" -> TipoToken.OPERADOR_BIT;
            case "++", "--" -> TipoToken.INCREMENTO;
            case "?", ":" -> TipoToken.CONDICIONAL;
            default -> TipoToken.OPERADOR;
        };
    }
}