INSERT IGNORE INTO `Person`
(`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `code`, `federalCode`, `regionalCode`, `lastName`, `firstName`, `patrName`, `post_id`, `speciality_id`, `org_id`, `orgStructure_id`, `office`, `office2`, `tariffCategory_id`, `finance_id`, `retireDate`, `ambPlan`, `ambPlan2`, `ambNorm`, `homPlan`, `homPlan2`, `homNorm`, `expPlan`, `expNorm`, `login`, `password`, `userProfile_id`, `retired`, `birthDate`, `birthPlace`, `sex`, `SNILS`, `INN`, `availableForExternal`, `primaryQuota`, `ownQuota`, `consultancyQuota`, `externalQuota`, `lastAccessibleTimelineDate`, `timelineAccessibleDays`, `typeTimeLinePerson`, `maxOverQueue`, `maxCito`, `quotUnit`, `academicdegree_id`, `academicTitle_id`, `uuid_id`, `displayInTimeline`)
  VALUES
  (41, '2014-03-05 16:37:24', NULL, '2014-03-05 16:37:24', NULL, 0, 'code', '', '', 'test', 'test', 'test', NULL, 1, NULL, 24, '', '', NULL, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 'utest', '098f6bcd4621d373cade4e832627b4f6', 29, 0, '1970-01-01', '', 0, '', '', 0, 50, 25, 25, 10, NULL, 0, 0, 0, 0, NULL, NULL, NULL, 984153, 1);

SET @last = LAST_INSERT_ID();
INSERT IGNORE INTO Person_Profiles (userProfile_id, person_id) VALUES (29, 41);

ALTER TABLE `rbTrfuBloodComponentType`AUTO_INCREMENT=1;
INSERT IGNORE INTO `rbBloodComponentType` (`code`, `name`) VALUES ('02.02.003', 'Плазма свежезамороженная, полученная автоматическим аферезом');
INSERT IGNORE INTO `rbBloodComponentType` (`code`, `name`) VALUES ('02.01.020', 'Тромбоцитный концентрат, полученный автоматическим аферезом');
INSERT IGNORE INTO `rbBloodComponentType` (`code`, `name`) VALUES ('01.01.010', 'Эритроцитная взвесь с ресуспендирующим раствором, фильтрованная');
INSERT IGNORE INTO `rbBloodComponentType` (`code`, `name`) VALUES ('01.03.001', 'Кровь консервированная');
INSERT IGNORE INTO `rbBloodComponentType` (`code`, `name`) VALUES ('09.01.001', 'Лейкотромбомасса с фильтром');
INSERT IGNORE INTO `rbBloodComponentType` (`code`, `name`) VALUES ('02.02.001', 'Плазма свежезамороженная из дозы крови');
INSERT IGNORE INTO `rbBloodComponentType` (`code`, `name`) VALUES ('02.02.008', 'Плазма свежезамороженная из дозы крови    Карантинизовано 6 месяцев');
INSERT IGNORE INTO `rbBloodComponentType` (`code`, `name`) VALUES ('01.01.005', 'Эритроцитная масса с удаленным лейкотромбослоем');
INSERT IGNORE INTO `rbBloodComponentType` (`code`, `name`) VALUES ('01.01.002', 'Эритроцитная масса, фильтрованная');
INSERT IGNORE INTO `rbBloodComponentType` (`code`, `name`) VALUES ('01.02.002', 'Аутоэритроцитная масса, фильтрованная');
INSERT IGNORE INTO `rbBloodComponentType` (`code`, `name`) VALUES ('01.01.009', 'Эритроцитная взвесь с ресуспендирующим раствором');
INSERT IGNORE INTO `rbBloodComponentType` (`code`, `name`) VALUES ('02.03.001', 'Аутоплазма свежезамороженная из дозы крови');
INSERT IGNORE INTO `rbBloodComponentType` (`code`, `name`) VALUES ('01.01.014', 'Эритроцитарная взвесь, лейкофильтрованная, полученная автоматическим аферезом');
INSERT IGNORE INTO `rbBloodComponentType` (`code`, `name`) VALUES ('03.01.006', 'Лейкоцитный концентрат');
SET FOREIGN_KEY_CHECKS = 0;
DELETE FROM `trfuOrderIssueResult`;
INSERT INTO `trfuOrderIssueResult` (`id`, `action_id`, `trfu_blood_comp`, `comp_number`, `comp_type_id`, `blood_type_id`, `volume`, `dose_count`, `trfu_donor_id`, `stickerUrl`) VALUES (1, 260, 56839, '17974', 5, 8, 280, 1, 8672, '/UNDEFINED/56839.png');
SET FOREIGN_KEY_CHECKS = 1;

INSERT IGNORE INTO `Event`
(`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `externalId`, `eventType_id`, `org_id`, `client_id`, `contract_id`, `prevEventDate`, `setDate`, `setPerson_id`, `execDate`, `execPerson_id`, `isPrimary`, `order`, `result_id`, `nextEventDate`, `payStatus`, `typeAsset_id`, `note`, `curator_id`, `assistant_id`, `pregnancyWeek`, `MES_id`, `mesSpecification_id`, `rbAcheResult_id`, `version`, `privilege`, `urgent`, `orgStructure_id`, `uuid_id`, `lpu_transfer`)
  VALUES
  (841695, '2014-03-05 15:44:43', 37, '2014-03-05 15:44:44', 37, 0, '2014/474', 2, NULL, 2, 61, NULL, '2014-03-05 15:44:43', 37, NULL, 37, 2, 1, NULL, NULL, 0, NULL, ' ', NULL, NULL, 0, NULL, NULL, NULL, 2, 0, 0, NULL, 984138, NULL);

UPDATE `ActionType` SET `defaultPlannedEndDate`=0 WHERE  `id`=3911;
UPDATE `Contract` SET `endDate`='2020-12-31' WHERE  `id`=61;

INSERT IGNORE INTO `DrugChart` (`id`, `action_id`, `master_id`, `begDateTime`, `endDateTime`, `status`, `statusDateTime`, `note`, `uuid`, `version`) VALUES (14, 259, NULL, '2014-06-11 15:00:00', '2014-06-13 15:01:00', 0, '2014-05-21 14:26:58', NULL, 'b5ae2f0f-0c3f-4e88-addc-7c702672fe52', NULL);
INSERT IGNORE INTO `DrugChart` (`id`, `action_id`, `master_id`, `begDateTime`, `endDateTime`, `status`, `statusDateTime`, `note`, `uuid`, `version`) VALUES (15, 259, 14, '2014-05-22 15:02:00', '2014-05-30 15:03:00', 0, '2014-05-21 14:05:41', '', '6c296380-6026-4b48-b414-450999b29379', NULL);

INSERT IGNORE INTO `rbAcheResult` (`eventPurpose_id`, `code`, `name`) VALUES (8, '105', 'Выздоровление');