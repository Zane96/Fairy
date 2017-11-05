package me.zane.fairy;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
//        String timeLine = "10-26 16:40:35.584";
//        String options = "-t \"10-26 16:40:35.666\" -d";
//        String oldTime = options.substring(options.indexOf("-t \""), options.indexOf("-t \"") + 23);
//        assertEquals("-t \"10-26 16:40:35.666\"", oldTime);
//        String newOptions = options.replace(oldTime, String.format("-t \"%s\"", timeLine));
//        assertEquals("-t \"10-26 16:40:35.584\" -d", newOptions);
        //assertEquals("10-26 16:40:36.000", DataEngine.getInstance().currentTimeLine("10-26 16:40:35.999"));

        assertEquals("-v threadtime", extractOptions("-v threadtime -t \"11-5 8:2:21.121\""));
    }

    private String extractOptions(String rawOptions) {
        if (!rawOptions.contains("-v")) {
            return rawOptions;
        }
        Pattern p = Pattern.compile("-v\\s[a-z]*");
        Matcher m = p.matcher(rawOptions);

        String ss = "";
        while (m.find()) {
            ss = rawOptions.substring(m.start(), m.end());
            break;
        }

        return ss;
//        String s = rawOptions.replaceAll("\\s-v\\s[a-z]*", "");
//        return s;
    }
}