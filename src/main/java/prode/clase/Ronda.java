package prode.clase;

public class Ronda {
    private String nro;
    private Partido partidos[];
    private boolean rondaFinalizada;

//****** CONSTRUCTOR *******
    public Ronda(Partido[] PartidosDeRonda,String nroRonda) {
        this.partidos = PartidosDeRonda;
        this.nro = nroRonda;
    }

   // public boolean isRondaJugada() {return rondaFinalizada;}

    public void setRondaJugada(boolean rondaJugada) {this.rondaFinalizada = rondaJugada;}

    public Partido[] getPartidos() {
        return partidos;
    }

    public void infoRonda() {

        System.out.println();
        System.out.println("-------- "+this.partidos[0].getIdFase()+ "º fase "+" ------ " + this.nro + "º Ronda --------");
        System.out.println("Partido    Equipo1     Equipo2");
        for (int i = 0; i < this.partidos.length; i++)
            System.out.println("   " + this.partidos[i].getIdPartido() + "      " + this.partidos[i].getEquipo1().getNombre() + " vs " + this.partidos[i].getEquipo2().getNombre());
        if (!this.rondaFinalizada){
            System.out.println();
            System.out.println("* IMPORTANTE *  "+this.nro+"º Ronda no finalizada -");
        }
    }
}

