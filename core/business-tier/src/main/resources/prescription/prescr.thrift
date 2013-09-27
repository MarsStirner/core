namespace java ru.korus.tmis.prescription.thservice
namespace py prescr

typedef i64 timestamp



/**
 * Данные экшена назначения и значения его свойств
 */
struct ActionData {
	1:optional i32 id, // Action.id
	2:optional i32 actionType_id,
	3:optional string note, // Action.note
	4:optional i32 setPerson_id, // Назначивший Action.setPerson_id
	5:optional string moa, // способ ввода препарата AP_S.value для APT с кодом moa
	6:optional string voa // скорость ввода препарата AP_S.value для APT с кодом voa
}

/**
 * Компонент лекарства, входящий в назначение, таблица DrugComponent.
 */
struct DrugComponent {
	1:optional i32 id,
	2:optional i32 action_id,
	3:optional i32 nomen, // код РЛС препарата
	4:optional string name, // наименование препарата; используется там, где нет РЛС
	5:optional double dose, // доза препарата в единицах измерения препарата
	6:optional i32 unit, // идентификатор ед.изм. препарата {rbUnit.id}
	7:optional timestamp createDateTime, // датавремя назначения препарата
	8:optional timestamp cancelDateTime // датавремя отменя препарата

}


/**
 * Интервал времени исполнения назначенного диапазона времени.
 * Для этих записей установлено поле master_id, ссылающееся на запись
 * назначенного интервала
 */
struct DrugIntervalExec {
	1:optional i32 id,
	2:optional i32 action_id,
	3:optional i32 master_id, // назначенный интервал
	4:optional timestamp begDateTime, // начало
	5:optional timestamp endDateTime, // окончание
	6:optional i32 status, // статус исполнения (назначено, отменено, исполнено)
	7:optional timestamp statusDateTime, // время изменения статуса с "назначено" на иной
	8:optional string note // заметка для интервала назначения или исполнения
}

/**
 * Интервал времени, на которое был назначен препарат или набор препаратов,
 * таблица DrugComponent. В списке execIntervals хранятся связанные интервалы
 * с информацией об исполнении назначенного
 */
struct DrugInterval {
	1:optional i32 id,
	2:optional i32 action_id,
	3:optional timestamp begDateTime, // начало
	4:optional timestamp endDateTime, // окончание
	5:optional i32 status, // статус исполнения (назначено, отменено, исполнено)
	6:optional timestamp statusDateTime, // время изменения статуса с "назначено" на иной
	7:optional string note, // заметка для интервала назначения или исполнения
	8:optional list<DrugIntervalExec> execIntervals // интервалы исполнения
}

/**
 * Структура отдельного назначения. Включает в информацию о действии,
 * список компонентов, список интервалов времен назначений
 */
struct Prescription {
	1:optional ActionData actInfo,
	2:optional list<DrugComponent> drugComponents,
	3:optional list<DrugInterval> drugIntervals
}

/**
 * Базовая структура листа назначений, включает в себя список отдельных назначений
 */
struct PrescriptionList {
	1:required list<Prescription> prescriptionList,
        2:optional i32 eventId

}


exception SavePrescrListException {
	1: string message,
	2: i32 error_code
}

/**
 * Сервис обмена данных о назначениях. Выполняет функции получения общей
 * структуры назначений и сохранения её. Входной параметр на получение - это
 * идентификатор обращения event_id.
 */
service PrescriptionExchange {

	PrescriptionList getPrescriptionList(1:i32 eventId),
	void save(1:PrescriptionList prescrList) throws (1: SavePrescrListException e)
	bool updateBalanceOfGoods(1:list<i32> drugIds)
}