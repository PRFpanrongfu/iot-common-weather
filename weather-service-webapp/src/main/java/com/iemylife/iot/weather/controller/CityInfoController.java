package com.iemylife.iot.weather.controller;

import com.iemylife.iot.weather.domain.po.CityInfo;
import com.iemylife.iot.weather.domain.vo.CityInfoReturnValue;
import com.iemylife.iot.weather.service.impl.CityInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by prf on 2017/3/29.
 */
@RestController
public class CityInfoController extends BaseController {
    private static final String EMPTY_RESPONESEBODY_VALUE = "{}";
    @Autowired
    private CityInfoServiceImpl cityInfoService;

    public static Map getModelResponseMap(CityInfo cityInfo) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("province", cityInfo.getProvince());
        modelMap.put("city", cityInfo.getCity());
        modelMap.put("districtEn", cityInfo.getDistrictEn());
        modelMap.put("lon", cityInfo.getLon());
        modelMap.put("districtZh", cityInfo.getDistrictZh());
        modelMap.put("lat", cityInfo.getLat());
        modelMap.put("cnty", cityInfo.getCnty());
        modelMap.put("code", cityInfo.getCode());
        return modelMap;
    }

    @PostMapping("citys")
    public ResponseEntity<?> create(@RequestBody CityInfo cityInfo) {

        try {
            int id = cityInfoService.insertSelective(cityInfo);
            if (id < 1) {
                return new ResponseEntity<Object>(EMPTY_RESPONESEBODY_VALUE, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<Integer>(id, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>("参数错误", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/citys/{code}")
    public ResponseEntity<?> delete(@PathVariable String code) {

        try {
            cityInfoService.deleteByCode(code);
            return new ResponseEntity<String>(EMPTY_RESPONESEBODY_VALUE, HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<String>(EMPTY_RESPONESEBODY_VALUE, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/citys/{code}")
    public ResponseEntity<?> update(@RequestBody CityInfo cityInfo, @PathVariable String code) {

        try {
            if (cityInfoService.updateByCodeSelective(code, cityInfo) < 1) {
                return new ResponseEntity<String>(EMPTY_RESPONESEBODY_VALUE, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<String>(EMPTY_RESPONESEBODY_VALUE, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(EMPTY_RESPONESEBODY_VALUE, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/citys/{code}")
    public ResponseEntity<?> searchByCode(@PathVariable String code) {
        try {
            CityInfo cityInfo = cityInfoService.selectByCode(code);
            if (cityInfo.getId() == 0) {
                return new ResponseEntity<String>(EMPTY_RESPONESEBODY_VALUE, HttpStatus.NOT_FOUND);
            }

            Map<String, Object> modelMapResponse = new HashMap<>();
            modelMapResponse = getModelResponseMap(cityInfo);
            return new ResponseEntity<Object>(modelMapResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(EMPTY_RESPONESEBODY_VALUE, HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            return new ResponseEntity<String>(EMPTY_RESPONESEBODY_VALUE, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "citys/{code}", params = {"code", "size", "page"})
    public ResponseEntity<?> searchByCodeAndPage(@PathVariable String code, @RequestParam Integer size, @RequestParam Integer page) {
        List<CityInfo> cityInfoList = new ArrayList<>();
        cityInfoList = cityInfoService.selectBymodelIdAndPage(code, size, page);
        if (cityInfoList == null) {
            return new ResponseEntity<String>(EMPTY_RESPONESEBODY_VALUE, HttpStatus.NOT_FOUND);
        }
        List<CityInfoReturnValue> cityInfoReturnValues = new ArrayList<>();
        for (CityInfo cityInfoTemp : cityInfoList) {
            cityInfoReturnValues.add(cityInfoTemp.getCityInfoReturnValue());
        }
        return new ResponseEntity<List<CityInfoReturnValue>>(cityInfoReturnValues, HttpStatus.OK);

    }
}
