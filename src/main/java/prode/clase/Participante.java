package prode.clase;

import java.util.ArrayList;

public class Participante {
    private String nombre;
    private String idParticipante;
    private int puntaje;
    private String [][] aciertos; // [0] idPartido  [1] idRonda  [2] EquipoApostado  [3] Resultado

//****** CONSTRUCTOR *******
    public Participante(String nombre) {this.nombre = nombre;}

    public void setAciertos(ArrayList <String> aciertos) {
//Convierte <lista> a matriz de aciertos
        String [][] matriz = new String[aciertos.size()][4];
        for (int i=0;i<aciertos.size();i++){
            String filas = aciertos.get(i);
            var vector= filas.split(";");  //Separa las columnas
            for (int j=0;j<matriz[0].length;j++){
                matriz[i][j]= vector[j]; // Convierte vector columna a fila de la matriz
            }
        }
        this.aciertos= matriz;
    }

    public String[][] getAciertos() {return aciertos;}

    public void setPuntaje(int puntaje) {this.puntaje = puntaje;}

    public String getNombre() {return nombre;}

    public int getPuntaje() {return puntaje;}

    public int aciertosPorRonda (String ronda){
        int aciertos=0;
        for (int i=0;i<this.aciertos.length;i++){
            if ((this.aciertos[i][1]).equals(ronda)) {
             aciertos++;
            }
        }
    return aciertos;
    }

}
