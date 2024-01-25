CREATE DATABASE  IF NOT EXISTS `forum` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `forum`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: forum
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `bookmark`
--

DROP TABLE IF EXISTS `bookmark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookmark` (
  `user_id` int NOT NULL,
  `message_id` int NOT NULL,
  KEY `fk_bookmark_user1_idx` (`user_id`),
  KEY `fk_bookmark_message1_idx` (`message_id`),
  CONSTRAINT `fk_bookmark_message1` FOREIGN KEY (`message_id`) REFERENCES `message` (`idmessage`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_bookmark_user1` FOREIGN KEY (`user_id`) REFERENCES `user_data` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookmark`
--

LOCK TABLES `bookmark` WRITE;
/*!40000 ALTER TABLE `bookmark` DISABLE KEYS */;
/*!40000 ALTER TABLE `bookmark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `idcategory` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `description` varchar(90) COLLATE utf8mb3_bin NOT NULL,
  `url` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`idcategory`),
  UNIQUE KEY `url_UNIQUE` (`url`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Filmy i Seriale','Pochwal się co teraz oglądasz','filmy-i-seriale'),(2,'Kraby w Grach','grab na telebimie','kraby-w-grach'),(3,'Kuchnia','mniam mniam mniam','kuchnia'),(4,'Literatura','Kategoria poświęcona literaturze','literatura'),(5,'Muzyka i Sztuka itp','Tutaj może porozmawiać z innymi o wszelkich utworach i dziełachs&#13;&#10;s&#13;&#10;','muzyka-i-sztuka-itp'),(6,'Nauka i Technologia','Skomplikowane rzeczy','nauka-i-technologia'),(7,'Podróże','Do odległych krain','podróże'),(8,'sasannn','ad','sasannn'),(12,'kykykyksusua','ads','kykykyksusua'),(13,'megussykl','susses','megussykl'),(14,'sdsadas123','asdsad','sdsadas123'),(17,'adminasss$','admin','adminasss$'),(18,'admin','admin','admin');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `idmessage` int NOT NULL AUTO_INCREMENT,
  `creation_date` datetime NOT NULL,
  `content` varchar(180) COLLATE utf8mb3_bin NOT NULL,
  `thread_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`idmessage`),
  KEY `fk_message_thread1_idx` (`thread_id`),
  KEY `fk_message_user1_idx` (`user_id`),
  CONSTRAINT `fk_message_thread1` FOREIGN KEY (`thread_id`) REFERENCES `thread` (`idthread`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_message_user1` FOREIGN KEY (`user_id`) REFERENCES `user_data` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=224 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (88,'2023-06-26 20:57:29','123',63,2),(89,'2023-06-27 08:49:26','uśmiech',64,2),(90,'2023-06-27 08:49:59','uśmiech',65,2),(91,'2023-06-27 08:53:35','fdsfsd',66,2),(92,'2023-06-27 08:54:01','pop',67,2),(93,'2023-06-27 08:56:32','Bruh',46,2),(94,'2023-06-27 08:57:04','ziom musimy zrabowć tego chłopa',68,2),(95,'2023-06-27 08:57:50','asda',69,2),(96,'2023-06-27 08:59:32','sadge',70,2),(97,'2023-06-27 09:01:50','das',71,2),(98,'2023-06-27 09:05:06','123',72,2),(99,'2023-06-27 09:11:02','sad',73,2),(100,'2023-06-27 09:12:28','c',74,2),(101,'2023-06-27 09:12:38','sda',75,2),(102,'2023-06-27 09:13:36','3',76,2),(103,'2023-06-27 09:13:50','3',77,2),(104,'2023-06-27 09:18:47','222',78,2),(105,'2023-06-27 09:23:43','11',79,2),(106,'2023-06-27 09:23:57','222',80,2),(107,'2023-06-27 09:43:59','222',81,2),(108,'2023-06-27 09:44:57','dsa',53,2),(109,'2023-06-27 09:45:03','sad',53,2),(110,'2023-06-27 09:45:05','sda',53,2),(111,'2023-06-27 09:45:56','dsa',53,2),(112,'2023-06-27 09:46:06','ds',53,2),(125,'2023-06-27 10:49:54','3213',40,2),(126,'2023-06-27 10:49:56','1',40,2),(127,'2023-06-27 10:49:57','31',40,2),(128,'2023-06-27 10:49:59','2',40,1),(129,'2023-06-27 10:50:01','fs',40,1),(130,'2023-06-27 10:50:04','http://localhost/projekt/public/thread/40',40,1),(131,'2023-06-27 10:50:10','fs',40,1),(132,'2023-06-27 10:50:12','fs',40,1),(133,'2023-06-27 10:50:14','fs',40,1),(134,'2023-06-27 10:50:15','fs',40,1),(136,'2023-06-27 10:50:18','fs',40,1),(137,'2023-06-27 10:50:23','as',40,2),(138,'2023-06-27 10:50:26','as',40,2),(139,'2023-06-27 12:37:35','a',85,1),(140,'2023-06-27 12:37:40','a',86,1),(141,'2023-06-27 12:37:42','a',87,1),(142,'2023-06-27 12:37:44','a',88,1),(143,'2023-06-27 12:37:46','a',89,1),(144,'2023-06-27 12:37:47','a',90,1),(145,'2023-06-27 12:37:49','a',91,1),(146,'2023-06-27 12:37:51','a',92,1),(147,'2023-06-27 12:38:03','a',93,1),(148,'2023-06-27 12:38:07','a&#13;&#10;',93,1),(149,'2023-06-27 12:38:10','a',94,1),(150,'2023-06-27 12:38:17','a',95,1),(151,'2023-06-27 12:38:21','a',96,1),(152,'2023-06-27 12:38:23','a',97,1),(153,'2023-06-27 12:38:25','a',98,1),(154,'2023-06-27 12:38:26','a',99,1),(155,'2023-06-27 12:38:27','a',100,1),(156,'2023-06-27 12:38:28','a',101,1),(157,'2023-06-27 12:38:28','a',101,1),(158,'2023-06-27 12:38:29','a',103,1),(159,'2023-06-27 12:38:44','d',104,1),(160,'2023-06-27 12:38:46','d',105,1),(161,'2023-06-27 12:38:48','d',106,1),(162,'2023-06-27 12:39:01','sdf',107,1),(163,'2023-06-27 12:39:22','f',108,1),(164,'2023-06-27 17:52:16','bla',109,1),(165,'2023-09-13 12:56:12','sadsad',52,1),(166,'2023-09-13 14:37:09','sdaaaaaaaaaaaaaaaaaaaaaaaaaaa',48,3),(167,'2023-10-09 13:15:32','s',80,2),(168,'2023-10-09 13:15:45','123',80,2),(169,'2023-10-09 13:22:43','co nie ',52,2),(170,'2023-10-09 13:35:56','co tak',52,2),(171,'2023-10-09 13:56:14','1',52,2),(172,'2023-10-09 14:02:14','123',52,2),(173,'2023-10-09 14:07:31','3',52,2),(174,'2023-10-09 14:31:16','amo',52,2),(175,'2023-10-12 15:45:27','1234',52,2),(176,'2023-10-14 11:58:05','212',40,2),(177,'2023-10-14 11:59:50','test',40,2),(178,'2023-10-14 11:59:53','test ',40,2),(179,'2023-10-14 11:59:58','tesafsafdsaddf',40,2),(180,'2023-10-14 12:00:00','1',40,2),(181,'2023-10-14 12:00:01','2',40,2),(182,'2023-10-14 12:00:15','3',40,2),(183,'2023-10-14 12:00:17','4',40,2),(184,'2023-10-14 12:00:19','5',40,2),(185,'2023-10-14 12:00:22','6',40,2),(186,'2023-10-14 12:00:24','7',40,2),(187,'2023-10-14 12:00:27','8',40,2),(188,'2023-10-14 12:00:29','9',40,2),(189,'2023-10-14 12:00:32','10',40,2),(190,'2023-10-14 12:00:42','11',40,2),(191,'2023-10-14 12:00:45','12',40,2),(192,'2023-10-14 12:00:47','13',40,2),(193,'2023-10-14 12:00:53','14',40,2),(194,'2023-10-14 12:00:57','15',40,2),(195,'2023-10-16 11:36:49','1',40,2),(196,'2023-10-16 11:43:36','jiop',40,2),(197,'2023-10-16 11:45:16','jiop',40,2),(198,'2023-10-16 11:48:18','123',40,2),(199,'2023-10-16 11:48:35','123',40,2),(200,'2023-10-16 11:50:49','1',40,2),(201,'2023-10-16 12:14:00','zupa',40,2),(202,'2023-10-16 12:16:45','zupa2',40,2),(203,'2023-10-16 12:21:40','zupa3',40,2),(204,'2023-10-16 12:24:15','zupa4',40,2),(205,'2023-10-16 12:24:21','zupa5',40,2),(206,'2023-10-21 11:18:12','sadsadasd',26,31),(207,'2023-10-27 09:56:39','sadfdsafasf',42,2),(208,'2023-10-27 09:57:38','0980',52,2),(209,'2023-10-27 09:59:51','1. 21adwd',47,2),(210,'2023-10-27 10:00:31','dsfffffffffffff',17,2),(211,'2023-10-27 10:02:25','0-k',12,2),(212,'2023-10-27 10:08:56','ohkklj',32,2),(213,'2023-10-27 10:10:02','su',40,2),(214,'2023-10-27 10:15:51','ads',27,2),(215,'2023-10-27 10:29:47','312222222222',22,2),(216,'2023-10-27 10:32:04','fsaf',7,2),(217,'2023-10-27 15:02:57','ads',7,2),(218,'2023-11-04 17:36:13','asdf',110,1),(221,'2023-12-09 15:09:02','hhio',26,52),(222,'2023-12-09 15:30:25','duuuude',110,1),(223,'2023-12-18 13:56:48','sad',40,2);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `private_message`
--

DROP TABLE IF EXISTS `private_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `private_message` (
  `idmessage` int NOT NULL AUTO_INCREMENT,
  `creation_date` datetime NOT NULL,
  `content` varchar(180) COLLATE utf8mb3_bin NOT NULL,
  `sender_id` int NOT NULL,
  `receiver_id` int NOT NULL,
  PRIMARY KEY (`idmessage`),
  KEY `fk_message_user1_idx` (`sender_id`),
  KEY `fk_private_message_user1_idx` (`receiver_id`),
  CONSTRAINT `fk_message_user10` FOREIGN KEY (`sender_id`) REFERENCES `user_data` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_private_message_user1` FOREIGN KEY (`receiver_id`) REFERENCES `user_data` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `private_message`
--

LOCK TABLES `private_message` WRITE;
/*!40000 ALTER TABLE `private_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `private_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `roleID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`roleID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_admin'),(2,'ROLE_user');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread`
--

DROP TABLE IF EXISTS `thread`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thread` (
  `idthread` int NOT NULL AUTO_INCREMENT,
  `topic` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `creation_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `category_id` int NOT NULL,
  `message_count` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`idthread`),
  KEY `fk_thread_category1_idx` (`category_id`),
  KEY `fk_thread_user1_idx` (`user_id`),
  CONSTRAINT `fk_thread_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`idcategory`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_thread_user1` FOREIGN KEY (`user_id`) REFERENCES `user_data` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread`
--

LOCK TABLES `thread` WRITE;
/*!40000 ALTER TABLE `thread` DISABLE KEYS */;
INSERT INTO `thread` VALUES (1,'Gloryhammer nowy album','2023-06-17 14:13:18','2023-06-17 14:13:18',5,0,2),(2,'Co sądzicie o breakcore, czy można jeszcze na','2023-06-18 19:28:31','2023-06-18 19:28:31',5,0,1),(3,'Najnowszy singiel Arctic Monkeys','2023-06-19 10:45:22','2023-06-19 10:45:22',5,0,3),(4,'Ostatnia wystawa malarstwa abstrakcyjnego','2023-06-19 16:57:10','2023-06-19 16:57:10',5,0,4),(5,'Recital pianistyczny Chopina','2023-06-19 20:12:45','2023-06-19 20:12:45',5,0,5),(6,'Najlepsze miejsca do zwiedzania w Paryżu','2023-06-20 09:23:14','2023-06-20 09:23:14',7,0,2),(7,'Filmowe premiery tego miesiąca','2023-06-20 14:45:29','2023-10-27 15:02:57',1,2,1),(8,'Co sądzicie o teatrze eksperymentalnym?','2023-06-21 11:36:08','2023-06-21 11:36:08',5,0,3),(9,'Najlepsze książki fantasy ostatnich lat','2023-06-21 18:09:52','2023-06-21 18:09:52',4,0,4),(10,'Najnowszy album Ed Sheeran','2023-06-22 15:28:07','2023-06-22 15:28:07',5,0,5),(11,'Najlepsze plaże na Costa Brava','2023-06-23 10:55:41','2023-06-23 10:55:41',7,0,2),(12,'Nowy sezon serialu \"The Witcher\"','2023-06-23 16:40:19','2023-06-23 16:40:19',1,1,1),(14,'Najlepsze powieści kryminalne','2023-06-24 19:57:34','2023-06-24 19:57:34',4,0,4),(15,'Koncert Beyoncé w Madison Square Garden','2023-06-25 17:30:21','2023-06-25 17:30:21',5,0,5),(16,'Najpiękniejsze zamki w Europie','2023-06-26 12:48:56','2023-06-26 12:48:56',7,0,2),(17,'Najlepsze seriale dramatyczne','2023-06-26 18:22:42','2023-06-26 18:22:42',1,1,1),(18,'Wystawa sztuki współczesnej w Muzeum Guggenhe','2023-06-27 15:39:18','2023-06-27 15:39:18',5,0,3),(19,'Najlepsze powieści science fiction','2023-06-27 21:11:59','2023-06-27 21:11:59',4,0,4),(20,'Koncert Coldplay w Londynie','2023-06-28 17:56:33','2023-06-28 17:56:33',5,0,5),(21,'Wyprawa w góry Alpy','2023-06-29 14:27:19','2023-06-29 14:27:19',7,0,2),(22,'Nowy sezon serialu \"Breaking Bad\"','2023-06-29 20:08:45','2023-06-29 20:08:45',1,1,1),(23,'Wystawa fotografii Anselma Adamsa','2023-06-30 16:40:22','2023-06-30 16:40:22',5,0,3),(24,'Najlepsze thrillery psychologiczne','2023-06-30 22:15:09','2023-06-30 22:15:09',4,0,4),(25,'Koncert Taylor Swift w Staples Center','2023-07-01 19:08:37','2023-07-01 19:08:37',5,0,5),(26,'Najlepsze przepisy kulinarne z całego świata','2023-07-02 15:51:24','2023-12-09 15:09:02',3,2,2),(27,'Ranking najlepszych filmów komediowych','2023-07-02 21:37:59','2023-07-02 21:37:59',1,1,1),(28,'Wystawa malarstwa renesansowego w Luwrze','2023-07-03 17:59:08','2023-07-03 17:59:08',5,0,3),(29,'Najlepsze powieści romantyczne','2023-07-03 23:44:56','2023-07-03 23:44:56',4,0,4),(31,'Najpiękniejsze plaże na Hawajach','2023-07-05 16:28:47','2023-07-05 16:28:47',7,0,2),(32,'Nowy sezon serialu \"Friends\"','2023-07-05 22:13:14','2023-07-05 22:13:14',1,1,1),(36,'Najlepsze miejsca na kemping','2023-07-02 15:40:55','2023-07-02 15:40:55',7,0,2),(38,'Nowa wystawa w Muzeum Historii Naturalnej','2023-07-03 18:30:41','2023-07-03 18:30:41',6,0,3),(39,'Najlepsze powieści romantyczne','2023-07-03 23:59:16','2023-07-03 23:59:16',4,0,4),(40,'Koncert Queen + Adam Lambert','2023-07-04 20:45:29','2023-12-18 13:56:48',5,45,5),(41,'Najlepsze miejsca na nurkowanie','2023-07-05 17:30:52','2023-07-05 17:30:52',7,0,2),(42,'Najlepsze seriale fantasy','2023-07-05 23:08:14','2023-07-05 23:08:14',1,1,1),(46,'Najlepsze restauracje w Rzymie','2023-07-02 16:53:15','2023-06-27 08:56:32',3,1,2),(47,'Ranking najlepszych filmów komediowych','2023-07-02 22:41:09','2023-07-02 22:41:09',1,1,1),(48,'Wystawa rzeźby nowoczesnej w Tate Modern','2023-07-03 18:29:56','2023-07-03 18:29:56',5,1,3),(49,'Najlepsze powieści romantyczne','2023-07-03 23:57:41','2023-07-03 23:57:41',4,0,4),(51,'Najlepsze miejsca do nurkowania na Karaibach','2023-07-05 17:48:19','2023-07-05 17:48:19',7,0,2),(52,'Nowy sezon serialu \"Friends\"','2023-07-05 23:35:06','2023-07-05 23:35:06',1,9,1),(53,'Wystawa malarstwa impresjonistycznego','2023-07-06 19:22:53','2023-06-27 09:46:06',5,5,3),(56,'Witam wszystkich','2023-06-26 20:43:47','2023-06-26 20:43:47',5,1,1),(58,'Witam wszystkich','2023-06-26 20:46:24','2023-06-26 20:46:24',5,1,1),(59,'Witam','2023-06-26 20:48:13','2023-06-26 20:48:13',5,1,2),(60,'Witam','2023-06-26 20:48:26','2023-06-26 20:48:26',5,1,2),(61,'nowy watek','2023-06-26 20:50:01','2023-06-26 20:50:01',5,1,2),(62,'nowy watek','2023-06-26 20:56:21','2023-06-26 20:56:21',5,1,2),(63,'nowy watek','2023-06-26 20:57:29','2023-06-26 20:57:29',5,1,2),(64,'Siemka','2023-06-27 08:49:26','2023-06-27 08:49:26',5,1,2),(65,'Siemka','2023-06-27 08:49:59','2023-06-27 08:49:59',5,1,2),(66,'asdsa','2023-06-27 08:53:35','2023-06-27 08:53:35',5,1,2),(67,'lbkbj','2023-06-27 08:54:01','2023-06-27 08:54:01',5,1,2),(68,'Ja rabuje człowieka','2023-06-27 08:57:04','2023-06-27 08:57:04',3,1,2),(69,'dasd','2023-06-27 08:57:50','2023-06-27 08:57:50',3,1,2),(70,'dead by dawn','2023-06-27 08:59:32','2023-06-27 08:59:32',5,1,2),(71,'dasd','2023-06-27 09:01:50','2023-06-27 09:01:50',5,1,2),(72,'123','2023-06-27 09:05:06','2023-06-27 09:05:06',3,1,2),(73,'dsadas','2023-06-27 09:11:02','2023-06-27 09:11:02',3,1,2),(74,'w','2023-06-27 09:12:28','2023-06-27 09:12:28',3,1,2),(75,'asd','2023-06-27 09:12:38','2023-06-27 09:12:38',3,1,2),(76,'132','2023-06-27 09:13:36','2023-06-27 09:13:36',3,1,2),(77,'132','2023-06-27 09:13:50','2023-06-27 09:13:50',3,1,2),(78,'111','2023-06-27 09:18:47','2023-06-27 09:18:47',3,1,2),(79,'21','2023-06-27 09:23:43','2023-06-27 09:23:43',3,1,2),(80,'111a','2023-06-27 09:23:57','2023-06-27 09:23:57',3,3,2),(81,'111a','2023-06-27 09:43:59','2023-06-27 09:43:59',3,1,2),(85,'a','2023-06-27 12:37:35','2023-06-27 12:37:35',4,1,1),(86,'a','2023-06-27 12:37:40','2023-06-27 12:37:40',4,1,1),(87,'a','2023-06-27 12:37:42','2023-06-27 12:37:42',4,1,1),(88,'a','2023-06-27 12:37:44','2023-06-27 12:37:44',4,1,1),(89,'a','2023-06-27 12:37:46','2023-06-27 12:37:46',4,1,1),(90,'a','2023-06-27 12:37:47','2023-06-27 12:37:47',4,1,1),(91,'a','2023-06-27 12:37:49','2023-06-27 12:37:49',4,1,1),(92,'a','2023-06-27 12:37:51','2023-06-27 12:37:51',4,1,1),(93,'a','2023-06-27 12:38:03','2023-06-27 12:38:07',4,2,1),(94,'a','2023-06-27 12:38:10','2023-06-27 12:38:10',4,1,1),(95,'a','2023-06-27 12:38:17','2023-06-27 12:38:17',4,1,1),(96,'a','2023-06-27 12:38:21','2023-06-27 12:38:21',4,1,1),(97,'a','2023-06-27 12:38:23','2023-06-27 12:38:23',4,1,1),(98,'a','2023-06-27 12:38:25','2023-06-27 12:38:25',4,1,1),(99,'a','2023-06-27 12:38:26','2023-06-27 12:38:26',4,1,1),(100,'a','2023-06-27 12:38:27','2023-06-27 12:38:27',4,1,1),(101,'a','2023-06-27 12:38:28','2023-06-27 12:38:28',4,1,1),(102,'a','2023-06-27 12:38:28','2023-06-27 12:38:28',4,1,1),(103,'a','2023-06-27 12:38:29','2023-06-27 12:38:29',4,1,1),(104,'fsd','2023-06-27 12:38:44','2023-06-27 12:38:44',4,1,1),(105,'fsd','2023-06-27 12:38:46','2023-06-27 12:38:46',4,1,1),(106,'fsd','2023-06-27 12:38:48','2023-06-27 12:38:48',4,1,1),(107,'d','2023-06-27 12:39:01','2023-06-27 12:39:01',4,1,1),(108,'fds','2023-06-27 12:39:22','2023-06-27 12:39:22',4,1,1),(109,'Bla bla bla','2023-06-27 17:52:16','2023-06-27 17:52:16',1,1,1),(110,'fdsaf','2023-11-04 17:34:32','2023-12-09 15:30:25',2,2,1);
/*!40000 ALTER TABLE `thread` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_data`
--

DROP TABLE IF EXISTS `user_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_data` (
  `iduser` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8mb3_bin NOT NULL,
  `password` varchar(80) COLLATE utf8mb3_bin NOT NULL,
  `email` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `creation_data` datetime NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `username_2` (`username`,`email`),
  UNIQUE KEY `email` (`email`),
  KEY `role_id_fk_idx` (`role_id`),
  CONSTRAINT `role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`roleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_data`
--

LOCK TABLES `user_data` WRITE;
/*!40000 ALTER TABLE `user_data` DISABLE KEYS */;
INSERT INTO `user_data` VALUES (1,'admin','{bcrypt}$2a$10$WsDUX7Sg0MeWEVC65BB4Iu9SecNOOyjjSlXRMI0OCzAq8O5FeQUEy','admin@interia.pl','2023-05-21 13:00:28',1),(2,'Krisent','{bcrypt}$2a$10$PtVYV94tcpVqiNodV5Q5NeRd8BOgJhpP1/SRC/af/9xbuGiLiVC2G','krisent@gmail.com','2023-05-21 13:01:37',2),(3,'Wilbur Redpot','{bcrypt}$2a$10$psz4ysTEIbN5MSKeOS07geQ6IJu2WEy7GLFvJtbBIh/FunIVStajG','will@gmail.com','2023-05-21 13:00:28',2),(4,'FannybAws','{bcrypt}$2a$10$YXWPv3HvgnBY3.vXQwFj9esBp.y85JuF8PkFDE18TI3.y/cofTU4S','yarharhar@interia.pl','2023-05-21 13:00:28',2),(5,'sus69','{bcrypt}$2a$10$WJlLIIIEo5pitERBm1P3L.pDqnb7FOgf8SVg0jsp.rnvD7wGO4ZFe','sus@gmail.com','2023-06-19 15:32:07',2),(26,'kolejny','{bcrypt}$2a$10$7Pg43hO7HdM5j1kElENsj.qEXGnyuPORvx9JacO87aQGK.N8NtsYu','kolejny@gmail.com','2023-05-21 13:00:28',2),(30,'dsadsa','{bcrypt}$2a$10$.GBRd1ERxWtQ08pJQ7DmmOXTTG6zcETOHvGyLxKRkP7beljgUzd66','assdmin@interia.pl','2023-06-19 15:15:08',2),(31,'sdasadsładsa','{bcrypt}$2a$10$hOPPKoRi8mWvI8TxQAM5MeIRfa7AouRPw/qDlg674841ubTcvuK8W','sa@ds.pl','2023-06-19 15:28:23',2),(33,'Sir Roland The Wise','{bcrypt}$2a$10$CS4EX30YI0GreyvNxMKOf.jUq83mBg2MHD/t6hTxqFa43IPxMjsH6','roland@onet.pl','2023-06-22 17:06:20',2),(34,'kot2','{bcrypt}$2a$10$E4pmGv7R3MTNtb8YKWLege3ILe0YpEfrSuhl2k2a9KI.WZd1sWhXq','kot2@gmail.com','2023-11-10 00:00:00',2),(35,'kot3','{bcrypt}$2a$10$E0G.HYZ6NKRmidV.GunTVu.SQwRX/6zUfhPHOSJgURQZG93oboKIu','kot3@gmail.com','2023-11-10 00:00:00',2),(38,'kot6','{bcrypt}$2a$10$oZeeVqQeXLWrwQpnJ1O9qOwsT5v..Av19GCV7jYBaAmYgRKmHLOle','kot6@gmail.com','2023-12-03 00:00:00',2),(39,'kot7','{bcrypt}$2a$10$Uadu7/mhhmNKz4Fd6sWO1eJjk5qdoeiycELgQElEInpi9cZDk/mUa','kot7@gmail.com','2023-12-03 00:00:00',2),(40,'kot8','{bcrypt}$2a$10$3/tFsUvN44NPsY6S8TC7/Og5YD4ZqCfooKoSbpgeYle.vJJ5nj4Xy','kot8@gmail.com','2023-12-03 00:00:00',2),(41,'kot9','{bcrypt}$2a$10$6dW1h3wI0mo9bnxt94j4TedNseGsfdzy3j4A3K.F1oTJO7yXtbL5m','kot9@gmail.com','2023-12-03 00:00:00',2),(42,'kot10','{bcrypt}$2a$10$qYV24JmyxuwAfvrlIiegJO7ZRs1HvV2CsDDdcPeI5M1EO202f2l/G','kot10@gmail.com','2023-12-03 00:00:00',2),(44,'kot11','{bcrypt}$2a$10$FKFpR2ZeNaTFXdU63oxQZOglT.4FvScuOZY0VUkaZlU65CqMt0tAG','kot11@gmail.com','2023-12-03 00:00:00',2),(45,'kot12','{bcrypt}$2a$10$.n7NALDNCFJzxrC5n8z.mOHGb2R8JgZOaLbiQXfbXy7iDqpZHG7rO','kot12@gmail.com','2023-12-09 00:00:00',2),(46,'kot13','{bcrypt}$2a$10$7KM5xclpkmAoca7loiN3IOlHpkd.anjk6/aZS/9NkFm1rqVi.N8Hq','kot13@gmail.com','2023-12-09 00:00:00',2),(47,'kot14','{bcrypt}$2a$10$/njfPQl46B3fYueV0H0lVOkprDLG2FbiU4NN82Fdo7o4uMa/y.OdC','kot14@gmail.com','2023-12-09 00:00:00',2),(48,'kot15','{bcrypt}$2a$10$PvH.31cocfX1Zo8b3EnThuxVWlq2aZ/w51vvQhoYb9A5JtKF20kqC','kot15@gmail.com','2023-12-09 00:00:00',2),(49,'kot16','{bcrypt}$2a$10$f4aZmRTJGDeAyifeN/z35OTDEls.IM66bLu1B0c38Yjj7skxKnC1.','kot16@gmail.com','2023-12-09 00:00:00',2),(50,'kot18','{bcrypt}$2a$10$a6TC2GMnPsyd073gZxelwO9DI2/90CPDcsIJHQNMsvPLYvhlyKpYC','kot18@gmail.com','2023-12-09 00:00:00',2),(51,'kot19','{bcrypt}$2a$10$fhYErpCUqBxrCgYWO3UBzujnQ7SbrlemORJ4uqT.qKPODbtMG0p6G','kot19@gmail.com','2023-12-09 00:00:00',2),(52,'kot20','{bcrypt}$2a$10$TLIHAoxBC13qdEwL4qrU4O0AeYtR5c3U2kzf5SPnzKwKpC4r.VaHe','kot20@gmail.com','2023-12-09 00:00:00',2);
/*!40000 ALTER TABLE `user_data` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-23 16:55:51
