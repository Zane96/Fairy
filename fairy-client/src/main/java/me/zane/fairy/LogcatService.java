package me.zane.fairy;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Zane on 2017/10/24.
 * Email: zanebot96@gmail.com
 */

public interface LogcatService {

    @POST("/")
    @FormUrlEncoded
    Observable<LogcatData> logcat(@Field("options") String options,
                                @Field("filter") String filter);
}
