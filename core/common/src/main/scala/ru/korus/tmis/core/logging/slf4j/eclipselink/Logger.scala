package ru.korus.tmis.core.logging.slf4j.eclipselink

import grizzled.slf4j.Logging
import org.eclipse.persistence.logging.{SessionLogEntry, SessionLog, AbstractSessionLog}

import org.eclipse.persistence.logging.SessionLog._;

// slf4j to eclipselink logging adapter
class Logger extends AbstractSessionLog with Logging {
  def log(entry: SessionLogEntry) {
    def fMes = formatMessage(entry)

    entry.getLevel match {

      case SEVERE => error("ORM: " + fMes, entry.getException)
      case WARNING => warn("ORM: " + fMes, entry.getException)
      case INFO => info("ORM: " + fMes, entry.getException)
      case other => debug("ORM: Level " + entry.getLevel + ": " + fMes, entry.getException)
    }
  }
}
