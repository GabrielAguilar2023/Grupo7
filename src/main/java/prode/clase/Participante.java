package prode.clase;

import java.util.ArrayList;

public class Participante {
    private String nombre;
    private String idParticipante;
    private int puntaje;
 //   private String [][] aciertos;
    private int puntajeRonda;
    private int puntajeFase;
    private String indicacionExtra ="";
    private ArrayList <String> listaDeAciertos = new ArrayList<>(); // [0] idPartido  [1] idRonda  [2] idFase

//****** CONSTRUCTOR *******
    public Participante(String nombre) {this.nombre = nombre;}

    public void setAciertos(ArrayList <String> aciertos) {
        this.listaDeAciertos = aciertos;
    }

    public void setPuntaje(int puntaje) {this.puntaje = puntaje;}

    public void setPuntajeFase(int puntajeFase) {
        this.puntajeFase = puntajeFase;
    }

    public void setPuntajeRonda(int puntajeRonda) {this.puntajeRonda = puntajeRonda;}

    public String getNombre() {return nombre;}

    public String getIndicacionExtra() {
        return indicacionExtra;
    }

    public ArrayList<String> getListaDeAciertos() {
        return listaDeAciertos;
    }

    public int getPuntaje() {return puntaje;}

    public void sumarPuntosExtrasFase(ArrayList<String> partidosDeFase,String mostrar){
        int partidosDeLaFase = partidosDeFase.size();
        int aciertosDeEsteEquipoEnLaFase =0;
        var borrar =this.nombre;
        for (String linea:partidosDeFase) {
            if (this.listaDeAciertos.contains(linea)) {
                aciertosDeEsteEquipoEnLaFase+=1;
            }
        }
        if(aciertosDeEsteEquipoEnLaFase == partidosDeLaFase) {
            this.puntaje += puntajeFase;
            this.indicacionExtra = this.indicacionExtra + "* ";
            if(mostrar.equals("SI"))System.out.println(this.nombre +" --> PUNTOS POR FASE ");
        }
    }

    public void sumarPuntosExtrasRonda(){
        this.puntaje +=puntajeRonda;
        this.indicacionExtra= this.indicacionExtra +"x ";
    }

    public int aciertosPorRonda (String ronda,String fase){
        int aciertos=0;
//Convierte <lista> a matriz de aciertos
        String [][] matriz = new String[this.listaDeAciertos.size()][3];
        for (int i=0;i<listaDeAciertos.size();i++){
            String filas = listaDeAciertos.get(i);
            var vector= filas.split(";");  //Separa las columnas
            for (int j=0;j<matriz[0].length;j++){
                matriz[i][j]= vector[j]; // Convierte vector columna a fila de la matriz
            }
        }

        for (int i=0;i<matriz.length;i++){
            if ((matriz[i][1]).equals(ronda)&&(matriz[i][2]).equals(fase)) {
             aciertos++;
            }
        }
    return aciertos;
    }

}