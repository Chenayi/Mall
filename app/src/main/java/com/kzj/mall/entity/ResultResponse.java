package com.kzj.mall.entity;

/**
 * Created by Chenwy on 2018/2/1.
 */

public class ResultResponse {
    public int code;
    public String message;
    public Data data;

    public static class Data {
        public String msg;
    }
}
