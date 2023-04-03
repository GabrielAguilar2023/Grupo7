package tpi144g7.prode.clase;

import java.util.ArrayList;

public class Participante {
    private String nombre;
    private String idParticipante;
    private int puntaje;
    private ArrayList<String> aciertos; // [0] idPartido  [1] idRonda  [2] EquipoApostado  [3] Resultado

    public void setAciertos(ArrayList <String> aciertos) {
        this.aciertos= aciertos;

    }

    public ArrayList<String> getAciertos() {
        return aciertos;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public Participante(String nombre) {
        this.nombre = nombre;

    }
}
