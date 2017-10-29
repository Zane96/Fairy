package me.zane.fairy;

import org.junit.Test;

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
        String timeLine = "10-26 16:40:35.584";
        String options = "-t \"10-26 16:40:35.666\" -d";
        String oldTime = options.substring(options.indexOf("-t \""), options.indexOf("-t \"") + 23);
        assertEquals("-t \"10-26 16:40:35.666\"", oldTime);
        String newOptions = options.replace(oldTime, String.format("-t \"%s\"", timeLine));
        assertEquals("-t \"10-26 16:40:35.584\" -d", newOptions);
        //assertEquals("10-26 16:40:36.000", DataEngine.getInstance().currentTimeLine("10-26 16:40:35.999"));
    }
}