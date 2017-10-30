package me.zane.fairy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.StaticLayout;
import android.widget.TextView;

import me.zane.fairy.data.DataEngine;
import me.zane.fairy.data.LogcatData;


public class MainActivity extends AppCompatActivity {

    private TextView mText;
    private DataEngine engine;
    private StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = (TextView) findViewById(R.id.text);
        engine = new DataEngine();
        sb = new StringBuilder();

        engine.enqueue("", "", new DataEngine.DataCallBack() {
            @Override
            public void onSuccess(LogcatData date) {
                mText.append(date.getData());
            }

            @Override
            public void onFailed(String error) {
                mText.append(error);
            }
        });
    }

//    private void flushInfo(String info) {
//        sb.append(info).append("\n");
//        mText.setText(sb.toString());
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        engine.stop();
    }
}
