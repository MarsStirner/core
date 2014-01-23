package ru.korus.tmis.core.util

import org.junit.Test

import org.junit.Assert._
import ru.korus.tmis.scala.util.{Types, General}
import Types.{JLinked, JDouble, JInteger}
import ru.korus.tmis.scala.util.General

// this is no error, we override standard scala assert to junit one

import org.junit.Assert.{assertTrue => assert}

class GeneralTest {

  case class TestClass(next: TestClass) {
    def getNext = next
  }

  @Test
  def test_nullity {
    import General.nullity_implicits

    val vi0: List[JInteger] = null
    val vi1: List[JInteger] = List(1, 2, 3)
    val vi2: List[JInteger] = List(null, 2, 3)

    assert(vi0 ?! {
      _.head
    } ?!! {
      _.byteValue
    } == None)
    assert(vi1 ?! {
      _.head
    } ?!! {
      _.byteValue
    } == Some(1))
    assert(vi2 ?! {
      _.head
    } ?!! {
      _.byteValue
    } == None)

    val v0: JDouble = 2.0
    val v1 = Some(v0)


    val testClass = TestClass(TestClass(TestClass(null)))
    val testNull: TestClass = null

    assert(testClass ?! {
      _.next
    } ?! {
      _.getNext
    } ?! {
      _.next
    } ?! {
      _.next
    } ?! {
      _.next
    } == null)
    assert(testClass ?! {
      _.next
    } ?! {
      _.getNext
    } ?! {
      _.next
    } == null)
    assert(testClass ?! {
      _.next
    } ?! {
      _.getNext
    } == TestClass(null))

    assert(testNull ?! {
      _.next
    } ?! {
      _.getNext
    } ?! {
      _.next
    } ?! {
      _.next
    } ?! {
      _.next
    } == null)
    assert(testNull ?! {
      _.next
    } ?! {
      _.getNext
    } ?! {
      _.next
    } == null)
    assert(testNull ?! {
      _.next
    } ?! {
      _.getNext
    } == null)

    assert(testNull ?: testClass == testClass)
    assert(testClass ?: testNull == testClass)
    assert(testNull ?: testNull ?: testNull == testNull)
    assert(testNull ?: testClass ?: testNull == testClass)
    assert(testClass ?: testNull ?: testNull == testClass)
    assert(testNull ?: testNull ?: testClass == testClass)

    {
      val a = new {
        val b = new {
          val c = new {
            val d = 2
          }
        }
      }

      assert((a ?! {
        _.b
      } ?! {
        _.c
      } ?!! {
        _.d
      } getOrElse 0) == 2)
    }


    {
      var a = new {
        var b = new {
          var c = new {
            var d = 2
          }
        }
      }

      a.b.c = null

      assert((a ?! {
        _.b
      } ?! {
        _.c
      } ?!! {
        _.d
      } getOrElse 0) == 0)
    }

    assert(null ?: 25 == 25)

    {
      val nn: JInteger = null
      assert(nn ?: 25 == 25)
    }

    {
      val nn: JInteger = 23
      assert(nn ?: 25 == 23)
      lazy val rvalue: JInteger = {
        throw new NullPointerException
      }
      assert(nn ?: rvalue == 23)
      def rvalue2: JInteger = {
        throw new NullPointerException
      }
      assert(nn ?: rvalue2 == 23)
    }


  }

  @Test
  def test_typed_equality {
    import General.typedEquality

    val v0 = 12
    val v1 = 12
    val v2 = 12.0

    assertTrue(v0 == v1)
    assertTrue(v1 == v2)
    assertTrue(v0 =!= v1)
    // will not compile on purpose: assertFalse(v1 =!= v2)
  }

  @Test
  def test_flow_implicits {
    import General.flow_implicits

    var vi = new JLinked[Int]() stating {
      it => it.add(2); it.add(3)
    } stating {
      _.add(1)
    }

    assertTrue(vi.contains(1) && vi.contains(2) && vi.contains(3))

    vi = new JLinked[Int]() stating()

    assertTrue(vi.isEmpty)

    vi = new JLinked[Int]() stating(_.add(1), _.add(2), _.add(3))

    assertTrue(vi.contains(1) && vi.contains(2) && vi.contains(3))

  }

}
