# -*- coding: utf-8 -*-

import nose
import unittest

import time
from datetime import datetime

from suds import WebFault
from suds.client import Client
from suds.sax.element import Element

from authenticator import Authenticator

class TestAssessments(unittest.TestCase):

    def setUp(self):
        url = 'http://localhost:8080/tmis-ws-medipad/tmis-medipad?wsdl'
        # do not cache wsdl documents
        self.client = Client(url, username='', password='', cache=None)
        # Authenticate
        authenticator = Authenticator()
        authData = authenticator.asUser(u'Педиатров', '698d51a19d8a121ce581499d7b701668', 19)
        # Add auth headers
        tmisAuthNS = ('ta', 'http://korus.ru/tmis/auth')
        authHeader = Element('tmisAuthToken', ns = tmisAuthNS).setText(authData.authToken.id)
        self.client.set_options(soapheaders=authHeader)

    def assertIsNotNone(self, e):
        self.assertTrue(e != None)

    ## Utility methods
    def getAssessmentTypes(self, eventId):
        types = self.client.service.getAssessmentTypes("", eventId)
        return types

    def getAllAssessmentsForPatient(self, id):
        assessments = self.client.service.getAllAssessmentsForPatient(id)
        return assessments

    def getAssessmentForPatient(self, eventId, assessmentId):
        assessment = self.client.service.getAssessmentForPatient(eventId, assessmentId)
        return assessment

    def getIndicators(self, eventId, beginDate, endDate):
        inds = self.client.service.getIndicators(eventId, beginDate, endDate)
        return inds
    
    def getCurrentPatients(self):
        cdata = self.client.service.getCurrentPatients()
        return cdata

    def checkIndicators(self, commonData, indName):
        hasIndicator = False
        for g in commonData.entity[0].group:
            if hasattr(g, "attribute"):
                for a in g.attribute:
                    if indName in a._name:
                        hasIndicator = True
        self.assertTrue(hasIndicator, u"Indicator {ind} not found".format(ind=indName))

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

    ## Tests
    def testGetAssessmentTypes(self):
        eventId = 60172
        a = self.getAssessmentTypes(eventId)
        self.assertFalse(len(a.entity) == 0, "Empty assessment types")

    def testGetAllAssessments(self):
        eventId = 60172
        a = self.getAllAssessmentsForPatient(eventId)
        self.assertFalse(len(a.entity) == 0, "Empty assessments")

    def testGetAssessment(self):
        eventId = 60172
        assessmentId = 280606
        a = self.getAssessmentForPatient(eventId, assessmentId)
        self.assertFalse(len(a.entity) == 0, "Empty assessment")

    def testGetIndicatorsTemperature(self):
        # t
        cd = self.getIndicators(19862, datetime(2010, 5, 20), datetime(2010, 5, 31))
        self.assertTrue(len(cd.entity) > 0, "Empty temperature values")
        self.checkIndicators(cd, u"T")

    def testGetIndicatorsBloodPressure(self):
        # АД
        cd = self.getIndicators(23757, datetime(2011, 3, 29), datetime(2012, 1, 1))
        self.assertTrue(len(cd.entity) > 0, "Empty blood pressure values")
        self.checkIndicators(cd, u'АД верхн')
        self.checkIndicators(cd, u'АД нижн')

    def testGetIndicatorsHeartBeat(self):
        # ЧДД, ЧСС
        cd = self.getIndicators(23757, datetime(2010, 6, 13), datetime(2010, 6, 15))
        self.assertTrue(len(cd.entity) > 0, "Empty heartbeat values")
        self.checkIndicators(cd, u"ЧДД")
        self.checkIndicators(cd, u"ЧСС")

    def testCreateAssessment(self):
        # Ант Ка
        eventId = 29801
        # Осмотр: врач приемного отделения
        atId = 139
        types = self.getAssessmentTypes(eventId)
        assessmentType = None
        for e in types.entity:
            if e._id == atId:
                assessmentType = e
                break
        self.assertTrue(assessmentType != None, "No actionType found")
        attrs = assessmentType.group[0].attribute
        e = self.setProperty(attrs, u"t", "37.7")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"ЧД", "25")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"ЧСС", "108")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Эпидемиологический анамнез", u'''\
            Дисфункции кишечника за последние 3 недели не отмечалось.
            За пределы Саратовской области выезжала в Турцию, прибыла 12.07.2010.
            Заболевания: Tbc, вен.заболевания, педикулез, малярия, scabies отрицают.
            Флюорография 2010 года без патологии со слов мамы.
        ''')
        self.assertIsNotNone(e)
    #   e = self.setProperty(attrs, u"Жалобы", u'''\
    #       рвота с примесью алой крови 16.07.2010,
    #       кал кашицеобразный темного цвета 17.07.2010,
    #       повышение температуры тела до 37.4 С.,
    #       появление белых пятен на лице
    #   ''')
    #   self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Проф.прививки", u'''по календарю, реакция Манту сведений нет''')
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Перенесенные заболевания", u'''ОРВИ''')
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Перенесенные операции", u"не было")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Аллергоанамнез", u"пищевая молочный белок")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Лекарственная аллергия, побочные действия", u"отр")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u'''На "Д" учете с диагнозом''', u"не наблюдается")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"АД верхн.", "140")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"АД нижн.", "60")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Состояние", u"удовлетворительное")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Самочувствие", u"удовлетворительное")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Телосложение", u"нормальное")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Сознание", u"ясное")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Кожа и подкожная клетчатка", u'''\
           Кожные покровы: цвета загара, чистые, влажные, периорбитальный цианоз. 
           Эластичность кожи нормальная. Слизистые бледно-розовые чистые влажные.
           Подкожно-жировой слой развит нормально распределен равномерно.
           Тургор тканей сохранен.
           Волосы и ногти в норме. Отеки: видимых отеков нет.
        ''')
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Лимфатические узлы", u"не пальпируются")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Зев", u"чистый негиперемированный")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Миндалины", u"обычные,")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Дыхание", u'''\
           Форма грудной клетки нормальная.
           Грудная клетка: не деформирована.
           Носовое дыхание свободное, отделяемое отсутствует.
           Аускультативно дыхание везикулярное, хрипы не выслушиваются.
           Перкуторный звук ясный легочный.
           Участие обеих половин в акте дыхания синхронно. 
        ''')
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Сердце", u'''
           Область сердца не изменена.
           Тоны сердца: ясные, ритмичные.
           Границы относительной сердечной тупости соответствуют возрасту.
           Ритм правильный.
           Верхушечный толчок локализованный, неинтенсивный.
        ''')
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Язык", u"чистый, влажный,")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Живот", u"мягкий, безболезненный,  правильной формы, симметричный, увеличен в объеме")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Печень", u'''
            выступает из подреберья на 2,0 - 3,0 см,
            край ровный мягкоэластичной консистенции безболезненный
        ''')
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Селезенка", u"выступает на 10 см")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Стул", u'''
            ежедневный, без запоров, без патологических примесей, консистенция, оформленный, 17.07.2010 с примесью алой крови
        ''')
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Диурез", u"адекватный")
        self.assertIsNotNone(e)
        # MKB.id = 4761 (Острая печеночная недостаточность)
        e = self.setProperty(attrs, u"Диагноз", u"4761")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Назначения", u'''
            режим: общий уход матери,
            стол: щадящая диета.
            Бак анализ ребенок и мама 20.07.2010 сдан.
            Кровь на малярию.
            Госпитализация в бокс провизорного отделения.
        ''')
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Госпитализируется", u'''
            мама по уходу за ребенком, осмотрена, данных за инфекционные заболевания не выявлено
        ''')
        self.assertIsNotNone(e)
        #
        commonData = self.client.factory.create("entities")
        commonData.entity.append(assessmentType)
        #
        self.client.service.createAssessmentForPatient(eventId, commonData)

    def testGetCurrentPatients(self):
        cdata = self.client.service.getCurrentPatients()
        self.assertIsNotNone(cdata)

    def testModifyAssessmentForPatient(self):
        eventId = 60172
        assessmentId = 280606
        # Get
        a = self.getAssessmentForPatient(eventId, assessmentId)
        self.assertFalse(len(a.entity) == 0, "Empty assessment")
        # Modify
        ret = self.client.service.modifyAssessmentForPatient(eventId, assessmentId, a)
        self.assertFalse(len(ret) == 0, "Empty result")

    def testCreateAssessmentBug1(self):
        eventId = 64811
        atId = 462
        types = self.getAssessmentTypes(eventId)
        assessmentType = None
        for e in types.entity:
            if e._id == atId:
                assessmentType = e
                break
        self.assertTrue(assessmentType != None, "No actionType found")
        attrs = assessmentType.group[0].attribute
        e = self.setProperty(attrs, u"Состояние", u"удовлетворительное")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Сознание", u"ступор")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Мероприятия", u"Перевод на ИВЛ с параметрами")
        self.assertIsNotNone(e)
        #
        commonData = self.client.factory.create("entities")
        commonData.entity.append(assessmentType)
        #
        ret = self.client.service.createAssessmentForPatient(eventId, commonData)
        self.assertFalse(len(ret) == 0, "Empty result")

    def testCreateAssessmentBug2(self):
        eventId = 64811
        atId = 309
        types = self.getAssessmentTypes(eventId)
        assessmentType = None
        for e in types.entity:
            if e._id == atId:
                assessmentType = e
                break
        self.assertTrue(assessmentType != None, "No actionType found")
        attrs = assessmentType.group[0].attribute
        e = self.setProperty(attrs, u"Состав консилиума", u"Я и все остальные")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Сводка патологических данных", u"Все плохо")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Диагноз/Заключение консилиума", u"болен")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Рекомендации по обследованию", u"Анализы вместе все")
        self.assertIsNotNone(e)
        e = self.setProperty(attrs, u"Рекомендации по лечению", u"Протирания и банки")
        self.assertIsNotNone(e)
        #
        commonData = self.client.factory.create("entities")
        commonData.entity.append(assessmentType)
        #
        ret = self.client.service.createAssessmentForPatient(eventId, commonData)
        self.assertFalse(len(ret) == 0, "Empty result")
        #
        aa = self.getAllAssessmentsForPatient(eventId);
        found = False
        for a in aa.entity:
            if u"Консилиум" in a._name:
                found = True
        self.assertTrue(found, "Consilium not found")

if __name__ == '__main__':
    nose.main()
