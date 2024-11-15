package com.exmcs.transaction_service.controller;

import com.exmcs.transaction_service.model.request.TestRequest;
import com.exmcs.transaction_service.model.response.EmptyResponse;
import com.exmcs.transaction_service.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/")
    public EmptyResponse apiTest(){

        return testService.execute(new TestRequest());

    }

}
