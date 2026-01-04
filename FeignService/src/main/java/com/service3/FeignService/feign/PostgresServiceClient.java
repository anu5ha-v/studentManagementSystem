package com.service3.FeignService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "postgres-service", url = "http://localhost:8084")
public interface PostgresServiceClient {

    @GetMapping("/Hello/greetPostgres")
    String getMessageFromPostgres(@RequestParam("name") String name, @RequestParam("age") Integer age);
}
