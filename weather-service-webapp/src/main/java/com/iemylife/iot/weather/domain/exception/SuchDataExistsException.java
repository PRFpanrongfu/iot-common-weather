package com.iemylife.iot.weather.domain.exception;

/**
 * Created by prf on 2017/4/4.
 */
public class SuchDataExistsException extends Exception {
    public SuchDataExistsException() {
        super();
    }

    public SuchDataExistsException(String message) {
        super(message);
    }
}
