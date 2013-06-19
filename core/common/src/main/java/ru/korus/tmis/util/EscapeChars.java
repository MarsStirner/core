package ru.korus.tmis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        19.06.13, 17:09 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class EscapeChars {
    private static final Logger logger = LoggerFactory.getLogger(EscapeChars.class);


    /**
     * Synonym for <tt>URLEncoder.encode(String, "UTF-8")</tt>.
     * <p/>
     * <P>Used to ensure that HTTP query strings are in proper form, by escaping
     * special characters such as spaces.
     * <p/>
     * <P>An example use case for this method is a login scheme in which, after successful
     * login, the user is redirected to the "original" target destination. Such a target
     * might be passed around as a request parameter. Such a request parameter
     * will have a URL as its value, as in "LoginTarget=Blah.jsp?this=that&blah=boo", and
     * would need to be URL-encoded in order to escape its special characters.
     * <p/>
     * <P>It is important to note that if a query string appears in an <tt>HREF</tt>
     * attribute, then there are two issues - ensuring the query string is valid HTTP
     * (it is URL-encoded), and ensuring it is valid HTML (ensuring the ampersand is escaped).
     */

    public static String forURL(String aURLFragment) {
        String result = null;
        try {
            result = URLEncoder.encode(aURLFragment, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            //throw new RuntimeException("UTF-8 not supported", ex);
        }
        return result;
    }

    /**
     * Replace characters having special meaning <em>inside</em> HTML tags
     * with their escaped equivalents, using character entities such as <tt>'&amp;'</tt>.
     * <p/>
     * <P>The escaped characters are :
     * <ul>
     * <li> <
     * <li> >
     * <li> "
     * <li> '
     * <li> \
     * <li> &
     * </ul>
     * <p/>
     * <P>This method ensures that arbitrary text appearing inside a tag does not "confuse"
     * the tag. For example, <tt>HREF='Blah.do?Page=1&Sort=ASC'</tt>
     * does not comply with strict HTML because of the ampersand, and should be changed to
     * <tt>HREF='Blah.do?Page=1&amp;Sort=ASC'</tt>. This is commonly seen in building
     * query strings. (In JSTL, the c:url tag performs this task automatically.)
     */
    public static String forHTMLTag(String aTagFragment) {
        final StringBuffer result = new StringBuffer();
        if (aTagFragment != null) {
            final StringCharacterIterator iterator = new StringCharacterIterator(aTagFragment);
            char character = iterator.current();
            while (character != StringCharacterIterator.DONE) {
                if (character == '<') {
                    result.append("&lt;");
                } else if (character == '>') {
                    result.append("&gt;");
                } else if (character == '\"') {
                    result.append("&quot;");
                } else if (character == '\'') {
                    result.append("&#039;");
                } else if (character == '\\') {
                    result.append("&#092;");
                } else if (character == '&') {
                    result.append("&amp;");
                } else {
                    //the char is not a special one
                    //add it to the result as is
                    result.append(character);
                }
                character = iterator.next();
            }
        }
        return result.toString();
    }

    /**
     * Replace characters having special meaning <em>inside</em> HTML tags
     * with their escaped equivalents, using character entities such as <tt>'&amp;'</tt>.
     * <p/>
     * <P>The escaped characters are :
     * <ul>
     * <li> <
     * <li> >
     * <li> "
     * <li> '
     * <li> \
     * <li> &
     * </ul>
     * <p/>
     * <P>This method ensures that arbitrary text appearing inside a tag does not "confuse"
     * the tag. For example, <tt>HREF='Blah.do?Page=1&Sort=ASC'</tt>
     * does not comply with strict HTML because of the ampersand, and should be changed to
     * <tt>HREF='Blah.do?Page=1&amp;Sort=ASC'</tt>. This is commonly seen in building
     * query strings. (In JSTL, the c:url tag performs this task automatically.)
     */
    public static String forMULTag(String aTagFragment) {
        final StringBuffer result = new StringBuffer();
        if (aTagFragment != null && !"".equals(aTagFragment)) {
            final StringCharacterIterator iterator = new StringCharacterIterator(aTagFragment);
            char character = iterator.current();
            while (character != StringCharacterIterator.DONE) {
                if (character == '<') {
                    result.append("&lt;");
                } else if (character == '>') {
                    result.append("&gt;");
                } else if (character == '\"') {
                    result.append("&quot;");
                } else if (character == '\'') {
                    result.append("&#013;");
                } else if (character == '\'') {
                    result.append("&#039;");
                } else if (character == '\\') {
                    result.append("&#092;");
                } else if (character == '&') {
                    result.append("&amp;");
                } else if (!((character == '\u0009') || (character == '\n') || (character == '\r') ||
                        ('\u0020' <= character && character <= '\uD7FF') ||
                        ('\uE000' <= character && character <= '\uFFFD'))) {
                    result.append("?");
                } else {
                    //the char is not a special one
                    //add it to the result as is
                    result.append(character);
                }
                character = iterator.next();
            }
        }
        return result.toString();
    }

    /**
     * Return <tt>aText</tt> with all start-of-tag and end-of-tag characters
     * replaced by their escaped equivalents.
     * <p/>
     * <P>If user input may contain tags which must be disabled, then call
     * this method, not {@link #forHTMLTag}. This method is used for text appearing
     * <em>outside</em> of a tag, while {@link #forHTMLTag} is used for text appearing
     * <em>inside</em> an HTML tag.
     * <p/>
     * <P>It is not uncommon to see text on a web page presented erroneously, because
     * <em>all</em> special characters are escaped (as in {@link #forHTMLTag}). In
     * particular, the ampersand character is often escaped not once but <em>twice</em> :
     * once when the original input occurs, and then a second time when the same item is
     * retrieved from the database. This occurs because the ampersand is the only escaped
     * character which appears in a character entity.
     */
    public static String toDisableTags(String aText) {
        final StringBuffer result = new StringBuffer();
        final StringCharacterIterator iterator = new StringCharacterIterator(aText);
        char character = iterator.current();
        while (character != StringCharacterIterator.DONE) {
            if (character == '<') {
                result.append("&lt;");
            } else if (character == '>') {
                result.append("&gt;");
            } else {
                //the char is not a special one
                //add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }

    /**
     * make url for storing in database :
     * http://www.mail.ru?&lt;sid&gt;&amp;&lt;msisdn&gt; convert to:
     * http://www.mail.ru?&lt;sid&gt;&amp;&lt;msisdn&gt;
     */
    public static String toEnableTags(String aText) {
        final String resultString = aText.replaceAll("&lt;", "<");
        return resultString.replaceAll("&gt;", ">");
    }

    /**
     * Replace characters having special meaning in regular expressions
     * with their escaped equivalents.
     * <p/>
     * <P>The escaped characters include :
     * <ul>
     * <li>.
     * <li>\
     * <li>?, * , and +
     * <li>&
     * <li>:
     * <li>{ and }
     * <li>[ and ]
     * <li>( and )
     * <li>^ and $
     * </ul>
     */
    public static String forRegex(String aRegexFragment) {
        final StringBuffer result = new StringBuffer();

        final StringCharacterIterator iterator = new StringCharacterIterator(aRegexFragment);
        char character = iterator.current();
        while (character != StringCharacterIterator.DONE) {
            /*
            * All literals need to have backslashes doubled.
            */
            if (character == '.') {
                result.append("\\.");
            } else if (character == '\\') {
                result.append("\\\\");
            } else if (character == '?') {
                result.append("\\?");
            } else if (character == '*') {
                result.append("\\*");
            } else if (character == '+') {
                result.append("\\+");
            } else if (character == '&') {
                result.append("\\&");
            } else if (character == ':') {
                result.append("\\:");
            } else if (character == '{') {
                result.append("\\{");
            } else if (character == '}') {
                result.append("\\}");
            } else if (character == '[') {
                result.append("\\[");
            } else if (character == ']') {
                result.append("\\]");
            } else if (character == '(') {
                result.append("\\(");
            } else if (character == ')') {
                result.append("\\)");
            } else if (character == '^') {
                result.append("\\^");
            } else if (character == '$') {
                result.append("\\$");
            } else {
                //the char is not a special one
                //add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }

    /**
     * Returns hex String representation of byte b
     */
    static public String byteToHex(byte b) {
        //
        char hexDigit[] = {'0',
                '1',
                '2',
                '3',
                '4',
                '5',
                '6',
                '7',
                '8',
                '9',
                'a',
                'b',
                'c',
                'd',
                'e',
                'f'};
        char[] array = {hexDigit[(b >> 4) & 0x0f],
                hexDigit[b & 0x0f]};
        return new String(array);
    }

    /**
     * Returns hex String representation of char c
     */
    static public String charToHex(char c) {
        byte hi = (byte) (c >>> 8);
        byte lo = (byte) (c & 0xff);
        return byteToHex(hi) + byteToHex(lo);
    }

    /**
     * Replace characters with their escaped equivalents.
     *
     * @param stringFragment - input string
     * @param escapeAscii    - replace non-russian characters to escape-symbols. True - replace, false - not.
     * @return escaped string
     */
    static public String forEscapeUnicode(String stringFragment, boolean escapeAscii) {
        char ch;
        final StringBuffer result = new StringBuffer();
        char[] stringFragmentChars = stringFragment.toCharArray();
        for (int i = 0; i < stringFragmentChars.length; i++) {
            ch = stringFragmentChars[i];

            if (!escapeAscii && ((ch >= 0x0020) && (ch <= 0x007e))) {
                result.append(ch);
            } else {
                result.append("&#x");
                // String hex = Integer.toHexString(stringFragment.charAt(i) & 0xFFFF);
                String hex = charToHex(ch);
                if (hex.length() == 2) {
                    result.append("00");
                }
                result.append(hex.toUpperCase(Locale.ENGLISH) + ";");
            }
        }
        return result.toString();
    }

    private static Map<String, String> entities;

    private synchronized static Map<String, String> getEntities() {
        if (entities == null) {
            entities = new Hashtable<String, String>();
            //Quotation mark
            entities.put("quot", "\"");
            //Ampersand
            entities.put("amp", "\u0026");
            //Less than
            entities.put("lt", "\u003C");
            //Greater than
            entities.put("gt", "\u003E");
            //Nonbreaking space
            entities.put("nbsp", "\u00A0");
            //Inverted exclamation point
            entities.put("iexcl", "\u00A1");
            //Cent sign
            entities.put("cent", "\u00A2");
            //Pound sign
            entities.put("pound", "\u00A3");
            //General currency sign
            entities.put("curren", "\u00A4");
            //Yen sign
            entities.put("yen", "\u00A5");
            //Broken vertical bar
            entities.put("brvbar", "\u00A6");
            //Section sign
            entities.put("sect", "\u00A7");
            //Umlaut
            entities.put("uml", "\u00A8");
            //Copyright
            entities.put("copy", "\u00A9");
            //Feminine ordinal
            entities.put("ordf", "\u00AA");
            //Left angle quote
            entities.put("laquo", "\u00AB");
            //Not sign
            entities.put("not", "\u00AC");
            //Soft hyphen
            entities.put("shy", "\u00AD");
            //Registered trademark
            entities.put("reg", "\u00AE");
            //Macron accent
            entities.put("macr", "\u00AF");
            //Degree sign
            entities.put("deg", "\u00B0");
            //Plus or minus
            entities.put("plusmn", "\u00B1");
            //Superscript 2
            entities.put("sup2", "\u00B2");
            //Superscript 3
            entities.put("sup3", "\u00B3");
            //Acute accent
            entities.put("acute", "\u00B4");
            //Micro sign (Greek mu)
            entities.put("micro", "\u00B5");
            //Paragraph sign
            entities.put("para", "\u00B6");
            //Middle dot
            entities.put("middot", "\u00B7");
            //Cedilla
            entities.put("cedil", "\u00B8");
            //Superscript 1
            entities.put("sup1", "\u00B9");
            //Masculine ordinal
            entities.put("ordm", "\u00BA");
            //Right angle quote
            entities.put("raquo", "\u00BB");
            //Fraction one-fourth
            entities.put("frac14", "\u00BC");
            //Fraction one-half
            entities.put("frac12", "\u00BD");
            //Fraction three-fourths
            entities.put("frac34", "\u00BE");
            //Inverted question mark
            entities.put("iquest", "\u00BF");
            //Capital A, grave accent
            entities.put("Agrave", "\u00C0");
            //Capital A, acute accent
            entities.put("Aacute", "\u00C1");
            //Capital A, circumflex accent
            entities.put("Acirc", "\u00C2");
            //Capital A, tilde
            entities.put("Atilde", "\u00C3");
            //Capital A, umlaut
            entities.put("Auml", "\u00C4");
            //Capital A, ring
            entities.put("Aring", "\u00C5");
            //Capital AE ligature
            entities.put("AElig", "\u00C6");
            //Capital C, cedilla
            entities.put("Ccedil", "\u00C7");
            //Capital E, grave accent
            entities.put("Egrave", "\u00C8");
            //Capital E, acute accent
            entities.put("Eacute", "\u00C9");
            //Capital E, circumflex accent
            entities.put("Ecirc", "\u00CA");
            //Capital E, umlaut
            entities.put("Euml", "\u00CB");
            //Capital I, grave accent
            entities.put("Igrave", "\u00CC");
            //Capital I, acute accent
            entities.put("Iacute", "\u00CD");
            //Capital I, circumflex accent
            entities.put("Icirc", "\u00CE");
            //Capital I, umlaut
            entities.put("Iuml", "\u00CF");
            //Capital eth, Icelandic
            entities.put("ETH", "\u00D0");
            //Capital N, tilde
            entities.put("Ntilde", "\u00D1");
            //Capital O, grave accent
            entities.put("Ograve", "\u00D2");
            //Capital O, acute accent
            entities.put("Oacute", "\u00D3");
            //Capital O, circumflex accent
            entities.put("Ocirc", "\u00D4");
            //Capital O, tilde
            entities.put("Otilde", "\u00D5");
            //Capital O, umlaut
            entities.put("Ouml", "\u00D6");
            //Multiply sign
            entities.put("times", "\u00D7");
            //Capital O, slash
            entities.put("Oslash", "\u00D8");
            //Capital U, grave accent
            entities.put("Ugrave", "\u00D9");
            //Capital U, acute accent
            entities.put("Uacute", "\u00DA");
            //Capital U, circumflex accent
            entities.put("Ucirc", "\u00DB");
            //Capital U, umlaut
            entities.put("Uuml", "\u00DC");
            //Capital Y, acute accent
            entities.put("Yacute", "\u00DD");
            //Capital thorn, Icelandic
            entities.put("THORN", "\u00DE");
            //Small sz ligature, German
            entities.put("szlig", "\u00DF");
            //Small a, grave accent
            entities.put("agrave", "\u00E0");
            //Small a, acute accent
            entities.put("aacute", "\u00E1");
            //Small a, circumflex accent
            entities.put("acirc", "\u00E2");
            //Small a, tilde
            entities.put("atilde", "\u00E3");
            //Small a, umlaut
            entities.put("auml", "\u00E4");
            //Small a, ring
            entities.put("aring", "\u00E5");
            //Small ae ligature
            entities.put("aelig", "\u00E6");
            //Small c, cedilla
            entities.put("ccedil", "\u00E7");
            //Small e, grave accent
            entities.put("egrave", "\u00E8");
            //Small e, acute accent
            entities.put("eacute", "\u00E9");
            //Small e, circumflex accent
            entities.put("ecirc", "\u00EA");
            //Small e, umlaut
            entities.put("euml", "\u00EB");
            //Small i, grave accent
            entities.put("igrave", "\u00EC");
            //Small i, acute accent
            entities.put("iacute", "\u00ED");
            //Small i, circumflex accent
            entities.put("icirc", "\u00EE");
            //Small i, umlaut
            entities.put("iuml", "\u00EF");
            //Small eth, Icelandic
            entities.put("eth", "\u00F0");
            //Small n, tilde
            entities.put("ntilde", "\u00F1");
            //Small o, grave accent
            entities.put("ograve", "\u00F2");
            //Small o, acute accent
            entities.put("oacute", "\u00F3");
            //Small o, circumflex accent
            entities.put("ocirc", "\u00F4");
            //Small o, tilde
            entities.put("otilde", "\u00F5");
            //Small o, umlaut
            entities.put("ouml", "\u00F6");
            //Division sign
            entities.put("divide", "\u00F7");
            //Small o, slash
            entities.put("oslash", "\u00F8");
            //Small u, grave accent
            entities.put("ugrave", "\u00F9");
            //Small u, acute accent
            entities.put("uacute", "\u00FA");
            //Small u, circumflex accent
            entities.put("ucirc", "\u00FB");
            //Small u, umlaut
            entities.put("uuml", "\u00FC");
            //Small y, acute accent
            entities.put("yacute", "\u00FD");
            //Small thorn, Icelandic
            entities.put("thorn", "\u00FE");
            //Small y, umlaut
            entities.put("yuml", "\u00FF");
        }
        return entities;
    }

    /**
     * Replace escaped characters to ascii
     */
    public static String forDecodeUnicodeToString(String str) {
        StringBuffer ostr = new StringBuffer();
        int i1 = 0;
        int i2 = 0;

        while (i2 < str.length()) {
            i1 = str.indexOf("&", i2);
            if (i1 == -1) {
                ostr.append(str.substring(i2, str.length()));
                break;
            }
            ostr.append(str.substring(i2, i1));
            i2 = str.indexOf(";", i1);
            if (i2 == -1) {
                ostr.append(str.substring(i1, str.length()));
                break;
            }

            String tok = str.substring(i1 + 1, i2);
            if (tok.charAt(0) == '#') {
                tok = tok.substring(1);
                try {
                    int radix = 10;
                    if (tok.trim().charAt(0) == 'x') {
                        radix = 16;
                        tok = tok.substring(1, tok.length());
                    }
                    ostr.append((char) Integer.parseInt(tok, radix));
                } catch (NumberFormatException exp) {
                    ostr.append('?');
                }
            } else {
                tok = getEntities().get(tok);
                if (tok != null) {
                    ostr.append(tok);
                } else {
                    ostr.append('?');
                }
            }
            i2++;
        }
        return ostr.toString();
    }


    private static HashMap<String, String> replaceEntities = new HashMap<String, String>();

    static {
        // ? - 00AB \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB laquo \CD\C5\CE\D1\D4\D8 \CE\C1 " - 0022
        replaceEntities.put("\u00AB", "\"");
        // ? - 00BB \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB raquo \CD\C5\CE\D1\D4\D8 \CE\C1 " - 0022
        replaceEntities.put("\u00BB", "\"");
        // - - 00AD \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB SHY \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC \C9\CC\C9 - - 002D
        replaceEntities.put(("\u00AD"), "-");
        // ? - 00A7 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB sect \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC
        replaceEntities.put(("\u00A7"), " ");
        // ? - 00AE \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB reg  \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC
        replaceEntities.put(("\u00AE"), " ");
        // ? - 2013 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8211 \CD\C5\CE\D1\D4\D8 \CE\C1   \D0\D2\CF\C2\C5\CC \C9\CC\C9 - - 002D
        replaceEntities.put(("\u2013"), "-");
        // ? - 2014 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8212 \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC \C9\CC\C9 - - 002D
        replaceEntities.put(("\u2014"), "-");
        // ? - 2015 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8213 \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC \C9\CC\C9 - - 002D
        replaceEntities.put(("\u2015"), "-");
        // ? - 2017 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8215 \CD\C5\CE\D1\D4\D8 \CE\C1 005F
        replaceEntities.put(("\u2017"), "_");
        // ? ? 2018 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8216 \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC
        replaceEntities.put(("\u2018"), " ");
        // ? ? 2019 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8217 \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC
        replaceEntities.put(("\u2019"), " ");
        // ? - 201A \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8218 \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC
        replaceEntities.put(("\u201A"), " ");
        // ? - 201B \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8219 \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC
        replaceEntities.put(("\u201B"), " ");
        // ? - 201C \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8220 \CD\C5\CE\D1\D4\D8 \CE\C1 " - 0022
        replaceEntities.put(("\u201C"), "\"");
        // ? - 201D \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8221 \CD\C5\CE\D1\D4\D8 \CE\C1 " - 0022
        replaceEntities.put(("\u201D"), "\"");
        // ? - 201E \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8222 \CD\C5\CE\D1\D4\D8 \CE\C1 " - 0022
        replaceEntities.put(("\u201E"), "\"");
        // ? - 2032 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB 8#242 \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC
        replaceEntities.put(("\u2032"), " ");
        // ? - 2033 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB 8#243 \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC
        replaceEntities.put(("\u2033"), " ");
        // ? - 2039 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8249 \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC
        replaceEntities.put(("\u2039"), " ");
        // ? - 203A \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8250 \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC
        replaceEntities.put(("\u203A"), " ");
        //  ? - 2044 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8260 \CD\C5\CE\D1\D4\D8 \CE\C1 / - 002F
        replaceEntities.put(("\u2044"), "/");
        // ? - 2116 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8470 \CD\C5\CE\D1\D4\D8 \CE\C1 # - 0023
        replaceEntities.put(("\u2116"), "#");
        // ? - 2122 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8482 \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC
        replaceEntities.put(("\u2122"), " ");
        // ? - 2212 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8722 \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC \C9\CC\C9 - - 002D
        replaceEntities.put(("\u2212"), "-");
        // ? - 2215 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #8725 \CD\C5\CE\D1\D4\D8 \CE\C1 / - 002F
        replaceEntities.put(("\u2215"), "/");
        // - F004 \CF\D4\CF\C2\D2\C1\D6\C1\C5\D4\D3\D1 \CB\C1\CB #61444 \CD\C5\CE\D1\D4\D8 \CE\C1 \D0\D2\CF\C2\C5\CC
        replaceEntities.put(("\uF004"), " ");
    }

    public static String forSpecialCharacters(String str) {
        StringBuffer output = new StringBuffer();

        char[] input = str.toCharArray();
        for (int i = 0; i < input.length; i++) {
            char ch = input[i];

            if (replaceEntities.containsKey("" + ch)) {
                output.append(replaceEntities.get("" + ch));
            } else {
                output.append(ch);
            }
        }
        return output.toString();
    }

    public static void main(String[] args) {


        String str = null;
//        try {
        str = new String("\u00AB");
//        } catch (UnsupportedEncodingException e) {
//            System.out.println("!!!!");
//        }
        System.out.println("Input byte: " + str);

        System.out.println("Output: " + EscapeChars.forSpecialCharacters(str));

//        for (int i = 1; i < 75; i++) {
//            try {
//                FileOutputStream fos = new FileOutputStream("22.txt");
//                String rr = "existImageId.add(\"" + i + "\");";
//                System.out.println(""+rr);
//                fos.write(rr.getBytes());
//                fos.close();
//            } catch (FileNotFoundException e) {
//            } catch (IOException e) {
//            }
//        }

//        String source = "\E1 \FE\E5\E7\EF \D7\C1\DD\C5 \CE\C1\C4\CF-\D4\CF? Hello world";

//        String dest = forEscapeUnicode(source, false);
//        String ressss = forDecodeUnicodeToString(dest);

//        String res = EscapeChars.forHTMLTag("\F0\D2\C9\D7\C5\D4!");
//        String res2 = EscapeChars.forURL("\F0\D2\C9\D7\C5\D4!");
//        String res3 = EscapeChars.forRegex("\F0\D2\C9\D7\C5\D4!");
//        System.out.println("res: " + dest);
//        System.out.println("res2: " + ressss);

    }


}
