# -*- coding: utf-8 -*-

import nose
import unittest

import time

from suds import WebFault
from suds.client import Client
from suds.sax.element import Element

class TestAuthentication(unittest.TestCase):

    def setUp(self):
        url = 'http://localhost:8080/tmis-ws-medipad/tmis-auth?wsdl'
        # do not cache wsdl documents
        self.client = Client(url, username='', password='', cache=None)

    ## Utility methods
    def getRoles(self, username, password):
        roles = self.client.service.getRoles(username, password)
        return roles

    def auth(self, username, password, role):
        authData = self.client.service.authenticate(username, password, role)
        return authData

    def createRole(self, id, code, name, rights):
        r = self.client.factory.create("role")
        r.id = id
        r.code = code
        r.name = name
        r.rights = rights
        return r

    def createUserRight(self, id, code, name):
        ur = self.client.factory.create("userRight")
        ur.id = id
        ur.code = code
        ur.name = name
        return ur

    ## Tests
    def testGetRolesWrongLogin(self):
        login = "#####BAD_LOGIN#####"
        password = "*****"
        self.assertRaises(WebFault, self.getRoles, self, (login, password))

    def testGetRolesWrongPassword(self):
        login = u"Педиатров"
        password = "*****"
        self.assertRaises(WebFault, self.getRoles, self, (login, password))

    def testGetRolesSuccess(self):
        login = u"Педиатров"
        password = "698d51a19d8a121ce581499d7b701668"
        roles = self.getRoles(login, password)
        self.assertFalse(len(roles) == 0, "No roles found")

    def testAuthenticateWrongLogin(self):
        login = "#####BAD_LOGIN#####"
        password = "*****"
        role = self.createRole(1, 0, u"Администратор", [])
        #with self.assertRaises(WebFault) as cm:
        ex = False
        try:
    	    self.auth(login, password, role)
    	except WebFault as wtf:
    	    ex = True
    	self.assertTrue(ex)

    def testAuthenticateWrongPassword(self):
        login = u"Педиатров"
        password = "*****"
        role = self.createRole(19, 0, u"Врач отделения", [])
        ex = False
        try:
    	    self.auth(login, password, role)
    	except WebFault as wtf:
    	    ex = True
    	self.assertTrue(ex)

    def testAuthenticateWrongRole(self):
        login = u"Педиатров"
        password = "698d51a19d8a121ce581499d7b701668"
        role = self.createRole(0, 0, u"####BAD_ROLE####", [])
        ex = False
        try:
    	    self.auth(login, password, role)
    	except WebFault as wtf:
    	    ex = True
    	self.assertTrue(ex)

    def testAuthenticateSuccess(self):
        login = u"Педиатров"
        password = "698d51a19d8a121ce581499d7b701668"
        userRight = self.createUserRight(186, 'cardsAccess', u'Видит картотеку')
        role = self.createRole(19, 0, u"Врач отделения", [userRight])
        authData = self.auth(login, password, role)
        self.assertTrue(authData != None, "Incorrect AuthData")
        self.assertTrue(authData.authToken != None, "Incorrect Token")

if __name__ == '__main__':
    nose.main()
