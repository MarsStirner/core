package ru.korus.tmis.core.reflect.clones

import org.testng.Assert._

import org.testng.annotations.Test
import ru.korus.tmis.util.reflect.clones.Cloner

import ru.korus.tmis.core.entity.model._

class ClonerTest {

  // this test is intended only to check if our understanding of what google.cloning does reflects what it really does
  @Test
  def testClone(): Unit = {
    import Cloner.Java._

    val ap1 = new ActionProperty(26)
    ap1.setAction(new Action(44))
    ap1.setNorm("norm")

    val ap2 = deepClone(ap1)


    assertTrue(ap1 == ap2)
    assertFalse(ap1 eq ap2)

    assertTrue {
      ap2 match {
        case ap2: ActionProperty => true
        case _ => false
      }
    }

    assertTrue(ap1.getAction == ap2.getAction)
    assertFalse(ap1.getAction eq ap2.getAction)

    // strings are immutable, so they must not be cloned
    assertTrue(ap1.getNorm == ap2.getNorm)
    assertTrue(ap1.getNorm eq ap2.getNorm)


    // staff has circular link problems
    val staff = new Staff(23)
    // creating a circular link. this will fail to clone with naive impl
    staff.setCreatePerson(staff)

    // naivettes here will go infinite, resulting with a stack overflow
    val rett = deepClone(staff)

    // smart cloning instead should clone the value PRESERVING the circular link
    assertTrue(rett == staff)
    assertFalse(rett.getCreatePerson eq staff.getCreatePerson)
    assertTrue(rett.getCreatePerson eq rett)


  }


  case class A(v: List[Int])

  case class B(v: A)


  // showing off that with scala classes (most notably, case classes) not everything works as expected
  @Test
  def testCloneA(): Unit = {
    import Cloner._

    val a = A(List(1, 2, 3, 4))
    a.copy()
    val b = deepClone(a)
    val c = A(List(1, 2, 3, 4))

    // assertTrue(a == b) //does not work =(
    assertFalse(a eq b)
    assertTrue(a.v == b.v)
    // immutable lists are, well, immutable
    assertTrue(a.v eq b.v)

    val bb = B(a)
    val ba = deepClone(bb)

    scala.collection.mutable.ListBuffer

    // assertTrue(bb == ba) does not work =(
    assertFalse(bb.v eq ba.v)


    val cc = scala.collection.mutable.ListBuffer(1, 2, 3)
    val ccopy = deepClone(cc)

    assertEquals(cc, ccopy)
    assertFalse(cc eq ccopy)

  }
}
