###############################################################################
# Поддержка уведомлений о создании новых действий
###############################################################################

CREATE TABLE IF NOT EXISTS `NotificationActionType` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`actionType_id` INT(11) NOT NULL,
	`baseUrl` VARCHAR(2048) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `NotificationActionType_userId_fk` (`actionType_id`),
	CONSTRAINT `NotificationActionType_actionType_id_fk` FOREIGN KEY (`actionType_id`) REFERENCES `ActionType` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COMMENT='Notificated ActionType List'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `NotificationAction` (
  `action_id` INT(11) NOT NULL COMMENT 'Идентификатор события {Action.id}',
  `sendTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Время следующего уведомления',
  `errCount` INT(11) NOT NULL DEFAULT '0' COMMENT 'Количество неудачных попыток',
  `info` VARCHAR(1024) NULL DEFAULT NULL COMMENT 'Сообщение об ошибке',
  PRIMARY KEY (`action_id`),
  CONSTRAINT `FK__Action_id` FOREIGN KEY (`action_id`) REFERENCES `Action` (`id`)
)
COMMENT='новые действия для нотификации'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `ActionToODVD` (
  `action_id` INT(11) NOT NULL COMMENT 'Идентификатор события {Action.id}',
  `sendTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Время следующей отсылки в 1С ОДВД',
  `errCount` INT(11) NOT NULL DEFAULT '0' COMMENT 'Количество неудачных попыток',
  `info` VARCHAR(1024) NULL DEFAULT NULL COMMENT 'Сообщение об ошибке',
  PRIMARY KEY (`action_id`),
  CONSTRAINT `FK__Action_id` FOREIGN KEY (`action_id`) REFERENCES `Action` (`id`)
)
  COMMENT='события для передачи в 1С ОДВД'
  COLLATE='utf8_general_ci'
  ENGINE=InnoDB;

INSERT INTO NotificationActionType (actionType_id, baseUrl) SELECT ActionType.id, "http://localhost:8080/ws-risar//api/notification/new/exam/" FROM ActionType WHERE ActionType.MNEM like "EXAM"

delimiter //
CREATE TRIGGER `onInsertAction` AFTER INSERT ON `Action` FOR EACH ROW BEGIN
  DECLARE flatCode VARCHAR(64);
  SELECT `ActionType`.`flatCode` INTO flatCode FROM `ActionType` WHERE `ActionType`.`id` = NEW.`actionType_id`;
  IF flatCode LIKE "received"
     OR flatCode LIKE "del_received"
     OR flatCode LIKE "moving"
     OR flatCode LIKE "del_moving"
     OR flatCode LIKE "leaved" THEN
    INSERT IGNORE INTO `Pharmacy` (actionId, flatCode) VALUES (NEW.id, flatCode);
  END IF;
  IF NEW.actionType_id IN (SELECT NotificationActionType.actionType_id FROM NotificationActionType) THEN
    INSERT IGNORE INTO `NotificationAction` (action_id) VALUES (NEW.id);
  END IF;
  IF isPaidAction(NEW.id) AND NEW.status = 2 AND NEW.endDate IS NOT NULL THEN
    INSERT IGNORE INTO `ActionToODVD` (action_id) VALUES (NEW.id);
  END IF;
END//
CREATE  FUNCTION `isPaidAction`(`action_id` INT)
  RETURNS tinyint(4)
LANGUAGE SQL
NOT DETERMINISTIC
READS SQL DATA
  SQL SECURITY DEFINER
  COMMENT 'Возвращает 1 для платных действий'
  BEGIN
    DECLARE finaceCode VARCHAR(10);
    SELECT  rbFinance.code  INTO finaceCode FROM
                                                `Action` INNER JOIN
                                                `Event` ON `Action`.event_id = `Event`.id INNER JOIN
                                                `EventType` ON `Event`.eventType_id = `EventType`.id INNER JOIN
                                                `rbFinance` ON `EventType`.finance_id = `rbFinance`.id
    WHERE `Action`.id = action_id;
    IF finaceCode LIKE '4' THEN
      RETURN 1;
    END IF;
    RETURN  0;
END//
delimiter ;