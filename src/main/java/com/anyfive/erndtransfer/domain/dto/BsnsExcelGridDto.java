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
public class BsnsExcelGridDto implements ExcelDto{
  @ExcelColumnName(headerName = "번호")
  private int no;
  
  @ExcelColumnName(headerName = "선정년도")
  private String seleYy;
  
  @ExcelColumnName(headerName = "프로그램사업코드")
  private String programBsnsCd;
  
  @ExcelColumnName(headerName = "단위사업코드")
  private String unitBsnsCd;
  
  @ExcelColumnName(headerName = "세부사업코드")
  private String ptcBsnsCd;
  
  @ExcelColumnName(headerName = "ERND사업코드")
  private String erndBsnsCd;
  
  @ExcelColumnName(headerName = "IRIS사업코드")
  private String irisBsnsCd;
  
  @ExcelColumnName(headerName = "세부사업명")
  private String ptcBsnsName;
  
  private String mappingYn;
  
  public String getMappingYn() {
      return mappingYn;
  }
  
  public void setMappingYn(String mappingYn) {
      this.mappingYn = mappingYn;
  }
  
  @Override
  public List<String> mapToList() {
      return Arrays.asList(String.valueOf(no),seleYy,programBsnsCd,unitBsnsCd,ptcBsnsCd,erndBsnsCd,irisBsnsCd,ptcBsnsName);
  }

}