package tpi44g7.prode.clase;

import tpi44g7.prode.enumeracion.ResultadoEnum;

public class Pronostico {
    public Partido partido;
    public Equipo equipo;
    public ResultadoEnum resultado;

    // |    Equipo1    |     Gana1     |    Empata     |     Gana2     |    Equipo2    |  idPartido    |
    // | prediccion[0] | prediccion[1] | prediccion[2] | prediccion[3] | prediccion[4] | prediccion[5] |

    public Pronostico(Partido partidoExistente, String[] prediccion) {
        this.partido = partidoExistente;
        this.equipo = new Equipo("");

        // Determina quien es el ganador pronosticado (Si el equipo1 o el equipo2)
        if (prediccion[1].equals("X")) {
            this.equipo.nombre = prediccion[0];
            this.resultado = ResultadoEnum.GANADOR;
        } else {
            if (prediccion[2].equals("X")) {
                this.equipo.nombre = prediccion[0];
                this.resultado = ResultadoEnum.EMPATE;
            } else {
                if (prediccion[3].equals("X")) {
                    this.equipo.nombre = prediccion[4];
                    this.resultado = ResultadoEnum.GANADOR;
                }
            }
        }
    }

    public int puntos(){

        if ((this.partido.golesEquipo1) > (this.partido.golesEquipo2))
        {
            if ((this.partido.equipo1.nombre.equals(this.equipo.nombre)) && (this.resultado.equals(ResultadoEnum.GANADOR)))
            {
                return 1;
            } else
                {
                return 0;
                }
        } else
            {
                if ((this.partido.golesEquipo1) < (this.partido.golesEquipo2))
                {
                    if ((this.partido.equipo2.nombre.equals(this.equipo.nombre)) && (this.resultado.equals(ResultadoEnum.GANADOR))) {
                        return 1;
                    } else
                        {
                         return 0;
                        }
                }else
                   {
                      if (this.resultado.equals(ResultadoEnum.EMPATE))
                      {
                        return 1;
                      }else
                         {
                          return 0;
                         }
                   }
            }
    }
}




