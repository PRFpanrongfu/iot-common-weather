package com.iemylife.iot.weather.domain.exception;

/**
 * 用于描述truncate table时的异常
 * Created by prf on 2017/4/1.
 */
public class TruncateTableException extends Exception {
    public TruncateTableException() {
        super();
    }

    public TruncateTableException(String message) {
        super(message);
    }
}
