# -*- coding: utf-8 -*-

import nose
import unittest

import time

from suds import WebFault
from suds.client import Client
from suds.sax.element import Element

## Utility methods
def getDiagnosticTypes(client, eventId):
    types = client.service.getDiagnosticTypes("", eventId)
    return types

def createDiagnosticForPatient(client, eventId, diagnosticTypeId):
    template = None
    
    types = getDiagnosticTypes(client, eventId)
    for e in types.entity:
        if (e._id == diagnosticTypeId):
            template = e
            break
    
    if (template is None):
        return False        
    
    commonData = client.factory.create("entities")
    commonData.entity.append(template)
    
    return client.service.createDiagnosticForPatient(eventId, commonData)

def getAssessmentTypes(client, eventId):
    types = client.service.getAssessmentTypes("", eventId)
    return types

def createAssessmentForPatient(client, eventId, assessmentTypeId):
    template = None
    
    types = getAssessmentTypes(client, eventId)
    for e in types.entity:
        if (e._id == assessmentTypeId):
            template = e
            break
    
    if (template is None):
        return False        
    
    commonData = client.factory.create("entities")
    commonData.entity.append(template)
    
    return client.service.createAssessmentForPatient(eventId, commonData)
