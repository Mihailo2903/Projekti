CREATE DATABASE  IF NOT EXISTS `podsistem2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `podsistem2`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem2
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `transakcija`
--

DROP TABLE IF EXISTS `transakcija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transakcija` (
  `IdTransakcija` int NOT NULL AUTO_INCREMENT,
  `IdRacun` int NOT NULL,
  `RedniBroj` int NOT NULL,
  `DatumVreme` datetime NOT NULL,
  `Iznos` decimal(10,2) NOT NULL,
  `Svrha` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`IdTransakcija`),
  KEY `FK_Transakcija_IdRacun_idx` (`IdRacun`),
  CONSTRAINT `FK_Transakcija_IdRacun` FOREIGN KEY (`IdRacun`) REFERENCES `racun` (`IdRacun`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transakcija`
--

LOCK TABLES `transakcija` WRITE;
/*!40000 ALTER TABLE `transakcija` DISABLE KEYS */;
INSERT INTO `transakcija` VALUES (1,2,1,'2022-02-11 10:03:46',10000.00,'uplata'),(2,3,1,'2022-02-12 18:12:44',20000.00,'isplata'),(3,4,1,'2022-02-13 13:11:27',15000.00,'uplata'),(4,2,2,'2022-02-13 14:22:59',30000.00,'uplata'),(5,1,1,'2022-02-14 10:11:55',10000.00,'isplata'),(6,5,1,'2022-02-14 22:31:12',20000.00,'prenos'),(7,3,2,'2022-02-14 23:11:23',50000.00,'prenos'),(8,9,1,'2022-02-08 13:12:36',200.00,'mosa'),(9,10,2,'2022-02-08 13:17:39',200.00,'opl'),(10,9,3,'2022-02-08 13:21:59',0.00,'ww'),(11,9,4,'2022-02-08 19:24:13',500.00,'ff'),(12,9,5,'2022-02-08 19:27:31',1400.00,'ss'),(13,10,4,'2022-02-08 19:38:08',2000.00,'ss'),(14,10,5,'2022-02-08 19:59:50',5000.00,'ss'),(15,9,6,'2022-02-08 20:00:49',3000.00,'ss'),(16,9,7,'2022-02-08 20:02:18',100.00,'ssss'),(17,9,8,'2022-02-10 20:14:57',10000.00,'sss'),(18,9,9,'2022-02-10 20:15:57',5000.00,'sss'),(19,9,10,'2022-02-10 20:21:53',1000.00,'sss'),(20,11,1,'2022-02-13 17:59:28',5000.00,'sasasas'),(21,12,1,'2022-02-13 18:00:12',5000.00,'aaaa'),(22,11,2,'2022-02-13 18:00:25',2000.00,'dcdcc');
/*!40000 ALTER TABLE `transakcija` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-13 20:08:58
