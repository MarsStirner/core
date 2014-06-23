INSERT IGNORE INTO `Action` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `actionType_id`, `event_id`, `idx`, `directionDate`, `status`, `setPerson_id`, `isUrgent`, `begDate`, `plannedEndDate`, `endDate`, `note`, `person_id`, `office`, `amount`, `uet`, `expose`, `payStatus`, `account`, `finance_id`, `prescription_id`, `takenTissueJournal_id`, `contract_id`, `coordDate`, `coordAgent`, `coordInspector`, `coordText`, `hospitalUidFrom`, `pacientInQueueType`, `AppointmentType`, `version`, `parentAction_id`, `uuid_id`, `dcm_study_uid`) VALUES (267, '2014-06-22 10:08:18', 1, '2014-06-22 10:08:18', 1, 0, 123, 189, 0, '2014-06-22 10:07:23', 0, NULL, 0, '2014-06-22 10:07:23', '2014-06-24 07:00:00', NULL, '', 1, '', 0, 0, 1, 0, 0, NULL, NULL, NULL, NULL, NULL, '', '', '', '0', 0, '0', 0, NULL, 1188116, NULL);

DELETE FROM `PrescriptionsTo1C`;
DELETE FROM `PrescriptionSendingRes`;
DELETE FROM `DrugChart`;
DELETE FROM `DrugComponent`;
INSERT IGNORE INTO `DrugChart` (`id`, `action_id`, `master_id`, `begDateTime`, `endDateTime`, `status`, `statusDateTime`, `note`, `uuid`, `version`) VALUES (14, 267, NULL, '2014-06-22 11:00:00', NULL, 0, '2014-06-22 10:08:05', '', NULL, NULL);
INSERT IGNORE INTO `DrugChart` (`id`, `action_id`, `master_id`, `begDateTime`, `endDateTime`, `status`, `statusDateTime`, `note`, `uuid`, `version`) VALUES (15, 267, 14, '2014-06-22 11:00:00', NULL, 0, '2014-06-22 10:08:05', '', NULL, NULL);
INSERT IGNORE INTO `DrugComponent` (`id`, `action_id`, `nomen`, `name`, `dose`, `unit`, `createDateTime`, `cancelDateTime`) VALUES (8, 267, 19040, 'Анальгин', 11, 327, '2014-06-22 10:08:18', NULL);

INSERT IGNORE INTO `ActionProperty` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) VALUES (1522, '2014-06-22 10:08:18', 1, '2014-06-22 10:08:18', 1, 0, 267, 24638, NULL, '', 0, NULL, 0);
INSERT IGNORE INTO `ActionProperty` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) VALUES (1521, '2014-06-22 10:08:18', 1, '2014-06-22 10:08:18', 1, 0, 267, 5176, NULL, '', 0, NULL, 0);
INSERT IGNORE INTO `ActionProperty` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) VALUES (1520, '2014-06-22 10:08:18', 1, '2014-06-22 10:08:18', 1, 0, 267, 1811, NULL, '', 0, NULL, 0);
INSERT IGNORE INTO `ActionProperty` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) VALUES (1519, '2014-06-22 10:08:18', 1, '2014-06-22 10:08:18', 1, 0, 267, 1810, NULL, '', 0, NULL, 0);
INSERT IGNORE INTO `ActionProperty` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) VALUES (1518, '2014-06-22 10:08:18', 1, '2014-06-22 10:08:18', 1, 0, 267, 11848, NULL, '', 0, NULL, 0);
INSERT IGNORE INTO `ActionProperty` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) VALUES (1517, '2014-06-22 10:08:18', 1, '2014-06-22 10:08:18', 1, 0, 267, 24643, NULL, '', 0, NULL, 0);
INSERT IGNORE INTO `ActionProperty` (`id`, `createDatetime`, `createPerson_id`, `modifyDatetime`, `modifyPerson_id`, `deleted`, `action_id`, `type_id`, `unit_id`, `norm`, `isAssigned`, `evaluation`, `version`) VALUES (1516, '2014-06-22 10:08:18', 1, '2014-06-22 10:08:18', 1, 0, 267, 24642, NULL, '', 0, NULL, 0);

INSERT IGNORE INTO `ActionProperty_Integer` (`id`, `index`, `value`) VALUES (1521, 0, 3);

INSERT IGNORE INTO `DrugChart` (`id`, `action_id`, `master_id`, `begDateTime`, `endDateTime`, `status`, `statusDateTime`, `note`, `uuid`, `version`) VALUES (14, 267, NULL, '2014-06-22 11:00:00', NULL, 0, '2014-06-22 10:08:05', '', NULL, NULL);
INSERT IGNORE INTO `DrugChart` (`id`, `action_id`, `master_id`, `begDateTime`, `endDateTime`, `status`, `statusDateTime`, `note`, `uuid`, `version`) VALUES (15, 267, 14, '2014-06-22 11:00:00', NULL, 0, '2014-06-22 10:08:05', '', NULL, NULL);

INSERT IGNORE INTO `DrugComponent` (`id`, `action_id`, `nomen`, `name`, `dose`, `unit`, `createDateTime`, `cancelDateTime`) VALUES (8, 267, 19040, 'Анальгин', 11, 327, '2014-06-22 10:08:18', NULL);

UPDATE `pharmacy` SET `status`='COMPLETE';