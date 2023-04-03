package tpi144g7.prode.clase;

public class Ronda {
    private String nro;
    private Partido partidos[];

    public Ronda(Partido[] PartidosDeRonda,String nro) {
        this.partidos = PartidosDeRonda;
        this.nro = nro;
    }

    public int puntos(){
for (int i=0;i<this.partidos.length;i++)
    System.out.println(this.partidos[i].getIdPartido() + " " + this.partidos[i].getIdRonda());
        return 1;
//todo
    }
}
