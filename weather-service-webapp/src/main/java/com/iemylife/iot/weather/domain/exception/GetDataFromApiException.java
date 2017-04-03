package com.iemylife.iot.weather.domain.exception;

/**
 * Created by prf on 2017/4/4.
 */
public class GetDataFromApiException extends Exception {
    public GetDataFromApiException() {
    }

    public GetDataFromApiException(String message) {
        super(message);
    }
}
