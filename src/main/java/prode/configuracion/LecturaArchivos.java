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

    public static void cargarArchivoDeConfiguracion(String arg) {
       // String[] configuracion = new String[]{"1","0","","","","NO SQL"};

        try {
            Properties propiedadesDelProyecto = new Properties();
//Carga el archivo en el objeto Properties
            propiedadesDelProyecto.load(new FileReader(arg));
            configuracion[0] = propiedadesDelProyecto.getProperty("puntajePorPartidoGanado", "1");
            configuracion[1] = propiedadesDelProyecto.getProperty("puntajeExtra", "0");
            configuracion[2] = propiedadesDelProyecto.getProperty("mysql_url", "");
            configuracion[3] = propiedadesDelProyecto.getProperty("mysql_user", "");
            configuracion[4] = propiedadesDelProyecto.getProperty("mysql_password", "");
// Si no hay info de SQL entonces <<configuracion [5]>> avisa que NO es posible conectar a base de datos
            configuracion[5] = "NO SQL";
            if (!(configuracion[2].isEmpty() || configuracion[3].isEmpty() || configuracion[4].isEmpty()))
                configuracion[5] = "SI SQL";
        } catch (Exception e){  System.out.println("No hay configuracion externa cargada...");}
    }

    public static int analizarArchivos(String archivo, int numeroDeColumnasObligatorias) throws IOException {
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
        return numeroDeFilas;
    }

    public static Partido[] cargarArchivoDeResultados (String archivo)throws IOException{
        int contadorFila = 0;
        Partido[] informacionArchivo = new Partido[dimensionResultado-1];
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

    public static Pronostico[] cargarArchivoDePronosticos(String archivo, Partido[] resultadosdePartidos) throws IOException {
        int contadorFila = 0;
        Pronostico[] informacionArchivo = new Pronostico[dimensionPronosticos-1];
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


}
