package com.anyfive.erndtransfer.domain.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@Alias("SbjtTrnsResultListDto")
@RequiredArgsConstructor
public class SbjtTrnsResultListDto extends PagingDto{
  /*e-R&D 과제번호*/
  private String erndSbjtNo;
  /*IRIS 과제번호*/
  private String irisSbjtId;
  /*과제명*/
  private String hanSbjtNm;
  /*주관기관명*/
  private String spvsAgcNm;
  /*연구책임자명*/
  private String rschRpbPeRschrRegNm;
  /*번호*/
  private int no;
  /*세부사업명*/
  private String ptcBsnsName;
  /*내역사업명*/
  private String brdnBsnsName;
  /*과제구분*/
  private String sbjtProplStrcSeNm;
  /*사업년도*/
  private String seleYy;
  /*연구개발과제번호*/
  private String rndSbjtNo;
  /*과제번호*/
  private String stepAnuSbjtNo;
  /*주무부처*/
  private String bizDepartNm;
  /*총연구기간*/
  private String totRschDate;
  
  
}
