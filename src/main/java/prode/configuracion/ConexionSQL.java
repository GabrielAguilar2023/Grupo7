package prode.configuracion;

import prode.clase.Partido;
import prode.clase.Pronostico;
import java.sql.*;

import static prode.configuracion.Inicializacion.*;

public class ConexionSQL {

    public static boolean conexionMySql() {
    int lineaLeida=0;
//Hacer la conexion con la base de datos
        try (Connection conexion = DriverManager.getConnection(mysql_url,mysql_user, mysql_password)) {
        Statement consultaSQL = conexion.createStatement();
        ResultSet infoDevuelta = consultaSQL.executeQuery("SELECT * FROM pronosticos;");
//Determinar el numero de registros de la base de datos para dimensionar el vector
        while(infoDevuelta.next()) {
            lineaLeida ++;
            boolean campo0 = infoDevuelta.getString(1).matches("^[A-Z][a-z]+(\\s[a-zA-Z]+(\\s[a-zA-Z]+)?)?");
            boolean campo1 = infoDevuelta.getString(2).matches("[X]?");
            boolean campo2 = infoDevuelta.getString(3).matches("[X]?");
            boolean campo3 = infoDevuelta.getString(4).matches("[X]?");
            boolean campo4 = infoDevuelta.getString(5).matches("^[A-Z][a-z]+(\\s[a-zA-Z]+(\\s[a-zA-Z]+)?)?");
            boolean campo5 = infoDevuelta.getString(6).matches("[0-9]+");
            boolean campo6 = infoDevuelta.getString(7).matches("[0-9]");
            boolean campo7 = infoDevuelta.getString(8).matches("^[A-Z][a-z]+(\\s[a-zA-Z]+(\\s[a-zA-Z]+)?)?");
            boolean campo8 = infoDevuelta.getString(9).matches("[0-9]");
            boolean campo9 = infoDevuelta.getString(10).matches("[0-9]+");
            boolean exclusividad1 = infoDevuelta.getString(2).matches("[X]");
            boolean exclusividad2 = infoDevuelta.getString(3).matches("[X]");
            boolean exclusividad3 = infoDevuelta.getString(4).matches("[X]");
// Comprueba que existe solamente un campo de pronostico de resultado seleccionado.
            var campo10 = ((exclusividad1 && !exclusividad2 && !exclusividad3)||
                                    (!exclusividad1 && exclusividad2 && !exclusividad3)||
                                    (!exclusividad1 && !exclusividad2 && exclusividad3));
            if(!(campo0 && campo1 && campo2 && campo3 && campo4 && campo5 && campo6 && campo7 && campo8 && campo9 && campo10)) {
                System.out.println("Inconsistencia en la Base de Datos: --> registro "+lineaLeida);
                infoDevuelta.close();
                consultaSQL.close();
                conexion.close();
                return false;
            }
        }
//Cerrar todos los procesos
           infoDevuelta.close();
           consultaSQL.close();
           conexion.close();
           filasDePronosticos = lineaLeida;
           System.out.println("\n     Base de datos OK! con "+lineaLeida +" pronosticos.");
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
                if ((vectorAux[5].equals(resultadosdePartidos[i].getIdPartido())) &&
                    (vectorAux[6].equals(resultadosdePartidos[i].getIdRonda())) &&
                    (vectorAux[8].equals(resultadosdePartidos[i].getIdFase()))) {
                    informacionArchivo[contadorFila] = new Pronostico(resultadosdePartidos[i], vectorAux);
                    informacionArchivo[contadorFila].setPuntosPorCadaAcierto(Integer.parseInt(puntajePorPronosticoAcertado));
                    break;
                }
            }
            contadorFila++;
        }
        return informacionArchivo;
    }
}