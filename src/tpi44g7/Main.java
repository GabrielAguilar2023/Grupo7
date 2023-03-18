package tpi44g7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException  {

        final int columnasResultados =4;
        final int columnasPronosticos =5;
        String archivoResultados = "D:\\El Ayudante\\Clases  de Java UTN\\TPI\\ArchivosEntrada\\resultados.csv";
        String archivoPronosticos = "D:\\El Ayudante\\Clases  de Java UTN\\TPI\\ArchivosEntrada\\pronosticos.csv";
        System.out.println(pasarArchivoAMatriz(archivoResultados,columnasResultados)[0][1]);
        System.out.println(pasarArchivoAMatriz(archivoPronosticos,columnasPronosticos)[2][3]);
    }

    public static String[][] pasarArchivoAMatriz(String archivo,int columnas) throws IOException {
        // Determinar numero de filas del archivo
        int numeroDeFilas = 0;
        for (String ignored : Files.readAllLines(Paths.get(archivo))) numeroDeFilas++;

        var matriz= new String[numeroDeFilas][columnas];

        // Convertir vector de texto separado por ";" en matriz de 2x2
        int contadorFila = 0;
        var vectorAux = new String[columnas];  //Vector auxiliar para cargar las columnas
        for (String texto : Files.readAllLines(Paths.get(archivo))) {
            vectorAux = texto.split(";");  //Separa las columnas de cada fila
        // Convierte vector de elementos de la fila a columnas de la matriz
            if (columnas >= 0) System.arraycopy(vectorAux, 0, matriz[contadorFila], 0, columnas);
            contadorFila++;
        }
        return matriz;
    }
}

