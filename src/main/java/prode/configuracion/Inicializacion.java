package prode.configuracion;

import java.util.Scanner;

public class Inicializacion {
//Indica la cantidad de filas de la tabla leida en el archivo resultados.csv
    public static int dimensionResultado;

//Indica la cantidad de filas de la tabla leida en el archivo pronosticos.csv
    public static int dimensionPronosticos;
//Almacena los valores configurables de la aplicacion: puntos Extra, Puntaje por partido y configuracion del motor de base de datos
    public static String[] configuracion = new String[]{"1","0","","","","NO SQL"};

//Autodefinidas
    public static int numeroFijoDeColumnasResultados = 7;
    public static int numeroFijoDeColumnasPronosticos = 10;
    public static String archivoResultados = "";
    public static String archivoPronosticos = "";
    public static Scanner capturaArchivo = new Scanner(System.in);

// Metodo utilizado para determinar si un String contiene dato numerico
    public static boolean isNumeric(String texto) {

        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
}
