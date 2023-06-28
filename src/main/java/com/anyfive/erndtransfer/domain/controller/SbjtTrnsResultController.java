package com.anyfive.erndtransfer.domain.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anyfive.erndtransfer.domain.dto.SbjtTrnsResultDetailDto;
import com.anyfive.erndtransfer.domain.dto.SbjtTrnsResultItepdDto;
import com.anyfive.erndtransfer.domain.dto.SbjtTrnsResultKwdDto;
import com.anyfive.erndtransfer.domain.dto.SbjtTrnsResultListDto;
import com.anyfive.erndtransfer.domain.service.SbjtTrnsResultService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/result")
@RequiredArgsConstructor
public class SbjtTrnsResultController {
  
  private final SbjtTrnsResultService sbjtTrnsResultService;
  
  // 과제 목록 조회 
  @PostMapping("/retriveSbjtTrnsResultList")
  public HashMap<String, Object> retriveSbjtTrnsResultList(@RequestBody SbjtTrnsResultListDto sbjtTrnsResultListDto) {
    List<SbjtTrnsResultListDto> sbjtTrnsResultList = sbjtTrnsResultService.retriveSbjtTrnsResultList(sbjtTrnsResultListDto);
    int totalCnt = sbjtTrnsResultService.retriveSbjtTrnsResultListCnt(sbjtTrnsResultListDto);
    HashMap<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("totalCnt", totalCnt);
    resultMap.put("sbjtTrnsResultList", sbjtTrnsResultList);
    
    return resultMap;
  }
  
  // 과제정보 조회
  @PostMapping("/retriveSbjtTrnsResultDetail")
  public SbjtTrnsResultDetailDto retriveSbjtTrnsResultDetail(@RequestBody SbjtTrnsResultDetailDto sbjtTrnsResultDetailDto) {
    SbjtTrnsResultDetailDto sbjtTrnsResultDetail = sbjtTrnsResultService.retriveSbjtTrnsResultDetail(sbjtTrnsResultDetailDto);
    return sbjtTrnsResultDetail;
  }
  
  // 키워드 조회 
  @PostMapping("/retriveSbjtTrnsResultKwd")
  public SbjtTrnsResultKwdDto retriveSbjtTrnsResultKwd(@RequestBody SbjtTrnsResultKwdDto sbjtTrnsResultKwdDto) {
    SbjtTrnsResultKwdDto sbjtTrnsResultKwd = sbjtTrnsResultService.retriveSbjtTrnsResultKwd(sbjtTrnsResultKwdDto);
    return sbjtTrnsResultKwd;
  }
  
  // 연구비 목록 조회 
  @PostMapping("/retriveSbjtTrnsResultItepdList")
  public List<SbjtTrnsResultItepdDto> retriveSbjtTrnsResultItepdList(@RequestBody SbjtTrnsResultItepdDto sbjtTrnsResultItepdDto) {
    List<SbjtTrnsResultItepdDto> sbjtTrnsResultItepdList = sbjtTrnsResultService.retriveSbjtTrnsResultItepdList(sbjtTrnsResultItepdDto);
    return sbjtTrnsResultItepdList;
  }
  
}
