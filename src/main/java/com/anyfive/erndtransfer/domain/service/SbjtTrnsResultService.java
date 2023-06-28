package com.anyfive.erndtransfer.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anyfive.erndtransfer.domain.dto.SbjtTrnsResultDetailDto;
import com.anyfive.erndtransfer.domain.dto.SbjtTrnsResultExcelDto;
import com.anyfive.erndtransfer.domain.dto.SbjtTrnsResultItepdDto;
import com.anyfive.erndtransfer.domain.dto.SbjtTrnsResultKwdDto;
import com.anyfive.erndtransfer.domain.dto.SbjtTrnsResultListDto;
import com.anyfive.erndtransfer.domain.mapper.SbjtTrnsResultMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SbjtTrnsResultService {
  
  private final SbjtTrnsResultMapper sbjtTrnsResultMapper;

  //과제목록 조회
  public List<SbjtTrnsResultListDto> retriveSbjtTrnsResultList(SbjtTrnsResultListDto sbjtTrnsResultListDto){
    return sbjtTrnsResultMapper.retriveSbjtTrnsResultList(sbjtTrnsResultListDto);
  }
  //과제목록 건수 조회
  public int retriveSbjtTrnsResultListCnt(SbjtTrnsResultListDto sbjtTrnsResultListDto) {
    return sbjtTrnsResultMapper.retriveSbjtTrnsResultListCnt(sbjtTrnsResultListDto);
  }
  //과제 키워드 조회
  public SbjtTrnsResultKwdDto retriveSbjtTrnsResultKwd(SbjtTrnsResultKwdDto sbjtTrnsResultKwdDto) {
    return sbjtTrnsResultMapper.retriveSbjtTrnsResultKwd(sbjtTrnsResultKwdDto);
  }
  //과제 비목 조회
  public List<SbjtTrnsResultItepdDto> retriveSbjtTrnsResultItepdList(SbjtTrnsResultItepdDto sbjtTrnsResultItepdDto) {
    return sbjtTrnsResultMapper.retriveSbjtTrnsResultItepdList(sbjtTrnsResultItepdDto);
  }
  public List<SbjtTrnsResultExcelDto> retriveSbjtTrnsResultExcelList(SbjtTrnsResultExcelDto sbjtTrnsResultExcelDto) {
    return sbjtTrnsResultMapper.retriveSbjtTrnsResultExcelList(sbjtTrnsResultExcelDto);
  }
  public SbjtTrnsResultDetailDto retriveSbjtTrnsResultDetail(SbjtTrnsResultDetailDto sbjtTrnsResultDetailDto) {
    return sbjtTrnsResultMapper.retriveSbjtTrnsResultDetail(sbjtTrnsResultDetailDto);
  }
  
  
}
