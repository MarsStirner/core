/**
 * User: FKurilov
 * Date: 09.04.13
 */

define(function (require) {

    /**
     * Структура модуля
     * @type {{Views: {}, Collections: {}, Models: {}}}
     */
    var Monitoring = {
        Views: {},
        Collections: {},
        Models: {}
    };

    // var shared = require('views/appeal/edit/pages/monitoring/shared');


    // // var DictionaryValues = require('collections/dictionary-values');
    // // var Moves = require('collections/moves/moves');

    // /**
    //  * История изменения группы крови пациента
    //  * @type {*}
    //  */
    // Monitoring.Collections.PatientBloodTypes = require('views/appeal/edit/pages/monitoring/collections/PatientBloodTypes');


    // //Monitoring.Models.MonitoringInfo = require('views/appeal/edit/pages/monitoring/models/MonitoringInfo');




    // /**
    //  * Коллекция для таблицы "Мониторинг"
    //  */
    // Monitoring.Collections.MonitoringInfos = require('views/appeal/edit/pages/monitoring/collections/MonitoringInfos');

    // /**
    //  * Коллекция для таблицы "Экспресс-анализы"
    //  */
    // Monitoring.Collections.ExpressAnalyses = require('views/appeal/edit/pages/monitoring/collections/ExpressAnalyses');

    // Monitoring.Collections.PatientDiagnoses = require('views/appeal/edit/pages/monitoring/collections/PatientDiagnoses');



    // /**
    //  * Экземпляры моделей/коллекций общих для нескольких классов
    //  */
    // // var appeal;
    // // var appealJSON;
    // // var bloodTypes;

    // // Коллекции
    // //////////////////////////////////////////////////////




    // /**
    //  * Облегчённая коллекция персонала ЛПУ (без bb.relational)
    //  * @type {*}
    //  */
    // var Persons = require('views/appeal/edit/pages/monitoring/collections/Persons');

    // var AppealExecPerson = require('views/appeal/edit/pages/monitoring/models/AppealExecPerson');

    // // Лэйаут
    // //////////////////////////////////////////////////////

    /**
     * Главная вьюха, контейнер для виджетов
     * @type {*}
     */
    Monitoring.Views.Layout = require('views/appeal/edit/pages/monitoring/views/Layout');


    // // Базовые вью для виджетов
    // //////////////////////////////////////////////////////

    // /**
    //  * Базовый класс для простых вьюшек
    //  * @type {*}
    //  */
    //  Monitoring.Views.BaseView = require('views/appeal/edit/pages/monitoring/views/BaseView');



    // /**
    //  * Базовый класс для виджетов-таблиц сортируемых на клиенте
    //  * @type {*}
    //  */
    // Monitoring.Views.ClientSortableGrid = require('views/appeal/edit/pages/monitoring/views/ClientSortableGrid')


    // // Виджеты
    // //////////////////////////////////////////////////////

    // /**
    //  * Заголовок страницы
    //  * @type {*}
    //  */
    // Monitoring.Views.Header = require('views/appeal/edit/pages/monitoring/views/Header');

    // /**
    //  * Сведения о пациенте
    //  * @type {*}
    //  */
    // Monitoring.Views.PatientInfo = require('views/appeal/edit/pages/monitoring/views/PatientInfo')

    // Monitoring.Views.PatientBsaRow = require('views/appeal/edit/pages/monitoring/views/PatientBsaRow')

    // /**
    //  * Текущая группа крови пациента
    //  * @type {*}
    //  */

    // Monitoring.Views.PatientBloodTypeRow = require('views/appeal/edit/pages/monitoring/views/PatientBloodTypeRow')

    // *
    //  * Истрория изменения группы крови
    //  * @type {*}

    //  Monitoring.Views.PatientBloodTypeHistoryRow = require('views/appeal/edit/pages/monitoring/views/PatientBloodTypeHistoryRow');



    // /**
    // * Блок химиотерапии
    // *
    // */
    // Monitoring.Views.ChemotherapyInfo = require('views/appeal/edit/pages/monitoring/views/ChemotherapyInfo');

    // /**
    //  * Блок сигнальной информации о пациенте
    //  * @type {*}
    //  */
    // Monitoring.Views.SignalInfo = require('views/appeal/edit/pages/monitoring/views/SignalInfo');

    // /**
    //  * Диалог назначения врача
    //  * @type {*}
    //  */
    // Monitoring.Views.ExecPersonAssignmentDialog = require('views/appeal/edit/pages/monitoring/views/ExecPersonAssignmentDialog')

    // /**
    //  * Список диагнозов пациента
    //  * @type {*}
    //  */
    // Monitoring.Views.PatientDiagnosesList = require('views/appeal/edit/pages/monitoring/views/PatientDiagnosesList');

    // /**
    //  * Вью таблицы-виджета "Мониторинг"
    //  * @type {*}
    //  */
    // Monitoring.Views.MonitoringInfoGrid = require('views/appeal/edit/pages/monitoring/views/MonitoringInfoGrid');
    // /**
    //  * Вью таблицы-виджета "Экспресс-анализы"
    //  * @type {*}
    //  */
    // Monitoring.Views.ExpressAnalyses = require('views/appeal/edit/pages/monitoring/views/ExpressAnalysesView');
    return Monitoring;
});
