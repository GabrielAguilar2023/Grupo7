package tpi144g7.prode.clase;

public class Ronda {
    private String nro;
    private Partido partidos[];

    public Ronda(String nro, Partido[] partido) {
        this.nro = nro;
        this.partidos = partido;
    }

    public Ronda(Partido[] subPart,String nro) {
        this.partidos = subPart;
        this.nro = nro;
    }

    public int puntos(){
        return 0;
//todo
    }
}
