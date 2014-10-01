###############################################################################
# Поддержка уведомлений о создании новых действий
###############################################################################

DROP TRIGGER `onInsertAction`;
DELIMITER //
CREATE TRIGGER `onInsertAction` AFTER INSERT ON `ACTION` FOR EACH ROW BEGIN
  DECLARE flatCode VARCHAR(64);
  SELECT
    `ActionType`.`flatCode`
  INTO flatCode
  FROM `ActionType`
  WHERE `ActionType`.`id` = NEW.`actionType_id`;
  IF flatCode LIKE "received"
     OR flatCode LIKE "del_received"
     OR flatCode LIKE "moving"
     OR flatCode LIKE "del_moving"
     OR flatCode LIKE "leaved"
  THEN
    INSERT IGNORE INTO `Pharmacy` (actionId, flatCode) VALUES (NEW.id, flatCode);
  END IF;
  IF NEW.actionType_id IN (SELECT
                             NotificationActionType.actionType_id
                           FROM NotificationActionType)
  THEN
    INSERT IGNORE INTO `NotificationAction` (action_id, method) VALUES (NEW.id, 'POST');
  END IF;
  IF isPaidAction(NEW.id) AND NEW.status = 2 AND NEW.endDate IS NOT NULL
  THEN
    INSERT IGNORE INTO `ActionToODVD` (action_id) VALUES (NEW.id);
  END IF;
END//
DROP TRIGGER `OnUpdateAction`//
CREATE TRIGGER `onUpdateAction` AFTER UPDATE ON `ACTION` FOR EACH ROW BEGIN
  IF NEW.deleted IS NOT NULL AND NEW.deleted != OLD.deleted
  THEN
    UPDATE ActionProperty
    SET deleted = NEW.deleted
    WHERE action_id = NEW.id;
  END IF;
  IF isPaidAction(NEW.id) AND (NEW.status != OLD.status OR (OLD.endDate IS NULL AND NEW.endDate IS NOT NULL)) AND (NEW.status = 2 AND NEW.endDate IS NOT NULL)
  THEN
    INSERT IGNORE INTO `ActionToODVD` (action_id) VALUES (NEW.id);
  END IF;
  IF NEW.actionType_id IN (SELECT
                             NotificationActionType.actionType_id
                           FROM NotificationActionType)
  THEN
    INSERT IGNORE INTO `NotificationAction` (action_id, method) VALUES (NEW.id, 'PUT');
  END IF;
END//
DELIMITER;