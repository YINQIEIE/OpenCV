package com.jdhr.http;

import android.text.TextUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by Administrator on 2017/10/13.
 */

public class RetrofitManager {

    private static Retrofit retrofit = null;

    private static String BASE_URL = "";

    private RetrofitManager() {
    }

    public static void init(String apiUrl) {
        if (TextUtils.isEmpty(apiUrl))
            throw new IllegalArgumentException("ApiUrl cannot be null");
        BASE_URL = apiUrl;
    }

    /**
     * @return Retrofit 实例
     */
    private static Retrofit getRetrofit() {
        if (TextUtils.isEmpty(BASE_URL))
            throw new IllegalArgumentException("You should call method<init> first");
        if (null == retrofit)
            synchronized (RetrofitManager.class) {
                if (null == retrofit)
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(OkHttpClientManager.getClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build();
            }
        return retrofit;
    }

    /**
     * 获取 Service 请求
     *
     * @param clazz 请求 service 对应的 class
     * @return 请求 service 对象
     */
    public static <T> T getHttpService(Class<T> clazz) {
        return getRetrofit().create(clazz);
    }

}
