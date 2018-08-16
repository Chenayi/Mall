package com.kzj.mall.http;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.kzj.mall.entity.ResultResponse;
import com.kzj.mall.exception.ResultException;
import java.io.IOException;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Chenwy on 2017/10/20.
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private Gson gson;
    private Type type;

    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        ResultResponse resultResponse = gson.fromJson(response, ResultResponse.class);

        //成功
        if (resultResponse.code == 1) {
            return gson.fromJson(response, type);
        }

        LogUtils.e("message ===> " + resultResponse.message);
        throw new ResultException(resultResponse.code, resultResponse.message);
    }
}
