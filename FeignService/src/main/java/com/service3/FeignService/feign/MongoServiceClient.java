package com.service3.FeignService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mongo-service", url = "http://localhost:8004")
public interface MongoServiceClient {
    
    @GetMapping("/Hello/greetMongo")
    String getMessageFromMongo(@RequestParam("name") String name, @RequestParam("age") Integer age);
}
