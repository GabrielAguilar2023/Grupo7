CREATE DATABASE  IF NOT EXISTS `prode` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `prode`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: prode
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `pronosticos`
--

DROP TABLE IF EXISTS `pronosticos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pronosticos` (
  `Equipo 1` varchar(25) NOT NULL,
  `Gana 1` varchar(1) DEFAULT NULL,
  `Empata` varchar(1) DEFAULT NULL,
  `Gana 2` varchar(1) DEFAULT NULL,
  `Equipo 2` varchar(25) NOT NULL,
  `idPartido` int NOT NULL,
  `idRonda` int NOT NULL,
  `Participante` varchar(25) NOT NULL,
  `idFase` int NOT NULL,
  `idPronostico` int NOT NULL,
  PRIMARY KEY (`idPronostico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pronosticos`
--

LOCK TABLES `pronosticos` WRITE;
/*!40000 ALTER TABLE `pronosticos` DISABLE KEYS */;
INSERT INTO `pronosticos` VALUES ('Argentina','X','','','Arabia Saudita',13,1,'Mariana',1,1),('Mexico','','X','','Polonia',14,1,'Mariana',1,2),('Argentina','','X','','Mexico',15,1,'Pedro',1,3),('Polonia','','','X','Arabia Saudita',16,1,'Mariana',1,4),('Polonia','X','','','Argentina',17,1,'Mariana',1,5),('Arabia Saudita','','','X','Mexico',18,1,'Mariana',1,6),('Argentina','X','','','Arabia Saudita',13,1,'Pedro',1,7),('Mexico','','','X','Polonia',14,1,'Pedro',1,8),('Argentina','X','','','Mexico',15,1,'Mariana',1,9),('Polonia','','','X','Arabia Saudita',16,1,'Pedro',1,10),('Polonia','X','','','Argentina',17,1,'Pedro',1,11),('Arabia Saudita','','','X','Mexico',18,1,'Pedro',1,12),('Argentina','','','X','Arabia Saudita',13,1,'Jorge',1,13),('Mexico','','X','','Polonia',14,1,'Jorge',1,14),('Argentina','X','','','Mexico',15,1,'Jorge',1,15),('Polonia','X','','','Arabia Saudita',16,1,'Daniel',1,16),('Polonia','','','X','Argentina',17,1,'Jorge',1,17),('Arabia Saudita','','','X','Mexico',18,1,'Jorge',1,18),('Paises Bajos','X','','','Estados Unidos',1,1,'Gabriel',2,19),('Argentina','X','','','Australia',2,1,'Gabriel',2,20),('Inglaterra','X','','','Senegal',3,1,'Gabriel',2,21),('Francia','X','','','Polonia',4,1,'Gabriel',2,22),('Japon','','X','','Croacia',5,1,'Gabriel',2,23),('Brasil','X','','','Corea del Sur',6,1,'Gabriel',2,24),('Marruecos','','X','','Espana',7,1,'Gabriel',2,25),('Portugal','X','','','Suiza',8,1,'Gabriel',2,26);
/*!40000 ALTER TABLE `pronosticos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-16 22:59:47
