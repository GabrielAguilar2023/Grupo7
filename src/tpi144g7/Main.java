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
        int numeroFijoDeColumnasResultados=6;
        int numeroFijoDeColumnasPronosticos=8;

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

// Determina si los archivos existen y devuelve sus dimensiones (filas y columnas)
        dimensionResultado   = analizarArchivos(archivoResultados,numeroFijoDeColumnasResultados);
        dimensionPronosticos = analizarArchivos(archivoPronosticos,numeroFijoDeColumnasPronosticos);


// Si los archivos tienen filas continuar a creacion y carga de los objetos.
        if ((dimensionResultado[0]>1) && dimensionPronosticos[0]>1){
            var resultados  = cargarArchivoDeResultados(archivoResultados,dimensionResultado);
            var pronosticos = cargarArchivoDePronosticos(archivoPronosticos,dimensionPronosticos,resultados);
           mostrarResultados(pronosticos);
        }
    }

    public static int[] analizarArchivos (String archivo,int numeroDeColumnasObligatorias) throws IOException {
        int numeroDeFilas = 0;
        int errorArchivo = 0;
        int lineaLeida = 0;
// Determina si los archivos existen y devuelve la cantidad de filas y columnas
        if (Files.exists(Paths.get(archivo))) {
            for (String ignored : Files.readAllLines(Paths.get(archivo))) numeroDeFilas++;
        }else{
            errorArchivo = 1;
        }
// Detrmina si el archivo tiene datos cargado y no solo la linea de encabezado
        if (numeroDeFilas > 1)
            for (String texto : Files.readAllLines(Paths.get(archivo))) {
                String[] vectorAux = texto.split(";");  //Separa las columnas de cada fila
// Determina el numero de columnas de cada linea del archivo y si los campos de goles son valores numericos
                lineaLeida++;
                if(lineaLeida>1){
                    if (!(vectorAux.length == numeroDeColumnasObligatorias)) {errorArchivo = 2;}

                    if (numeroDeColumnasObligatorias == 6) { //Si es 6 es el archivo de resultados
// Determina si los goles son datos numericos
                        if (!(isNumeric(vectorAux[1]) && isNumeric(vectorAux[2]))) {errorArchivo = 3;}
                    }
                }
            }
// Manejo de error de lectura de archivo
        switch (errorArchivo) {
            case 1:
                System.out.println("No se puede acceder al archivo --> " + archivo);
                System.exit(0);
            case 2:
                System.out.println("Error en la informacion del archivo --> " + archivo);
                System.exit(0);
            case 3:
                System.out.println("Error en la lectura de goles");
                System.exit(0);
        }
        
        return new int[]{numeroDeFilas,numeroDeColumnasObligatorias};
  }

    private static boolean isNumeric(String texto) {
            try {
                Integer.parseInt(texto);
                return true;
            } catch (NumberFormatException nfe){
                return false;
            }
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
        for (int i=0;i < pronosticos.length;i++)
        listaOrdenar[i] = pronosticos[i].Participante;
        Arrays.sort(listaOrdenar); // Ordena la lista para poder extraer la cantidad de participantes y sus nombres sin repeticiones
//Determinar la cantidad de participantes
        int contador = 1;
        for(int i = 1;i< listaOrdenar.length;i++)
            if (!listaOrdenar[i].equals(listaOrdenar[i - 1]))
                contador++;
        String[] nombreParticipante = new String[contador];
        int[]   puntajeParticipante = new int[contador];
        nombreParticipante[0] = listaOrdenar[0]; //Asigna el primer participante a la lista y comienza a comparar
//Identifica los participantes
        if (listaOrdenar.length>1) {
            contador = 1;
            for (int i = 1; i < listaOrdenar.length; i++)
                if (!listaOrdenar[i].equals(listaOrdenar[i - 1])) {
                    nombreParticipante[contador] = listaOrdenar[i];
                    contador++;
                }
        }
//Asigna puntaje a cada participante
    for (int j=0;j< nombreParticipante.length;j++) {
        int aciertos = 0;
        for (int i = 0; i < pronosticos.length; i++)
            if (nombreParticipante[j].equals(pronosticos[i].Participante))
            aciertos += pronosticos[i].puntos();
        puntajeParticipante[j]=aciertos;
    }
// Ordena los participantes por puntaje (Metodo busrbuja)
        for (int i = 0; i < puntajeParticipante.length; i++)
            for (int j = 1; j < (puntajeParticipante.length - i); j++) {
                if (puntajeParticipante[j - 1] < puntajeParticipante[j]) {
                    int auxiliarPuntaje = puntajeParticipante[j - 1];
                    String auxiliarNombre = nombreParticipante[j - 1];
                    puntajeParticipante[j - 1] = puntajeParticipante[j];
                    nombreParticipante[j - 1] = nombreParticipante[j];
                    puntajeParticipante[j] = auxiliarPuntaje;
                    nombreParticipante[j] = auxiliarNombre;
                }
            }
//Impresion de los resultados ordenados de mayor a menor
        for(int i=0;i<nombreParticipante.length;i++)
            System.out.println(nombreParticipante[i] + ": " + puntajeParticipante[i] + " puntos.");
    }

}



