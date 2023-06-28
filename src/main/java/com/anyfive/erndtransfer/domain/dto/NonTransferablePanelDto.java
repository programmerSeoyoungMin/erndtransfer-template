package com.anyfive.erndtransfer.domain.dto;


import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("NonTransferablePanelDto")
@RequiredArgsConstructor
public class NonTransferablePanelDto {
  /*사업*/
  private int bsnsNocs;
  /*공고*/
  private int ancmNocs;
  /*과제*/
  private int sbjtNocs;
  /*비목*/
  private int itepdNocs;
}
