package prodeTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import prode.clase.Equipo;

@DisplayName("Prueba de la clase Equipo")
public class EquipoTest {
    @DisplayName("Test de nombre de la clase Equipo")
    @Test
    public void testdeNombreDeEquipoAlemania (){
        Equipo EquipoTest = new Equipo("Alemania");
        Assertions.assertEquals(EquipoTest.getNombre(),"Alemania");
    }

}