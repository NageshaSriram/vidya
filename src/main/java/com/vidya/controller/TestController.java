package com.vidya.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class); 
    @GetMapping("/test")
    public ResponseEntity<?> test() {

       logger.info("Testing API success.");
       return ResponseEntity.ok("Testing API success");
    }

}
