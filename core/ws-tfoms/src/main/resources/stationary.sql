-- Запрос по стационарным обращениям
SELECT
@serial := @serial+1 AS 'ID',
-- Единица учета
AID_UNIT.id AS 'unitId',
-- тип стационарного обращения
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
-- -----------------------------------------------------------------------------------
-- идентификатор обращения
E.id AS 'eventId',
-- результат обращения
COALESCE((SELECT rbResult.id FROM rbResult WHERE rbResult.code = LEFT(AP_RSLT.value,3) LIMIT 1), RSLT.id) AS 'RSLT',
-- исход заболевания
COALESCE(LEFT(AP_ISHOD.value,3), ISHOD.code) AS 'ISHOD',
-- Условия оказания мед. помощи
E_TYPE_PURPOSE.codePlace AS 'USL_OK',
-- Вид помощи
AID_TYPE.code AS 'VIDPOM',
-- Главная диагностика
MAIN_DIAGNOSTIC.id AS 'mainDiagnosticId',
-- Значение основного диагноза
COALESCE(AP_DS1.DiagID, DS1.MKB) AS 'DS1',
-- Стадия заболеваия
COALESCE(LEFT(AP_DS0.value,1), DS0.code) AS 'DS0',
-- Значение сопутствующего диагноза
COALESCE(AP_DS2.DiagID, DS2.MKB) AS 'DS2',
-- идентификатор действия
ACT.id AS 'actionId',
-- Лечащий врач
STAFF.id AS 'executorId',
-- дата выписки
E.execDate AS 'checkDate',
-- идентификатор оказанной услуги
SERVICE.id AS 'rbServiceId',
-- профиль помощи
PROFIL.code AS 'PROFIL',
-- идентификатор записи-связки
MKU.id AS 'medicalKindUnitId',
-- Способ оплаты
PAY_TYPE.code AS 'IDSP',
-- идентификатор тарифа
TARIFF.id AS 'contractTariffId',
-- цена тарифа
TARIFF.price AS 'price',
-- источник финансирования
FINANCE.id AS 'financeId'
-- -----------------------------------------------------------------------------------
FROM Event E
-- обращение пациента
INNER JOIN Action ACT ON E.id = ACT.event_id
-- Тип действия -движение
INNER JOIN ActionType ACT_TYPE	ON (ACT.actionType_id = ACT_TYPE.id AND ACT_TYPE.id = %d)
-- пациент
LEFT JOIN Client PATIENT ON PATIENT.id = E.client_id
-- Полис ОМС клиента
LEFT JOIN ClientPolicy PATIENT_POLICY ON PATIENT_POLICY.id = (  SELECT MAX(POL.id)
                                                                  FROM ClientPolicy POL
                                                                    INNER JOIN rbPolicyType POL_TYPE ON POL.policyType_id = POL_TYPE.id AND POL_TYPE.NAME LIKE 'ОМС%%'
                                                                  WHERE POL.client_id = PATIENT.id
                                                                        AND POL.deleted <> 1
                                                                        AND (POL.begDate = '0000-00-00' OR POL.begDate <= ACT.endDate)
                                                                        AND (POL.endDate IS NULL OR POL.endDate >= DATE(ACT.endDate) OR POL.endDate = '0000-00-00')
)
-- документы клиента
LEFT JOIN ClientDocument PATIENT_DOCUMENT ON PATIENT_DOCUMENT.id = ( SELECT MAX(DOC.id)
                                                                       FROM ClientDocument DOC
                                                                         INNER JOIN rbDocumentType DOC_TYPE ON DOC_TYPE.id = DOC.documentType_id
                                                                       WHERE DOC_TYPE.group_id = %d
                                                                             AND DOC.client_id = PATIENT.id
                                                                             AND DOC.deleted <> 1
                                                                             AND (DOC.date = '0000-00-00' OR DOC.date IS NULL OR DOC.date <= DATE(ACT.endDate))
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
-- Тип события связанный с событием
LEFT JOIN EventType E_TYPE ON E_TYPE.id = E.eventType_id
-- Назначение типа события
LEFT JOIN rbEventTypePurpose E_TYPE_PURPOSE ON E_TYPE.purpose_id= E_TYPE_PURPOSE.id
-- Тип обращения
INNER JOIN rbRequestType E_TYPE_REQUEST ON E_TYPE_REQUEST.id = E_TYPE.requestType_id AND (E_TYPE_REQUEST.code = 'clinic' OR E_TYPE_REQUEST.code = 'hospital' OR E_TYPE_REQUEST.code = 'stationary')
-- Вид помощи
LEFT JOIN rbMedicalAidType AID_TYPE ON AID_TYPE.id = E_TYPE.medicalAidType_id
-- Основная Диагностика из обращения
LEFT JOIN Diagnostic MAIN_DIAGNOSTIC ON (MAIN_DIAGNOSTIC.event_id = E.id AND MAIN_DIAGNOSTIC.deleted = 0 AND (MAIN_DIAGNOSTIC.diagnosisType_id = %d OR MAIN_DIAGNOSTIC.diagnosisType_id = %d))
-- Основой диагноз из поликлиники
LEFT JOIN Diagnosis DS1 ON DS1.id = MAIN_DIAGNOSTIC.diagnosis_id AND DS1.client_id = PATIENT.id
-- Сопуствующая диагностика
LEFT JOIN Diagnostic SECONDARY_DIAGNOSTIC ON (SECONDARY_DIAGNOSTIC.event_id = E.id AND SECONDARY_DIAGNOSTIC.deleted = 0 AND (SECONDARY_DIAGNOSTIC.diagnosisType_id = %d OR SECONDARY_DIAGNOSTIC.diagnosisType_id = %d) )
-- Сопутствующий диагноз
LEFT JOIN Diagnosis DS2 ON DS2.id = SECONDARY_DIAGNOSTIC.diagnosis_id AND DS2.client_id = PATIENT.id
-- Результат обращения
  LEFT JOIN rbResult RSLT ON RSLT.id = COALESCE(E.result_id, MAIN_DIAGNOSTIC.result_id)
-- Исход заболевания
  LEFT JOIN rbAcheResult ISHOD ON ISHOD.id = COALESCE(E.rbAcheResult_id, MAIN_DIAGNOSTIC.rbAcheResult_id)
-- Стадия заболевания (0 по-умолчанию)
  LEFT JOIN rbDiseasePhases DS0 ON DS0.id = COALESCE(MAIN_DIAGNOSTIC.phase_id, %d)
-- ActionProperties ---------------------------------------------------------------
-- Значение основного диагноза из свойств дивжения
  LEFT JOIN MKB AP_DS1 ON AP_DS1.id = (
    SELECT AP_MKB.value
    FROM ActionProperty AP
      INNER JOIN ActionProperty_MKB AP_MKB ON AP.id = AP_MKB.id
    WHERE AP.action_id = ACT.id
          AND AP.type_id = %d
    LIMIT 1
  )
-- Значение сопутствующего диагноза из свойств дивжения
  LEFT JOIN MKB AP_DS2 ON AP_DS2.id = (
    SELECT AP_MKB.value
    FROM ActionProperty AP
      INNER JOIN ActionProperty_MKB AP_MKB ON AP.id = AP_MKB.id
    WHERE AP.action_id = ACT.id
          AND AP.type_id = %d
    LIMIT 1
  )
-- Значение Лечения по МЭС из свойств движения
  LEFT JOIN ActionProperty_String AP_MES ON AP_MES.id = (
    SELECT AP.id
    FROM ActionProperty AP
    WHERE AP.action_id = ACT.id
          AND AP.type_id = %d
    LIMIT 1
  )
-- Значение исхода из свойств движения
  LEFT JOIN ActionProperty_String AP_ISHOD ON AP_ISHOD.id = (
    SELECT AP.id
    FROM ActionProperty AP
    WHERE AP.action_id = ACT.id
          AND AP.type_id = %d
    LIMIT 1
  )
-- Значение результата из свойств движения
  LEFT JOIN ActionProperty_String AP_RSLT ON AP_RSLT.id = (
    SELECT AP.id
    FROM ActionProperty AP
    WHERE AP.action_id = ACT.id
          AND AP.type_id = %d
    LIMIT 1
  )
-- Значение Стадии заболевания из свойств движения
  LEFT JOIN ActionProperty_String AP_DS0 ON AP_DS0.id = (
    SELECT AP.id
    FROM ActionProperty AP
    WHERE AP.action_id = ACT.id
          AND AP.type_id = %d
    LIMIT 1
  )
-- ActionProperties ---------------------------------------------------------------
-- Значения свойства профиль койки
  LEFT JOIN ActionProperty_HospitalBedProfile AP_BED_VALUE ON (AP_BED_VALUE.index = 0  AND
                                                               AP_BED_VALUE.id = (
                                                                 SELECT AP.id
                                                                 FROM ActionProperty AP
                                                                 WHERE  AP.deleted = 0
                                                                        AND AP.action_id = ACT.id
                                                                        AND AP.type_id = %d
                                                                 LIMIT 1)
  )
-- Перечень услуг, связанных с профилем койки
LEFT JOIN rbHospitalBedProfile_Service BED_SERVICE ON BED_SERVICE.rbHospitalBedProfile_id  = AP_BED_VALUE.value
-- Сервис связанный с действием
INNER JOIN rbService SERVICE
    ON SERVICE.id = BED_SERVICE.rbService_id  AND (
                                                    (
                                                       (  NOT SERVICE.code LIKE 'А%%'
                                                          AND SUBSTRING(SERVICE.code, 9, 1) = IF(TIMESTAMPDIFF(YEAR, PATIENT.birthDate, DATE(ACT.begDate) ) >= 18, '1', '2')
                                                       )
                                                       OR (SERVICE.code LIKE 'А%%')
                                                    )

                                              )
                                              AND (
                                                      SERVICE.rbMedicalKind_id = E_TYPE.rbMedicalKind_id
                                                      OR E_TYPE.rbMedicalKind_id IS NULL
                                                      OR SERVICE.code LIKE 'А%%'
                                              )
-- Профиль помощи
LEFT JOIN rbMedicalAidProfile PROFIL ON PROFIL.id = SERVICE.medicalAidProfile_id
-- Категория помощи (дневной \ круглосуточный стацонар \ гемодиализ)
LEFT JOIN rbMedicalKind MEDICAL_KIND ON MEDICAL_KIND.id = COALESCE(E_TYPE.rbMedicalKind_id, SERVICE.rbMedicalKind_id)
                                           AND MEDICAL_KIND.code IN ('S', 'D', 'G')
-- Таблица-связка для определения единицы учета
LEFT JOIN MedicalKindUnit MKU ON (
-- совпадает категория помощи
MKU.rbMedicalKind_id = MEDICAL_KIND.id
-- и тип события (или тип события IS NULL, но с искомым типом и категорией ничего нету )
AND (MKU.eventType_id = E_TYPE.id   OR (MKU.eventType_id IS NULL AND NOT EXISTS(SELECT * FROM MedicalKindUnit bMKU   WHERE bMKU.eventType_id =  E_TYPE.id AND bMKU.rbMedicalKind_id = MEDICAL_KIND.id LIMIT 1)))
AND(
-- условия для круглосуточного стационара
(
  MEDICAL_KIND.code = 'S'
  AND
  (
    MKU.rbMedicalAidUnit_id = IF(TIMESTAMPDIFF(DAY, DATE(ACT.begDate), DATE(ACT.endDate))  > 2,
                                 -- THEN
                                 IF(
                                     AP_MES.value = 'да'
                                     -- И есть тариф по найденному диагнозу
                                     AND EXISTS (
                                         SELECT mesCT.id FROM Contract_Tariff mesCT
                                         WHERE
-- тариф действовал до оказания услуги
                                           mesCT.begDate <= ACT.begDate
                                           -- тариф не был прекращен на момент оказания услуги
                                           AND (mesCT.endDate IS NULL OR mesCT.endDate >= ACT.begDate OR mesCT.endDate = '0000-00-00')
                                           -- возраст пациента подходит под тариф
                                           AND (mesCT.age ='' OR (
-- Проверяем левую часть селектора возраста
(
  SUBSTRING_INDEX(mesCT.age, '-', 1) = ''
  -- Год ли это
  OR (INSTR(SUBSTRING_INDEX(mesCT.age, '-', 1), 'Г') AND REPLACE(SUBSTRING_INDEX(mesCT.age, '-', 1), 'Г', '') <= TIMESTAMPDIFF(YEAR, PATIENT.birthDate, ACT.begDate) )
  -- А может месяц
  OR (INSTR(SUBSTRING_INDEX(mesCT.age, '-', 1), 'М') AND REPLACE(SUBSTRING_INDEX(mesCT.age, '-', 1), 'М', '') <= TIMESTAMPDIFF(MONTH, PATIENT.birthDate, ACT.begDate) )
  -- Или вдруг день
  OR (INSTR(SUBSTRING_INDEX(mesCT.age, '-', 1), 'Д') AND REPLACE(SUBSTRING_INDEX(mesCT.age, '-', 1), 'Д', '') <= TIMESTAMPDIFF(DAY, PATIENT.birthDate, ACT.begDate) )
)
AND
(
  SUBSTRING_INDEX(mesCT.age, '-', -1) = ''
  -- Год ли это
  OR (INSTR(SUBSTRING_INDEX(mesCT.age, '-', -1), 'Г') AND REPLACE(SUBSTRING_INDEX(mesCT.age, '-', -1), 'Г', '') > TIMESTAMPDIFF(YEAR, PATIENT.birthDate, ACT.begDate) )
  -- А может месяц
  OR (INSTR(SUBSTRING_INDEX(mesCT.age, '-', -1), 'М') AND REPLACE(SUBSTRING_INDEX(mesCT.age, '-', -1), 'М', '') > TIMESTAMPDIFF(MONTH, PATIENT.birthDate, ACT.begDate) )
  -- Или вдруг день
  OR (INSTR(SUBSTRING_INDEX(mesCT.age, '-', -1), 'Д') AND REPLACE(SUBSTRING_INDEX(mesCT.age, '-', -1), 'Д', '') > TIMESTAMPDIFF(DAY, PATIENT.birthDate, ACT.begDate) )
)
                                           )
                                           ) -- конец проверки возраста пациента
				   -- пол пациента попадает под тариф
                                           AND (mesCT.sex = PATIENT.sex OR mesCT.sex = 0)
                                           -- тип события IS NULL
                                           AND mesCT.eventType_id IS NULL
                                           -- единица учета совпадает с найденной
                                           AND mesCT.unit_id = 6
                                           -- услуга для лечения по МЭС не указывается
                                           AND mesCT.service_id IS NULL
                                           -- совпадение контракта из события с контрактом тарифа
                                           AND mesCT.master_id = E.contract_id
                                           -- диагноз найден
                                           AND mesCT.MKB = COALESCE(AP_DS1.DiagID, DS1.MKB)
                                         -- Ограничение выборки (для ускорения запроса)
                                         LIMIT 1
                                     ) -- END OF SECOND IF expression
                                     , 6, 2),1)
  )
)
-- условия для дневного стационара
OR
(
  MEDICAL_KIND.code = 'D'
  AND
  -- если лечение дольше двух дней то ищем по законченному случаю(2), иначе по койко-дням (1)
  MKU.rbMedicalAidUnit_id = IF(TIMESTAMPDIFF(DAY, DATE(ACT.begDate), DATE(ACT.endDate)) + 1 - num_sunday_days(ACT.begDate, ACT.endDate) > 2, 2, 1)
  AND ( MKU.eventType_id = E_TYPE.id
        OR
        ( MKU.eventType_id IS NULL
          AND NOT EXISTS
        (
            SELECT * FROM MedicalKindUnit bMKU
            WHERE bMKU.eventType_id =  E_TYPE.id
                  AND bMKU.rbMedicalKind_id = MEDICAL_KIND.id
                  AND bMKU.rbMedicalAidUnit_id = IF(TIMESTAMPDIFF(DAY, DATE(ACT.begDate), DATE(ACT.endDate)) + 1 - num_sunday_days(ACT.begDate, ACT.endDate) > 2, 2, 1)
            LIMIT 1
        )
        )
  )
)
)

  ) -- END OF \"ON\" MKU
-- Единица учета
LEFT JOIN rbMedicalAidUnit AID_UNIT ON AID_UNIT.id = MKU.rbMedicalAidUnit_id
-- Способ оплаты
LEFT JOIN rbPayType PAY_TYPE ON PAY_TYPE.id = MKU.rbPayType_id
-- исполнитель
LEFT JOIN Person STAFF ON STAFF.id = ACT.person_id AND STAFF.deleted = 0
-- Тариф, связанный с услугой
LEFT JOIN Contract_Tariff TARIFF ON              -- тариф действовал до оказания услуги
                                      TARIFF.begDate <= ACT.endDate
                                      -- тариф не был прекращен на момент оказания услуги
                                      AND (TARIFF.endDate IS NULL OR TARIFF.endDate >= ACT.endDate OR TARIFF.endDate = '0000-00-00')
                                      -- возраст пациента подходит под тариф
                                      AND (TARIFF.age ='' OR (
-- Проверяем левую часть селектора возраста
(
  SUBSTRING_INDEX(TARIFF.age, '-', 1) = ''
  -- Год ли это
  OR (INSTR(SUBSTRING_INDEX(TARIFF.age, '-', 1), 'Г') AND REPLACE(SUBSTRING_INDEX(TARIFF.age, '-', 1), 'Г', '') <= TIMESTAMPDIFF(YEAR, PATIENT.birthDate, ACT.begDate) )
  -- А может месяц
  OR (INSTR(SUBSTRING_INDEX(TARIFF.age, '-', 1), 'М') AND REPLACE(SUBSTRING_INDEX(TARIFF.age, '-', 1), 'М', '') <= TIMESTAMPDIFF(MONTH, PATIENT.birthDate, ACT.begDate) )
  -- Или вдруг день
  OR (INSTR(SUBSTRING_INDEX(TARIFF.age, '-', 1), 'Д') AND REPLACE(SUBSTRING_INDEX(TARIFF.age, '-', 1), 'Д', '') <= TIMESTAMPDIFF(DAY, PATIENT.birthDate, ACT.begDate) )
)
AND
(
  SUBSTRING_INDEX(TARIFF.age, '-', -1) = ''
  -- Год ли это
  OR (INSTR(SUBSTRING_INDEX(TARIFF.age, '-', -1), 'Г') AND REPLACE(SUBSTRING_INDEX(TARIFF.age, '-', -1), 'Г', '') > TIMESTAMPDIFF(YEAR, PATIENT.birthDate, ACT.begDate) )
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
                                      AND (TARIFF.eventType_id = E_TYPE.id   OR (TARIFF.eventType_id IS NULL AND NOT EXISTS(SELECT * FROM Contract_Tariff bCT   WHERE bCT.eventType_id =  E_TYPE.id AND bCT.unit_id = MKU.rbMedicalAidUnit_id LIMIT 1)))
                                      -- единица учета совпадает с найденной
                                      AND TARIFF.unit_id = AID_UNIT.id
                                      -- тариф связан с нужной услугой (ИЛИ тариф по диагнозу)
                                      AND (
                                        TARIFF.service_id = SERVICE.id
                                        -- (ИЛИ тариф по диагнозу)
                                        OR (
                                          AID_UNIT.id = 6
                                          AND TARIFF.service_id IS NULL
                                          -- диагноз совпадает с найденным
                                          AND TARIFF.MKB = COALESCE(AP_DS1.DiagID, DS1.MKB)
                                        )
                                      )
                                      -- совпадение контракта из события с контрактом тарифа
                                      AND TARIFF.master_id = E.contract_id
-- источник финансирования услуги
LEFT JOIN rbServiceFinance FINANCE ON FINANCE.id = TARIFF.rbServiceFinance_id
-- --------------------------------------------------------------------------------------------------------------------------------
WHERE
-- пациент не удален
  PATIENT.deleted = 0
  -- обращение не удалено
  AND E.deleted = 0
  -- порядок поступления не плановый
  AND E_TYPE_PURPOSE.code <>'0'
  -- действие не удалено
  AND ACT.deleted = 0
  -- действие закончено
  AND ACT.status = 2
  -- действие имеет дату окончания
  AND ACT.endDate IS NOT NULL
  -- обращение имеет дату завершения
  AND E.execDate IS NOT NULL
  -- имеет источник финансирования отличный от платных услуг или не выставленный
  AND(ACT.finance_id <> 4 OR ACT.finance_id IS NULL)
  -- костыль для отсечения лишних услуг при выгрузке случая с тарифом по диагнозу
  -- AND IF(TARIFF.unit_id <> 6, 1, MEDICAL_KIND.code <> 'D' AND IF(TIMESTAMPDIFF(YEAR, PATIENT.birthDate, DATE(E.execDate)) < 18, 2, 1) = RIGHT(SERVICE.code, 1))
  AND FINANCE.id IS NOT NULL
-- Далее в коде будут добавлены еще ограничения