package com.anyfive.erndtransfer.domain.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@Alias("SbjtTrnsResultItepdDto")
@RequiredArgsConstructor
public class SbjtTrnsResultItepdDto {
  /*과제단계*/
  private String sbjtStep;
  /*과제연차*/
  private String sbjtAnnl;
  /*역할*/
  private String rschOrgnRoleSeNm;
  /*기관명*/
  private String rschOrgnNm;      
  /*항목*/
  private String itepdCdNm;       
  /*현금*/
  private String bndsCashAm;      
  /*현물*/
  private String bndsSpotAm;      
  /*합계*/
  private String bndsTotAm;       
  /*미지급*/
  private String bndsNpayAm;   
  /*과제아이디 */
  private String sbjtId;   
}
