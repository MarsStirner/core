INSERT INTO `Person`
(`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `code`, `federalCode`, `regionalCode`, `lastName`, `firstName`, `patrName`, `post_id`, `speciality_id`, `org_id`, `orgStructure_id`, `office`, `office2`, `tariffCategory_id`, `finance_id`, `retireDate`, `ambPlan`, `ambPlan2`, `ambNorm`, `homPlan`, `homPlan2`, `homNorm`, `expPlan`, `expNorm`, `login`, `password`, `userProfile_id`, `retired`, `birthDate`, `birthPlace`, `sex`, `SNILS`, `INN`, `availableForExternal`, `primaryQuota`, `ownQuota`, `consultancyQuota`, `externalQuota`, `lastAccessibleTimelineDate`, `timelineAccessibleDays`, `typeTimeLinePerson`, `maxOverQueue`, `maxCito`, `quotUnit`, `academicdegree_id`, `academicTitle_id`, `uuid_id`, `displayInTimeline`)
  VALUES
  (41, '2014-03-05 16:37:24', NULL, '2014-03-05 16:37:24', NULL, 0, 'code', '', '', 'test', 'test', 'test', NULL, 1, NULL, 24, '', '', NULL, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 'utest', '098f6bcd4621d373cade4e832627b4f6', 29, 0, '1970-01-01', '', 0, '', '', 0, 50, 25, 25, 10, NULL, 0, 0, 0, 0, NULL, NULL, NULL, 984153, 1);

SET @last = LAST_INSERT_ID();
INSERT INTO Person_Profiles (userProfile_id, person_id) VALUES (29, 41);

INSERT INTO `rbBloodComponentType` (`code`, `name`) VALUES ('02.02.003', 'Плазма свежезамороженная, полученная автоматическим аферезом');
INSERT INTO `rbBloodComponentType` (`code`, `name`) VALUES ('02.01.020', 'Тромбоцитный концентрат, полученный автоматическим аферезом');
INSERT INTO `rbBloodComponentType` (`code`, `name`) VALUES ('01.01.010', 'Эритроцитная взвесь с ресуспендирующим раствором, фильтрованная');
INSERT INTO `rbBloodComponentType` (`code`, `name`) VALUES ('01.03.001', 'Кровь консервированная');
INSERT INTO `rbBloodComponentType` (`code`, `name`) VALUES ('09.01.001', 'Лейкотромбомасса с фильтром');
INSERT INTO `rbBloodComponentType` (`code`, `name`) VALUES ('02.02.001', 'Плазма свежезамороженная из дозы крови');
INSERT INTO `rbBloodComponentType` (`code`, `name`) VALUES ('02.02.008', 'Плазма свежезамороженная из дозы крови    Карантинизовано 6 месяцев');
INSERT INTO `rbBloodComponentType` (`code`, `name`) VALUES ('01.01.005', 'Эритроцитная масса с удаленным лейкотромбослоем');
INSERT INTO `rbBloodComponentType` (`code`, `name`) VALUES ('01.01.002', 'Эритроцитная масса, фильтрованная');
INSERT INTO `rbBloodComponentType` (`code`, `name`) VALUES ('01.02.002', 'Аутоэритроцитная масса, фильтрованная');
INSERT INTO `rbBloodComponentType` (`code`, `name`) VALUES ('01.01.009', 'Эритроцитная взвесь с ресуспендирующим раствором');
INSERT INTO `rbBloodComponentType` (`code`, `name`) VALUES ('02.03.001', 'Аутоплазма свежезамороженная из дозы крови');
INSERT INTO `rbBloodComponentType` (`code`, `name`) VALUES ('01.01.014', 'Эритроцитарная взвесь, лейкофильтрованная, полученная автоматическим аферезом');
INSERT INTO `rbBloodComponentType` (`code`, `name`) VALUES ('03.01.006', 'Лейкоцитный концентрат');

INSERT INTO `Event`
(`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `externalId`, `eventType_id`, `org_id`, `client_id`, `contract_id`, `prevEventDate`, `setDate`, `setPerson_id`, `execDate`, `execPerson_id`, `isPrimary`, `order`, `result_id`, `nextEventDate`, `payStatus`, `typeAsset_id`, `note`, `curator_id`, `assistant_id`, `pregnancyWeek`, `MES_id`, `mesSpecification_id`, `rbAcheResult_id`, `version`, `privilege`, `urgent`, `orgStructure_id`, `uuid_id`, `lpu_transfer`)
  VALUES
  (841695, '2014-03-05 15:44:43', 37, '2014-03-05 15:44:44', 37, 0, '2014/474', 2, NULL, 2, 61, NULL, '2014-03-05 15:44:43', 37, NULL, 37, 2, 1, NULL, NULL, 0, NULL, ' ', NULL, NULL, 0, NULL, NULL, NULL, 2, 0, 0, NULL, 984138, NULL);

UPDATE `ActionType` SET `defaultPlannedEndDate`=0 WHERE  `id`=3911;
UPDATE `Contract` SET `endDate`='2020-12-31' WHERE  `id`=61;
