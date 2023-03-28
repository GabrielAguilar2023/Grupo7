package prodeTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tpi144g7.prode.clase.Equipo;

public class EquipoTest {
    @Test
    public void testdeNombreDeEquipoAlemania (){
        Equipo EquipoTest = new Equipo("Alemania");
        Assertions.assertEquals(EquipoTest.getNombre(),"Alemania");



    }

}
