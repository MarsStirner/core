INSERT INTO NotificationActionType (actionType_id, baseUrl)
  SELECT
    ActionType.id,
    "http://localhost:8080/ws-risar/api/notification/new/exam/"
  FROM ActionType
  WHERE ActionType.id IN (SELECT
                            ActionPropertyType.actionType_id
                          FROM ActionPropertyType
                          WHERE `ActionPropertyType`.typeName = "MKB") AND
        ActionType.id IN (SELECT
                            ActionPropertyType.actionType_id
                          FROM ActionPropertyType
                          WHERE `ActionPropertyType`.name LIKE "Рекоменд%");

UPDATE `ActionPropertyType`
SET `code` = 'diagnosis'
WHERE `ActionPropertyType`.typeName = "MKB" AND
      `code` IS NULL AND
      `ActionPropertyType`.actionType_id IN (SELECT
                                               NotificationActionType.actionType_id
                                             FROM NotificationActionType );
UPDATE `ActionPropertyType`
SET `code` = 'recommended'
WHERE `ActionPropertyType`.name LIKE "Рекоменд%" AND
      `code` IS NULL AND
      `ActionPropertyType`.actionType_id IN (SELECT
                                               NotificationActionType.actionType_id
                                             FROM NotificationActionType );


