/* Russian initialisation for the timepicker plugin */
/* Written by Fedor Kurilov (fkuril.dev at gmail). */
jQuery(function($){
    $.timepicker.regional['ru'] = {
                hourText: 'Часы',
                minuteText: 'Минуты',
                amPmText: ['AM', 'PM'] ,
                closeButtonText: 'Закрыть',
                nowButtonText: 'Сейчас',
                deselectButtonText: 'Отменить'
		};
    $.timepicker.setDefaults($.timepicker.regional['ru']);
});