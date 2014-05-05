-- Запрос на доплнительные обращения в диспансеризации (Formatted to String.format [percent char doubled])
SELECT
-- генерирование уникального номера строки
@serial := @serial+1 AS 'ID',
-- Единица учета
AID_UNIT.id AS 'unitId',
-- Стоматология имеет коды услуг, начинающиеся с символа 'T'
MEDICAL_KIND.code AS 'type',
-- пациент
PATIENT.id AS 'clientId',
-- представитель пациента
SPOKESMAN.id AS 'spokesmanId',
-- полис пациента (если нету, то представителя)
COALESCE(PATIENT_POLICY.id, SPOKESMAN_POLICY.id) AS 'clientPolicyId',
-- Страховщик клиента (страховая, выдавшая полис)
INSURER.id AS 'insurerId',
-- Документы пациента (если нету, то представителя)
COALESCE(PATIENT_DOCUMENT.id, SPOKESMAN_DOCUMENT.id) AS 'clientDocumentId',
-- NOVOR признак новорожденного
IF( TIMESTAMPDIFF(DAY, PATIENT.birthDate, ACT.endDate) < 28 AND PATIENT_POLICY.id IS NULL,
    CONCAT(PATIENT.sex, date_format(PATIENT.birthDate, '%%d%%m%%y'), IF(ClientContact.id IS NOT NULL, ClientContact.contact, '1')),
    '0')
AS 'NOVOR',
-- идентификатор обращения
E.id AS 'eventId',
-- результат обращения
RSLT.id AS 'RSLT',
-- код исхода заболевания
ISHOD.code AS 'ISHOD',
-- Условия оказания мед. помощи
E_TYPE_PURPOSE.codePlace AS 'USL_OK',
-- Вид помощи
AID_TYPE.code AS 'VIDPOM',
-- Главная диагностика
MAIN_DIAGNOSTIC.id AS 'mainDiagnosticId',
-- Значение основного диагноза
DS1.MKB AS 'DS1',
-- Стадия заболевания
DS0.code AS 'DS0',
-- Сопутствующий диагноз
DS2.MKB AS 'DS2',
-- идентификатор действия
ACT.id AS 'actionId',
-- Лечащий врач
STAFF.id AS 'executorId',
-- дата завершения услуги
ACT.endDate AS 'checkDate',
-- идентификатор оказанной услуги
SERVICE.id AS 'rbServiceId',
-- код профиля помощи
PROFIL.code AS 'PROFIL',
-- идентификатор записи-связки
MKU.id AS 'medicalKindUnitId',
-- Код способа оплаты
IDSP.code AS 'IDSP',
-- идентификатор тарифа
TARIFF.id AS 'contractTariffId',
-- цена тарифа (Если источник финансирования не 2, то цена тарифа, иначе 0 )
IF(FINANCE.code <> '2', IF(LEFT(SERVICE.code,1) = 'T' AND TARIFF.uet <> 0, TARIFF.price / TARIFF.uet ,TARIFF.price), 0) AS 'price',
-- источник финансирования
FINANCE.id AS 'financeId'
-- ------------------------------------------------
FROM Action ACT
-- Событие связанное с действием
INNER JOIN Event E ON E.id = ACT.event_id
-- Тип события связанный с событием
INNER JOIN EventType E_TYPE ON E.eventType_id = E_TYPE.id
-- Назначение типа события (по типу события)
INNER JOIN rbEventTypePurpose E_TYPE_PURPOSE ON E_TYPE.purpose_id= E_TYPE_PURPOSE.id
-- Тип обращения
INNER JOIN rbRequestType E_TYPE_REQUEST ON E_TYPE_REQUEST.id = E_TYPE.requestType_id AND (E_TYPE_REQUEST.code = 'policlinic' OR E_TYPE_REQUEST.code = '4' OR E_TYPE_REQUEST.code = '5' OR E_TYPE_REQUEST.code = '6')
-- Пациент, связанный с событием
INNER JOIN Client PATIENT ON PATIENT.id = E.client_id
-- Документы пациента
LEFT JOIN ClientDocument PATIENT_DOCUMENT ON PATIENT_DOCUMENT.id = ( SELECT MAX(DOC.id)
                                                                       FROM ClientDocument DOC
                                                                         INNER JOIN rbDocumentType DOC_TYPE ON DOC_TYPE.id = DOC.documentType_id
                                                                       WHERE DOC_TYPE.group_id = %d
                                                                             AND DOC.client_id = PATIENT.id
                                                                             AND DOC.deleted <> 1
                                                                             AND (DOC.date = '0000-00-00' OR DOC.date IS NULL OR DOC.date <= DATE(ACT.endDate))
)
-- Полис ОМС клиента
LEFT JOIN ClientPolicy PATIENT_POLICY ON PATIENT_POLICY.id = (  SELECT MAX(POL.id)
                                                                  FROM ClientPolicy POL
                                                                    INNER JOIN rbPolicyType POL_TYPE ON POL.policyType_id = POL_TYPE.id AND POL_TYPE.NAME LIKE 'ОМС%%'
                                                                  WHERE POL.client_id = PATIENT.id
                                                                        AND POL.deleted <> 1
                                                                        AND (POL.begDate = '0000-00-00' OR POL.begDate <= ACT.endDate)
                                                                        AND (POL.endDate IS NULL OR POL.endDate >= DATE(ACT.endDate) OR POL.endDate = '0000-00-00')
)
-- Связь пациента с представителями
LEFT JOIN ClientRelation ON (ClientRelation.relative_id = PATIENT.id  AND ClientRelation.deleted = 0  AND (PATIENT_POLICY.id IS NULL OR PATIENT_DOCUMENT.id IS NULL))
-- представитель пациента
LEFT JOIN Client SPOKESMAN ON SPOKESMAN.id =  ClientRelation.client_id
-- Документы представителя пациента
LEFT JOIN ClientDocument SPOKESMAN_DOCUMENT ON SPOKESMAN_DOCUMENT.id = ( SELECT MAX(DOC.id)
                                                                           FROM ClientDocument DOC
                                                                             INNER JOIN rbDocumentType DOC_TYPE ON DOC_TYPE.id = DOC.documentType_id
                                                                           WHERE DOC_TYPE.group_id = %d
                                                                                 AND DOC.client_id = SPOKESMAN.id
                                                                                 AND DOC.deleted <> 1
                                                                                 AND (DOC.date = '0000-00-00' OR DOC.date IS NULL OR DOC.date <= ACT.endDate)
)
-- Полис ОМС представителя клиента
LEFT JOIN ClientPolicy SPOKESMAN_POLICY ON SPOKESMAN_POLICY.id = (  SELECT MAX(POL.id)
                                                                      FROM ClientPolicy POL
                                                                        INNER JOIN rbPolicyType POL_TYPE ON POL.policyType_id = POL_TYPE.id AND POL_TYPE.NAME LIKE 'ОМС%%'
                                                                      WHERE POL.client_id = SPOKESMAN.id
                                                                            AND POL.deleted <> 1
                                                                            AND (POL.begDate = '0000-00-00' OR POL.begDate <= ACT.endDate)
                                                                            AND (POL.endDate IS NULL OR POL.endDate >= ACT.endDate OR POL.endDate = '0000-00-00')
)
-- Страховщик клиента
LEFT JOIN Organisation INSURER ON INSURER.id = COALESCE(PATIENT_POLICY.insurer_id, SPOKESMAN_POLICY.insurer_id)
-- Контактные данные пациента (для определения номера новорожденного)
LEFT JOIN ClientContact	ON (ClientContact.client_id = PATIENT.id  AND ClientContact.deleted = 0  AND ClientContact.ContactType_id = %d)
-- Основная Диагностика из обращения
INNER JOIN Diagnostic MAIN_DIAGNOSTIC ON (MAIN_DIAGNOSTIC.event_id = E.id AND MAIN_DIAGNOSTIC.deleted = 0 AND (MAIN_DIAGNOSTIC.diagnosisType_id = %d OR MAIN_DIAGNOSTIC.diagnosisType_id = %d))
-- Результат обращения
INNER JOIN rbResult RSLT ON RSLT.id = COALESCE(E.result_id, MAIN_DIAGNOSTIC.result_id)
-- Исход заболевания
LEFT JOIN rbAcheResult ISHOD ON ISHOD.id = COALESCE(E.rbAcheResult_id, MAIN_DIAGNOSTIC.rbAcheResult_id)
-- Вид помощи
INNER JOIN rbMedicalAidType AID_TYPE ON AID_TYPE.id = E_TYPE.medicalAidType_id
-- Основой диагноз
INNER JOIN Diagnosis DS1 ON DS1.id = MAIN_DIAGNOSTIC.diagnosis_id AND DS1.client_id = PATIENT.id
-- Стадия заболевания (0 по-умолчанию)
INNER JOIN rbDiseasePhases DS0 ON DS0.id = COALESCE(MAIN_DIAGNOSTIC.phase_id, %d)
-- Сопуствующая диагностика
LEFT JOIN Diagnostic SECONDARY_DIAGNOSTIC ON (SECONDARY_DIAGNOSTIC.event_id = E.id AND SECONDARY_DIAGNOSTIC.deleted = 0 AND (SECONDARY_DIAGNOSTIC.diagnosisType_id = %d OR SECONDARY_DIAGNOSTIC.diagnosisType_id = %d) )
-- Сопутствующий диагноз
LEFT JOIN Diagnosis DS2 ON DS2.id = SECONDARY_DIAGNOSTIC.diagnosis_id AND DS2.client_id = PATIENT.id
-- исполнитель
LEFT JOIN Person STAFF ON STAFF.id = ACT.person_id AND STAFF.deleted = 0
-- Тип действия
INNER JOIN ActionType ACT_TYPE	ON ACT.actionType_id = ACT_TYPE.id
-- Сервис связанный с действием
-- Может быть связан через ActionType_Service
LEFT JOIN ActionType_Service ACT_TYPE_SERVICE ON ACT_TYPE_SERVICE.master_id = ACT_TYPE.id
-- Сервис связанный с действием
-- Может быть связан через ActionType.service_id
INNER JOIN rbService SERVICE ON SERVICE.id = COALESCE(ACT_TYPE_SERVICE.service_id, ACT_TYPE.service_id)
-- Профиль помощи
LEFT JOIN rbMedicalAidProfile PROFIL ON PROFIL.id = SERVICE.medicalAidProfile_id
-- Категория помощи
INNER JOIN rbMedicalKind MEDICAL_KIND ON MEDICAL_KIND.id = COALESCE(E_TYPE.rbMedicalKind_id, SERVICE.rbMedicalKind_id)
                                           AND MEDICAL_KIND.code NOT IN ('D', 'S', 'G')
-- Таблица-связка категории помощи и еденицы учета со способом оплаты
INNER JOIN MedicalKindUnit MKU ON (MKU.rbMedicalKind_id = MEDICAL_KIND.id
                                     AND ( MKU.eventType_id = E_TYPE.id
                                           OR
                                           ( MKU.eventType_id IS NULL
                                             AND NOT EXISTS
                                           (
                                               SELECT bMKU.id FROM MedicalKindUnit bMKU   WHERE bMKU.eventType_id =  E_TYPE.id AND bMKU.rbMedicalKind_id = MEDICAL_KIND.id LIMIT 1)
                                           )
  )
)
-- Единица учета
INNER JOIN rbMedicalAidUnit AID_UNIT ON AID_UNIT.id = IF(MEDICAL_KIND.code = 'P' AND MKU.rbMedicalAidUnit_id = 2 AND RSLT.code <> 304
                                                           AND (getPreviousResult(PATIENT.id, ACT.endDate, E.contract_id, SERVICE.infis, E_TYPE.id) <> 'P304'), 1,  MKU.rbMedicalAidUnit_id)
-- Способ оплаты
INNER JOIN rbPayType IDSP ON IDSP.id = MKU.rbPayType_id
-- Тариф, связанный с услугой
LEFT JOIN Contract_Tariff TARIFF ON
-- тариф действовал до оказания услуги
                                     TARIFF.begDate <= ACT.endDate
                                     -- тариф не был прекращен на момент оказания услуги
                                     AND (TARIFF.endDate IS NULL OR TARIFF.endDate >= ACT.endDate OR TARIFF.endDate = '0000-00-00')
                                     -- возраст пациента подходит под тариф
                                     AND (TARIFF.age ='' OR (
-- Проверяем левую часть селектора возраста
(
  SUBSTRING_INDEX(TARIFF.age, '-', 1) = ''
  -- Год ли это
  OR (INSTR(SUBSTRING_INDEX(TARIFF.age, '-', 1), 'Г') AND REPLACE(SUBSTRING_INDEX(TARIFF.age, '-', 1), 'Г', '') <= TIMESTAMPDIFF(YEAR, PATIENT.birthDate, IF(MEDICAL_KIND.code IN ('H', 'V', 'Z') AND AID_UNIT.code = '2', CONCAT(YEAR(ACT.endDate), '-12-31'), ACT.endDate) ) )
  -- А может месяц
  OR (INSTR(SUBSTRING_INDEX(TARIFF.age, '-', 1), 'М') AND REPLACE(SUBSTRING_INDEX(TARIFF.age, '-', 1), 'М', '') <= TIMESTAMPDIFF(MONTH, PATIENT.birthDate, ACT.begDate) )
  -- Или вдруг день
  OR (INSTR(SUBSTRING_INDEX(TARIFF.age, '-', 1), 'Д') AND REPLACE(SUBSTRING_INDEX(TARIFF.age, '-', 1), 'Д', '') <= TIMESTAMPDIFF(DAY, PATIENT.birthDate, ACT.begDate) )
)
AND
(
  SUBSTRING_INDEX(TARIFF.age, '-', -1) = ''
  -- Год ли это
  OR (INSTR(SUBSTRING_INDEX(TARIFF.age, '-', -1), 'Г') AND REPLACE(SUBSTRING_INDEX(TARIFF.age, '-', -1), 'Г', '') > TIMESTAMPDIFF(YEAR, PATIENT.birthDate, IF(MEDICAL_KIND.code IN ('H', 'V', 'Z') AND AID_UNIT.code = '2', CONCAT(YEAR(ACT.endDate), '-12-31'), ACT.endDate) ) )
  -- А может месяц
  OR (INSTR(SUBSTRING_INDEX(TARIFF.age, '-', -1), 'М') AND REPLACE(SUBSTRING_INDEX(TARIFF.age, '-', -1), 'М', '') > TIMESTAMPDIFF(MONTH, PATIENT.birthDate, ACT.begDate) )
  -- Или вдруг день
  OR (INSTR(SUBSTRING_INDEX(TARIFF.age, '-', -1), 'Д') AND REPLACE(SUBSTRING_INDEX(TARIFF.age, '-', -1), 'Д', '') > TIMESTAMPDIFF(DAY, PATIENT.birthDate, ACT.begDate) )
)
                                     )
                                     )
                                     -- пол пациента попадает под тариф
                                     AND (TARIFF.sex = PATIENT.sex OR TARIFF.sex = 0)
                                     -- тип события совпадает (или тип события IS NULL, но с искомым типом и категорией ничего нету )
                                     AND (TARIFF.eventType_id = E_TYPE.id   OR (TARIFF.eventType_id IS NULL AND NOT EXISTS(SELECT * FROM Contract_Tariff bCT   WHERE bCT.eventType_id =  E_TYPE.id AND bCT.unit_id = MKU.rbMedicalAidUnit_id AND bCT.master_id = E.contract_id  AND bCT.service_id = SERVICE.id LIMIT 1)))
                                     -- единица учета совпадает с найденной
                                     AND TARIFF.unit_id = AID_UNIT.id
                                     -- тариф связан с нужной услугой
                                     AND TARIFF.service_id = SERVICE.id
                                     -- совпадение контракта из события с контрактом тарифа
                                     AND TARIFF.master_id = E.contract_id
                                     --  тариф вообще необходим
                                     AND NOT (
                                       (LEFT(INSURER.area, 2) = '%s' OR INSURER.area IS NULL)
                                       AND (
                                         MEDICAL_KIND.code = 'F'
                                         OR
                                         MEDICAL_KIND.code = 'R'
                                         OR (
                                           MEDICAL_KIND.code = 'P'
                                           AND (AID_UNIT.code = '2' or AID_UNIT.code = '1')
                                           AND SUBSTRING(SERVICE.infis, 3, 3) IN('026','076','081')
                                         )
                                       )
                                     )
-- Источник финансирования тарифа
INNER JOIN rbServiceFinance FINANCE ON FINANCE.id = IF(
      (LEFT(INSURER.area, 2) = '%s' OR INSURER.area IS NULL)
      AND
      (
        MEDICAL_KIND.code = 'F'
        OR
        MEDICAL_KIND.code = 'R'
        OR (
          MEDICAL_KIND.code = 'P'
          AND (AID_UNIT.code = '2' or AID_UNIT.code = '1')
          AND SUBSTRING(SERVICE.infis, 3, 3) IN('026','076','081')
        )
      ) -- end of if expression
      , 2, TARIFF.rbServiceFinance_id)
-- -------------------------------------------------------------------------------------------------
WHERE
-- пациент не удален
PATIENT.deleted = 0
-- обращение не удалено
AND E.deleted = 0
-- Действие не удалено
AND ACT.deleted = 0
-- Действие закончено успешно
AND ACT.STATUS = 2
-- Действие имеет дату окончания
AND ACT.endDate IS NOT NULL
-- Тип обращения не плановый
AND E_TYPE_PURPOSE.code <> '0'
-- имеет источник финансирования отличный от платных услуг или не выставленный
AND(ACT.finance_id <> 4 OR ACT.finance_id IS NULL)
-- Далее внутри кода будут добавляться еще ограниения
