# -*- coding: utf-8 -*-

import time

from suds import WebFault
from suds.client import Client
from suds.sax.element import Element

class Authenticator():

    def __init__(self):
        url = 'http://localhost:8080/tmis-ws-medipad/tmis-auth?wsdl'
        # do not cache wsdl documents
        self.client = Client(url, username='', password='', cache=None)

    def asAdmin(self):
        login = u"админ"
        password = "698d51a19d8a121ce581499d7b701668"
        roleId = 1
        authData = self.client.service.authenticate(login, password, roleId)
        return authData

    def asUser(self, username, password, roleId):
        url = 'http://localhost:8080/tmis-ws-medipad/tmis-auth?wsdl'
        # do not cache wsdl documents
        client = Client(url, username='', password='', cache=None)
        roles = client.service.getRoles(username, password)
        if len(roles) == 0:
            raise Exception, "No roles found"
        for r in roles.role:
            if roleId == r.id:
                authData = client.service.authenticate(username, password, r.id)
                return authData
        return None
