package com.anyfive.erndtransfer.domain.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anyfive.erndtransfer.domain.dto.TrnsfMasterDto;
import com.anyfive.erndtransfer.domain.dto.TrnsfPrcdDto;
import com.anyfive.erndtransfer.domain.service.TrnsfService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trnsf")
@RequiredArgsConstructor
public class TrnsfController {
  
  private final TrnsfService trnsfService;
  
  //진행 중 이관 존재 여부
  @PostMapping("/retrivePrgrsTrnsfExistYn")
  public String retrivePrgrsTrnsfExistYn(@RequestBody TrnsfMasterDto trnsfMasterDto) {
    return trnsfService.retrivePrgrsTrnsfExistYn(trnsfMasterDto);
  }
  
  //진행 중 이관 아이디 조회
  @PostMapping("/retriveCurrentTrnsfId")
  public String retriveCurrentTrnsfId(@RequestBody TrnsfMasterDto trnsfMasterDto) {
    return trnsfService.retriveCurrentTrnsfId(trnsfMasterDto);
  }
  
  //종료된 마지막 이관 아이디 조회
  @PostMapping("/retriveLastTrnsfId")
  public String retriveLastTrnsfId(@RequestBody TrnsfMasterDto trnsfMasterDto) {
    return trnsfService.retriveLastTrnsfId(trnsfMasterDto);
  }
  
  //현재 이관 정보 조회
  @PostMapping("/retriveCurrentTrnsfInfo")
  public TrnsfMasterDto retriveCurrentTrnsfInfo(@RequestBody TrnsfMasterDto trnsfMasterDto) {
    return trnsfService.retriveCurrentTrnsfInfo(trnsfMasterDto);
  }
    
  //진행 중 이관 프로시저 정보 조회
  @PostMapping("/retrivePrgrsPrcdInfo")
  public TrnsfPrcdDto retrivePrgrsPrcdInfo(@RequestBody TrnsfMasterDto trnsfMasterDto) {
    return trnsfService.retrivePrgrsPrcdInfo(trnsfMasterDto);
  }
  
  //진행 중 프로시저 목록 조회
  @PostMapping("/retrivePrgrsPrcdList")
  public List<TrnsfPrcdDto> retrivePrgrsPrcdList(@RequestBody TrnsfMasterDto trnsfMasterDto) {
    return trnsfService.retrivePrgrsPrcdList(trnsfMasterDto);
  }
  
  //이관 마스터 저장
  @PostMapping("/registTrnsfMaster")
  public String registTrnsfMaster(@RequestBody TrnsfMasterDto trnsfMasterDto) {
    //전체이관 마스터 저장
    if(trnsfMasterDto.getTrnsfId() == null || trnsfMasterDto.getTrnsfId().equals("")) {
      trnsfMasterDto.setTrnsfId(trnsfService.registTrnsfMaster(trnsfMasterDto));
      //전체이관 마스터는 sbjtNocs 정보 직접 조회하여야 함.
      /*TODO*/
      //int sbjtNocs = trnsfService.retriveSbjtNocs(trnsfMasterDto);
      //trnsfMasterDto.setSbjtNocs(sbjtNocs);
    } 
    //이관 마스터 진행(DMND_DT 입력)
    trnsfService.updateTrnsfMaster(trnsfMasterDto);
    //이관 프로시저 수행결과 정보 저장(프로시저 정보 기반으로 cover 데이터 생성)
    trnsfService.registTrnsfPrcdFlfmtRslt(trnsfMasterDto);
    
    //저장프로시저 call
    trnsfService.callIrisPrcd(trnsfMasterDto);
    // 시작된 이관 아이디 리턴
    return trnsfMasterDto.getTrnsfId();
  }
  
  // 이관 업로드 파일 삭제 
  @PostMapping("/deleteTrnsfUploadFile")
  public void deleteTrnsfMaster(@RequestBody TrnsfMasterDto trnsfMasterDto) {
    trnsfService.deleteTrnsfUploadFile(trnsfMasterDto);
  }
}