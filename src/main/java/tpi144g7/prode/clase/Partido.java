package tpi144g7.prode.clase;

import tpi144g7.prode.enumeracion.ResultadoEnum;

public class Partido implements Comparable <Partido> { // implements Comparable para poder ordenar el vector de partidos en el main
    private Equipo equipo1;     // Declaracion del Objeto
    private Equipo equipo2;     // Declaracion del Objeto
    private int golesEquipo1;
    private int golesEquipo2;
    private String idPartido;
    private String idRonda;

    // Archivo resultados.csv:
    // |    Equipo1     |   Goles Eq 1   |   Goles Eq 2   |    Equipo2     |  idPartido    |    idRonda    |
    // | datoEntrada[0] | datoEntrada[1] | datoEntrada[2] | datoEntrada[3] | prediccion[4] | prediccion[5] |

    public Partido(String [] datoEntrada) {
        this.equipo1 = new Equipo(datoEntrada[0]);   // Creacion del objeto
        this.equipo2 = new Equipo(datoEntrada[3]);   // Creacion del Objeto
        this.golesEquipo1 = Integer.parseInt(datoEntrada[1]);
        this.golesEquipo2 = Integer.parseInt(datoEntrada[2]);
        this.idPartido = datoEntrada[4];
        this.idRonda = datoEntrada[5];
    }
    public ResultadoEnum resultado (Equipo equipoElegido){
        return ResultadoEnum.GANADOR;
    }
    @Override // Necesario para ordenar los array de tipo partido
    public int compareTo(Partido ordenar) {
    return this.idRonda.compareTo(ordenar.getIdRonda());
    }

//** Geters**

    public Equipo getEquipo2() {
        return equipo2;
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public int getGolesEquipo1() {
        return golesEquipo1;
    }

    public String getIdPartido() {
        return idPartido;
    }

    public int getGolesEquipo2() {
        return golesEquipo2;
    }

    public String getIdRonda() {
        return idRonda;
    }

}