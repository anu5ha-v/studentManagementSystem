package com.service3.FeignService.controller;

import com.service3.FeignService.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class GatewayController {

    @Autowired
    GatewayService feignService;

    @GetMapping("/access/{mongo}")
    public ResponseEntity<String> getMessage(@RequestParam Boolean mongo){
        return new ResponseEntity<>(feignService.getMessage(mongo), HttpStatus.OK);
    }

}
