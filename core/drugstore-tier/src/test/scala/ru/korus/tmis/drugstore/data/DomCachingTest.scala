package ru.korus.tmis.drugstore.data

import org.junit.Test
import ru.korus.tmis.drugstore.util.{DomCaching, ScalaXmlable}

class DomCachingTest {

  class FooXmlable extends ScalaXmlable {
    override val toXml = <foo><bar/></foo>
  }

  @Test
  def testCreation {
    // it must compile, that's all
    val cachingXmlAble = new FooXmlable with DomCaching

    assert(cachingXmlAble.toXmlDom eq cachingXmlAble.toXmlDom)

    val nonCachingXmlAble = new FooXmlable /* without DomCaching */

    assert(nonCachingXmlAble.toXmlDom ne nonCachingXmlAble.toXmlDom)
  }
}
