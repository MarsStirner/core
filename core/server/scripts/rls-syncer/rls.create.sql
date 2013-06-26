DROP DATABASE IF EXISTS rls1;
CREATE DATABASE `rls1` CHARACTER SET utf8 COLLATE utf8_general_ci;

GRANT ALL PRIVILEGES  ON rls1.* TO 'root'@'%' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES  ON rls1.* TO 'root'@'%';
/*GRANT ALL PRIVILEGES  ON rls1.* TO 'tmis'@'%';*/

USE rls1;

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

CREATE TABLE `rlsINPName` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `latName` varchar(255) DEFAULT NULL,
  `rawName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`,`latName`),
  KEY `rawName` (`rawName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rlsTradeName` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `latName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`,`latName`),
  KEY `latName` (`latName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rlsDosage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
