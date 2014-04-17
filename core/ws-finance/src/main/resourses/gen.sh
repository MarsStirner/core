#!/bin/bash
java --version
wsimport -d ../java ./wsdl/wsPoliclinic.wsdl -p ru.korus.tmis.ws.finance.odvd -keep -extension 