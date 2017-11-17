package me.zane.fairy.resource;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Zane on 2017/11/17.
 * Email: zanebot96@gmail.com
 */

public class ApiResponse<T> {
    private final boolean isSuccess;
    private final T body;

    public ApiResponse(Throwable e) {
        isSuccess = false;
        body = (T) e.getMessage();
    }

    public ApiResponse(T response) {
        isSuccess = true;
        body = response;
    }

    public boolean isSuccussful() {
        return isSuccess;
    }

    public T getBody() {
        return body;
    }
}
