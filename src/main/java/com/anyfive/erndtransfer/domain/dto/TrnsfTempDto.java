package com.anyfive.erndtransfer.domain.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("TrnsfTempDto")
@RequiredArgsConstructor
public class TrnsfTempDto {
  /*이관ID*/
  private String trnsfId;
  /*과제ID*/
  private String sbjtNo;
  /*순번*/
  private String no;
  /*등록자ID*/
  private String rgtrId;
  /* 등록상세일시*/
  private String regDtlDt;
}
