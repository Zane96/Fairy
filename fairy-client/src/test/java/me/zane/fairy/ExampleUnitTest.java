package me.zane.fairy;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.os.Handler;
import android.os.Looper;

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
        //assertEquals("10-26 16:40:36.000", LiveNetService.getInstance().currentTimeLine("10-26 16:40:35.999"));

        assertEquals("-v threadtime", extractOptions("-v threadtime -t \"11-5 8:2:21.121\""));

        String rawString = "<p>sss</p><p>bbb</p>";
        String[] strs = rawString.split("<p>");
        assertEquals("sss", strs[2]);

//        Holder holder = new Holder();
//        holder.start();
//        holder.creat("").observe(this, str -> {
//            ZLog.i(str);
//        });

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