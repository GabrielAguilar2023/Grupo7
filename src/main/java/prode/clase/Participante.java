package prode.clase;

import java.util.ArrayList;

public class Participante {
    private String nombre;
    private int puntaje;
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
        for (String linea:partidosDeFase)
            if (this.listaDeAciertos.contains(linea)) aciertosDeEsteEquipoEnLaFase++;
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
        for (String filas : listaDeAciertos) {
            var vectorAuxiliar = filas.split(";");  //Separa las columnas
            if (vectorAuxiliar[1].equals(ronda) && vectorAuxiliar[2].equals(fase)) aciertos++;
        }
    return aciertos;
    }

}