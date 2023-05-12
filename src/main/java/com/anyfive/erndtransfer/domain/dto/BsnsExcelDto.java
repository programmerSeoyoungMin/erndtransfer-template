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
public class BsnsExcelDto implements ExcelDto{
  @ExcelColumnName(headerName = "번호")
  private int no;
  
  @ExcelColumnName(headerName = "선정년도")
  private String seleYy;
  
  @ExcelColumnName(headerName = "ERND사업코드")
  private String erndBsnsCd;
  
  @ExcelColumnName(headerName = "선정년도사업코드")
  private String seleYyBsnsCd;
  
  @ExcelColumnName(headerName = "대사업명")
  private String erndBsnsName1;
  
  @ExcelColumnName(headerName = "중사업명")
  private String erndBsnsName2;
  
  @ExcelColumnName(headerName = "소사업명")
  private String erndBsnsName3;
  
  @ExcelColumnName(headerName = "세부사업명")
  private String erndBsnsName4;
  
  @ExcelColumnName(headerName = "D-BRAIN코드")
  private String dbrainCd;
  
  @ExcelColumnName(headerName = "D-BRAIN코드")
  private String moefDbrainCd;
  
  @ExcelColumnName(headerName = "D-BRAIN코드")
  private String erndDbrainCd;
  
  @ExcelColumnName(headerName = "사업년도")
  private String bsnsYy;
  
  @ExcelColumnName(headerName = "소관")
  private String jrsdCd;
  
  @ExcelColumnName(headerName = "소관명")
  private String jrsdName;
  
  @ExcelColumnName(headerName = "회계")
  private String actgCd;
  
  @ExcelColumnName(headerName = "회계명")
  private String actgName;
  
  @ExcelColumnName(headerName = "분야명")
  private String fildName;
  
  @ExcelColumnName(headerName = "분야코드")
  private String fildCd;
  
  @ExcelColumnName(headerName = "부문명")
  private String sectorName;
  
  @ExcelColumnName(headerName = "부문코드")
  private String sectorCd;
  
  @ExcelColumnName(headerName = "프로그램ID")
  private String programId;
  
  @ExcelColumnName(headerName = "프로그램명")
  private String programName;
  
  @ExcelColumnName(headerName = "프로그램코드")
  private String programCode;
  
  @ExcelColumnName(headerName = "단위ID")
  private String unitId;
  
  @ExcelColumnName(headerName = "단위명")
  private String unitName;
  
  @ExcelColumnName(headerName = "단위코드")
  private String unitCd;
  
  @ExcelColumnName(headerName = "세부ID")
  private String ptcId;
  
  @ExcelColumnName(headerName = "세부명")
  private String ptcName;
  
  @ExcelColumnName(headerName = "세부코드")
  private String ptcCd;
  
  @ExcelColumnName(headerName = "사업코드")
  private String bsnsCd;
  
  @ExcelColumnName(headerName = "예산액")
  private long budAm;
  
  @ExcelColumnName(headerName = "결산액")
  private long stacAm;
  
  @ExcelColumnName(headerName = "내역사업명")
  private String brdnBsnsName;
  
  @ExcelColumnName(headerName = "내역사업명2")
  private String brdnBsnsName2;
  
  @ExcelColumnName(headerName = "IRIS사업코드")
  private String irisBsnsCd;
  
  @ExcelColumnName(headerName = "연도")
  private String yy;
  
  @ExcelColumnName(headerName = "프로그램사업코드")
  private String programBsnsCd;
  
  @ExcelColumnName(headerName = "단위사업코드")
  private String unitBsnsCd;
  
  @ExcelColumnName(headerName = "세부사업코드")
  private String ptcBsnsCd;
  
  @ExcelColumnName(headerName = "세부사업명")
  private String ptcBsnsName;
  
  @ExcelColumnName(headerName = "Z1")
  private String z1;
  
  private String mappingYn;
  
  @Override
  public List<String> mapToList() {
      return Arrays.asList(String.valueOf(no),seleYy,erndBsnsCd,seleYyBsnsCd,erndBsnsName1,erndBsnsName2,erndBsnsName3,erndBsnsName4,dbrainCd,moefDbrainCd,erndDbrainCd,bsnsYy,jrsdCd,jrsdName,actgCd,actgName,fildName,fildCd,sectorName,sectorCd,programId,programName,programCode,unitId,unitName,unitCd,ptcId,ptcName,ptcCd,bsnsCd,String.valueOf(budAm),String.valueOf(stacAm),brdnBsnsName,brdnBsnsName2,irisBsnsCd,yy,programBsnsCd,unitBsnsCd,ptcBsnsCd,ptcBsnsName,z1);
  }
}