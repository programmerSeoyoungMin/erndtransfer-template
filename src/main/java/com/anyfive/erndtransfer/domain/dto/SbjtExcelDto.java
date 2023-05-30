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
public class SbjtExcelDto implements ExcelDto {
    @ExcelColumnName(headerName = "번호")
    private int no;
    @ExcelColumnName(headerName = "ERND과제번호")
    private String erndSbjtNo;
    @ExcelColumnName(headerName = "ERND수행년도")
    private String erndFlfmtYy;
    @ExcelColumnName(headerName = "ERND과제명")
    private String erndsbjtNm;
    @ExcelColumnName(headerName = "IRIS과제번호")
    private String irisSbjtNo;
    @ExcelColumnName(headerName = "IRIS수행년도")
    private String irisFlfmtYy;
    @ExcelColumnName(headerName = "과제상태")
    private String sbjtStts;


    @Override
    public List<String> mapToList() {
        return Arrays.asList(String.valueOf(no),erndSbjtNo,erndFlfmtYy,erndsbjtNm,irisSbjtNo,irisFlfmtYy,sbjtStts);
    }
}
