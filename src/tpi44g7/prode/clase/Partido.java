package tpi44g7.prode.clase;

import tpi44g7.prode.enumeracion.ResultadoEnum;

public class Partido {
    Equipo equipo1;
    Equipo equipo2;
    int golesEquipo1;
    int golesEquipo2;

    public ResultadoEnum resultado (Equipo equipo) {
        return ResultadoEnum.GANADOR ;
        //todo
    }
}