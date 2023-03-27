package tpi144g7.prode.clase;

public class Partido {
    public Equipo equipo1;     // Declaracion del Objeto
    public Equipo equipo2;     // Declaracion del Objeto
    public int golesEquipo1;
    public int golesEquipo2;
    public String idPartido;

    // |    Equipo1     |   Goles Eq 1   |   Goles Eq 2   |    Equipo2     |  idPartido    |
    // | datoEntrada[0] | datoEntrada[1] | datoEntrada[2] | datoEntrada[3] | prediccion[4] |

    public Partido(String [] datoEntrada) {
        this.equipo1 = new Equipo(datoEntrada[0]);   // Creacion del objeto
        this.equipo2 = new Equipo(datoEntrada[3]);   // Creacion del Objeto
        this.golesEquipo1 = Integer.parseInt(datoEntrada[1]);
        this.golesEquipo2 = Integer.parseInt(datoEntrada[2]);
        this.idPartido = datoEntrada[4];
    }

}