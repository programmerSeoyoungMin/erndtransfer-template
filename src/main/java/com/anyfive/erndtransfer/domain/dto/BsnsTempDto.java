package com.anyfive.erndtransfer.domain.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("BsnsTempDto")
@RequiredArgsConstructor
public class BsnsTempDto {
  private int no;
  private String uploadDe;
  private String uploadFileNm;
  private int excelRowNum;
  private String errorMsg;
  private String seleYy;
  private String erndBsnsCd;
  private String seleYyBsnsCd;
  private String erndBsnsName1;
  private String erndBsnsName2;
  private String erndBsnsName3;
  private String erndBsnsName4;
  private String dbrainCd;
  private String moefDbrainCd;
  private String erndDbrainCd;
  private String bsnsYy;
  private String jrsdCd;
  private String jrsdName;
  private String actgCd;
  private String actgName;
  private String fildName;
  private String fildCd;
  private String sectorName;
  private String sectorCd;
  private String programId;
  private String programName;
  private String programCode;
  private String unitId;
  private String unitName;
  private String unitCd;
  private String ptcId;
  private String ptcName;
  private String ptcCd;
  private String bsnsCd;
  private long budAm;
  private long stacAm;
  private String brdnBsnsName;
  private String brdnBsnsName2;
  private String irisBsnsCd;
  private String yy;
  private String programBsnsCd;
  private String unitBsnsCd;
  private String ptcBsnsCd;
  private String ptcBsnsName;
  private String z1;
  private String errorYn;
}
