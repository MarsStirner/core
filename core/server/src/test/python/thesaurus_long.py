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
        authData = authenticator.asUser(u'Педиатров', '698d51a19d8a121ce581499d7b701668', u'Врач отделения')

        # Add auth headers
        tmisAuthNS = ('ta', 'http://korus.ru/tmis/auth')
        authHeader = Element('tmisAuthToken', ns = tmisAuthNS).setText(authData.authToken.id)
        self.client.set_options(soapheaders=authHeader)

    def testSumOfGetThesaurusByCodeEqualsGetThesaurusLong(self):
        allts = self.client.service.getThesaurus()
        n = len(allts.entry)
        allts = []
        m = 0
        for i in [1,2,3,4,5,6,7]:
    	    self.setUp()
    	    ts = self.client.service.getThesaurusByCode(i)
    	    print "i=%d len(ts)=%d" % (i, len(ts.entry))
    	    m = m + len(ts.entry)
        self.assertEquals(n, m, "Sum error: %d != %d" % (n, m))

if __name__ == '__main__':
    nose.main()
