package ru.korus.tmis.core.util

import org.junit.Test
import ru.korus.tmis.util.General
import java.util.{List => JList, LinkedList}

import org.junit.Assert._

class GeneralTest {

  @Test
  def test_?! {
    import General.nullity_implicits

    val vi0: List[Integer] = null
    val vi1: List[Integer] = List(1,2,3)

    assert(vi0 ?! { _.head } ?!! { _.byteValue } == None)
    assert(vi1 ?! { _.head } ?!! { _.byteValue } == Some(1))

    val v0 :java.lang.Double = 2.0
    val v1 = Some(v0)


  }

  @Test
  def test_flow_implicits {
    import General.flow_implicits

    var vi = new LinkedList[Int]() stating { it => it.add(2); it.add(3) } stating { _.add(1) }

    assertTrue(vi.contains(1) && vi.contains(2) && vi.contains(3))

    vi = new LinkedList[Int]() stating ()

    assertTrue(vi.isEmpty)

    vi = new LinkedList[Int]() stating (_.add(1),_.add(2),_.add(3))

    assertTrue(vi.contains(1) && vi.contains(2) && vi.contains(3))

  }

}
