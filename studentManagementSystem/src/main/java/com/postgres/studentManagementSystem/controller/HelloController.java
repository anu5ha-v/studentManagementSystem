package com.postgres.studentManagementSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Hello")
public class HelloController {

    @GetMapping("/greetPostgres")
    public ResponseEntity<String> hello(@RequestParam String name, @RequestParam Integer age) {
        return new ResponseEntity<>("Hello This is being sent from Postgres service --> Hello " + name+" - age : " +age, HttpStatus.OK);
    }
}
