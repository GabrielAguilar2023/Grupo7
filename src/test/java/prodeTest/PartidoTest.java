package prodeTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tpi144g7.prode.clase.Partido;
@DisplayName("Conjunto de tests relativos a la clase Partidos")
public class PartidoTest {
    @DisplayName("Prueba de la cracion de un objeto de la clase Partido ")
    @Test
    public void partido(){
        Partido partido = new Partido(new String[]{"Angola", "2", "3", "Croacia", "3", "2"});
        Assertions.assertEquals(partido.equipo1.getNombre(),"Angola");
        Assertions.assertEquals(partido.idPartido,"3");
    }


}