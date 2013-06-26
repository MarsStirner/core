package ru.korus.tmis.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import ru.korus.tmis.util.TextFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        19.06.13, 0:51 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class TextFormatTests {
    private static final Logger logger = LoggerFactory.getLogger(TextFormatTests.class);

    @Test
    public void testBadCases() {
        assertEquals("asd  qwe", new TextFormat("asd ${absent} qwe").format("test", "zzz"));
        assertEquals("asd  qwe", new TextFormat("asd ${} qwe").format("test", "zzz"));
        assertEquals("asd  qwe", new TextFormat("asd ${test} qwe").format());

        assertEquals("asd $ {test} qwe", new TextFormat("asd $ {test} qwe").format());
        assertEquals("asd {test} qwe", new TextFormat("asd {test} qwe").format());
    }

    @Test
    public void testArray() {
        assertEquals("asd zzz qwe", new TextFormat("asd ${test} qwe").format("test", "zzz"));
        assertEquals("zzz qwe", new TextFormat("${test} qwe").format("test", "zzz"));
        assertEquals("asd zzz", new TextFormat("asd ${test}").format("test", "zzz"));
    }

    @Test
    public void testMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("test", "zzz");

        assertEquals("asd zzz qwe", new TextFormat("asd ${test} qwe").format(map));
        assertEquals("zzz qwe", new TextFormat("${test} qwe").format(map));
        assertEquals("asd zzz", new TextFormat("asd ${test}").format(map));

        assertEquals("asd  qwe", new TextFormat("asd ${absent} qwe").format(map));
    }

    @Test
    public void testNameResolver() {
        TextFormat.NameResolver nr = new TextFormat.NameResolver() {
            public String getValue(String name) {
                return "<" + name + ">";
            }
        };

        assertEquals("asd <test> qwe", new TextFormat("asd ${test} qwe").format(nr));
        assertEquals("asd <test>", new TextFormat("asd ${test}").format(nr));
        assertEquals("<test> qwe", new TextFormat("${test} qwe").format(nr));
    }

    @Test
    public void testParamList() {
        TextFormat tf = new TextFormat("asd ${test} qwe ${test2}");
        List<String> set = tf.getVariableNames();
        assertEquals(set.size(), 2);
        assertTrue(set.contains("test"));
        assertTrue(set.contains("test2"));
    }

    @Test
    public void testOtherPattern() {
        TextFormat tf = new TextFormat("SELECT * FROM blabla WHERE date BETWEEN @STARTDATE and @ENDDATE and operator=@OPERATOR", Pattern.compile("@([\\d\\w_-]+)"));

        assertEquals(
                "SELECT * FROM blabla WHERE date BETWEEN <start> and <end> and operator=<operator>",
                tf.format("STARTDATE", "<start>", "ENDDATE", "<end>", "OPERATOR", "<operator>")
        );


        System.out.println("result: " + tf.format("STARTDATE", "<start>", "ENDDATE", "<end>", "OPERATOR", "<operator>"));
        System.out.println("result for sql: " + tf.format("STARTDATE", "?", "ENDDATE", "?", "OPERATOR", "?"));
        System.out.println("vars: " + tf.getVariables());
        assertEquals("[STARTDATE, ENDDATE, OPERATOR]", tf.getVariables().toString());
    }
}
