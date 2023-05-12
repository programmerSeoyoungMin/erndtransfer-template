package com.anyfive.erndtransfer.domain.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anyfive.erndtransfer.domain.dto.ItepdDto;
import com.anyfive.erndtransfer.domain.dto.ItepdTempDto;
import com.anyfive.erndtransfer.domain.service.ItepdService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/itepd")
@RequiredArgsConstructor
public class ItepdController {
  
  private final ItepdService itepdService;
  
  // 비목 목록 조회 
  @PostMapping("/retriveItepdList")
  public HashMap<String, Object> retriveItepdList(@RequestBody ItepdDto itepdDto) {
    List<ItepdDto> itepdList = itepdService.retriveItepdList(itepdDto);

    int totalCnt = itepdService.retriveItepdListTotalCnt(itepdDto);
    
    HashMap<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("totalCnt", totalCnt);
    resultMap.put("itepdList", itepdList);
    
    return resultMap;
  }
  
  // 비목 엑셀 업로드 temp 데이터 -> 비목 데이터 테이블 저장
  @PostMapping("/saveItepdList")
  public void saveItepdTempList(@RequestBody List<ItepdDto> data) {
    itepdService.saveItepdList(data);
  }
  
  // 비목 엑셀 업로드 temp 데이터 조회
  @GetMapping("/retriveItepdTempList")
  public List<ItepdTempDto> retriveItepdTempList(ItepdTempDto itepdTempDto) {
      List<ItepdTempDto> itepdList = itepdService.retriveItepdTempList(itepdTempDto);
      return itepdList;
  }
  
  @GetMapping("/retriveErndIoeCdList")
  public List<ItepdDto> retriveErndIoeCdList() {
      List<ItepdDto> erndIoeCdList = itepdService.retriveErndIoeCdList();
      return erndIoeCdList;
  }
  
  @GetMapping("/retriveIrisItepdCdList")
  public List<ItepdDto> retriveIrisItepdCdList() {
      List<ItepdDto> irisItepdCdList = itepdService.retriveIrisItepdCdList();
      return irisItepdCdList;
  }
  
}
