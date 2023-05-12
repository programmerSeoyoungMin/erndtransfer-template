package com.anyfive.erndtransfer.domain.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("BsnsDto")
@RequiredArgsConstructor
public class BsnsDto extends PagingDto{
  
  /*번호*/
  private String no;
  private int seq;
  /*선정년도*/
  private String seleYy;
  /*ERND사업코드*/
  private String erndBsnsCd;
  /*선정년도사업코드*/
  private String seleYyBsnsCd;
  /*대사업명*/
  private String erndBsnsName1;
  /*중사업명*/
  private String erndBsnsName2;
  /*소사업명*/
  private String erndBsnsName3;
  /*세부사업명*/
  private String erndBsnsName4;
  /*D-BRAIN코드*/
  private String dbrainCd;
  /*D-BRAIN코드명(기재부)*/
  private String moefDbrainCd;
  /*D-BRAIN코드명(기존)*/
  private String erndDbrainCd;
  /*사업년도*/
  private String bsnsYy;
  /*소관*/
  private String jrsdCd;
  /*소관명*/
  private String jrsdName;
  /*회계*/
  private String actgCd;
  /*회계명*/
  private String actgName;
  /*분야명*/
  private String fildName;
  /*분야코드*/
  private String fildCd;
  /*부문명*/
  private String sectorName;
  /*부문코드*/
  private String sectorCd;
  /*프로그램ID*/
  private String programId;
  /*프로그램명*/
  private String programName;
  /*프로그램코드*/
  private String programCode;
  /*단위ID*/
  private String unitId;
  /*단위명*/
  private String unitName;
  /*단위코드*/
  private String unitCd;
  /*세부ID*/
  private String ptcId;
  /*세부명*/
  private String ptcName;
  /*세부코드*/
  private String ptcCd;
  /*사업코드*/
  private String bsnsCd;
  /*예산액*/
  private String budAm;
  /*결산액*/
  private String stacAm;
  /*내역사업명*/
  private String brdnBsnsName;
  /*내역사업명2*/
  private String brdnBsnsName2;
  /*IRIS사업코드*/
  private String irisBsnsCd;
  /*연도*/
  private String yy;
  /*프로그램사업코드*/
  private String programBsnsCd;
  /*단위사업코드*/
  private String unitBsnsCd;
  /*세부사업코드*/
  private String ptcBsnsCd;
  /*세부사업명*/
  private String ptcBsnsName;
  /*Z1*/
  private String z1;
  /*매핑여부*/
  private String mappingYn;
}
