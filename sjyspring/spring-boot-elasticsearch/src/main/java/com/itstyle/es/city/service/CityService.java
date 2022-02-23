package com.itstyle.es.city.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itstyle.es.city.entity.City;

import java.io.IOException;

@Service
public interface CityService {
    /**
     * 新增城市
     *
     * @param log
     * @return
     */
    void saveCity(City city);

    void getNearbyCities(double lat, double lon) throws IOException;
}
