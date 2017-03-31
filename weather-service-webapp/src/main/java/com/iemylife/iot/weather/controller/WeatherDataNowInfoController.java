package com.iemylife.iot.weather.controller;

import com.iemylife.iot.weather.domain.po.WeatherDataNowInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 实况天气
 * Created by prf on 2017/3/30.
 */
public class WeatherDataNowInfoController extends BaseController {
    @GetMapping("/weathers/now")
    public ResponseEntity<?> searchByCode(@RequestParam String code) {

        return new ResponseEntity<Object>("", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> searchByCity(@RequestParam String city) {
        return new ResponseEntity<Object>("", HttpStatus.OK);
    }

}
