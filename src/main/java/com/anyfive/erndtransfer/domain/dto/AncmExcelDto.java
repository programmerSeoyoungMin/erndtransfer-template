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
    @ExcelColumnName(headerName = "접수환경명")
    private String ancmNm;
    @ExcelColumnName(headerName = "IRIS공고ID")
    private String irisAncmNo;
    @ExcelColumnName(headerName = "총단계차수")
    private String totStepCycl;
    @ExcelColumnName(headerName = "전체연차차수")
    private String wholAnuCycl;
    @ExcelColumnName(headerName = "현재단계")
    private String prsStep;
    @ExcelColumnName(headerName = "현재연차")
    private String prsAnu;


    @Override
    public List<String> mapToList() {
        return Arrays.asList(no,erndAncmNo,erndBizYr,erndBizCd,ancmNm,irisAncmNo,totStepCycl,wholAnuCycl,prsStep,prsAnu);
    }
}
