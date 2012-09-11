# -*- coding: utf-8 -*-

import nose
import unittest

import time

from suds import WebFault
from suds.client import Client
from suds.sax.element import Element

from authenticator import Authenticator

class TestThesaurus(unittest.TestCase):

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

    # Tests
    def testGetThesaurus(self):
        ts = self.client.service.getThesaurus()
        self.assertTrue(len(ts.entry) > 0, "Empty thesaurus")

    def testGetThesaurusByCode(self):
        ts = self.client.service.getThesaurusByCode(1)
        self.assertTrue(len(ts.entry) > 0, "Empty thesaurus")

if __name__ == '__main__':
    nose.main()
