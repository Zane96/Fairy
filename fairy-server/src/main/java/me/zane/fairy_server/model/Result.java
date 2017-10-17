package me.zane.fairy_server.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * JSON bean
 * Created by Zane on 2017/10/17.
 * Email: zanebot96@gmail.com
 */

public class Result implements Parcelable{

    private String data;
    private String timeLine;

    public Result(String data, String timeLine) {
        this.data = data;
        this.timeLine = timeLine;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(String timeLine) {
        this.timeLine = timeLine;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.data);
        dest.writeString(this.timeLine);
    }


    protected Result(Parcel in) {
        this.data = in.readString();
        this.timeLine = in.readString();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
