package com.kzj.mall.http.interceptor;

import com.kzj.mall.http.DownloadResponseBody;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Chenwy on 2018/1/5 14:18
 */

public class DownloadInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder().body(
                new DownloadResponseBody(response.body()))
                .build();
    }
}
