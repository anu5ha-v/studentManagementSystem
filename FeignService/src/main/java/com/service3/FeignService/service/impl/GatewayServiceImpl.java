package com.service3.FeignService.service.impl;

import com.service3.FeignService.feign.MongoServiceClient;
import com.service3.FeignService.feign.PostgresServiceClient;
import com.service3.FeignService.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GatewayServiceImpl implements GatewayService {

    @Autowired
    MongoServiceClient mongoServiceClient;

    @Autowired
    PostgresServiceClient postgresServiceClient;

    @Override
    public String getMessage(Boolean mongo) {
        if(mongo)
            return mongoServiceClient.getMessageFromMongo("anushafromMongo",5);
        else
            return postgresServiceClient.getMessageFromPostgres("arunfromPostgres", 8);
    }
}
