package com.anyfive.erndtransfer.domain.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@Alias("SbjtTrnsResultKwdDto")
@RequiredArgsConstructor
public class SbjtTrnsResultKwdDto {
  private String rndSbjtNo;
  private String sbjtId;
  private String kr1KwdNm;
  private String kr2KwdNm;
  private String kr3KwdNm;
  private String kr4KwdNm;
  private String kr5KwdNm;
  private String en1KwdNm;
  private String en2KwdNm;
  private String en3KwdNm;
  private String en4KwdNm;
  private String en5KwdNm;
}
