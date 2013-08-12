#!/bin/bash
mkdir src
mkdir gen
wsimport -d gen -s src -verbose -B-XautoNameResolution MISExchange.wsdl
rm -rf gen