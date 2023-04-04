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

    @DisplayName("Test de prueba de puntaje, de dos rondas consecutivas, pedido en la 2º entrega")
    @Test
    public void puntajeEnRondasConsecutivas(){
        Partido[] Ronda1 = new Partido[6];
        Partido[] Ronda2 = new Partido[2];

//********************************* Partidos Jugados *************************************************

//    |    Equipo1     |   Goles Eq 1   |   Goles Eq 2   |    Equipo2     |  idPartido    |    idRonda    |

//Primera Ronda
        Ronda1[0] = new Partido(new String[]{"Angola", "2", "3", "Croacia", "1", "1"});
        Ronda1[1] = new Partido(new String[]{"Costa Rica", "1", "0", "Nigeria", "2", "1"});
        Ronda1[2] = new Partido(new String[]{"Croacia", "1", "3", "Costa Rica", "3", "1"});
        Ronda1[3] = new Partido(new String[]{"Angola", "2", "2", "Nigeria", "4", "1"});
        Ronda1[4] = new Partido(new String[]{"Angola", "1", "3", "Costa Rica", "5", "1"});
        Ronda1[5] = new Partido(new String[]{"Croacia", "2", "3", "Nigeria","6","1"});
//Segunda Ronda
        Ronda2[0] = new Partido(new String[]{"Costa Rica", "2", "2", "Francia", "1", "2"});
        Ronda2[1] = new Partido(new String[]{"Francia", "1", "2", "Costa Rica", "2", "2"});

//Tercera Ronda
        Partido partidosDeTest3_1 = new Partido(new String[]{"Costa Rica", "4", "3", "Brasil", "0", "3"});
        Partido partidosDeTest3_2 = new Partido(new String[]{"Brasil", "2", "2", "Costa Rica", "1", "3"});
//Cuarta Ronda
        Partido partidosDeTest4_1 = new Partido(new String[]{"Costa Rica", "1", "3", "Argentina", "0", "4"});
        Partido partidosDeTest4_2 = new Partido(new String[]{"Argentina", "5", "2", "Costa Rica","1","4"});


//***************************** Pronostico de Juan en las 2 primeras rondas ********************************

// Apuesta de Juan de las 2 primeras rondas, es decir 8 partidos 6 de primera ronda y 2 de la segunda ronda
        Pronostico[] Juan = new Pronostico[8];
//      |   Equipo1   |    Gana1    |   Empata    |    Gana2    |   Equipo2   | idPartido   |   idRonda   | Participante|

//--------1º Ronda -------------
/*No*/        Juan [0] = new Pronostico (Ronda1[0],new String[]{"Angola","","X","","Croacia","1","1","Juan"});
/*Si*/        Juan [1] = new Pronostico (Ronda1[1],new String[]{"Costa Rica","X","","","Nigeria","2","1","Juan"});
/*Si*/        Juan [2] = new Pronostico (Ronda1[2],new String[]{"Croacia","","","X","Costa Rica","3","1","Juan"});
/*No*/        Juan [3] = new Pronostico (Ronda1[3],new String[]{"Angola","X","","","Nigeria","4","1","Juan"});
/*Si*/        Juan [4] = new Pronostico (Ronda1[4],new String[]{"Angola","","","X","Costa Rica","5","1","Juan"});
/*No*/        Juan [5] = new Pronostico (Ronda1[5],new String[]{"Croacia","","X","","Nigeria","6","1","Juan"});
//--------2º Ronda -------------
/*Si*/        Juan [6] = new Pronostico (Ronda2[0],new String[]{"Costa Rica","","X","","Francia","1","2","Juan"});
/*Si*/        Juan [7] = new Pronostico (Ronda2[1],new String[]{"Francia","","","X","Costa Rica","2","2","Juan"});

// Llama al metodo punto() de la clase "Pronostico", para cada pronostico dado por Juan.
int puntajeDeJuan=0;
        int desaciertosDeJuan=0;
        for (int i=0;i<8;i++){
            puntajeDeJuan += Juan[i].puntos();
            if (Juan[i].puntos()==0){  // No suma puntos
                desaciertosDeJuan++;
            }
        }
        Assertions.assertEquals(puntajeDeJuan,5);       // 5 aciertos --------> /*Si*/
        Assertions.assertEquals(desaciertosDeJuan,3);   // 3 desaciertos -----> /*No*/
    }
}
