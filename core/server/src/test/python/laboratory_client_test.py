# -*- coding: utf-8 -*-

import nose
import unittest
import time
import random
import datetime

from suds import WebFault
from suds.client import Client
from suds.sax.element import Element

from authenticator import Authenticator
from creation import *

import db

ids = [18559,
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

class TestLaboratoryClientWS(unittest.TestCase):

    def setUp(self):
        medipadWsUrl = 'http://localhost:8080/tmis-ws-medipad/tmis-medipad?wsdl'
        self.medipadClient = Client(medipadWsUrl, username='', password='', cache=None)
        
        # Authenticate
        authenticator = Authenticator()
        authData = authenticator.asAdmin()

        # Add auth headers
        tmisAuthNS = ('ta', 'http://korus.ru/tmis/auth')
        authHeader = Element('tmisAuthToken', ns=tmisAuthNS).setText(authData.authToken.id)
        self.medipadClient.set_options(soapheaders=authHeader)

    # Tests
    def testSendAnalysisRequestFromList(self):
        for aid in ids:
            try:
                self.sendRequest(aid)
            except WebFault, e:
                if "Action has no tests" in e.message:
                    pass
                elif "Action has no event" in e.message:
                    pass
                elif "No biomaterial found" in e.message:
                    pass
                else:
                    print e.message
                    raise e
    
    def testSendAnalysisRequest(self):
        eventId = 29801
        # Общий анализ мочи
        diagnosticTypeId = 5
        ids = createDiagnosticForPatient(self.medipadClient, eventId, diagnosticTypeId)
        self.assertTrue(len(ids) == 1)
        for aid in ids:
            inserted = self.addBiomaterial(aid, eventId)
            try:
                self.sendRequest(aid)
            except WebFault as e:
        	print e.message
        	raise e
	    db.deleteAction(aid)

    def testSendSensitivityAnalysisRequest(self):
	eventId = 29801
	# Чувствительность к антибиотикам
	diagnosticTypeId = 606
	ids = createDiagnosticForPatient(self.medipadClient, eventId, diagnosticTypeId)
	self.assertTrue(len(ids) == 1)
	for aid in ids:
	    self.addBacteriumName(aid, 'bacteria nueva')
	    inserted = self.addBiomaterial(aid, eventId)
	    try:
		self.sendRequest(aid)
	    except WebFault as e:
		print e.message
		raise e
	    db.deleteAction(aid)

    # Utils
    def sendRequest(self, aid):
        url = 'http://localhost:8080/tmis-ws-laboratory/tmis-client-laboratory?wsdl'
        client = Client(url, username='', password='', cache=None)
        client.service.sendAnalysisRequest(aid)
    
    def addBacteriumName(self, aid, name):
	with db.connect() as conn:
	    try:
		aptid = 12993
		now = datetime.datetime.now()
		apid = db.insert(conn, '''insert into ActionProperty (createDatetime, modifyDatetime, action_id, type_id, norm) values 
				('{date}', '{date}', {actionId}, {typeId}, '')'''.format(date=now, actionId=aid, typeId=aptid))
		db.insert(conn, '''insert into ActionProperty_String (id, `index`, value) values ({id}, 0, '{name}')'''.format(id=apid, name=name))
		conn.commit()
	    except Exception as e:
		conn.rollback()
		raise e

    # Возвращает список кортежей из названия таблицы и id
    def addBiomaterial(self, actionId, eventId):
        with db.connect() as conn:
            try:
                tissueTypeId = db.anyId(conn, 'rbTissueType')
                barcode = random.randint(1, 250000)
                now = datetime.datetime.now()
                sql = '''insert into Tissue (type_id, date, barcode, event_id) 
                      values ({typeId}, '{date}', {barcode}, {eventId})'''.format(
                      typeId=tissueTypeId, date=now, barcode=barcode, eventId=eventId)
                tissueId = db.insert(conn, sql)
                print "tissueId=%d" % tissueId
                conn.commit()
                sql = '''insert into ActionTissue (action_id, tissue_id)
                      values (%d, %d)''' % (actionId, tissueId)
                actionTissueId = db.query(conn, sql)
                conn.commit()
                return {'Tissue': tissueId, 'ActionTissue': actionTissueId}
            except Exception as e:
                conn.rollback()
                raise e
        return []


if __name__ == '__main__':
    nose.main()



