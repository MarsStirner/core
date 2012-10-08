package ru.korus.tmis.util

import grizzled.slf4j.Logging

trait ExtendedLogging extends Logging {
  def traceVal(v: Any) = {
    trace(v.toString);
    v
  }

  def infoVal(v: Any) = {
    info(v.toString);
    v
  }

  def debugVal(v: Any) = {
    debug(v.toString);
    v
  }
}

