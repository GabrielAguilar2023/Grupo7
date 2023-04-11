package prode.configuracion;

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
/*        infoDevuelta = consultaSQL.executeQuery("SELECT * FROM pronosticos;");
        while (infoDevuelta.next()) {






              String idPartido = infoDevuelta.getString(6);
               String Equipo1 = infoDevuelta.getString(1);
               String Equipo2 = infoDevuelta.getString(5);
//
               System.out.println("  " + "Partido " + idPartido + " : " + Equipo1 + " vs " + Equipo2);
           }

        */
           infoDevuelta.close();
           consultaSQL.close();
           conexion.close();
           filasDePronosticos = contadorFila;
           System.out.println(contadorFila +" Registros");
           return true;
       }catch (SQLException e){
            System.out.println("\n Error de conexion a MySQL\n");
            return false;
       }

   }


}



