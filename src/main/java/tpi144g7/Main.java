package tpi144g7;

import tpi144g7.prode.clase.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException  {
        int [] dimensionResultado;
        int [] dimensionPronosticos;
        int numeroFijoDeColumnasResultados=7;
        int numeroFijoDeColumnasPronosticos=10;
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
//Creacion de todos los Objetos necesarios para el proceso
            Partido[] resultados  = cargarArchivoDeResultados(archivoResultados,dimensionResultado);
            Ronda[] rondas = crearObjetosRonda(resultados);
            Pronostico[] pronosticos = cargarArchivoDePronosticos(archivoPronosticos,dimensionPronosticos,resultados);
            Participante[] participantes =CrearObjetosParticipantes(pronosticos);
//-------------------------------------------------------
            imprimirRankingDeAciertos(participantes);
        }
    }

    private static int[] analizarArchivos (String archivo,int numeroDeColumnasObligatorias) throws IOException {
        int numeroDeFilas = 0;
        int errorArchivo = 0;
        int lineaLeida = 0;
// Determina si los archivos existen y devuelve el numero de registros que tiene cargado
        if (!Files.exists(Paths.get(archivo))) {errorArchivo = 1;}
        else {
            for (String ignored : Files.readAllLines(Paths.get(archivo))) numeroDeFilas++;
// Detrmina si el archivo tiene datos cargado y no solo la linea de encabezado
            if (numeroDeFilas > 1) {
// Determina el numero de columnas de cada linea del archivo y si los campos de goles son valores numericos
                for (String texto : Files.readAllLines(Paths.get(archivo))) {
                    String[] vectorAux = texto.split(";");  //Separa las columnas de cada fila
                    lineaLeida++;
                    if (lineaLeida > 1) {
                        if (!(vectorAux.length == numeroDeColumnasObligatorias)) { errorArchivo = 2; }
                        else{
                            if (numeroDeColumnasObligatorias == 6) { //Si es 6 es que estoy analizando el archivo de resultados
// Determina si los goles son datos numericos
                                if (!(isNumeric(vectorAux[1]) && isNumeric(vectorAux[2]))) {
                                    errorArchivo = 3;
                                }
                            }
                        }
                    }
                }
            } else {errorArchivo = 4;}
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
            case 4:
                System.out.println("El archivo no tiene datos a procesar");
                System.exit(0);
        }
        return new int[]{numeroDeFilas,numeroDeColumnasObligatorias};
    }

    private static Partido[] cargarArchivoDeResultados (String archivo,int []dimension)throws IOException{
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
//    crearObjetosRonda(informacionArchivo);
        return informacionArchivo;
    }

    private static Pronostico[] cargarArchivoDePronosticos(String archivo,int []dimension, Partido[] resultadosdePartidos) throws IOException {
        int contadorFila = 0;
        Pronostico[] informacionArchivo = new Pronostico[dimension[0]-1];
// Lectura del archivo "pronostico.csv" recorriendolo y almacenandolo la informacion en clases
        for (String texto : Files.readAllLines(Paths.get(archivo))) { // Extrae filas del archivo
            String[] vectorAux = texto.split(";");  //Separa las columnas de cada fila
            if(contadorFila>0) {    // Para evitar cargar en un objeto el encabezado de la tabla
// Asociacion de los objetos "Partido" creados anteriormente con el archivo resultados a los objetos "Pronostico"
                for(int i=0; i<resultadosdePartidos.length;i++) {
                    if ((vectorAux[5].equals(resultadosdePartidos[i].getIdPartido())) && (vectorAux[6].equals(resultadosdePartidos[i].getIdRonda()))) {
                        informacionArchivo[contadorFila - 1] = new Pronostico(resultadosdePartidos[i], vectorAux);
                        break;
                    }
                }
            }
            contadorFila++;
        }
        return informacionArchivo;
    }

    private static Ronda[] crearObjetosRonda(Partido[] partidosJugados) {
        Ronda[] rondas = null;
//Primero ordeno el vector de partidos por "idRonda"
        Arrays.sort(partidosJugados);
        int indiceDeRondas=0;
//Determino el numero de rondas
        int cantidadRondas = 1;
        for (int i = 1; i < partidosJugados.length; i++)
            if (!(partidosJugados[i].getIdRonda().equals(partidosJugados[i - 1].getIdRonda()))) {
                cantidadRondas++;
            }
//Creo el vector de objetos Ronda de la cantidad necesaria
        rondas = new Ronda[cantidadRondas];
//------ Comienza la carga del vector de Rondas
        while (partidosJugados.length>0){

        if(indiceDeRondas>0)
            rondas[indiceDeRondas-1].setRondaJugada(true);  //********* Prueba
//Determino el numero de partidos por rondas
            int cantpart = 0;
            for (int i = 0; i < partidosJugados.length; i++) {
                if (partidosJugados[i].getIdRonda().equals(partidosJugados[0].getIdRonda())) {
                    cantpart = i + 1;
                } else {
                    break;
                }
            }
//Asigno partidos de la misma ronda al vector creado para llamar al constructor de Ronda
            rondas[indiceDeRondas] = new Ronda(Arrays.copyOfRange(partidosJugados, 0, cantpart),partidosJugados[0].getIdRonda());

            indiceDeRondas++;
//Quito los partidos de la ronda anterior, del vector de partidos jugados
            partidosJugados = Arrays.copyOfRange(partidosJugados, cantpart, partidosJugados.length);
        }
//-------Fin de la carga del vector de Rondas
        return rondas;
    }

    private static Participante[] CrearObjetosParticipantes(Pronostico[] pronosticos) {
    String[] listaOrdenar = new String[pronosticos.length];
    Participante [] participantes;
//Extrae los nombres asociados a cada linea de pronosticos
    for (int i = 0; i < pronosticos.length; i++)
        listaOrdenar[i] = pronosticos[i].getParticipante();
        Arrays.sort(listaOrdenar); // Ordena la lista para poder extraer la cantidad de participantes y sus nombres sin repeticiones
//Determinar la cantidad de participantes
        int contador = 1;
        for (int i = 1; i < listaOrdenar.length; i++)
            if (!listaOrdenar[i].equals(listaOrdenar[i - 1]))
                contador++;
//Crea objeto participante
        participantes = new Participante[contador];
        int[][] puntajeParticipante = new int[contador][2];
        participantes[0] = new Participante(listaOrdenar[0]); //Asigna el primer participante a la lista y comienza a comparar
//Identifica los participantes y los carga en un vector para su posterior asignacion de puntaje
        if (listaOrdenar.length > 1) {
            contador = 1;
            for (int i = 1; i < listaOrdenar.length; i++)
                if (!listaOrdenar[i].equals(listaOrdenar[i - 1])) {
                    participantes[contador] = new Participante( listaOrdenar[i]);
                    contador++;
                }
        }
//Asigna puntaje a cada participante
        for (int j = 0; j < participantes.length; j++) {
            ArrayList <String> pasarAciertos = new ArrayList<>();
            int puntajeDeAciertos = 0;
            int cantidadDeAciertos = 0;
            for (int i = 0; i < pronosticos.length; i++) {
                if (participantes[j].getNombre().equals(pronosticos[i].getParticipante())) {
                    int acierto = pronosticos[i].puntos();
                    puntajeDeAciertos += acierto;
//Carga de informacion de aciertos de cada participante
                    if (acierto>0) {
                        pasarAciertos.add(pronosticos[i].getPartido().getIdPartido()+";"+pronosticos[i].getPartido().getIdRonda()+";"+pronosticos[i].getEquipo().getNombre()+";"+pronosticos[i].getResultado());
                        cantidadDeAciertos++;
                    }
                }
            }
            participantes[j].setAciertos(pasarAciertos);
            participantes[j].setPuntaje(puntajeDeAciertos);
        }
    return participantes;
    }

    private static void imprimirRankingDeAciertos(Participante[] participantes){
        for (int i = 0; i < participantes.length; i++){
            for (int j = 1; j < (participantes.length - i); j++) {
                if (participantes[j - 1].getPuntaje() < participantes[j].getPuntaje()) {
                    Participante auxiliar = participantes[j - 1];
                    participantes[j - 1] = participantes[j];
                    participantes[j] = auxiliar;
                }
            }
        }
//Impresion de los resultados ordenados de mayor a menor
        System.out.println();
        System.out.println("------------ Puntajes --------------");
        for(int i=0;i<participantes.length;i++)
            System.out.println(participantes[i].getNombre() + ": " + participantes[i].getPuntaje() + " puntos," + " con " +participantes[i].getAciertos().length+  " aciertos");
        System.out.println("------------------------------------");
    }

    private static boolean isNumeric(String texto) {
// Metodo utilizado para determinar si un String contiene dato numerico
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
}