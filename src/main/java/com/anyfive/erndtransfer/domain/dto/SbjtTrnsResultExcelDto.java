package com.anyfive.erndtransfer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelFileName
public class SbjtTrnsResultExcelDto implements ExcelDto{
  @ExcelColumnName(headerName = "번호")
  private int no;
  @ExcelColumnName(headerName = "세부사업명")
  private String ptcBsnsName;
  @ExcelColumnName(headerName = "내역사업명")
  private String brdnBsnsName;
  @ExcelColumnName(headerName = "과제구분")
  private String sbjtProplStrcSeNm;
  @ExcelColumnName(headerName = "사업년도")
  private String seleYy;
  @ExcelColumnName(headerName = "연구개발과제번호")
  private String rndSbjtNo;
  @ExcelColumnName(headerName = "과제번호")
  private String stepAnuSbjtNo;
  @ExcelColumnName(headerName = "과제명")
  private String hanSbjtNm;
  @ExcelColumnName(headerName = "연구책임자명")
  private String rschRpbPeRschrRegNm;
  @ExcelColumnName(headerName = "주관기관명")
  private String spvsAgcNm;
  @ExcelColumnName(headerName = "주무부처")
  private String bizDepartNm;
  @ExcelColumnName(headerName = "총연구기간")
  private String totRschDate;

  private String erndSbjtNo;
  private String irisSbjtId;
  
  @Override
  public List<String> mapToList() {
      return Arrays.asList(String.valueOf(no),ptcBsnsName,brdnBsnsName,sbjtProplStrcSeNm,seleYy,rndSbjtNo,stepAnuSbjtNo,hanSbjtNm,rschRpbPeRschrRegNm,spvsAgcNm,bizDepartNm,totRschDate);
  }
}
