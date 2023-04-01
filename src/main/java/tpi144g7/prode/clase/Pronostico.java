package tpi144g7.prode.clase;

import tpi144g7.prode.enumeracion.ResultadoEnum;

public class Pronostico {
    private Partido partido;
    private Equipo equipo;
    private String idRonda;
    private String Participante;
    private ResultadoEnum resultado;


    // |   Equipo1   |    Gana1    |   Empata    |    Gana2    |   Equipo2   | idPartido   |   idRonda   | Participante|
    // |prediccion[0]|prediccion[1]|prediccion[2]|prediccion[3]|prediccion[4]|prediccion[5]|prediccion[6]|prediccion[7]|

    public Pronostico(Partido partidoExistente, String[] prediccion) {
        this.idRonda = prediccion[6];
        this.Participante = prediccion[7];
        this.partido = partidoExistente;
        this.equipo = new Equipo("");

        // Determina quien es el ganador pronosticado (Si el equipo1 o el equipo2)
        if (prediccion[1].equals("X")) {
            this.equipo.setNombre(prediccion[0]);
            this.resultado = ResultadoEnum.GANADOR;
        } else {
            if (prediccion[2].equals("X")) {
                this.equipo.setNombre(prediccion[0]);
                this.resultado = ResultadoEnum.EMPATE;
            } else {
                if (prediccion[3].equals("X")) {
                    this.equipo.setNombre(prediccion[4]);
                    this.resultado = ResultadoEnum.GANADOR;
                }
            }
        }
    }

    public int puntos() {
        if (idRonda.equals(this.partido.getIdRonda())) { // Verifica si es la misma ronda
            if ((this.partido.getGolesEquipo1()) > (this.partido.getGolesEquipo2())) {
                if ((this.partido.getEquipo1().getNombre().equals(this.equipo.getNombre())) && (this.resultado.equals(ResultadoEnum.GANADOR))) {
                    return 1;
                }
            } else {
                if ((this.partido.getGolesEquipo1()) < (this.partido.getGolesEquipo2())) {
                    if ((this.partido.getEquipo2().getNombre().equals(this.equipo.getNombre())) && (this.resultado.equals(ResultadoEnum.GANADOR))) {
                        return 1;
                    }
                } else {
                    if (this.resultado.equals(ResultadoEnum.EMPATE)) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

//** Getters **

    public Partido getPartido() {
        return partido;
    }

    public ResultadoEnum getResultado() {
        return resultado;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public String getIdRonda() {
        return idRonda;
    }

    public String getParticipante() {
        return Participante;
    }
}