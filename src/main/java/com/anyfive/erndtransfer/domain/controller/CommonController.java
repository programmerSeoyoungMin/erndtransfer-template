package com.anyfive.erndtransfer.domain.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anyfive.erndtransfer.domain.dto.HeaderDto;
import com.anyfive.erndtransfer.domain.service.CommonService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;

    @PostMapping("/retriveHeaderList")
    public List<HeaderDto> selectHeaderList(@RequestBody(required = false) HeaderDto dto) {
      if(dto.getExclude() != null && dto.getExclude().length() != 0){
        dto.setExcludeColumns(dto.getExclude().split(","));  
      }
      List<HeaderDto> headerList = commonService.retriveHeaderList(dto);
      return headerList;
    }
    
}
