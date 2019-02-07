package com.example.demo.controller;

import com.example.demo.service.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/demo")
public class Controller {

    @Autowired(required = true)
    JsonService jsonService;

    @GetMapping(value = "/testing")
    public String testing() {
    return jsonService.pushStaticDumpDataToExtranet();
       // return  jsonService.jsonToJava();
    }
}
