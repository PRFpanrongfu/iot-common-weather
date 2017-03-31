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
public class HistoricWeatherController extends BaseController {

    @Autowired
    private WeatherDataDailyInfoServicesImpl weatherDataDailyInfoServices;

    @GetMapping(value = "/weathers/historical", params = {"code", "date"})
    public ResponseEntity<?> searchByCode(@RequestParam String code, @RequestParam String date) {
        return new ResponseEntity<Object>("", HttpStatus.OK);
    }

    @GetMapping(value = "/weathers/historical", params = {"city", "date"})
    public ResponseEntity<?> searchByCity(@RequestParam String city, @RequestParam String date) {
        return new ResponseEntity<Object>("", HttpStatus.OK);
    }


}
