package tpi144g7.prode.clase;

import tpi144g7.prode.enumeracion.ResultadoEnum;

public class Pronostico {
    public Partido partido;
    public Equipo equipoElegido;
    public String idRonda;
    public String Participante;
    public ResultadoEnum resultado;


    // |   Equipo1   |    Gana1    |   Empata    |    Gana2    |   Equipo2   | idPartido   |   idRonda   | Participante|
    // |prediccion[0]|prediccion[1]|prediccion[2]|prediccion[3]|prediccion[4]|prediccion[5]|prediccion[6]|prediccion[7]|

    public Pronostico(Partido partidoExistente, String[] prediccion) {
        this.idRonda = prediccion[6];
        this.Participante = prediccion[7];
        this.partido = partidoExistente;
        this.equipoElegido = new Equipo("");

        // Determina quien es el ganador pronosticado (Si el equipo1 o el equipo2)
        if (prediccion[1].equals("X")) {
            this.equipoElegido.nombre = prediccion[0];
            this.resultado = ResultadoEnum.GANADOR;
        } else {
            if (prediccion[2].equals("X")) {
                this.equipoElegido.nombre = prediccion[0];
                this.resultado = ResultadoEnum.EMPATE;
            } else {
                if (prediccion[3].equals("X")) {
                    this.equipoElegido.nombre = prediccion[4];
                    this.resultado = ResultadoEnum.GANADOR;
                }
            }
        }
    }

    public int puntos() {
        if (idRonda.equals(this.partido.idRonda)) { // Verifica si es la misma ronda
            if ((this.partido.golesEquipo1) > (this.partido.golesEquipo2)) {
                if ((this.partido.equipo1.nombre.equals(this.equipoElegido.nombre)) && (this.resultado.equals(ResultadoEnum.GANADOR))) {
                    return 1;
                }
            } else {
                if ((this.partido.golesEquipo1) < (this.partido.golesEquipo2)) {
                    if ((this.partido.equipo2.nombre.equals(this.equipoElegido.nombre)) && (this.resultado.equals(ResultadoEnum.GANADOR))) {
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
}