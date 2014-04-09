DELETE FROM `Event`;
INSERT IGNORE INTO `Person` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `code`, `federalCode`, `regionalCode`, `lastName`, `firstName`, `patrName`, `post_id`, `speciality_id`, `org_id`, `orgStructure_id`, `office`, `office2`, `tariffCategory_id`, `finance_id`, `retireDate`, `ambPlan`, `ambPlan2`, `ambNorm`, `homPlan`, `homPlan2`, `homNorm`, `expPlan`, `expNorm`, `login`, `password`, `userProfile_id`, `retired`, `birthDate`, `birthPlace`, `sex`, `SNILS`, `INN`, `availableForExternal`, `primaryQuota`, `ownQuota`, `consultancyQuota`, `externalQuota`, `lastAccessibleTimelineDate`, `timelineAccessibleDays`, `typeTimeLinePerson`, `academicdegree_id`, `academicTitle_id`, `uuid_id`, `displayInTimeline`, `maxOverQueue`, `maxCito`, `quotUnit`) VALUES (458, '2013-07-05 11:31:32', 170, '2013-10-02 08:23:41', 170, 0, '1204', '', '', 'Власова', 'Светлана', 'Николаевна', 75, 52, 4378, 51, '', '', NULL, 2, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 'власова', 'caf1a3dfb505ffed0d024130f58c5cfa', 7, 0, '1964-10-25', '', 0, '03674644273', '', 0, 0, 0, 0, 0, NULL, 0, 0, NULL, NULL, 2689040, 1, 0, 0, 0);
INSERT IGNORE INTO `Event` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `externalId`, `eventType_id`, `org_id`, `client_id`, `contract_id`, `prevEventDate`, `setDate`, `setPerson_id`, `execDate`, `execPerson_id`, `isPrimary`, `order`, `result_id`, `nextEventDate`, `payStatus`, `typeAsset_id`, `note`, `curator_id`, `assistant_id`, `pregnancyWeek`, `MES_id`, `mesSpecification_id`, `rbAcheResult_id`, `version`, `privilege`, `urgent`, `orgStructure_id`, `uuid_id`, `lpu_transfer`) VALUES (841672, '2014-01-22 10:39:23', 458, '2014-01-23 14:07:29', 311, 0, '1111/22', 13, 3479, 347610, 65, NULL, '2014-01-21 23:20:46', 458, '2014-01-22 15:45:33', 458, 1, 2, 52, NULL, 0, NULL, '', NULL, NULL, 0, NULL, NULL, 2, 7, 0, 0, NULL, 2850761, '');
INSERT IGNORE INTO `Client` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `lastName`, `firstName`, `patrName`, `birthDate`, `sex`, `SNILS`, `bloodType_id`, `bloodDate`, `bloodNotes`, `growth`, `weight`, `notes`, `birthPlace`, `embryonalPeriodWeek`, `uuid_id`, `version`) VALUES (347610, '2012-01-09 12:27:40', 170, '2014-01-23 14:07:29', 311, 0, 'КОЗИНА', 'АРИНА', 'ВЯЧЕСЛАВОВНА', '2009-03-03', 2, '16043046928', 4, NULL, '', '0', '0', '', 'г. Урюпинск', '', 990372, 0);
INSERT IGNORE INTO `ClientPolicy` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `insurer_id`, `policyType_id`, `serial`, `number`, `begDate`, `endDate`, `name`, `note`, `version`) VALUES (326579, '2012-01-09 12:30:01', 170, '2014-01-05 13:55:50', 377, 0, 347610, 3910, 4, '111', '5801989121913368', '2009-11-25', '2019-11-25', '', '', 0);
INSERT IGNORE INTO `ClientPolicy` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `insurer_id`, `policyType_id`, `serial`, `number`, `begDate`, `endDate`, `name`, `note`, `version`) VALUES (326580, '2012-01-09 12:30:01', 170, '2014-01-05 13:55:50', 377, 0, 347610, 3910, 3, '222', '3333333333333333', '2013-10-27', NULL, '', '', 0);
INSERT IGNORE INTO `ClientAddress` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `type`, `address_id`, `freeInput`, `version`, `localityType`) VALUES (2731471, '2013-10-25 15:15:53', 292, '2013-10-25 15:15:53', 292, 0, 347610, 0, 94767, '', 0, 1);
INSERT IGNORE INTO `ClientAddress` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `type`, `address_id`, `freeInput`, `version`, `localityType`) VALUES (2731472, '2013-10-25 15:15:53', 292, '2013-10-25 15:15:53', 292, 0, 347610, 1, NULL, 'Тестовый адрес строкой', 0, 1);
INSERT IGNORE INTO `Address` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `house_id`, `flat`) VALUES (94767, '2012-02-01 20:55:40', 170, '2012-02-01 20:55:40', 170, 0, 30000, '60');
INSERT IGNORE INTO `AddressHouse` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `KLADRCode`, `KLADRStreetCode`, `number`, `corpus`) VALUES (30000, '2012-02-01 20:53:38', 170, '2012-02-01 20:53:38', 170, 0, '5801900011000', '58019000110000100', '12', '1');
INSERT IGNORE INTO `ClientSocStatus` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `socStatusClass_id`, `socStatusType_id`, `begDate`, `endDate`, `document_id`, `version`, `note`, `benefitCategory_id`) VALUES (4, '2011-12-16 10:20:56', 311, '2012-06-25 16:08:08', 292, 0, 347610, 36, 316, '2011-05-01', '2013-04-29', NULL, 0, '', NULL);
INSERT IGNORE INTO `ClientSocStatus` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `socStatusClass_id`, `socStatusType_id`, `begDate`, `endDate`, `document_id`, `version`, `note`, `benefitCategory_id`) VALUES (5, '2013-10-25 13:01:52', 292, '2013-10-25 13:01:52', 292, 0, 347610, 33, 311, '0000-00-00', NULL, NULL, 0, '', NULL);
INSERT IGNORE INTO `ClientSocStatus` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `socStatusClass_id`, `socStatusType_id`, `begDate`, `endDate`, `document_id`, `version`, `note`, `benefitCategory_id`) VALUES (6, '2013-10-25 13:01:52', 292, '2013-10-25 13:01:52', 292, 0, 347610, 35, 166, '0000-00-00', NULL, NULL, 0, '', NULL);
INSERT IGNORE INTO `ClientWork` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `org_id`, `freeInput`, `post`, `stage`, `OKVED`, `version`, `rank_id`, `arm_id`) VALUES (21136, '2013-03-20 09:31:42', 369, '2013-03-20 09:31:42', 369, 0, 347610, 3797, '', 'сторож', 0, '01114', 0, 0, 0);
UPDATE `Organisation` SET `OGRN`='1234567890123' WHERE  `id`=3797 LIMIT 1;
INSERT IGNORE INTO `ClientWork_Hurt` (`id`, `master_id`, `hurtType_id`, `stage`) VALUES (5, 21136, 1, 0);
INSERT IGNORE INTO `ClientDocument` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `documentType_id`, `serial`, `number`, `date`, `origin`, `version`, `endDate`) VALUES (4, '2012-01-09 12:22:08', 170, '2012-01-09 12:22:08', 170, 0, 347610, 1, '56 02', '571655', '2000-04-01', '6 УФМС по району', 0, '2140-01-01');
INSERT IGNORE INTO `ClientDocument` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `documentType_id`, `serial`, `number`, `date`, `origin`, `version`, `endDate`) VALUES (5, '2012-01-09 12:28:34', 170, '2012-01-09 12:28:34', 170, 0, 347610, 3, '1-ИЗ', '715169', '1913-04-05', 'ЗАГС', 0, '2174-03-03');
UPDATE `Organisation` SET `infisCode`='12345', `OKATO`='9875' WHERE  `id`=3910;
UPDATE `rbPolicyType` SET `code`='vmi' WHERE  `id`=3;
UPDATE `rbContactType` SET `code`='1' WHERE  `id`=1;
UPDATE `rbContactType` SET `code`='2' WHERE  `id`=2;
UPDATE `rbContactType` SET `code`='3' WHERE  `id`=3;
UPDATE `rbContactType` SET `code`='9' WHERE  `id`=5;
INSERT IGNORE INTO `rbContactType` (`id`, `code`, `name`) VALUES (9, '9', 'Номер ребенка при многоплодных родах');
INSERT IGNORE INTO `ClientContact` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `contactType_id`, `contact`, `notes`, `version`) VALUES (3364, '2013-01-10 14:43:32', 369, '2013-07-29 08:48:28', 369, 0, 347610, 1, '485279', '', 0);
INSERT IGNORE INTO `ClientContact` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `contactType_id`, `contact`, `notes`, `version`) VALUES (3365, '2013-01-10 14:43:32', 369, '2013-07-29 08:48:28', 369, 0, 347610, 2, '567890', '', 0);
INSERT IGNORE INTO `ClientContact` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `contactType_id`, `contact`, `notes`, `version`) VALUES (3366, '2013-01-10 14:43:32', 369, '2013-07-29 08:48:28', 369, 0, 347610, 3, '+7-999-888-77-66', '', 0);
INSERT IGNORE INTO `ClientContact` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `contactType_id`, `contact`, `notes`, `version`) VALUES (3367, '2013-01-10 14:43:32', 369, '2013-07-29 08:48:28', 369, 0, 347610, 5, 'M010120141', '', 0);
UPDATE `EventType` SET `scene_id`=2 WHERE  `id`=13;
INSERT IGNORE INTO `Action` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `actionType_id`, `event_id`, `idx`, `directionDate`, `status`, `setPerson_id`, `isUrgent`, `begDate`, `plannedEndDate`, `endDate`, `note`, `person_id`, `office`, `amount`, `uet`, `expose`, `payStatus`, `account`, `finance_id`, `prescription_id`, `takenTissueJournal_id`, `contract_id`, `coordDate`, `coordAgent`, `coordInspector`, `coordText`, `hospitalUidFrom`, `pacientInQueueType`, `AppointmentType`, `dcm_study_uid`, `version`, `parentAction_id`, `uuid_id`) VALUES (793630, '2014-01-22 10:40:36', 458, '2014-01-22 15:45:22', 311, 0, 112, 841672, 0, '2014-01-21 23:20:46', 2, 458, 0, '2014-01-21 23:20:40', '0000-00-00 00:00:00', '2014-01-21 23:39:40', '', 391, '', 1, 0, 1, 0, 0, NULL, NULL, NULL, NULL, NULL, '', '', '', '0', 0, '0', NULL, 2, NULL, 2850832);
INSERT IGNORE INTO `ActionProperty` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) VALUES (3790912, '2014-01-22 10:40:36', 458, '2014-01-22 10:40:36', 458, 0, 793630, 1613, NULL, '', 0, NULL, 0);
INSERT IGNORE INTO `ActionProperty` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) VALUES (3790908, '2014-01-22 10:40:36', 458, '2014-01-22 10:44:56', 458, 0, 793630, 1607, NULL, '', 0, NULL, 1);
INSERT IGNORE INTO `ActionProperty` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) VALUES (3790913, '2014-01-22 10:40:36', 458, '2014-01-22 10:44:56', 458, 0, 793630, 1614, NULL, '', 0, NULL, 1);
INSERT IGNORE INTO `ActionProperty` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) VALUES (3790905, '2014-01-22 10:40:36', 458, '2014-01-22 10:44:56', 458, 0, 793630, 1601, NULL, '', 0, NULL, 1);
INSERT IGNORE INTO `ActionProperty` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) VALUES (3790911, '2014-01-22 10:40:36', 458, '2014-01-22 10:44:56', 458, 0, 793630, 1612, NULL, '', 0, NULL, 1);
INSERT IGNORE INTO `ActionProperty_String` (`id`, `index`, `value`) VALUES (3790912, 0, 'по экстренным показаниям');
INSERT IGNORE INTO `ActionProperty_String` (`id`, `index`, `value`) VALUES (3790908, 0, 'в течение 7-24 часов');
INSERT IGNORE INTO `ActionProperty_String` (`id`, `index`, `value`) VALUES (3790913, 0, 'на руках');
INSERT IGNORE INTO `ActionProperty_String` (`id`, `index`, `value`) VALUES (3790905, 0, 'Самостоятельно');
INSERT IGNORE INTO `ActionProperty_String` (`id`, `index`, `value`) VALUES (3790911, 0, 'первично');


INSERT IGNORE INTO `Action` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `actionType_id`, `event_id`, `idx`, `directionDate`, `status`, `setPerson_id`, `isUrgent`, `begDate`, `plannedEndDate`, `endDate`, `note`, `person_id`, `office`, `amount`, `uet`, `expose`, `payStatus`, `account`, `finance_id`, `prescription_id`, `takenTissueJournal_id`, `contract_id`, `coordDate`, `coordAgent`, `coordInspector`, `coordText`, `hospitalUidFrom`, `pacientInQueueType`, `AppointmentType`, `dcm_study_uid`, `version`, `parentAction_id`, `uuid_id`) VALUES (793635, '2014-01-22 10:41:11', 458, '2014-01-23 14:04:13', 311, 0, 113, 841672, 0, '2014-01-21 23:20:46', 2, 391, 0, '2014-01-21 23:45:38', '0000-00-00 00:00:00', '2014-01-22 15:44:03', '', 391, '', 1, 0, 1, 0, 0, NULL, NULL, NULL, NULL, NULL, '', '', '', '0', 0, '0', NULL, 4, NULL, 2850840);
INSERT IGNORE INTO `ActionProperty` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) VALUES (3790964, '2014-01-22 10:41:11', 458, '2014-01-22 10:41:11', 458, 0, 793635, 7021, NULL, '', 0, NULL, 0);
INSERT IGNORE INTO `ActionProperty_OrgStructure` (`id`, `index`, `value`) VALUES (3790964, 0, 51);
INSERT IGNORE INTO `ActionProperty` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) VALUES (3790958, '2014-01-22 10:41:11', 458, '2014-01-22 15:44:15', 311, 0, 793635, 1616, NULL, '', 0, NULL, 1);
INSERT IGNORE INTO `ActionProperty_HospitalBed` (`id`, `index`, `value`) VALUES (3790958, 0, 397);
INSERT IGNORE INTO `Action` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `actionType_id`, `event_id`, `idx`, `directionDate`, `status`, `setPerson_id`, `isUrgent`, `begDate`, `plannedEndDate`, `endDate`, `note`, `person_id`, `office`, `amount`, `uet`, `expose`, `payStatus`, `account`, `finance_id`, `prescription_id`, `takenTissueJournal_id`, `contract_id`, `coordDate`, `coordAgent`, `coordInspector`, `coordText`, `hospitalUidFrom`, `pacientInQueueType`, `AppointmentType`, `dcm_study_uid`, `version`, `parentAction_id`, `uuid_id`) VALUES (794636, '2014-01-10 10:41:11', 458, '2014-01-23 14:04:13', 311, 0, 113, 841672, 0, '2014-01-21 23:20:46', 2, 391, 0, '2014-01-21 23:45:38', '0000-00-00 00:00:00', '2014-01-22 15:44:03', '', 391, '', 1, 0, 1, 0, 0, NULL, NULL, NULL, NULL, NULL, '', '', '', '0', 0, '0', NULL, 4, NULL, 2850840);
INSERT IGNORE INTO `ActionProperty` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) VALUES (3791964, '2014-01-22 10:41:11', 458, '2014-01-22 10:41:11', 458, 0, 794636, 7021, NULL, '', 0, NULL, 0);
INSERT IGNORE INTO `ActionProperty_OrgStructure` (`id`, `index`, `value`) VALUES (3791964, 0, 50);
INSERT IGNORE INTO `Diagnostic` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `event_id`, `diagnosis_id`, `diagnosisType_id`, `character_id`, `stage_id`, `phase_id`, `dispanser_id`, `sanatorium`, `hospital`, `traumaType_id`, `speciality_id`, `person_id`, `healthGroup_id`, `result_id`, `setDate`, `endDate`, `notes`, `rbAcheResult_id`, `version`, `action_id`) VALUES (377605, '2014-01-22 15:43:14', 311, '2014-01-23 14:05:03', 311, 0, 841672, 342569, 1, 1, NULL, NULL, 2, 0, 0, 2, 52, 458, NULL, NULL, '2014-01-21 23:20:46', '2014-01-22 15:45:33', '', NULL, 0, NULL);
INSERT IGNORE INTO `Diagnosis` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `diagnosisType_id`, `character_id`, `MKB`, `MKBEx`, `dispanser_id`, `traumaType_id`, `setDate`, `endDate`, `mod_id`, `person_id`) VALUES (342569, '2014-01-22 15:43:14', 311, '2014-01-23 14:05:03', 311, 0, 347610, 1, 1, 'J06.9', '', NULL, NULL, '2014-01-22', '2014-01-22', NULL, 458);
INSERT IGNORE INTO `Diagnostic` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `event_id`, `diagnosis_id`, `diagnosisType_id`, `character_id`, `stage_id`, `phase_id`, `dispanser_id`, `sanatorium`, `hospital`, `traumaType_id`, `speciality_id`, `person_id`, `healthGroup_id`, `result_id`, `setDate`, `endDate`, `notes`, `rbAcheResult_id`, `version`, `action_id`) VALUES (379118, '2014-01-23 14:05:03', 311, '2014-01-23 14:05:03', 311, 0, 841672, 343841, 5, 1, NULL, NULL, 3, 0, 0, 3, 52, 458, NULL, NULL, '2014-01-21 23:20:46', '2014-01-22 15:45:33', '', NULL, 0, NULL);
INSERT IGNORE INTO `Diagnosis` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `diagnosisType_id`, `character_id`, `MKB`, `MKBEx`, `dispanser_id`, `traumaType_id`, `setDate`, `endDate`, `mod_id`, `person_id`) VALUES (343841, '2014-01-23 14:05:03', 311, '2014-01-23 14:05:03', 311, 0, 347610, 5, 2, 'T45.0', '', NULL, NULL, '2014-01-22', '2014-01-22', NULL, 458);

INSERT IGNORE INTO `ClientSocStatus` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `socStatusClass_id`, `socStatusType_id`, `begDate`, `endDate`, `document_id`, `version`, `note`, `benefitCategory_id`) VALUES (60, '2013-02-01 14:51:12', 305, '2013-11-01 11:53:32', 382, 0, 347610, 32, 26, '2000-01-25', '2014-01-13', NULL, 0, '', NULL);
INSERT IGNORE INTO `ClientSocStatus` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `socStatusClass_id`, `socStatusType_id`, `begDate`, `endDate`, `document_id`, `version`, `note`, `benefitCategory_id`) VALUES (61, '2013-02-01 14:51:12', 305, '2014-11-01 11:00:00', 382, 0, 347610, 32, 28, '2001-01-25', '2015-01-13', NULL, 0, '', NULL);
INSERT IGNORE INTO `ClientSocStatus` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `socStatusClass_id`, `socStatusType_id`, `begDate`, `endDate`, `document_id`, `version`, `note`, `benefitCategory_id`) VALUES (62, '2013-02-01 14:51:12', 305, '2015-11-01 12:00:11', 382, 0, 347610, 32, 29, '2002-01-25', '2016-01-13', NULL, 0, '', NULL);
UPDATE `rbSocStatusType` SET `code`='082' WHERE  `id`=26;
UPDATE `rbSocStatusType` SET `code`='088' WHERE  `id`=29;
INSERT IGNORE INTO `ClientAllergy` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `client_id`, `nameSubstance`, `power`, `createDate`, `notes`, `version`) VALUES (2, '2012-12-05 10:47:12', 458, '2013-10-04 14:27:31', 170, 0, 347610, 'цитрусовые', 0, NULL, 'test note', 0);

