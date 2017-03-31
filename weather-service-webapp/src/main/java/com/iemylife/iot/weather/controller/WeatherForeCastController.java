package com.iemylife.iot.weather.controller;

import com.iemylife.iot.weather.service.impl.WeatherDataDailyInfoServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by prf on 2017/3/30.
 */
@RestController
public class WeatherForeCastController extends BaseController {
    @Autowired
    private WeatherDataDailyInfoServicesImpl weatherDataDailyInfoServices;

    @GetMapping(value = "/weathers/forecast", params = {"code"})
    public ResponseEntity<?> searchByCode(@RequestParam String code) {
        return new ResponseEntity<Object>("", HttpStatus.OK);
    }

    @GetMapping(value = "/weathers/forecast", params = {"city"})
    public ResponseEntity<?> searchByCity(@RequestParam String city) {
        return new ResponseEntity<Object>("", HttpStatus.OK);
    }

}
