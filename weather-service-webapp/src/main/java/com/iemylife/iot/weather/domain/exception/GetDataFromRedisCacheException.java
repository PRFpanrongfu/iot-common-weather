package com.iemylife.iot.weather.domain.exception;

/**
 * Created by prf on 2017/4/5.
 */
public class GetDataFromRedisCacheException extends Exception {
    public GetDataFromRedisCacheException() {
    }

    public GetDataFromRedisCacheException(String message) {
        super(message);
    }
}
