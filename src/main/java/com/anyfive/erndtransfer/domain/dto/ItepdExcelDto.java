package com.anyfive.erndtransfer.domain.dto;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelFileName
public class ItepdExcelDto implements ExcelDto{
  @ExcelColumnName(headerName = "순번")
  private int no;
  
  @ExcelColumnName(headerName = "ERND 비목코드")
  private String erndIoeCd;
  
  @ExcelColumnName(headerName = "ERND 비목명")
  private String erndIoeNm;
  
  @ExcelColumnName(headerName = "ERND 상위 비목코드")
  private String erndUprIoeCd;
  
  @ExcelColumnName(headerName = "ERND 비세목구분")
  private String erndIoeTpNm;
  
  @ExcelColumnName(headerName = "IRIS 비목 그룹명")
  private String irisItepdGrpCdNm;
  
  @ExcelColumnName(headerName = "IRIS 비목 코드")
  private String irisItepdCd;
  
  @ExcelColumnName(headerName = "IRIS 비목 코드명")
  private String irisItepdCdNm;
  
  @ExcelColumnName(headerName = "IRIS 상위 비목 코드")
  private String irisUprItepdCd;
  
  @Override
  public List<String> mapToList() {
    return Arrays.asList(
      String.valueOf(no),
      erndIoeCd,
      erndIoeNm,
      erndUprIoeCd,
      erndIoeTpNm,
      irisItepdGrpCdNm,
      irisItepdCd,
      irisItepdCdNm,
      irisUprItepdCd);
  }
}
