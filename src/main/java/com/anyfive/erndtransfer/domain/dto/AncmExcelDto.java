package com.anyfive.erndtransfer.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class AncmExcelDto implements ExcelDto{
    @ExcelColumnName(headerName = "번호")
    private String no;
    @ExcelColumnName(headerName = "ERND 접수환경번호")
    private String erndAncmNo;
    @ExcelColumnName(headerName = "ERND 사업년도")
    private String erndBizYr;
    @ExcelColumnName(headerName = "ERND 사업코드")
    private String erndBizCd;
    @ExcelColumnName(headerName = "ERND 접수환경명")
    private String erndAncmNm;
    @ExcelColumnName(headerName = "IRIS공고ID")
    private String irisAncmNo;
    @ExcelColumnName(headerName = "IRIS사업년도")
    private String irisBizYy;
    @ExcelColumnName(headerName = "IRIS사업코드")
    private String irisBizCd;
    @ExcelColumnName(headerName = "IRIS접수환경명")
    private String irisAncmNm;


    @Override
    public List<String> mapToList() {
        return Arrays.asList(no,erndAncmNo,erndBizYr,erndBizCd,erndAncmNm,irisAncmNo,irisBizYy,irisBizCd,irisAncmNm);
    }
}
