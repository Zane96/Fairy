package me.zane.fairy;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.os.Handler;
import android.os.Looper;

import org.junit.Test;

import java.util.Calendar;
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
        //assertEquals(4, 2 + 2);
//        String timeLine = "10-26 16:40:35.584";
//        String options = "-t \"10-26 16:40:35.666\" -d";
//        String oldTime = options.substring(options.indexOf("-t \""), options.indexOf("-t \"") + 23);
//        assertEquals("-t \"10-26 16:40:35.666\"", oldTime);
//        String newOptions = options.replace(oldTime, String.format("-t \"%s\"", timeLine));
//        assertEquals("-t \"10-26 16:40:35.584\" -d", newOptions);
        //assertEquals("10-26 16:40:36.000", LiveNetService.getInstance().currentTimeLine("10-26 16:40:35.999"));

//        assertEquals("-v threadtime", extractOptions("-v threadtime -t \"11-5 8:2:21.121\""));
//
//        String rawString = "<p>sss</p><p>bbb</p>";
//        String[] strs = rawString.split("<p>");
//        assertEquals("sss", strs[2]);

//        Holder holder = new Holder();
//        holder.start();
//        holder.creat("").observe(this, str -> {
//            ZLog.i(str);
//        });

        //assertEquals("12-01 15:52:05.667", getStartTimeLine());

        assertEquals("12", addOneMillsecond("01-29 14:13:03.066"));
    }

    public String addOneMillsecond(String timeLine) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.MONTH, Integer.valueOf(timeLine.substring(0, timeLine.indexOf("-"))) + 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(timeLine.substring(timeLine.indexOf("-") + 1, timeLine.indexOf(" "))));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeLine.substring(timeLine.indexOf(" ") + 1, timeLine.indexOf(":"))));
        calendar.set(Calendar.MINUTE, Integer.valueOf(timeLine.substring(timeLine.indexOf(":") + 1, timeLine.lastIndexOf(":"))));
        calendar.set(Calendar.SECOND, Integer.valueOf(timeLine.substring(timeLine.lastIndexOf(":") + 1, timeLine.indexOf("."))));
        calendar.set(Calendar.MILLISECOND, Integer.valueOf(timeLine.substring(timeLine.indexOf(".") + 1, timeLine.length())));
        calendar.add(Calendar.MILLISECOND, 1);

        int month = calendar.get(Calendar.MONTH) == 0 ? 12 : calendar.get(Calendar.MONTH) - 1;
        return String.format("%d-%d %d:%d:%d.%d",
                month,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                calendar.get(Calendar.MILLISECOND));
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.MONTH, 12);12

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, 29);  //设置日期
//        calendar.set(Calendar.MONTH, 0);
//        calendar.set(Calendar.YEAR, 2018);
//        return calendar.get(Calendar.MONTH) + 1 +" " + calendar.get(Calendar.DAY_OF_MONTH);
//        calendar.set(Calendar.DATE, Integer.valueOf(timeLine.substring(timeLine.indexOf("-") + 1, timeLine.indexOf(" "))));
//        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeLine.substring(timeLine.indexOf(" ") + 1, timeLine.indexOf(":"))));
//        calendar.set(Calendar.MINUTE, Integer.valueOf(timeLine.substring(timeLine.indexOf(":") + 1, timeLine.lastIndexOf(":"))));
//        calendar.set(Calendar.SECOND, Integer.valueOf(timeLine.substring(timeLine.lastIndexOf(":") + 1, timeLine.indexOf("."))));
//        calendar.set(Calendar.MILLISECOND, Integer.valueOf(timeLine.substring(timeLine.indexOf(".") + 1, timeLine.length())));
//        calendar.add(Calendar.MILLISECOND, 1);

        //return calendar.get(Calendar.MONTH)+"";
//        return String.format("%d-%d %d:%d:%d.%d",
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DATE),
//                calendar.get(Calendar.HOUR_OF_DAY),
//                calendar.get(Calendar.MINUTE),
//                calendar.get(Calendar.SECOND),
//                calendar.get(Calendar.MILLISECOND));
    }

    private String getStartTimeLine() {
        String timeLine;
        timeLine = Utils.addOneMillsecond("12-01 15:52:05.666");

        return timeLine;
    }

    class Holder{
        MediatorLiveData<String> data;
        MutableLiveData<String> rawData = new MutableLiveData<>();
        LiveData<String> grepData;
        MutableLiveData<Boolean> transData = new MutableLiveData<>();
        String grep = "";

        public Holder() {
            data = new MediatorLiveData<>();
            data.addSource(rawData, str -> {
                data.setValue(str);
            });
            transData.setValue(false);
        }

        public void start() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 15; i++) {
                        int finalI = i;
                        if (i == 5) {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                data.removeSource(rawData);
                                rawData.setValue("grep");
                                grepData = Transformations.map(rawData, str -> {
                                    return str + " " + grep;
                                });

                                data.addSource(grepData, str -> {
                                    data.setValue(str);
                                });
                            });
                            transData.postValue(true);
                            grep = " 222";
                        }
                        if (i == 6) {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                data.removeSource(grepData);
                                rawData.setValue("raw");
                                data.addSource(rawData, str -> {
                                    data.setValue(str);
                                });
                            });

                            transData.postValue(false);
                        }
                        if (i == 10) {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                data.removeSource(rawData);
                                rawData.setValue("grep");
                                grepData = Transformations.map(rawData, str -> {
                                    return str + " " + grep;
                                });

                                data.addSource(grepData, str -> {
                                    data.setValue(str);
                                });
                            });
                            transData.postValue(true);
                            grep = " 333";
                        }
                        if (i != 5 && i != 6 && i != 10) {
                            rawData.postValue("sb: " + i);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        public LiveData<String> creat(String grep) {
            return Transformations.switchMap(transData, boo -> {
                return data;
            });
        }
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