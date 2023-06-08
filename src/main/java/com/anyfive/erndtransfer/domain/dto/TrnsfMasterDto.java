package com.anyfive.erndtransfer.domain.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("TrnsfMasterDto")
@RequiredArgsConstructor
public class TrnsfMasterDto {
  /*이관ID*/
  private String trnsfId;
  /*이관구분*/
  private String trnsfSe;
  /*이관일자*/
  private String trnsfYmd;
  /*요청일시*/
  private String dmndDt;
  /*종료일시*/
  private String endDt;
  /*과제건수*/
  private int sbjtNocs;
  /*소요시간*/
  private String reqTm;
  /*등록자ID*/
  private String rgtrId;
  /*등록상세일시*/
  private String regDtlDt;
  /*업로드파일명*/
  private String uldFileNm;
  /*업로드 ROW COUNT*/
  private int uldRowCnt;
  
  /* 진행중인 이관 존재여부 */
  private String prgrsTrnsfExistYn;
  
}
