package tpi144g7;

import tpi144g7.prode.clase.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException  {
        int [] dimensionResultado;
        int [] dimensionPronosticos;
        String archivoResultados;
        String archivoPronosticos;
        Scanner CapturaArchivo = new Scanner(System.in);

// Carga de Archivos por argumento: {"archivos.csv\resultados.csv","archivos.csv\pronosticos.csv"}
        if (args.length==2) {
            archivoResultados = args[0];
            archivoPronosticos = args[1];
        }
        else{
// Entrada de archivos por consola
            System.out.println("Directorio donde se encuentra el archivo resultados.csv: ");
            archivoResultados = CapturaArchivo.nextLine()+"\\resultados.csv";
            System.out.println("Directorio donde se encuentra el archivo pronosticos.csv: ");
            archivoPronosticos = CapturaArchivo.nextLine()+"\\pronosticos.csv";
            }

//Determina si los archivos existen y devuelve sus dimensiones (filas y columnas)
        dimensionResultado   = analizarArchivos(archivoResultados);
        dimensionPronosticos = analizarArchivos(archivoPronosticos);

//Si los archivos tienen filas continuar a creacion y carga de los objetos.
        if ((dimensionResultado[0]>1) && dimensionPronosticos[0]>1){
            var resultados  = cargarArchivoDeResultados(archivoResultados,dimensionResultado);
            var pronosticos = cargarArchivoDePronosticos(archivoPronosticos,dimensionPronosticos,resultados);
           mostrarResultados(pronosticos);
        }
    }

    public static int[] analizarArchivos (String archivo) throws IOException {
        int numeroDeFilas = 0;
        int numeroDeColumnas = 0;
// Determina si los archivos existen y devuelve la cantidad de filas y columnas
        if (Files.exists(Paths.get(archivo))) {
            for (String ignored : Files.readAllLines(Paths.get(archivo))) numeroDeFilas++;
        }
        if (numeroDeFilas>1){
            for (String texto : Files.readAllLines(Paths.get(archivo))) {
                String[] vectorAux = texto.split(";");  //Separa las columnas de cada fila
                numeroDeColumnas=vectorAux.length;
            }
        }
        return new int[]{numeroDeFilas,numeroDeColumnas};
    }

    public static Partido[] cargarArchivoDeResultados (String archivo,int []dimension)throws IOException{
        int contadorFila = 0;
        Partido[] informacionArchivo = new Partido[dimension[0]-1];

        // Lectura del archivo "resultados.csv" y almacenandolo en las clases Partido
        for (String texto : Files.readAllLines(Paths.get(archivo))) { // Extrae filas del archivo
            String[] vectorAux = texto.split(";");  //Separa las columnas de cada fila
            if(contadorFila>0) {    // Para evitar cargar en un objeto el encabezado de la tabla
                informacionArchivo[contadorFila-1]= new Partido(vectorAux);
            }
            contadorFila++;
        }
        return informacionArchivo;
    }

    public static Pronostico[] cargarArchivoDePronosticos(String archivo,int []dimension, Partido[] resultadosdePartidos) throws IOException {
        int contadorFila = 0;
        Pronostico[] informacionArchivo = new Pronostico[dimension[0]-1];

        // Lectura del archivo "pronostico.csv" recorriendolo y almacenandolo la informacion en clases
        for (String texto : Files.readAllLines(Paths.get(archivo))) { // Extrae filas del archivo
            String[] vectorAux = texto.split(";");  //Separa las columnas de cada fila
            if(contadorFila>0) {    // Para evitar cargar en un objeto el encabezado de la tabla

              // Asociacion de los objetos "Partido" creados anteriormente con el archivo resultados a los objetos "Pronostico"
              for(int i=0; i<resultadosdePartidos.length;i++) {
                  if (vectorAux[5].equals(resultadosdePartidos[i].idPartido)) {
                      informacionArchivo[contadorFila - 1] = new Pronostico(resultadosdePartidos[i], vectorAux);
                      break;
                  }
              }
            }
            contadorFila++;
        }
        return informacionArchivo;
    }

    public static void mostrarResultados(Pronostico[] pronosticos){
        String [] listaOrdenar= new String [pronosticos.length];

// Extrae los nombres asociados a cada linea de pronosticos
        for (int i=0;i < pronosticos.length;i++) {
            listaOrdenar [i]= pronosticos[i].Participante;
        }
        Arrays.sort(listaOrdenar); // Ordena la lista para poder extraer la cantidad de participantes y sus nombres sin repeticiones

//determinar la cantidad de participantes
        int contador=1;
        for(int i = 1;i< listaOrdenar.length;i++){
            if (!listaOrdenar[i].equals(listaOrdenar[i-1])){
               contador++;
            }
        }
        String [] nombreParticipante = new String[contador];
        nombreParticipante[0]=listaOrdenar[0];

//identifica los participantes
                if (listaOrdenar.length>0) {
                    contador = 1;
                    for (int i = 1; i < listaOrdenar.length; i++) {
                        if (!listaOrdenar[i].equals(listaOrdenar[i - 1])) {
                            nombreParticipante[contador] =listaOrdenar[i];
                        }
                    }
                }
                int aciertos=0;
        String aux1="";
        String aux2="";
        for (int i=0;i< pronosticos.length;i++){
            aux1=nombreParticipante[1];
            aux2=pronosticos[i].Participante;
            if (aux1.equals(aux2)) {
                aciertos += pronosticos[i].puntos();
            }
        }




        System.out.println("Cantidad de puntos de "+ nombreParticipante[1] +" es : "+ aciertos);
    }
}

