package com.anyfive.erndtransfer.domain.test.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anyfive.erndtransfer.domain.test.dto.TestDto;
import com.anyfive.erndtransfer.domain.test.service.TestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/selectTest")
    public List<TestDto> selectTest() {
        List<TestDto> testList = testService.selectTest();
        return testList;
    }
}
