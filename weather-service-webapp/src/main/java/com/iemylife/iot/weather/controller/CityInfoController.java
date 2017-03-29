package com.iemylife.iot.weather.controller;

import com.iemylife.iot.weather.domain.po.CityInfo;
import com.iemylife.iot.weather.service.impl.CityInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by prf on 2017/3/29.
 */
@RestController
public class CityInfoController extends BaseController {
    @Autowired
    private CityInfoServiceImpl cityInfoService;

    private static final String EMPTY_BODY_VALUE = "{}";

    @PostMapping("ciyts")
    public ResponseEntity<?> create(@RequestBody CityInfo cityInfo) {

        int id = cityInfoService.insertSelective(cityInfo);
        if (!(id > 0)) {
            return new ResponseEntity<Object>(EMPTY_BODY_VALUE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Integer>(id, HttpStatus.OK);
    }


}
