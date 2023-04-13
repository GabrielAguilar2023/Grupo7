package prode;

import prode.clase.*;
import static prode.configuracion.Inicializacion.*;
import static prode.configuracion.LecturaArchivos.*;
import static prode.configuracion.ConexionSQL.*;

import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {

/**
Carga de Archivos por argumento: src\main\resources\configuracion.properties
                                 archivos.csv\resultados.csv
                                 archivos.csv\pronosticos.csv
**/
        switch (args.length){
            case 1: {
                cargarArchivoDeConfiguracion(args[0]);
            }
            case 0:{
                if(args.length==0) System.out.println("\nNo hay configuracion externa cargada...");
                System.out.println("\n\nDirectorio donde se encuentra el archivo resultados.csv: ");
                archivoResultados = capturaArchivo.nextLine()+"\\resultados.csv";
                System.out.println("Directorio donde se encuentra el archivo pronosticos.csv: ");
                archivoPronosticos = capturaArchivo.nextLine()+"\\pronosticos.csv";
                break;
            }
            case 2: {
                if (cargarArchivoDeConfiguracion(args[0])&& conexionMySql()) {
                        mySql_OK = true;
                        archivoResultados = args[1];
                    // todo ---> analizar el pronostico desde la base de datos

                } else {
                    archivoResultados = args[0];
                    archivoPronosticos = args[1];
                }
                break;
            }
            case 3:{
                cargarArchivoDeConfiguracion(args[0]);
                archivoResultados= args[1];
                archivoPronosticos=args[2];
            }
        }

        filasDeResultados = analizarArchivos(archivoResultados,numeroFijoDeColumnasResultados);
        if (!mySql_OK)
            filasDePronosticos = analizarArchivos(archivoPronosticos,numeroFijoDeColumnasPronosticos);
// Si los archivos tienen filas continuar a creacion y carga de los objetos.
        if ((filasDeResultados >1) && filasDePronosticos >1){
//Creacion de todos los Objetos necesarios para el proceso
            Partido[] resultados  = cargarArchivoDeResultados(archivoResultados);
            Ronda[] rondas = crearObjetosRonda(resultados);
            for (int i=0;i<rondas.length;i++){
                rondas[i].infoRonda();
            }




            if (!mySql_OK) {
                pronosticos = cargarArchivoDePronosticos(archivoPronosticos, resultados);
            }else {
                pronosticos = cargarBaseDeDatosPronosticos(resultados);
            }
            Participante[] participantes =CrearObjetosParticipantes(pronosticos);
//-------------------------------------------------------
            imprimirRankingDeAciertos(participantes);
        }
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
//Cerrar la ronda si el archivo de configuracion indica que el campeonato finalizo
        if (campeonatoFinalizado.equals("SI")){rondas[indiceDeRondas-1].setRondaJugada(true);}
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