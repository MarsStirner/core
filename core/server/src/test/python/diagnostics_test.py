# -*- coding: utf-8 -*-

import nose
import unittest

import time

from suds import WebFault
from suds.client import Client
from suds.sax.element import Element

from authenticator import Authenticator
from creation import *

class TestDiagnostics(unittest.TestCase):

    def setUp(self):
        url = 'http://localhost:8080/tmis-ws-medipad/tmis-medipad?wsdl'
        # do not cache wsdl documents
        self.client = Client(url, username='', password='', cache=None)
        # Authenticate
        authenticator = Authenticator()
        authData = authenticator.asAdmin()
        # Add auth headers
        tmisAuthNS = ('ta', 'http://korus.ru/tmis/auth')
        authHeader = Element('tmisAuthToken', ns = tmisAuthNS).setText(authData.authToken.id)
        self.client.set_options(soapheaders=authHeader)

    ## Utility methods
    def getDiagnosticTypes(self, eventId):
        types = self.client.service.getDiagnosticTypes("", eventId)
        return types

    def getAllDiagnosticsForPatient(self, id):
        diagnostics = self.client.service.getAllDiagnosticsForPatient(id)
        return diagnostics

    def getDiagnosticForPatient(self, eventId, diagnosticId):
        diagnostic = self.client.service.getDiagnosticForPatient(eventId, diagnosticId)
        return diagnostic

    ## Tests
    def testGetDiagnosticTypes(self):
        eventId = 60172
        t = self.getDiagnosticTypes(eventId)
        self.assertFalse(len(t) == 0, "Empty diagnostic types")

    def testGetAllDiagnostics(self):
        eventId = 60172
        d = self.getAllDiagnosticsForPatient(eventId)
        self.assertFalse(len(d) == 0, "Empty diagnostics")

    def testGetDiagnostic(self):
        eventId = 60172
        diagnosticId = 280612
        d = self.getDiagnosticForPatient(eventId, diagnosticId)
        self.assertFalse(len(d) == 0, "Empty diagnostic")

    def testCreateDiagnostic(self):
        eventId = 29801
        diagnosticTypeId = 5
        created = createDiagnosticForPatient(self.client, eventId, diagnosticTypeId)
        self.assertTrue(len(created) == 1, "No action created")
        for e in created.entity:
            self.assertTrue(u"Общий анализ мочи" in e._name, "Invalid action name")

    def testModifyDiagnosticForPatient(self):
        eventId = 29801
        diagId = 280612
        # Get
        d = self.getDiagnosticForPatient(eventId, diagId)
        self.assertFalse(len(d.entity) == 0, "Empty diagnostic")
        # Modify
        ret = self.client.service.modifyDiagnosticForPatient(eventId, diagId, d)
        self.assertFalse(len(ret) == 0, "Empty result")

    def testCallOffDiagnosticForPatient(self):
        eventId = 29801
        diagId = 280612
        ret = self.client.service.callOffDiagnosticForPatient(eventId, diagId)
        self.assertTrue(ret, "Update failed")

if __name__ == '__main__':
    nose.main()
