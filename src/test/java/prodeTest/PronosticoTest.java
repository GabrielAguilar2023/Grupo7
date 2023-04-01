package prodeTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tpi144g7.prode.clase.Partido;
import tpi144g7.prode.clase.Pronostico;
import tpi144g7.prode.enumeracion.ResultadoEnum;

@DisplayName("Test de la clase Pronostico")
public class PronosticoTest {

    @DisplayName("Pronostico acertado: Gana Equipo 1 y se asigna puntaje")
    @Test
    public void pronosticoAciertoGanaEquipo1 (){
        Partido partido = new Partido(new String[]{"Angola", "2", "3", "Croacia", "3", "2"});
        Pronostico pronostico = new Pronostico (partido,new String[]{"Angola","","","X","Croacia","3","2","Juan"});

        Assertions.assertEquals(pronostico.puntos(),1);
        Assertions.assertEquals(pronostico.getEquipo().getNombre(),partido.getEquipo2().getNombre());
        Assertions.assertEquals(pronostico.getResultado(), ResultadoEnum.GANADOR);
    }
}
