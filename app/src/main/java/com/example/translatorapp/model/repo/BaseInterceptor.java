package com.example.translatorapp.model.repo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class BaseInterceptor implements Interceptor {

    private int responseCode = 0;

    private static BaseInterceptor instance;

    private BaseInterceptor() {
    }

    private static void init() {
        instance = new BaseInterceptor();
    }

    public static BaseInterceptor getInstance() {
        if (instance == null) {
            init();
        }
        return instance;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        responseCode = response.code();
        return response;
    }

    private ServerResponseStatusCode getResponseCode() {
        ServerResponseStatusCode statusCode = ServerResponseStatusCode.UNDEFINED_ERROR;
        switch (responseCode / 100) {
            case 1:
                statusCode = ServerResponseStatusCode.INFO;
                break;
            case 2:
                statusCode = ServerResponseStatusCode.SUCCESS;
                break;
            case 3:
                statusCode = ServerResponseStatusCode.REDIRECTION;
                break;
            case 4:
                statusCode = ServerResponseStatusCode.CLIENT_ERROR;
                break;
            case 5:
                statusCode = ServerResponseStatusCode.SERVER_ERROR;
                break;
        }
        return statusCode;
    }

    enum ServerResponseStatusCode {
        INFO,
        SUCCESS,
        REDIRECTION,
        CLIENT_ERROR,
        SERVER_ERROR,
        UNDEFINED_ERROR
    }
}