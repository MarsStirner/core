/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Дамп структуры для таблица devel.rb_F001_Tfoms
DROP TABLE IF EXISTS `rb_F001_Tfoms`;
CREATE TABLE IF NOT EXISTS `rb_F001_Tfoms` (
  `tf_kod` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `d_edit` date DEFAULT NULL,
  `d_end` date DEFAULT NULL,
  `e_mail` varchar(255) DEFAULT NULL,
  `fam_dir` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `idx` varchar(255) DEFAULT NULL,
  `im_dir` varchar(255) DEFAULT NULL,
  `kf_tf` bigint(20) DEFAULT NULL,
  `name_tfk` varchar(255) DEFAULT NULL,
  `name_tfp` varchar(255) DEFAULT NULL,
  `ot_dir` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `tf_ogrn` varchar(255) DEFAULT NULL,
  `tf_okato` varchar(255) DEFAULT NULL,
  `www` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tf_kod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_F001_Tfoms: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_F001_Tfoms` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_F001_Tfoms` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_F002_SMO
DROP TABLE IF EXISTS `rb_F002_SMO`;
CREATE TABLE IF NOT EXISTS `rb_F002_SMO` (
  `smocod` varchar(255) NOT NULL,
  `addr_f` varchar(255) DEFAULT NULL,
  `addr_j` varchar(255) DEFAULT NULL,
  `d_begin` date DEFAULT NULL,
  `d_edit` date DEFAULT NULL,
  `d_end` date DEFAULT NULL,
  `d_start` date DEFAULT NULL,
  `data_e` date DEFAULT NULL,
  `DUVED` date DEFAULT NULL,
  `e_mail` varchar(255) DEFAULT NULL,
  `fam_ruk` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `im_ruk` varchar(255) DEFAULT NULL,
  `index_f` varchar(255) DEFAULT NULL,
  `index_j` varchar(255) DEFAULT NULL,
  `inn` varchar(255) DEFAULT NULL,
  `kol_zl` bigint(20) DEFAULT NULL,
  `KPP` varchar(255) DEFAULT NULL,
  `n_doc` varchar(255) DEFAULT NULL,
  `Nal_p` varchar(255) DEFAULT NULL,
  `nam_smok` varchar(255) DEFAULT NULL,
  `nam_smop` varchar(255) DEFAULT NULL,
  `name_e` varchar(255) DEFAULT NULL,
  `Ogrn` varchar(255) DEFAULT NULL,
  `okopf` varchar(255) DEFAULT NULL,
  `org` bigint(20) DEFAULT NULL,
  `ot_ruk` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `tf_okato` varchar(255) DEFAULT NULL,
  `www` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`smocod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_F002_SMO: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_F002_SMO` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_F002_SMO` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_F003_MO
DROP TABLE IF EXISTS `rb_F003_MO`;
CREATE TABLE IF NOT EXISTS `rb_F003_MO` (
  `mcod` varchar(255) NOT NULL,
  `addr_j` varchar(255) DEFAULT NULL,
  `d_begin` date DEFAULT NULL,
  `d_edit` date DEFAULT NULL,
  `d_end` date DEFAULT NULL,
  `d_start` date DEFAULT NULL,
  `data_e` date DEFAULT NULL,
  `DUVED` date DEFAULT NULL,
  `e_mail` varchar(255) DEFAULT NULL,
  `fam_ruk` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `im_ruk` varchar(255) DEFAULT NULL,
  `index_j` varchar(255) DEFAULT NULL,
  `inn` varchar(255) DEFAULT NULL,
  `KPP` varchar(255) DEFAULT NULL,
  `lpu` int(11) DEFAULT NULL,
  `mp` varchar(255) DEFAULT NULL,
  `n_doc` varchar(255) DEFAULT NULL,
  `nam_mok` varchar(255) DEFAULT NULL,
  `nam_mop` varchar(255) DEFAULT NULL,
  `name_e` varchar(255) DEFAULT NULL,
  `Ogrn` varchar(255) DEFAULT NULL,
  `okopf` varchar(255) DEFAULT NULL,
  `org` bigint(20) DEFAULT NULL,
  `ot_ruk` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `tf_okato` varchar(255) DEFAULT NULL,
  `vedpri` bigint(20) DEFAULT NULL,
  `www` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mcod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_F003_MO: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_F003_MO` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_F003_MO` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_F007_Vedom
DROP TABLE IF EXISTS `rb_F007_Vedom`;
CREATE TABLE IF NOT EXISTS `rb_F007_Vedom` (
  `idved` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `vedname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idved`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_F007_Vedom: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_F007_Vedom` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_F007_Vedom` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_F008_TipOMS
DROP TABLE IF EXISTS `rb_F008_TipOMS`;
CREATE TABLE IF NOT EXISTS `rb_F008_TipOMS` (
  `iddoc` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `docname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`iddoc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_F008_TipOMS: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_F008_TipOMS` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_F008_TipOMS` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_F009_StatZL
DROP TABLE IF EXISTS `rb_F009_StatZL`;
CREATE TABLE IF NOT EXISTS `rb_F009_StatZL` (
  `IDStatus` varchar(255) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `StatusName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`IDStatus`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_F009_StatZL: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_F009_StatZL` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_F009_StatZL` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_F010_Subekti
DROP TABLE IF EXISTS `rb_F010_Subekti`;
CREATE TABLE IF NOT EXISTS `rb_F010_Subekti` (
  `KOD_TF` varchar(255) NOT NULL,
  `DATEBEG` date DEFAULT NULL,
  `DATEEND` date DEFAULT NULL,
  `KOD_OKATO` varchar(255) DEFAULT NULL,
  `OKRUG` bigint(20) DEFAULT NULL,
  `SUBNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`KOD_TF`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_F010_Subekti: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_F010_Subekti` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_F010_Subekti` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_F011_Tipdoc
DROP TABLE IF EXISTS `rb_F011_Tipdoc`;
CREATE TABLE IF NOT EXISTS `rb_F011_Tipdoc` (
  `IDDoc` bigint(20) NOT NULL,
  `DATEBEG` date DEFAULT NULL,
  `DATEEND` date DEFAULT NULL,
  `DocName` varchar(255) DEFAULT NULL,
  `DocNum` varchar(255) DEFAULT NULL,
  `DocSer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`IDDoc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_F011_Tipdoc: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_F011_Tipdoc` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_F011_Tipdoc` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_F015_FedOkr
DROP TABLE IF EXISTS `rb_F015_FedOkr`;
CREATE TABLE IF NOT EXISTS `rb_F015_FedOkr` (
  `KOD_OK` bigint(20) NOT NULL,
  `DATEBEG` date DEFAULT NULL,
  `DATEEND` date DEFAULT NULL,
  `OKRNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`KOD_OK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_F015_FedOkr: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_F015_FedOkr` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_F015_FedOkr` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_Kladr
DROP TABLE IF EXISTS `rb_Kladr`;
CREATE TABLE IF NOT EXISTS `rb_Kladr` (
  `code` varchar(255) NOT NULL,
  `gninmb` varchar(255) DEFAULT NULL,
  `idx` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `ocatd` varchar(255) DEFAULT NULL,
  `socr` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `uno` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_Kladr: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_Kladr` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_Kladr` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_KladrStreet
DROP TABLE IF EXISTS `rb_KladrStreet`;
CREATE TABLE IF NOT EXISTS `rb_KladrStreet` (
  `code` varchar(255) NOT NULL,
  `gninmb` varchar(255) DEFAULT NULL,
  `idx` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `ocatd` varchar(255) DEFAULT NULL,
  `socr` varchar(255) DEFAULT NULL,
  `uno` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_KladrStreet: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_KladrStreet` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_KladrStreet` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_M001_MKB10
DROP TABLE IF EXISTS `rb_M001_MKB10`;
CREATE TABLE IF NOT EXISTS `rb_M001_MKB10` (
  `idds` varchar(255) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `dsname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idds`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_M001_MKB10: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_M001_MKB10` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_M001_MKB10` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_O001_Oksm
DROP TABLE IF EXISTS `rb_O001_Oksm`;
CREATE TABLE IF NOT EXISTS `rb_O001_Oksm` (
  `KOD` varchar(255) NOT NULL,
  `ALFA2` varchar(255) DEFAULT NULL,
  `ALFA3` varchar(255) DEFAULT NULL,
  `DATA_UPD` date DEFAULT NULL,
  `NAME11` varchar(255) DEFAULT NULL,
  `NAME12` varchar(255) DEFAULT NULL,
  `NOMAKT` varchar(255) DEFAULT NULL,
  `NOMDESCR` varchar(255) DEFAULT NULL,
  `STATUS` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`KOD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_O001_Oksm: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_O001_Oksm` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_O001_Oksm` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_O002_Okato
DROP TABLE IF EXISTS `rb_O002_Okato`;
CREATE TABLE IF NOT EXISTS `rb_O002_Okato` (
  `TER` varchar(255) NOT NULL,
  `CENTRUM` varchar(255) DEFAULT NULL,
  `DATA_UPD` date DEFAULT NULL,
  `KOD1` varchar(255) DEFAULT NULL,
  `KOD2` varchar(255) DEFAULT NULL,
  `KOD3` varchar(255) DEFAULT NULL,
  `NAME1` varchar(255) DEFAULT NULL,
  `NOMAKT` varchar(255) DEFAULT NULL,
  `NOMDESCR` varchar(255) DEFAULT NULL,
  `RAZDEL` varchar(255) DEFAULT NULL,
  `STATUS` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`TER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_O002_Okato: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_O002_Okato` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_O002_Okato` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_O003_Okved
DROP TABLE IF EXISTS `rb_O003_Okved`;
CREATE TABLE IF NOT EXISTS `rb_O003_Okved` (
  `KOD` varchar(255) NOT NULL,
  `DATA_UPD` date DEFAULT NULL,
  `NAME11` varchar(255) DEFAULT NULL,
  `NAME12` varchar(255) DEFAULT NULL,
  `NOMAKT` varchar(255) DEFAULT NULL,
  `NOMDESCR` varchar(255) DEFAULT NULL,
  `PRAZDEL` varchar(255) DEFAULT NULL,
  `RAZDEL` varchar(255) DEFAULT NULL,
  `STATUS` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`KOD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_O003_Okved: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_O003_Okved` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_O003_Okved` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_O004_Okfs
DROP TABLE IF EXISTS `rb_O004_Okfs`;
CREATE TABLE IF NOT EXISTS `rb_O004_Okfs` (
  `KOD` varchar(255) NOT NULL,
  `ALG` varchar(255) DEFAULT NULL,
  `DATA_UPD` date DEFAULT NULL,
  `NAME1` varchar(255) DEFAULT NULL,
  `NOMAKT` varchar(255) DEFAULT NULL,
  `STATUS` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`KOD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_O004_Okfs: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_O004_Okfs` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_O004_Okfs` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_O005_Okopf
DROP TABLE IF EXISTS `rb_O005_Okopf`;
CREATE TABLE IF NOT EXISTS `rb_O005_Okopf` (
  `KOD` varchar(255) NOT NULL,
  `ALG` varchar(255) DEFAULT NULL,
  `DATA_UPD` date DEFAULT NULL,
  `NAME1` varchar(255) DEFAULT NULL,
  `NOMAKT` varchar(255) DEFAULT NULL,
  `STATUS` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`KOD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_O005_Okopf: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_O005_Okopf` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_O005_Okopf` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_V001_Nomerclr
DROP TABLE IF EXISTS `rb_V001_Nomerclr`;
CREATE TABLE IF NOT EXISTS `rb_V001_Nomerclr` (
  `idrb` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `rbname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idrb`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_V001_Nomerclr: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_V001_Nomerclr` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_V001_Nomerclr` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_V002_ProfOt
DROP TABLE IF EXISTS `rb_V002_ProfOt`;
CREATE TABLE IF NOT EXISTS `rb_V002_ProfOt` (
  `idpr` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `prname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idpr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_V002_ProfOt: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_V002_ProfOt` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_V002_ProfOt` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_V003_LicUsl
DROP TABLE IF EXISTS `rb_V003_LicUsl`;
CREATE TABLE IF NOT EXISTS `rb_V003_LicUsl` (
  `IDRL` bigint(20) NOT NULL,
  `DATEBEG` date DEFAULT NULL,
  `DATEEND` date DEFAULT NULL,
  `IERARH` bigint(20) DEFAULT NULL,
  `LICNAME` varchar(255) DEFAULT NULL,
  `PRIM` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`IDRL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_V003_LicUsl: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_V003_LicUsl` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_V003_LicUsl` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_V004_Medspec
DROP TABLE IF EXISTS `rb_V004_Medspec`;
CREATE TABLE IF NOT EXISTS `rb_V004_Medspec` (
  `idmsp` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `mspname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idmsp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_V004_Medspec: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_V004_Medspec` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_V004_Medspec` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_V005_Pol
DROP TABLE IF EXISTS `rb_V005_Pol`;
CREATE TABLE IF NOT EXISTS `rb_V005_Pol` (
  `idpol` bigint(20) NOT NULL,
  `polname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idpol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_V005_Pol: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_V005_Pol` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_V005_Pol` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_V006_UslMp
DROP TABLE IF EXISTS `rb_V006_UslMp`;
CREATE TABLE IF NOT EXISTS `rb_V006_UslMp` (
  `idump` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `umpname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idump`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_V006_UslMp: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_V006_UslMp` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_V006_UslMp` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_V007_NomMO
DROP TABLE IF EXISTS `rb_V007_NomMO`;
CREATE TABLE IF NOT EXISTS `rb_V007_NomMO` (
  `idnmo` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `nmoname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idnmo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_V007_NomMO: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_V007_NomMO` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_V007_NomMO` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_V008_VidMp
DROP TABLE IF EXISTS `rb_V008_VidMp`;
CREATE TABLE IF NOT EXISTS `rb_V008_VidMp` (
  `idvmp` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `vmpname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idvmp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_V008_VidMp: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_V008_VidMp` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_V008_VidMp` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_V009_Rezult
DROP TABLE IF EXISTS `rb_V009_Rezult`;
CREATE TABLE IF NOT EXISTS `rb_V009_Rezult` (
  `idrmp` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `iduslov` bigint(20) DEFAULT NULL,
  `rmpname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idrmp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_V009_Rezult: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_V009_Rezult` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_V009_Rezult` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_V010_Sposob
DROP TABLE IF EXISTS `rb_V010_Sposob`;
CREATE TABLE IF NOT EXISTS `rb_V010_Sposob` (
  `idsp` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `spname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idsp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_V010_Sposob: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_V010_Sposob` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_V010_Sposob` ENABLE KEYS */;


-- Дамп структуры для таблица devel.rb_V012_Ishod
DROP TABLE IF EXISTS `rb_V012_Ishod`;
CREATE TABLE IF NOT EXISTS `rb_V012_Ishod` (
  `idiz` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `iduslov` bigint(20) DEFAULT NULL,
  `izname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idiz`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы devel.rb_V012_Ishod: ~0 rows (приблизительно)
/*!40000 ALTER TABLE `rb_V012_Ishod` DISABLE KEYS */;
/*!40000 ALTER TABLE `rb_V012_Ishod` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
