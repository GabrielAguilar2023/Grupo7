package prode.configuracion;

import prode.clase.Partido;
import prode.clase.Pronostico;
import java.sql.*;

import static prode.configuracion.Inicializacion.*;

public class ConexionSQL {

    public static boolean conexionMySql() {
    int contadorFila = 0;
// hacer la conexion con la base de datos
        try (Connection conexion = DriverManager.getConnection(mysql_url,mysql_user, mysql_password)) {
        Statement consultaSQL = conexion.createStatement();
        ResultSet infoDevuelta = consultaSQL.executeQuery("SELECT * FROM pronosticos;");
//Determinar el numero de registros de la base de datos para dimensionar el vector
        while(infoDevuelta.next()) contadorFila+=1;
//Cerrar todos los procesos
           infoDevuelta.close();
           consultaSQL.close();
           conexion.close();
           filasDePronosticos = contadorFila;
           System.out.println("\n Base de datos con "+contadorFila +" pronosticos!");
           return true;
       }catch (SQLException e){
            System.out.println("\n Error de conexion a MySQL\n");
            return false;
       }
   }

    public static Pronostico[] cargarBaseDeDatosPronosticos(Partido[] resultadosdePartidos) throws SQLException {
        int contadorFila = 0;
        Pronostico[] informacionArchivo = new Pronostico[filasDePronosticos];
        String[] vectorAux= new String[10];
        Connection conexion = DriverManager.getConnection(mysql_url,mysql_user, mysql_password);
        Statement consultaSQL = conexion.createStatement();
        ResultSet infoDevuelta = consultaSQL.executeQuery("SELECT * FROM pronosticos;");
        while (infoDevuelta.next()){
//Carga el vector con un pronostico
            for (int i=0; i<10;i++){
                vectorAux[i]=infoDevuelta.getString(i+1);
            }
//Asociacion de los objetos Partido creados anteriormente con el archivo resultados a los objetos Pronostico"
            for(int i=0; i<resultadosdePartidos.length;i++) {
                if ((vectorAux[5].equals(resultadosdePartidos[i].getIdPartido())) && (vectorAux[6].equals(resultadosdePartidos[i].getIdRonda()))) {
                    informacionArchivo[contadorFila] = new Pronostico(resultadosdePartidos[i], vectorAux);
                    informacionArchivo[contadorFila].setPuntosPorCadaAcierto(Integer.parseInt(puntajePorPartidoGanado));
                    break;
                }
            }
            contadorFila++;
        }
        return informacionArchivo;
    }
}