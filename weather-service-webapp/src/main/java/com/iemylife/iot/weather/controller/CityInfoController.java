package com.iemylife.iot.weather.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemylife.iot.weather.domain.exception.TruncateTableException;
import com.iemylife.iot.weather.domain.po.CityInfo;
import com.iemylife.iot.weather.domain.vo.CityInfoForJson;
import com.iemylife.iot.weather.domain.vo.CityInfoReturnValue;
import com.iemylife.iot.weather.service.impl.CityInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

/**
 * Created by prf on 2017/3/29.
 */
@RestController
public class CityInfoController extends BaseController {
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

    @PostMapping("/citys")
    public ResponseEntity<?> create(@RequestBody CityInfo cityInfo) {

        try {
            int id = cityInfoService.insertSelective(cityInfo);
            if (id < 1) {
                return new ResponseEntity<Object>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.INTERNAL_SERVER_ERROR);
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
            return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/citys/{code}")
    public ResponseEntity<?> update(@RequestBody CityInfo cityInfo, @PathVariable String code) {

        try {
            if (cityInfoService.updateByCodeSelective(code, cityInfo) < 1) {
                return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/citys/{code}")
    public ResponseEntity<?> searchByCode(@PathVariable String code) {
        try {
            CityInfo cityInfo = cityInfoService.selectByCode(code);
            if (cityInfo.getId() == 0) {
                return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.NOT_FOUND);
            }

            Map<String, Object> modelMapResponse = new HashMap<>();
            modelMapResponse = getModelResponseMap(cityInfo);
            return new ResponseEntity<Object>(modelMapResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/citys/{code}", params = {"code", "size", "page"})
    public ResponseEntity<?> searchByCodeAndPage(@PathVariable String code, @RequestParam Integer size, @RequestParam Integer page) {
        List<CityInfo> cityInfoList = new ArrayList<>();
        cityInfoList = cityInfoService.selectBymodelIdAndPage(code, size, page);
        if (cityInfoList == null) {
            return new ResponseEntity<String>(EMPTY_RESPONSEBODY_VALUE, HttpStatus.NOT_FOUND);
        }
        //添加操作使用LinkedList
        List<CityInfoReturnValue> cityInfoReturnValues = new LinkedList<>();
        //for (CityInfo cityInfoTemp : cityInfoList) {
        //    cityInfoReturnValues.add(cityInfoTemp.getCityInfoReturnValue());
        //}
        for (int i = 1, length = cityInfoList.size(); i < length; i++) {
            CityInfo cityInfoTemp = cityInfoList.get(i);
            cityInfoReturnValues.add(cityInfoTemp.getCityInfoReturnValue());
        }
        return new ResponseEntity<List<CityInfoReturnValue>>(cityInfoReturnValues, HttpStatus.OK);

    }

    /**
     * 添加所有城市信息到数据库
     */
    @GetMapping("/citys/refresh")
    public void reshCityInfos() throws IOException {

        try {
            RestTemplate restTemplate = new RestTemplate();
            String returnValue = restTemplate.getForObject("https://cdn.heweather.com/china-city-list.json", String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);  //只对实体起作用，对map不起作用
            List<CityInfoForJson> listCity = objectMapper.readValue(returnValue, List.class);

            List<CityInfo> cityInfoList = new ArrayList<>();
            //for(CityInfoForJson info : listCity) {
            //    cityInfoList.add(info.getCityInfo());
            //}
            for (int i = 1, length = listCity.size(); i < length; i++) {
                CityInfoForJson cityInfoForJson = listCity.get(i);
                cityInfoList.add(cityInfoForJson.getCityInfo());
            }
            cityInfoService.insertBatch(cityInfoList);
        } catch (TruncateTableException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

}
