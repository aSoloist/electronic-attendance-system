-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: attendance
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `eas_application`
--

DROP TABLE IF EXISTS `eas_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eas_application` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `begin_date` date NOT NULL,
  `end_date` date NOT NULL,
  `result` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `eas_application_eas_user_id_fk` (`user_id`),
  CONSTRAINT `eas_application_eas_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `eas_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eas_application`
--

LOCK TABLES `eas_application` WRITE;
/*!40000 ALTER TABLE `eas_application` DISABLE KEYS */;
/*!40000 ALTER TABLE `eas_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eas_attendance`
--

DROP TABLE IF EXISTS `eas_attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eas_attendance` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `begin_time` time NOT NULL DEFAULT '00:00:00',
  `end_time` time NOT NULL DEFAULT '24:00:00',
  `status` tinyint(4) NOT NULL,
  `now_date` date NOT NULL,
  `is_workday` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `eas_attendance_eas_user_id_fk` (`user_id`),
  CONSTRAINT `eas_attendance_eas_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `eas_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eas_attendance`
--

LOCK TABLES `eas_attendance` WRITE;
/*!40000 ALTER TABLE `eas_attendance` DISABLE KEYS */;
/*!40000 ALTER TABLE `eas_attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eas_user`
--

DROP TABLE IF EXISTS `eas_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eas_user` (
  `id` bigint(20) NOT NULL,
  `root` tinyint(4) NOT NULL DEFAULT '0',
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eas_user`
--

LOCK TABLES `eas_user` WRITE;
/*!40000 ALTER TABLE `eas_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `eas_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-23 21:24:25
