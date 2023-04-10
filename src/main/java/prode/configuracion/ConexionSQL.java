package prode.configuracion;

import java.sql.*;

import static prode.configuracion.Inicializacion.configuracion;

public class ConexionSQL {

   public static void conexionMySql() {

       int cantidad = 0;
       // hacer la conexion con la base de datos
       try (Connection conexion = DriverManager.getConnection(configuracion[2], configuracion[3], configuracion[4])) {

           Statement comandoEnviado = conexion.createStatement();
           ResultSet infoDevuelta = comandoEnviado.executeQuery("SELECT * FROM pronosticos;");
           while (infoDevuelta.next()) {
               String idPartido = infoDevuelta.getString(6);
               String Equipo1 = infoDevuelta.getString(1);
               String Equipo2 = infoDevuelta.getString(5);
//                cantidad +=1;
               System.out.println("  " + "Partido " + idPartido + " : " + Equipo1 + " vs " + Equipo2);
           }

           infoDevuelta.close();
           comandoEnviado.close();
       }catch (SQLException e){
           System.out.println("\n Error de conexion a MySQL\n");

       }

   }


}



