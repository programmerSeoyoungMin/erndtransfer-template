package com.anyfive.erndtransfer.domain.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anyfive.erndtransfer.domain.dto.BsnsDto;
import com.anyfive.erndtransfer.domain.dto.BsnsTempDto;
import com.anyfive.erndtransfer.domain.service.BsnsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bsns")
@RequiredArgsConstructor
public class BsnsController {
  
  private final BsnsService bsnsService;
  
  @GetMapping("/retriveBsnsTempList")
  public List<BsnsTempDto> retriveBsnsTempList(@RequestParam(required = false) BsnsTempDto bsnsTempDto) {
      List<BsnsTempDto> bsnsList = bsnsService.retriveBsnsTempList(bsnsTempDto);
      return bsnsList;
  }
  
  @PostMapping("/retriveBsnsList")
  public HashMap<String, Object> retriveBsnsList(@RequestBody BsnsDto bsnsDto) {
    
    
    List<BsnsDto> bsnsList = bsnsService.retriveBsnsList(bsnsDto);

    bsnsDto.setLimit(null);
    int totalCnt = bsnsService.retriveBsnsListTotalCnt(bsnsDto);
    
    HashMap<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("totalCnt", totalCnt);
    resultMap.put("bsnsList", bsnsList);
    
    return resultMap;
  }
  
  @PostMapping("/saveBsnsList")
  public void saveBsnsTempList(@RequestBody List<BsnsDto> data) {
    bsnsService.saveBsnsList(data);
  }
  
  
}
