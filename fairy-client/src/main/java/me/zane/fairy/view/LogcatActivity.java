package me.zane.fairy.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import me.zane.fairy.MySharedPre;
import me.zane.fairy.R;
import me.zane.fairy.data.DataEngine;
import me.zane.fairy.data.LogcatData;

/**
 * Created by Zane on 2017/10/29.
 * Email: zanebot96@gmail.com
 */

public class LogcatActivity extends AppCompatActivity{
    public static final String INDEX_KEY = "index_key";
    private static final int DEFAULT_INDEX = -1;
    private EditText mOptionsEdit;
    private EditText mFilterEdit;
    private Button mStartBtn;
    private Button mStopBtn;
    private TextView mDataText;

    private int index;
    private String options = "";
    private String filter = "";

    private DataEngine engine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logcat);
        index = getIntent().getIntExtra(INDEX_KEY, DEFAULT_INDEX);
        if (index == DEFAULT_INDEX) {
            throw new IllegalArgumentException("index of item can't be null");
        }

        options = MySharedPre.getInstance().getOptions(index, "");
        filter = MySharedPre.getInstance().getFilter(index, "");
        engine = new DataEngine();

        mOptionsEdit = findViewById(R.id.edit_options_logcat);
        mFilterEdit = findViewById(R.id.edit_filter_logcat);
        mStartBtn = findViewById(R.id.btn_start_logcat);
        mStopBtn = findViewById(R.id.btn_stop_logcat);
        mDataText = findViewById(R.id.text_data_logcat);

        mStartBtn.setOnClickListener(v -> engine.enqueue(options, filter, new DataEngine.DataCallBack() {
            @Override
            public void onSuccess(LogcatData date) {
                mDataText.append(date.getData());
            }

            @Override
            public void onFailed(String error) {
                mDataText.append(error);
            }
        }));

        mStopBtn.setOnClickListener(v -> engine.stop());

        if (!options.equals("")) {
            mOptionsEdit.setHint(options);
        }
        mOptionsEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                options = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (!filter.equals("")) {
            mFilterEdit.setHint(filter);
        }
        mFilterEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MySharedPre.getInstance().putOptions(index, options);
        MySharedPre.getInstance().putFilter(index, filter);
    }
}
