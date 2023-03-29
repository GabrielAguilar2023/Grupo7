package prodeTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tpi144g7.prode.clase.Partido;
import tpi144g7.prode.clase.Pronostico;
import tpi144g7.prode.enumeracion.ResultadoEnum;

@DisplayName("Test de la clase Pronostico")
public class PronosticoTest {

    @DisplayName("Prueba del metodo asignar puntaje de la clase Pronostico")
    @Test
    public void pronosticoAciertoGanaEquipo1 (){
        Partido partido = new Partido(new String[]{"Angola", "2", "3", "Croacia", "3", "2"});
        Pronostico pronostico = new Pronostico (partido,new String[]{"Angola","","","X","Croacia","3","2","Juan"});

        Assertions.assertEquals(pronostico.puntos(),1);
        Assertions.assertEquals(pronostico.equipoElegido.getNombre(),partido.equipo2.getNombre());
        Assertions.assertEquals(pronostico.resultado, ResultadoEnum.GANADOR);


    }
}
