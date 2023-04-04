package tpi144g7.prode.clase;

public class Ronda {
    private String nro;
    private Partido partidos[];
    private boolean rondaFinalizada;

//****** CONSTRUCTOR *******
    public Ronda(Partido[] PartidosDeRonda,String nro) {
        this.partidos = PartidosDeRonda;
        this.nro = nro;
    }

    public boolean isRondaJugada() {return rondaFinalizada;}

    public void setRondaJugada(boolean rondaJugada) {this.rondaFinalizada = rondaJugada;}

    public void infoRonda() {
        if (this.rondaFinalizada) {
            System.out.println();
            System.out.println(" ----- Informacion de la " + this.nro + "º Ronda ------");
            System.out.println("Partido    Equipo1     Equipo2");
            for (int i = 0; i < this.partidos.length; i++)
                System.out.println("   " + this.partidos[i].getIdPartido() + "      " + this.partidos[i].getEquipo1().getNombre() + " vs " + this.partidos[i].getEquipo2().getNombre());
        }else{
            System.out.println();
            System.out.println("------ "+this.nro+"º Ronda no finalizada ----------");
        }
    }

}

