package me.zane.fairy.data;

import android.widget.Toast;

import java.util.ServiceConfigurationError;

import me.zane.fairy.App;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

/**
 * Created by Zane on 2017/10/26.
 * Email: zanebot96@gmail.com
 */

public class ErrorTransForm<T> implements Observable.Transformer<T, T>{

    private static final String TAG = "ErrorTransform";

    /**
     * 通过传递进来的错误判断来进行统一的错误处理
     * @param tObservable
     * @return
     */
    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable.onErrorResumeNext(throwable -> {
            //判断异常是什么类型
            String errorMessage = "";
            //通过状态码判断错误
            if (throwable instanceof HttpException) {
                HttpException response = (HttpException) throwable;
                switch (response.code()){
                    case 404:
                        errorMessage = "数据为空"+response.message();
                        break;
                    case 402:
                        errorMessage = "数据库连接错误"+response.message();
                        break;
                    case 403:
                        errorMessage = "无记录"+response.message();
                        break;
                    case 400:
                        errorMessage = "参数为空"+response.message();
                        break;
                    default:
                        errorMessage = "未知错误"+response.message();
                        break;
                }
            } else if (throwable instanceof ServiceConfigurationError){
                errorMessage = "服务器错误";
            } else {
                errorMessage = "网络错误";
            }

            Toast.makeText(App.getInstance(), errorMessage, Toast.LENGTH_SHORT).show();

            return Observable.empty();
        });
    }
}
