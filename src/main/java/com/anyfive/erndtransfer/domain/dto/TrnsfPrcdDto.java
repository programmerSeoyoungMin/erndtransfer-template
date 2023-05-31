package com.anyfive.erndtransfer.domain.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("TrnsfPrcdDto")
@RequiredArgsConstructor
public class TrnsfPrcdDto {
  /* 
   * 화면 조회 정보
   */
  /*프로시저명*/
  private String prcdNm;
  /*프로시저진행상태*/
  private String prcdPrgrsSttus;
  /*오류 발생 cnt*/
  private int errOcrnNocs;
  /*소요시간*/
  private String reqTm;
  /*등록 데이터 수*/
  private int insertedRows;
  /*이관 진행률*/
  private int trnsfPrgrsRt;
  /*전체 이관 진행시간*/
  private String trnsfPrgrsTm;
  /*소요시간*/
  private String trnsfDataNocs;
  
  /* 
   * 프로시저정보 테이블
   */
  /*업무구분*/
  private String taskSe;
  /*이관테이블*/
  private String trnsfTbl;
  /*기본테이블*/
  private String bscTbl;
  /* 
   * 이관프로시저수행결과 테이블
   */
  /*이관ID*/
  private String trnsfId;
  /*프로시저ID*/
  private String prcdId;
  /*순번*/
  private String no;
  /*전체건수*/
  private String allNocs;
  /*성공건수*/
  private String scsNocs;
  /*오류건수*/
  private String errNocs;
  /*미이관건수*/
  private String etcNocs;
  /*시작시간*/
  private String brngDt;
  /*종료시간*/
  private String endDt;
  /*등록자ID*/
  private String rgtrId;
  /*등록상세일시*/
  private String regDtlDt;
  
}
