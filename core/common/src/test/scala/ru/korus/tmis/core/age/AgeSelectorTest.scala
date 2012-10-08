package ru.korus.tmis.core.age

import org.junit.Test
import java.util.Date

class AgeSelectorTest {
  @Test def testCheck {
    val now = new Date()
    val y = now.getYear
    val m = now.getMonth
    val d = now.getDate

    var as = new AgeSelector("")
    assert(as.check(new Date(99, 11, 1)))

    as = new AgeSelector("someshit")
    assert(as.check(new Date(99, 11, 1)))


    as = new AgeSelector("-14Г")
    assert(as.check(new Date(y - 13, m, d)))

    as = new AgeSelector("-12Г")
    assert(!as.check(new Date(y - 13, m, d)))

    as = new AgeSelector("13Г-14Г")
    assert(!as.check(new Date(y - 12, m + 1, d)))
    assert(as.check(new Date(y - 14, m + 1, d)))
    assert(!as.check(new Date(y - 15, m + 1, d)))

    as = new AgeSelector("13Г-")
    assert(!as.check(new Date(y - 12, m + 1, d)))
    assert(as.check(new Date(y - 13, m - 1, d)))
    assert(as.check(new Date(y - 14, m - 1, d)))

    as = new AgeSelector("-14г")
    assert(as.check(new Date(y - 13, m, d)))

    as = new AgeSelector("-12г")
    assert(!as.check(new Date(y - 13, m, d)))

    as = new AgeSelector("-")
    assert(as.check(new Date(y - 12, m + 1, d)))
    assert(as.check(new Date(y - 13, m + 1, d)))
    assert(as.check(new Date(y - 14, m + 1, d)))

    as = new AgeSelector("10М-")
    assert(as.check(new Date(y - 10, m, d)))
    assert(as.check(new Date(y - 1, m, d)))
    assert(!as.check(new Date(y, m - 9, d)))
    //assert(as.check(new Date(y, m-10, d)))


    assert(as.check(new Date(y, m - 11, d)))

    as = new AgeSelector("-10м")
    assert(!as.check(new Date(y - 10, m, d)))
    assert(!as.check(new Date(y - 1, m, d)))
    assert(as.check(new Date(y, m - 9, d)))
    assert(as.check(new Date(y, m - 10, d)))
    assert(!as.check(new Date(y, m - 11, d)))

    as = new AgeSelector(null)
    assert(as.check(new Date(y - 12, m + 1, d)))
    assert(as.check(new Date(y - 13, m + 1, d)))
    assert(as.check(new Date(y - 14, m + 1, d)))
  }
}
