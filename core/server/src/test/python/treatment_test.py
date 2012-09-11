# -*- coding: utf-8 -*-

import nose
import unittest

import time

from suds import WebFault
from suds.client import Client
from suds.sax.element import Element

from authenticator import Authenticator

class TestTreatment(unittest.TestCase):

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

    # Utils
    def checkTreatmentInfo(self, commonData, apName):
        hasActionProperty = False
        for e in commonData.entity:
            if hasattr(e, "group"):
                for g in e.group:
                    if hasattr(g, "attribute"):
                        for a in g.attribute:
                            if apName in a._name:
                                hasActionProperty = True
        self.assertTrue(hasActionProperty, "Action property not found")

    def createProperty(self, value):
        pa = self.client.factory.create("propertyArray")
        p = self.client.factory.create("item")
        p._name = "value"
        p.value = value
        pa.item.append(p)
        return pa

    def setProperty(self, attrs, name, prop):
        for e in attrs:
            if e._name == unicode(name):
                e.properties = self.createProperty(prop)
                return e
        return None

    # Tests
    def testGetTreatmentTypes(self):
        eventId = 23128
        ts = self.client.service.getTreatmentTypes("", eventId)
        self.assertTrue(len(ts.entity) > 0, "Empty treatment types")

    def testGetTreatmentInfo(self):
        # Ант Ка
        eventId = 29801
        actionTypeId = 123
        beginDate = '2010-07-01 00:00'
        endDate = '2011-07-30 00:00'
        acts = self.client.service.getTreatmentInfo(
            eventId,
            actionTypeId,
            beginDate,
            endDate)
        self.assertTrue(len(acts.entity) > 0, "Empty treatment list")

    def testGetTreatmentInfoWithoutActionTypeId(self):
        eventId = 29801
        actionTypeId = None
        beginDate = '2010-07-01 00:00'
        endDate = '2011-07-30 00:00'
        acts = self.client.service.getTreatmentInfo(
            eventId,
            actionTypeId,
            beginDate,
            endDate)
        self.assertTrue(len(acts.entity) > 0, "Empty treatment list")
        self.checkTreatmentInfo(acts, u"Наименование")

    def testGetTreatmentInfoWithoutBeginDate(self):
        eventId = 29801
        actionTypeId = None
        beginDate = None
        endDate = '2011-07-30 00:00'
        acts = self.client.service.getTreatmentInfo(
            eventId,
            actionTypeId,
            beginDate,
            endDate)
        self.assertTrue(len(acts.entity) > 0, "Empty treatment list")
        self.checkTreatmentInfo(acts, u"Наименование")

    def testGetTreatmentInfoWithoutEndDate(self):
        eventId = 29801
        actionTypeId = None
        beginDate = '2010-07-01 00:00'
        endDate = None
        acts = self.client.service.getTreatmentInfo(
            eventId,
            actionTypeId,
            beginDate,
            endDate)
        self.assertTrue(len(acts.entity) > 0, "Empty treatment list")
        self.checkTreatmentInfo(acts, u"Наименование")

    def testGetTreatmentInfoWithoutActionTypeIdAndDates(self):
        eventId = 29801
        actionTypeId = None
        beginDate = None
        endDate = None
        acts = self.client.service.getTreatmentInfo(
            eventId,
            actionTypeId,
            beginDate,
            endDate)
        self.assertTrue(len(acts.entity) > 0, "Empty treatment list")
        self.checkTreatmentInfo(acts, u"Наименование")

    def testRevokeTreatment(self):
        eventId = 29801
        actionId = 90573
        self.client.service.revokeTreatment(eventId, actionId)

    def testCreateTreatment(self):
        eventId = 29801
        atId = 123
        ts = self.client.service.getTreatmentTypes("", eventId)
        self.assertTrue(len(ts.entity) > 0, "Empty treatment types")
        treatmentType = None
        for e in ts.entity:
            if e._id == atId:
                treatmentType = e
                break
        self.assertTrue(treatmentType != None, "No actionType found")
        attrs = treatmentType.group[0].attribute
        #
        e = self.setProperty(attrs, u"Способ введения", u"в/в струйно")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Наименование", u"66270")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Доза", u"200")
        self.assertIsNotNone(e)
        #
        newAttr = self.client.factory.create("attribute")
        newAttr._name = "treatmentDates"
        newAttr._type = "Datetime"
        #
        pa = self.client.factory.create("propertyArray")
        p = self.client.factory.create("item")
        p._name = "value"
        p.value = "2011-11-23 11:11:11"
        pa.item.append(p)
        #
        newAttr.properties = pa
        attrs.append(newAttr)
        #
        commonData = self.client.factory.create("entities")
        commonData.entity.append(treatmentType)
        #
        ret = self.client.service.createTreatmentForPatient(eventId, commonData)
        self.assertFalse(len(ret) == 0, "Empty result")
        #
        beginDate = '2011-07-01 00:00'
        endDate = None
        acts = self.client.service.getTreatmentInfo(
            eventId,
            atId,
            beginDate,
            endDate)
        self.assertTrue(len(acts.entity) > 0, "Empty treatment list")
        found = False
        for a in acts.entity:
            if (u"Назначения" == a._name) and (a._id == ret.entity[0]._id) :
                found = True
                break
        self.assertTrue(found, "Treatment not found")

    def testModifyTreatmentForPatient(self):
        eventId = 29801
        atId = 123
        beginDate = '2011-07-01 00:00'
        endDate = None
        # Get
        acts = self.client.service.getTreatmentInfo(
            eventId,
            atId,
            beginDate,
            endDate)
        self.assertTrue(len(acts.entity) > 0, "Empty treatment list")
        a = acts.entity[0]
        treatmentId = a._id

        attrs = a.group[0].attribute
        #
        newAttr = self.client.factory.create("attribute")
        newAttr._name = "treatmentDates"
        newAttr._type = "Datetime"
        #
        pa = self.client.factory.create("propertyArray")
        p = self.client.factory.create("item")
        p._name = "value"
        p.value = "2011-11-23 14:11:11"
        pa.item.append(p)
        #
        newAttr.properties = pa
        del attrs[:]
        attrs.append(newAttr)
        #

        # Modify
        commonData = self.client.factory.create("entities")
        commonData.entity.append(a)
        ret = self.client.service.modifyTreatmentForPatient(eventId, treatmentId, commonData)
        self.assertFalse(len(ret) == 0, "Empty result")

        acts = self.client.service.getTreatmentInfo(
            eventId,
            atId,
            beginDate,
            endDate)
        self.assertTrue(len(acts.entity) > 0, "Empty treatment list")
        a = acts.entity[0]
        #print a

        attrs = a.group[0].attribute
        #print attrs

        dates = [attr for attr in attrs if attr._name == "treatmentDates"][0].properties.item
        #print dates
        dates = [i.value for i in dates if i._name == "value"]
        #print dates

        assert(dates == ['2011-11-23 14:00:00'])

if __name__ == '__main__':
    nose.main()
