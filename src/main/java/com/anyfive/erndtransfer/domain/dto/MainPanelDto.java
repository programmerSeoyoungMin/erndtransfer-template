package com.anyfive.erndtransfer.domain.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("MainPanelDto")
@RequiredArgsConstructor
public class MainPanelDto {
  /*이관과제수*/
  private String trnsfSbjtNocs;
  /*이관률*/
  private int trnsfRt;
  /*이관불가과제수*/
  private String nonTrnsfNocs;
  /*오류발생률*/
  private int errOccrRt;
  /*오류카운트*/
  private int errNocs;
}
