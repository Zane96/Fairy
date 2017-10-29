package me.zane.fairy.data;


import me.zane.fairy.MySharedPre;
import me.zane.fairy.Utils;
import me.zane.fairy.ZLog;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zane on 2017/10/24.
 * Email: zanebot96@gmail.com
 */

public class LogcatModel {
    private static final String DEFAULT_IPADDRESS = "0.0.0.0";
    private static OkHttpClient client;
    private static Retrofit.Builder builder;
    private volatile static LogcatModel instance;

    private LogcatService service;
    private String ipAddress;

    static {
        client = new OkHttpClient.Builder()
                         .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                         .cache(new Cache(Utils.getDiskCacheDir("logcat"), 10 * 1024 * 1024))
                         .build();
        builder = new Retrofit.Builder()
                          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                          .addConverterFactory(GsonConverterFactory.create())
                          .client(client);
    }

    private LogcatModel(String ipAddress) {
        String baseUrl = String.format("http://%s:10255", ipAddress);
        Retrofit retrofit = builder.baseUrl(baseUrl).build();
        service = retrofit.create(LogcatService.class);
        this.ipAddress = ipAddress;
    }

    public static LogcatModel getInstance() {
        String currentIpAddress = MySharedPre.getInstance().getIpAddress(DEFAULT_IPADDRESS);
        if (currentIpAddress.equals(DEFAULT_IPADDRESS)) {
            //throw new NullIpAddressException();
            ZLog.e("The IpAddress can't be null when start a net connect");
        }

        if (instance == null) {
            synchronized (LogcatModel.class) {
                if (instance == null) {
                    instance =  new LogcatModel(currentIpAddress);
                }
            }
        } else if (!instance.ipAddress.equals(currentIpAddress)) {
            instance = new LogcatModel(currentIpAddress);
        }

        return instance;
    }

    public Observable<LogcatData> logcat(String options, String filter) {
        return service.logcat(options, filter)
                       .subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .unsubscribeOn(Schedulers.io());
    }
}
