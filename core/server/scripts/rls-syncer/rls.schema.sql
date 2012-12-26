-- MySQL dump 10.13  Distrib 5.5.23, for Linux (x86_64)
--
-- Host: mysql.local    Database: rls
-- ------------------------------------------------------
-- Server version	5.1.58

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


USE `rls`;

--
-- Table structure for table `rlsATCGroup`
--

DROP TABLE IF EXISTS `rlsATCGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsATCGroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL,
  `code` varchar(8) NOT NULL,
  `name` varchar(512) DEFAULT NULL,
  `nameRaw` varchar(512) NOT NULL,
  `path` varchar(128) NOT NULL,
  `pathx` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `nameRaw` (`nameRaw`(255))
) ENGINE=InnoDB AUTO_INCREMENT=2523 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rlsATCGroupExt`
--

DROP TABLE IF EXISTS `rlsATCGroupExt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsATCGroupExt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(8) NOT NULL,
  `name` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=601 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rlsATCGroupToCode`
--

DROP TABLE IF EXISTS `rlsATCGroupToCode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsATCGroupToCode` (
  `rlsATCGroup_id` int(11) NOT NULL DEFAULT '0',
  `code` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`rlsATCGroup_id`,`code`),
  KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rlsDosage`
--

DROP TABLE IF EXISTS `rlsDosage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsDosage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4331 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rlsFilling`
--

DROP TABLE IF EXISTS `rlsFilling`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsFilling` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `disabledForPrescription` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Скрывать при выборе для рецепта',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5436 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rlsForm`
--

DROP TABLE IF EXISTS `rlsForm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsForm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=745 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rlsInpName`
--

DROP TABLE IF EXISTS `rlsInpName`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsInpName` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `latName` varchar(255) DEFAULT NULL,
  `rawName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`,`latName`),
  KEY `rawName` (`rawName`)
) ENGINE=InnoDB AUTO_INCREMENT=1825 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rlsInpNameToCode`
--

DROP TABLE IF EXISTS `rlsInpNameToCode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsInpNameToCode` (
  `rlsINPName_id` int(11) NOT NULL DEFAULT '0',
  `code` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`rlsINPName_id`,`code`),
  KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rlsMKBToCode`
--

DROP TABLE IF EXISTS `rlsMKBToCode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsMKBToCode` (
  `MKB` varchar(16) NOT NULL DEFAULT '',
  `code` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`MKB`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rlsNomen`
--

DROP TABLE IF EXISTS `rlsNomen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsNomen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) NOT NULL COMMENT 'РЛС-овский код',
  `tradeName_id` int(11) DEFAULT NULL COMMENT 'Торговое название {rlsTradeName}',
  `INPName_id` int(11) DEFAULT NULL COMMENT 'МНН/НДВ {rlsINPName}',
  `form_id` int(11) DEFAULT NULL COMMENT 'Лекарственная форма {rlsForm}',
  `dosage_id` int(11) DEFAULT NULL COMMENT 'Доза в единице лекарственной формы {rlsDosage}',
  `filling_id` int(11) DEFAULT NULL COMMENT 'Фасовка {rlsFilling}',
  `packing_id` int(11) DEFAULT NULL COMMENT 'Упаковка {rlsPacking}',
  `regDate` date DEFAULT NULL COMMENT 'Дата регистрации',
  `annDate` date DEFAULT NULL COMMENT 'Дата отмены',
  `disabledForPrescription` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Скрывать при выборе для рецепта',
  PRIMARY KEY (`id`),
  KEY `code` (`code`),
  KEY `tradeName_id` (`tradeName_id`),
  KEY `INPName_id` (`INPName_id`),
  KEY `form_id` (`form_id`),
  KEY `dosage_id` (`dosage_id`),
  KEY `filling_id` (`filling_id`),
  KEY `packing_id` (`packing_id`),
  KEY `regDate` (`regDate`),
  KEY `annDate` (`annDate`)
) ENGINE=InnoDB AUTO_INCREMENT=173460 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rlsNomenRaw`
--

DROP TABLE IF EXISTS `rlsNomenRaw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsNomenRaw` (
  `code` int(11) NOT NULL,
  `tradeName` varchar(256) NOT NULL,
  `latName` varchar(256) NOT NULL,
  `NDV` varchar(256) NOT NULL,
  `kind` varchar(6) NOT NULL,
  `life` varchar(1) NOT NULL,
  `form` varchar(64) NOT NULL,
  `dosage` varchar(64) NOT NULL,
  `filling` varchar(128) NOT NULL,
  `packing` varchar(64) NOT NULL,
  `manufacturer` varchar(128) NOT NULL,
  `mcountry` varchar(32) NOT NULL,
  `distributor` varchar(128) NOT NULL,
  `dcountry` varchar(32) NOT NULL,
  `packer` varchar(128) NOT NULL,
  `pcountry` varchar(32) NOT NULL,
  `barcode` varchar(16) NOT NULL,
  `regNum` varchar(64) NOT NULL,
  `regDate` varchar(10) NOT NULL,
  `registrator` varchar(128) NOT NULL,
  `rcountry` varchar(32) NOT NULL,
  `price` varchar(64) NOT NULL,
  `age` varchar(256) NOT NULL,
  `group` varchar(512) NOT NULL,
  `MKB` varchar(10240) NOT NULL,
  `ATC` varchar(512) NOT NULL,
  KEY `code` (`code`),
  KEY `tradeName` (`tradeName`(255)),
  KEY `latName` (`latName`(255)),
  KEY `NDV` (`NDV`(255)),
  KEY `kind` (`kind`),
  KEY `life` (`life`),
  KEY `form` (`form`),
  KEY `dosage` (`dosage`),
  KEY `filling` (`filling`),
  KEY `packing` (`packing`),
  KEY `group` (`group`(255)),
  KEY `MKB` (`MKB`(255)),
  KEY `tradeName_2` (`tradeName`(255),`latName`(255)),
  KEY `ATC` (`ATC`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rlsPacking`
--

DROP TABLE IF EXISTS `rlsPacking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsPacking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `disabledForPrescription` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Скрывать при выборе для рецепта',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1046 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rlsPharmGroup`
--

DROP TABLE IF EXISTS `rlsPharmGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsPharmGroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL,
  `code` varchar(8) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `path` varchar(128) DEFAULT NULL,
  `pathx` varchar(128) DEFAULT NULL,
  `nameRaw` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `nameRaw` (`nameRaw`)
) ENGINE=InnoDB AUTO_INCREMENT=193 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rlsPharmGroupToCode`
--

DROP TABLE IF EXISTS `rlsPharmGroupToCode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsPharmGroupToCode` (
  `rlsPharmGroup_id` int(11) NOT NULL DEFAULT '0',
  `code` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`rlsPharmGroup_id`,`code`),
  KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rlsTradeName`
--

DROP TABLE IF EXISTS `rlsTradeName`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsTradeName` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `latName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`,`latName`),
  KEY `latName` (`latName`)
) ENGINE=InnoDB AUTO_INCREMENT=30926 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rlsTradeNameToCode`
--

DROP TABLE IF EXISTS `rlsTradeNameToCode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rlsTradeNameToCode` (
  `rlsTradeName_id` int(11) NOT NULL DEFAULT '0',
  `code` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`rlsTradeName_id`,`code`),
  KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `vNomen`
--

DROP TABLE IF EXISTS `vNomen`;
/*!50001 DROP VIEW IF EXISTS `vNomen`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `vNomen` (
  `id` int(11),
  `code` int(11),
  `tradeName_id` int(11),
  `INPName_id` int(11),
  `form_id` int(11),
  `dosage_id` int(11),
  `filling_id` int(11),
  `packing_id` int(11),
  `regDate` date,
  `annDate` date,
  `tradeName` varchar(255),
  `tradeNameLat` varchar(255),
  `INPName` varchar(255),
  `INPNameLat` varchar(255),
  `form` varchar(128),
  `dosage` varchar(128),
  `filling` varchar(128),
  `packing` varchar(128),
  `disabledForPrescription` int(1)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `vNomen`
--

/*!50001 DROP TABLE IF EXISTS `vNomen`*/;
/*!50001 DROP VIEW IF EXISTS `vNomen`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vNomen` AS select `rlsNomen`.`id` AS `id`,`rlsNomen`.`code` AS `code`,`rlsNomen`.`tradeName_id` AS `tradeName_id`,`rlsNomen`.`INPName_id` AS `INPName_id`,`rlsNomen`.`form_id` AS `form_id`,`rlsNomen`.`dosage_id` AS `dosage_id`,`rlsNomen`.`filling_id` AS `filling_id`,`rlsNomen`.`packing_id` AS `packing_id`,`rlsNomen`.`regDate` AS `regDate`,`rlsNomen`.`annDate` AS `annDate`,`rlsTradeName`.`name` AS `tradeName`,`rlsTradeName`.`latName` AS `tradeNameLat`,`rlsInpName`.`name` AS `INPName`,`rlsInpName`.`latName` AS `INPNameLat`,`rlsForm`.`name` AS `form`,`rlsDosage`.`name` AS `dosage`,`rlsFilling`.`name` AS `filling`,`rlsPacking`.`name` AS `packing`,(`rlsNomen`.`disabledForPrescription` or if(isnull(`rlsFilling`.`disabledForPrescription`),0,`rlsFilling`.`disabledForPrescription`) or if(isnull(`rlsPacking`.`disabledForPrescription`),0,`rlsPacking`.`disabledForPrescription`)) AS `disabledForPrescription` from ((((((`rlsNomen` left join `rlsTradeName` on((`rlsTradeName`.`id` = `rlsNomen`.`tradeName_id`))) left join `rlsInpName` on((`rlsInpName`.`id` = `rlsNomen`.`INPName_id`))) left join `rlsForm` on((`rlsForm`.`id` = `rlsNomen`.`form_id`))) left join `rlsDosage` on((`rlsDosage`.`id` = `rlsNomen`.`dosage_id`))) left join `rlsFilling` on((`rlsFilling`.`id` = `rlsNomen`.`filling_id`))) left join `rlsPacking` on((`rlsPacking`.`id` = `rlsNomen`.`packing_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-04-27 13:13:11
