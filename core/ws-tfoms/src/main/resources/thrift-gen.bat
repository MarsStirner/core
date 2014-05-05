//# Сгенеренные классы попадают сразу в папку src
thrift -out ../java/ -v -gen java TFOMS.thrift

thrift -out ../java/ -v -gen java TARIFF.thrift