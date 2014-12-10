define(function (require) {

    var Barcode = Model.extend({
        default: {
            datetime: '',
            barcode: '',
            fio: ''
        }
    });

	var Barcodes = Collection.extend({
		model: Barcode
	});

	return Barcodes;
});
