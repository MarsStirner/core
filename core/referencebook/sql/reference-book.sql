
CREATE TABLE `rb_F001_Tfoms` (
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

CREATE TABLE `rb_F002_SMO` (
  `inn` varchar(255) NOT NULL,
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
  `smocod` varchar(255) DEFAULT NULL,
  `tf_okato` varchar(255) DEFAULT NULL,
  `www` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`inn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_F003_MO` (
  `tf_okato` varchar(255) NOT NULL,
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
  `mcod` varchar(255) DEFAULT NULL,
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
  `vedpri` bigint(20) DEFAULT NULL,
  `www` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tf_okato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_F007_Vedom` (
  `idved` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `vedname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idved`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_F008_TipOMS` (
  `iddoc` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `docname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`iddoc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_F009_StatZL` (
  `IDStatus` varchar(255) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `StatusName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`IDStatus`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_F010_Subekti` (
  `KOD_TF` varchar(255) NOT NULL,
  `DATEBEG` date DEFAULT NULL,
  `DATEEND` date DEFAULT NULL,
  `KOD_OKATO` varchar(255) DEFAULT NULL,
  `OKRUG` bigint(20) DEFAULT NULL,
  `SUBNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`KOD_TF`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_F011_Tipdoc` (
  `IDDoc` bigint(20) NOT NULL,
  `DATEBEG` date DEFAULT NULL,
  `DATEEND` date DEFAULT NULL,
  `DocName` varchar(255) DEFAULT NULL,
  `DocNum` varchar(255) DEFAULT NULL,
  `DocSer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`IDDoc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_F015_FedOkr` (
  `KOD_OK` bigint(20) NOT NULL,
  `DATEBEG` date DEFAULT NULL,
  `DATEEND` date DEFAULT NULL,
  `OKRNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`KOD_OK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_M001_MKB10` (
  `idds` varchar(255) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `dsname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idds`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_O001_Oksm` (
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

CREATE TABLE `rb_O002_Okato` (
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

CREATE TABLE `rb_O003_Okved` (
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

CREATE TABLE `rb_O004_Okfs` (
  `KOD` varchar(255) NOT NULL,
  `ALG` varchar(255) DEFAULT NULL,
  `DATA_UPD` date DEFAULT NULL,
  `NAME1` varchar(255) DEFAULT NULL,
  `NOMAKT` varchar(255) DEFAULT NULL,
  `STATUS` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`KOD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_O005_Okopf` (
  `KOD` varchar(255) NOT NULL,
  `ALG` varchar(255) DEFAULT NULL,
  `DATA_UPD` date DEFAULT NULL,
  `NAME1` varchar(255) DEFAULT NULL,
  `NOMAKT` varchar(255) DEFAULT NULL,
  `STATUS` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`KOD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_V001_Nomerclr` (
  `idrb` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `rbname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idrb`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_V002_ProfOt` (
  `idpr` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `prname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idpr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_V003_LicUsl` (
  `IDRL` bigint(20) NOT NULL,
  `DATEBEG` date DEFAULT NULL,
  `DATEEND` date DEFAULT NULL,
  `IERARH` bigint(20) DEFAULT NULL,
  `LICNAME` varchar(255) DEFAULT NULL,
  `PRIM` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`IDRL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_V004_Medspec` (
  `idmsp` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `mspname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idmsp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_V005_Pol` (
  `idpol` bigint(20) NOT NULL,
  `polname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idpol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_V006_UslMp` (
  `idump` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `umpname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idump`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_V007_NomMO` (
  `idnmo` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `nmoname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idnmo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `rb_V008_VidMp` (
  `idvmp` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `vmpname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idvmp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `rb_V009_Rezult` (
  `idrmp` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `iduslov` bigint(20) DEFAULT NULL,
  `rmpname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idrmp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_V010_Sposob` (
  `idsp` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `spname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idsp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rb_V012_Ishod` (
  `idiz` bigint(20) NOT NULL,
  `datebeg` date DEFAULT NULL,
  `dateend` date DEFAULT NULL,
  `iduslov` bigint(20) DEFAULT NULL,
  `izname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idiz`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;