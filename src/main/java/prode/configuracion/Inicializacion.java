package prode.configuracion;

import prode.clase.Pronostico;

import java.util.Scanner;

public class Inicializacion {
//Indica la cantidad de filas de la tabla leida en el archivo resultados.csv
    public static int filasDeResultados = 0;

//Indica la cantidad de filas de la tabla leida en el archivo pronosticos.csv
    public static int filasDePronosticos = 0;

//Almacena los valores configurables de la aplicacion: puntos Extra, Puntaje por partido y configuracion del motor de base de datos
    public static String puntajeExtraPorFase="0";
    public static String puntajePorPartidoGanado="1";
    public static String puntajeExtraPorRonda ="0";
    public static String mysql_url ="";
    public static String mysql_user ="";
    public static String mysql_password ="";
    public static String campeonatoFinalizado = "NO";
    public static String infoDelCampeonato="SI";

//Autodefinidas
    public static int numeroFijoDeColumnasResultados = 7;
    public static int numeroFijoDeColumnasPronosticos = 10;
    public static String archivoResultados = "";
    public static String archivoPronosticos = "";
    public static Scanner capturaArchivo = new Scanner(System.in);
    public static boolean mySql_OK = false;

    public static Pronostico[] pronosticos;

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
