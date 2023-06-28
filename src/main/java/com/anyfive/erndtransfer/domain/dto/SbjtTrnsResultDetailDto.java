package com.anyfive.erndtransfer.domain.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@Alias("SbjtTrnsResultDetailDto")
@RequiredArgsConstructor
public class SbjtTrnsResultDetailDto {
  // 공고 제목 
  private String ancmTl;
  // 공고 번호
  private String ancmNo;
  // 사업년도(W2)
  private String bsnsYy;
  // e-R&D 과제번호
  private String erndSbjtNo;
  // 상위 전문기관 사업 코드
  private String hirkSorgnBsnsCd;
  // 상위 전문기관 사업 명
  private String hirkSorgnBsnsCdNm;
  // IRIS과제번호
  private String irisSbjtId;
  // 전문기관 사업 코드
  private String sorgnBsnsCd;
  // 전문기관 사업 명
  private String sorgnBsnsNm;
  // 공모 유형 구분[AB1] 
  private String pbofrTpSe;
  // 사업 공고 제목
  private String bsnsAncmTl;
  // 컨소시엄 구성 구분[AX7]
  private String cortiumCpstSe;
  // 과제 접수 형태 구분[AT6]
  private String sbjtRcveFormSe;
  // 연구 기간 설정 구분[AK3]
  private String rschPridStngSe;
  // 연구 기간 구성 구분[AB5]
  private String rschPridCpstSe;
  // 총 정부지원금 금액
  private String ttlGvstmAm;
  // 총괄 과제 ID
  private String ovrlSbjtId;
  // 한글 총괄 연구개발 명
  private String hanOvrlRndNm;
  // 영문 총괄 연구개발 명
  private String engOvrlRndNm;
  // 과제 ID
  private String sbjtId;
  // 한글 과제 명
  private String hanSbjtNm;
  // 영문과제명
  private String engSbjtNm;
  // 진행 과제 수행 연차
  private String prgSbjtExeAnnl;
  // 진행 사업 년도
  private String prgBsnsYy;
  // 진행 전문기관 사업 코드
  private String prgSorgnBsnsCd;
  // 보안 상세 구분[AG8]
  private String scurDtlSe;
  // 총개발기간
  private String ttlRschPrid;
  // 연구 기관 ID 
  private String rschOrgnId;
  // 연구 기관 명
  private String rschOrgnNm;
  // 국가구분
  private String natSe;
  // 연구자 명
  private String rscrNm;
  // 전문기관 명
  private String sorgnNm;
  // 연구개발 과제 번호
  private String rndSbjtNo;
  // 총괄 연구개발과제번호
  private String ovrlRndSbjtNo;
}
