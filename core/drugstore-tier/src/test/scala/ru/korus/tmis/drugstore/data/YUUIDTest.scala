package ru.korus.tmis.drugstore.data

/**
 * Created by IntelliJ IDEA.
 * User: belyaev
 * Date: 1/11/12
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.Test
import java.lang.{Integer => JInteger}
import ru.korus.tmis.core.entity.model.{Event, Patient, Action}

class YUUIDTest {

  @Test
  def testGenerate = {
    val any = "Some text"
    val action0 = new Action
    action0 setId 100500

    val action1 = new Action
    action1 setId 100500

    import YUUID._

    // id-based generation
    // if it wouldn't work, it wouldn't compile as well
    assert(generateById(action0) == generateById(action1))

    val event0 = new Event
    event0 setId 100500
    val event1 = new Event
    event1 setId 42

    assert(generateById(event0) != generateById(event1))
    assert(generateById(action0) != generateById(event0))

    // should also compile
    generateById(new Event())
    generateById(new Patient())
    generateById(new AnyRef {
      def getId(): JInteger = 0xCAFEBABE
    })


    // should not compile
    //generateById(any)
    //generateById("hi")
    //generateById(new AnyRef())

    // random-based generating
    assert(generateRandom() != generateRandom())

  }

}