# -*- coding: utf-8 -*-

import nose
import unittest

import time

from suds import WebFault
from suds.client import Client
from suds.sax.element import Element

from authenticator import Authenticator

from creation import *
import db

import datetime
import urllib2
import base64

ids = [ 18559,
	64272,
        64309,
        64356,
        64393,
        64450,
        64471,
        64884,
	64915,
	64919,
	17891,
	49944,
	51758,
	51770,
	52967,
	53014,
	53459,
	54177,
	54208,
	54241,
	54450]

# Action.id, имеющие test_id = 1
actionIds = [18559,
49939,
51755,
52963,
53010,
53384,
53455,
53726,
54172,
54203,
54236,
54446,
54504,
54934,
55074,
62677,
62727,
62745,
62818,
62855,
62875,
63110,
63146,
63174,
63223,
63257,
63300,
63443,
63444,
63531,
63581,
63605,
63683,
63786,
63950,
64009,
64078,
64126,
64174,
64273,
64310,
64338,
64357,
64394,
64419,
64443,
64451,
64472,
64655,
64727,
64774,
64798,
64846,
64885,
64922,
64979,
64993,
65222,
65259,
65326,
65368,
65417,
65477,
65510,
65753,
65801,
65861,
66055,
66067,
66099,
66132,
66152,
66198,
66227,
66271,
66294,
66301,
66562,
66617,
66648,
66721,
66793,
66829,
66848,
66906,
66973,
67084,
67243,
67291,
67318,
67339,
67399,
67445,
67523,
67542,
67596,
67609,
67673,
67841,
67898,
67974,
68029,
68060,
68096,
68111,
68168,
68304,
68381,
68423,
68461,
68501,
68548,
68564,
68589,
68624,
68880,
68919,
68959,
68993,
69016,
69037,
69097,
69172,
69230,
69269,
69628,
69645,
69677,
69709,
69744,
69810,
69844,
69877,
69902,
69925,
69955,
69969,
69985,
69995,
70028,
70172,
70222,
70314,
70348,
70420,
70454,
70476,
70503,
70560,
70610,
70634,
70663,
70922,
70969,
71001,
71023,
71048,
71070,
71094,
71125,
71157,
71180,
71204,
71239,
71263,
71318,
71477,
71514,
71539,
71663,
71739,
71773,
71809,
71915,
71986,
72053,
72110,
72231,
72256,
72340,
72364,
72404,
72431,
72455,
72486,
72781,
72851,
72882,
72903,
72938,
72969,
72999,
73028]

class TestLisIntegration(unittest.TestCase):
    def setUp(self):
	url = 'http://localhost:8080/tmis-ws-laboratory/tmis-laboratory-integration?wsdl'
        self.client = Client(url, username='', password='', cache=None)
        
        medipadWsUrl = 'http://localhost:8080/tmis-ws-medipad/tmis-medipad?wsdl'
        self.medipadClient = Client(medipadWsUrl, username='', password='', cache=None)
        
        # Authenticate
        authenticator = Authenticator()
        authData = authenticator.asAdmin()

        # Add auth headers
        tmisAuthNS = ('ta', 'http://korus.ru/tmis/auth')
        authHeader = Element('tmisAuthToken', ns=tmisAuthNS).setText(authData.authToken.id)
        self.medipadClient.set_options(soapheaders=authHeader)
        
        db.initialize()

    # Tests
    def testSetRequest(self):
	for aid in ids:
	    try:
		self.sendAnalysisRequest(aid)
	    except WebFault, e:
		if "Action has no tests" in e.message:
		    pass
		elif "Action has no event" in e.message:
		    pass
		elif "No biomaterial found for request" in e.message:
		    pass
		else:
		    print e.message
		    raise e

    def testSetResults(self):
	for aid in ids:
	    try:
		self.setAnalysisResults(aid, "12345678", True, [], "76543241")
	    except WebFault, e:
		if "Action has no tests" in e.message:
		    pass
		elif "Action has no event" in e.message:
		    pass
		else:
		    print e.message
		    raise e

    def testSetAnalysisResult(self):
	eventId = 29801
	diagnosticTypeId = 5
	ids = createDiagnosticForPatient(self.medipadClient, eventId, diagnosticTypeId)
	for aid in ids:
	    res = self.createAnalysisResult()
	    self.setAnalysisResults(aid, "", True, [res], "")
	    db.deleteAction(aid)

    def testSetSensitivityResult(self):
	eventId = 29801
	# Чувствительность к антибиотикам
	diagnosticTypeId = 482
	ids = createDiagnosticForPatient(self.medipadClient, eventId, diagnosticTypeId)
	for aid in ids:
	    res = self.createSensitivityResult()
	    self.setSensitivityResults(aid, "", True, [res], "")
	    db.deleteAction(aid)

    def testInvalidRequestId(self):
	ex = False
        try:
    	    self.setAnalysisResults(0, "12345678", False, [], "76543241")
    	except WebFault as wtf:
    	    ex = True
    	self.assertTrue(ex)

    # Utils
    def sendAnalysisRequest(self, aid):
	url = 'http://localhost:8080/tmis-ws-laboratory/tmis-client-laboratory?wsdl'
	client = Client(url, username='', password='',cache=None)
	client.service.sendAnalysisRequest(aid)
	
    def setAnalysisResults(self, aid, barcode, completed, results, defects):
	url = 'http://localhost:8080/tmis-ws-laboratory/tmis-laboratory-integration?wsdl'
	client = Client(url, username='', password='',cache=None)
	client.service.setAnalysisResults(aid, barcode, completed, results, defects)
	
    def setSensitivityResults(self, aid, barcode, completed, results, defects):
	url = 'http://localhost:8080/tmis-ws-laboratory/tmis-laboratory-integration?wsdl'
	client = Client(url, username='', password='',cache=None)
	client.service.setSensitivityResults(aid, barcode, completed, results, defects)

    def getImageBase64(self):
	#url = 'http://localhost:4848/resource/community-theme/images/login-product_name_open.png'
	#u = urllib2.urlopen(url)
	u = open('1.png', 'r')
	data = u.read()
	u.close()
	print str(data.__class__)
	base64str = base64.b64encode(data)
	return base64str

    def createAnalysisResult(self):
	result = self.client.factory.create("analysisResult")
	result.code = "999"
	result.name = u"Тестовый показатель"
	result.endDate = datetime.datetime.now()
	result.norm = "0.1-0.3"
	result.normality = "NORM"
	result.unitCode = u"ммоль/л"
	result.value = "0.2"
	result.valueType = "NUMERIC"
	image = self.getImageBase64()
	result.imageValues = [image]
	return result

    def createSensitivityResult(self):
	result = self.client.factory.create("antibioticSensitivity")
	result.name = u"Тестовый показатель"
	result.value = "S"
	result.mic = "42"
	result.comment = u"Выявлена относительно низкая чувствительность"
	return result

if __name__ == '__main__':
    nose.main()



