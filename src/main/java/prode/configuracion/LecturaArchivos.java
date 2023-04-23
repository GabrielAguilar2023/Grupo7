package prode.configuracion;

import prode.clase.Partido;
import prode.clase.Pronostico;
import static prode.configuracion.Inicializacion.*;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class LecturaArchivos {

    public static boolean cargarArchivoDeConfiguracion(String arg) {
        if (!arg.matches(".+\\.properties")){
            System.out.println("\n   No se cargo el archivo de configuracion...");
            return false;
        }
        try {
            Properties propiedadesDelProyecto = new Properties();
//Carga el archivo en el objeto Properties
            propiedadesDelProyecto.load(new FileReader(arg));
            puntajePorPronosticoAcertado = propiedadesDelProyecto.getProperty("puntajePorPronosticoAcertado", "1");
            puntajeExtraPorRonda = propiedadesDelProyecto.getProperty("puntajeExtraPorRonda", "0");
            puntajeExtraPorFase =propiedadesDelProyecto.getProperty("puntajeExtraPorFase", "0");
            mysql_url = propiedadesDelProyecto.getProperty("mysql_url", "");
            mysql_user = propiedadesDelProyecto.getProperty("mysql_user", "");
            mysql_password = propiedadesDelProyecto.getProperty("mysql_password", "");
            campeonatoFinalizado = propiedadesDelProyecto.getProperty("campeonatoFinalizado","NO");
            infoDebug =propiedadesDelProyecto.getProperty("infoDebug","NO");

            if ((puntajePorPronosticoAcertado.equals("1") &&
                    puntajeExtraPorRonda.equals("0") &&
                    puntajeExtraPorFase.equals("0"))) {
               System.out.println("\n ********** CONFIGURACION POR DEFECTO **********\n");
            }
// Si no hay info de SQL entonces se avisa que no hay configuracion MySQL
            if (!(mysql_url.isEmpty() || mysql_user.isEmpty() || mysql_password.isEmpty())) {
                return true;
            }else {
                System.out.println("\n******** No hay configuracion MySQL ********");
                return false;}
        } catch (Exception e){
            System.out.println("\n    No hay configuracion externa cargada...");
            pantallaDeConfiguracion();
            return false;
        }
    }

    public static int analizarArchivos(String archivo, int numeroDeColumnasObligatorias) throws IOException {
        int numeroDeFilas = 0;
        int errorArchivo = 0;
        int lineaLeida = 0;
        int errorEnLinea =0;
// Determina si los archivos existen y devuelve el numero de registros que tiene cargado
        if (!Files.exists(Paths.get(archivo))) {errorArchivo = 1;}
        else {
            for (String ignored : Files.readAllLines(Paths.get(archivo))) numeroDeFilas++;
// Determina si el archivo tiene datos y no solo es la linea de encabezado
            if (numeroDeFilas > 1) {
// Determina el numero de columnas de cada linea del archivo y si los campos de cada registros son validos
                for (String texto : Files.readAllLines(Paths.get(archivo))) {
                    String[] vectorAux = texto.split(";");  //Separa las columnas de cada fila
                    lineaLeida++;
                    if (lineaLeida > 1) {
                        if (!(vectorAux.length == numeroDeColumnasObligatorias)) { errorArchivo = 2; }
                        else{
                            if (numeroDeColumnasObligatorias == numeroFijoDeColumnasResultados) { //Si es 6 es que estoy analizando el archivo de resultados
//Comprueba la integridad de datos en los registros de los archivos
                                var campo0 = vectorAux[0].matches("^[A-Z][a-z]+(\\s[a-zA-Z]+(\\s[a-zA-Z]+)?)?");
                                var campo1 = vectorAux[1].matches("[0-9][0-9]?");
                                var campo2 = vectorAux[2].matches("[0-9][0-9]?");
                                var campo3 = vectorAux[3].matches("^[A-Z][a-z]+(\\s[a-zA-Z]+(\\s[a-zA-Z]+)?)?");
                                var campo4 = vectorAux[4].matches("[0-9]+");
                                var campo5 = vectorAux[5].matches("[0-9]");
                                var campo6 = vectorAux[6].matches("[0-9]");
                                if(!(campo0 && campo1 && campo2 && campo3 && campo4 && campo5 && campo6 )) {
                                    errorArchivo = 3;
                                     errorEnLinea = lineaLeida-1;
                                }
                            }else{
                                if (numeroDeColumnasObligatorias==numeroFijoDeColumnasPronosticos){
                                    var campo0 = vectorAux[0].matches("^[A-Z][a-z]+(\\s[a-zA-Z]+(\\s[a-zA-Z]+)?)?");
                                    var campo1 = vectorAux[1].matches("[X]?");
                                    var campo2 = vectorAux[2].matches("[X]?");
                                    var campo3 = vectorAux[3].matches("[X]?");
                                    var campo4 = vectorAux[4].matches("^[A-Z][a-z]+(\\s[a-zA-Z]+(\\s[a-zA-Z]+)?)?");
                                    var campo5 = vectorAux[5].matches("[0-9]+");
                                    var campo6 = vectorAux[6].matches("[0-9]");
                                    var campo7 = vectorAux[7].matches("^[A-Z][a-z]+(\\s[a-zA-Z]+(\\s[a-zA-Z]+)?)?");
                                    var campo8 = vectorAux[8].matches("[0-9]");
                                    var campo9 = vectorAux[9].matches("[0-9]+");
                                    boolean exclusividad1 = vectorAux[1].matches("[X]");
                                    boolean exclusividad2 = vectorAux[2].matches("[X]");
                                    boolean exclusividad3 = vectorAux[3].matches("[X]");
// Comprueba que existe solamente un campo de pronostico de resultado seleccionado.
                                    var campo10 = ((exclusividad1 && !exclusividad2 && !exclusividad3)||
                                                          (!exclusividad1 && exclusividad2 && !exclusividad3)||
                                                          (!exclusividad1 && !exclusividad2 && exclusividad3));
                                    if(!(campo0 && campo1 && campo2 && campo3 && campo4 && campo5 && campo6 && campo7 && campo8 && campo9 && campo10)) {
                                        errorArchivo = 4;
                                        errorEnLinea = lineaLeida-1;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                errorArchivo = 5;
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
                System.out.println("Inconsistencia en los datos del archivo resultados.scv");
                System.exit(0);
            case 4:
                System.out.println("Inconsistencia en los datos del archivo pronosticos.scv --> linea " + errorEnLinea);
                System.exit(0);
            case 5:
                System.out.println("El archivo no tiene datos a procesar");
                System.exit(0);
        }
        return numeroDeFilas;
    }

    public static Partido[] cargarArchivoDeResultados (String archivo)throws IOException{
        int contadorFila = 0;
        Partido[] informacionArchivo = new Partido[filasDeResultados -1];
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

    public static Pronostico[] cargarArchivoDePronosticos(String archivo, Partido[] resultadosDePartidos) throws IOException {
        int contadorFila = 0;
        Pronostico[] informacionArchivo = new Pronostico[filasDePronosticos -1];
// Lectura del archivo "pronostico.csv" recorriendolo y almacenando la informacion en clases
        for (String texto : Files.readAllLines(Paths.get(archivo))) { // Extrae filas del archivo
            String[] vectorAux = texto.split(";");  //Separa las columnas de cada fila
            if(contadorFila>0) {    // Para evitar cargar en un objeto el encabezado de la tabla
// Asociacion de los objetos "Partido" (creados anteriormente con el archivo resultados) a los objetos "Pronostico"
                for(int i=0; i<resultadosDePartidos.length;i++) {
                    if ((vectorAux[5].equals(resultadosDePartidos[i].getIdPartido())) &&
                       (vectorAux[6].equals(resultadosDePartidos[i].getIdRonda()))&&
                       (vectorAux[8].equals(resultadosDePartidos[i].getIdFase()))) {
                            informacionArchivo[contadorFila - 1] = new Pronostico(resultadosDePartidos[i], vectorAux);
                            informacionArchivo[contadorFila - 1].setPuntosPorCadaAcierto(Integer.parseInt(puntajePorPronosticoAcertado));
                            break;
                    }
                }
            }
            contadorFila++;
        }
        return informacionArchivo;
    }

    public static void pantallaDeConfiguracion (){
        System.out.println("\n**************** CONFIGURACION ****************");
        System.out.println("  Acierto = " + puntajePorPronosticoAcertado + " puntos.");
        System.out.println("  Ronda completa = " + puntajeExtraPorRonda + " puntos.  ");
        System.out.println("  Fase completa por equipo = " + puntajeExtraPorFase + " puntos.  ");
        System.out.println("***********************************************");

    }
}
