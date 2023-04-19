# Argentina Programa 4.0 - Desarrollador Java Inicial 2023
## Comision 144 - Grupo 7
|Integrantes|
| :-------: |
|Aguilar Gerardo Gabriel|
|De la Riva Rodolfo|
|Demichelli Amelia Clara|
|Lopez Ruben|
|Yañez Reyes Valeska Sara|

###Consideraciones Generales:
Para la configuracion se utiliza un archivo llamado 'configuracion.properties' y para los resultados
de los partidos se utiliza un archivo csv (resultados.csv) con separacion de campos por ";".
En cuanto a los datos de los pronosticos, estos pueden provenir de una base de datos MySQL o de un
archivo csv (pronosticos.csv) con separacion de campos por ";".

----
Para leer los pronosticos desde una base de datos MySQL se debe:
- Ingresar por parametro el archivo 'configuracion.properties' (Donde estan los datos de conexion a MySQL).
- El segundo parametro es el archivo 'resultados.csv'.

----
####Estructura de la entrada resultados:
|  Equipo1 | Cant.Goles 1   |  Cant. Goles 2 | Equipo 2  | idPartido  |idRonda   |idFase   |
| :-----------: | :------------: | :------------: | :------------: | :------------: | :------------: | :------------: |

####Estructura de la entrada pronosticos:
|  Equipo1 | Gana 1 | Empata | Gana 2  |Equipo2|idPartido|idRonda|Participante|idFase|idPronostico|
| :------: | :----: | :----: | :-----: | :---: | :-----: | :---: | :---------:| :---:| :---------:|

####Estructura del campeonato a considerar:
- Campeonato: formado por una o varias fases
- Fases: formadas por  una o mas rondas.
- Rondas: formadas por uno o mas partidos.
- Partidos: disputados por 2 equipos

Cada ronda termina cuando aparece un partido de la siguiente ronda o cuando el archivo de configuracion indica que el campeonato termino.

Los apostadores pueden seleccionar una unica opcion sobre el resultado de cada partido: "Ganador Equipo 1" o "Ganador equipo 2" o "Empate".

Este proyecto se desarrollo teniendo en cuenta la estructura del campeonato mundial Qatar 2022. Pero es aplicable a otros  tipos de campeonatos de formato similar.



+ Campeonato Mundial de Qatar consta de: 2 fases: 1 fase de grupos y una fase de eliminatoria.

    + 1º fase: 1 ronda  (8 grupos de 4 equipos) 48 partidos
      
    + 2º fase: 4 rondas (octavos, cuartos, semifinales y final)
        + 1ª Ronda -> 8 partidos (Octavos)
        + 2º Ronda -> 4 partidos (Cuartos)
        + 3º Ronda -> 2 partidos (Semifinales)
        + 4º Ronda -> 2 partidos (Final) y (3ºpuesto)

|Archivos de Ejemplo:|Ubicacion|
| :------------: | :------------: |
|Archivo de configuracion:|     src/main/resources/configuracion.properties|
|Archivos .csv de entrada      |src/main/resources/archivos.csv/resultados.csv|
| |src/main/resources/archivos.csv/pronosticos.csv|
|Base de datos de:| src/main/resources/baseDeDatosMySQL/prode.sql|


El archivo "configuracion.properties" debe tener los siguientes datos: key=valor

### Ejemplo de archivo configuracion.properties

-----------------------------------
    puntajeExtraPorRonda=10
    puntajeExtraPorFase=6
    puntajePorPronosticoAcertado=3
    mysql_url=jdbc:mysql://localhost:3306/prode
    mysql_user=root
    mysql_password=MiPasswordEnMySQL
    campeonatoFinalizado=SI
    infoDebug=NO
--------------------------------------------

###Ejemplo de carga de entradas:

---
    0 parametros
        + Ingresar por linea de comandos cuando lo solicite
            +   src/main/resources/configuracion.properties
            +   src/main/resources/archivos.csv/resultados.csv
            +   src/main/resources/archivos.csv/pronosticos.csv --> (o dar solamente Enter para cargar base de datos de MySQL.)
----
    1 parametros
        + parametro 0 ---> "src/main/resources/configuracion.properties"
            + Ingresar por linea de comandos cuando lo solicite
            + src/main/resources/archivos.csv/resultados.csv
            + src/main/resources/archivos.csv/pronosticos.csv
---
    2 parametros (con properties) <--------- PARA USAR DATOS DE PRONOSTICOS DESDE MySQL
        + parametro 0 --->  "src/main/resources/configuracion.properties"
        + parametro 1 --->  "src/main/resources/archivos.csv/resultados.csv"
---
    2 parametros (sin properties)
            + parametro 0 -> "src/main/resources/archivos.csv/resultados.csv"
            + parametro 1 -> "src/main/resources/archivos.csv/pronosticos.csv"
---
    3 parametros
        + parametro 0 -> "src/main/resources/configuracion.properties"
        + parametro 1 -> "src/main/resources/archivos.csv/resultados.csv"
        + parametro 2 -> "src/main/resources/archivos.csv/pronosticos.csv"
------------------------------------------------------------------------------------------------------
- La base de datos MySQL de prueba esta en la carpeta "src/main/resources/baseDeDatosMySQL/prode.sql"
------------------------------------------------------------------------------------------------------ 