package com.anyfive.erndtransfer.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SbjtDto extends PagingDto{
    private int No;
    private String erndSbjtNo;
    private String erndFlfmtYy;
    private String erndsbjtNm;
    private String irisSbjtNo;
    private String irisFlfmtYy;
    private String sbjtStts;


}
