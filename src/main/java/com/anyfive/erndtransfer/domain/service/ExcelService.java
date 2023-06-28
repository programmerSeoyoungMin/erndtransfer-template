package com.anyfive.erndtransfer.domain.service;

import com.anyfive.erndtransfer.domain.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyfive.erndtransfer.domain.dto.BsnsExcelDto;
import com.anyfive.erndtransfer.domain.dto.BsnsExcelGridDto;
import com.anyfive.erndtransfer.domain.dto.ItepdExcelDto;
import com.anyfive.erndtransfer.domain.util.ExcelWriter;

import java.util.*;


@Service
public class ExcelService {
  
    @Autowired
    private BsnsService bsnsService;
    @Autowired
    private ItepdService itepdService;
    @Autowired
    private SbjtService sbjtService;
    @Autowired
    private AncmService ancmService;
    @Autowired
    private SbjtTrnsResultService sbjtTrnsResultService;

    public Map<String, Object> excelDownload(Map<String,Object> paramMap) {
      Map<String, Object> resultMap = new HashMap<String, Object>();
      
      String bsnsDivCd = paramMap.get("divCd").toString();
      Map<String,String> paramObjMap = (Map<String,String>)paramMap.get("paramObj");
      
      //사업_전체인 경우
      if(bsnsDivCd.equals("BSNS_ALL")){
        BsnsExcelDto bsnsExcelDto = new BsnsExcelDto();
        bsnsExcelDto.setErndBsnsCd(paramObjMap.get("erndBsnsCd"));
        bsnsExcelDto.setIrisBsnsCd(paramObjMap.get("irisBsnsCd"));
        bsnsExcelDto.setSeleYy(paramObjMap.get("seleYy"));
        bsnsExcelDto.setMappingYn(paramObjMap.get("mappingYn"));
        List<BsnsExcelDto> bsnsExcelDtoList = bsnsService.retriveBsnsExcelList(bsnsExcelDto);
        resultMap = ExcelWriter.createExcelData(bsnsExcelDtoList, BsnsExcelDto.class);
      //사업_그리드인 경우
      }else if(bsnsDivCd.equals("BSNS_GRID")){
        BsnsExcelDto bsnsExcelDto = new BsnsExcelDto();
        bsnsExcelDto.setErndBsnsCd(paramObjMap.get("erndBsnsCd"));
        bsnsExcelDto.setIrisBsnsCd(paramObjMap.get("irisBsnsCd"));
        bsnsExcelDto.setSeleYy(paramObjMap.get("seleYy"));
        bsnsExcelDto.setMappingYn(paramObjMap.get("mappingYn"));
        List<BsnsExcelDto> bsnsExcelDtoList = bsnsService.retriveBsnsExcelList(bsnsExcelDto);
        //resultMap은 grid내용만 담도록 함.
        resultMap = ExcelWriter.createExcelData(bsnsExcelDtoList, BsnsExcelGridDto.class);
      //비목인 경우 
      }else if(bsnsDivCd.equals("ITEPD")){
        ItepdExcelDto itepdExcelDto = new ItepdExcelDto();
        itepdExcelDto.setErndIoeCd(paramObjMap.get("erndIoeCd"));
        itepdExcelDto.setErndIoeNm(paramObjMap.get("erndIoeNm"));
        itepdExcelDto.setIrisItepdCd(paramObjMap.get("irisItepdCd"));
        itepdExcelDto.setIrisItepdCdNm(paramObjMap.get("irisItepdNm"));
        List<ItepdExcelDto> itepdExcelDtoList = itepdService.retriveItepdExcelList(itepdExcelDto);
        resultMap = ExcelWriter.createExcelData(itepdExcelDtoList, ItepdExcelDto.class);
      // 과제인경 우
      }else if(bsnsDivCd.equals("SBJT")){
        SbjtExcelDto sbjtExcelDto = new SbjtExcelDto();
        sbjtExcelDto.setErndSbjtNo(paramObjMap.get("erndSbjtNo"));
        sbjtExcelDto.setErndFlfmtYy(paramObjMap.get("erndFlfmtYy"));
        sbjtExcelDto.setIrisSbjtNo(paramObjMap.get("irisSbjtNo"));
        sbjtExcelDto.setSbjtStts(paramObjMap.get("sbjtStts"));
        List<SbjtExcelDto> sbjtExcelList = sbjtService.retriveSbjtExcelList(sbjtExcelDto);
        resultMap = ExcelWriter.createExcelData(sbjtExcelList, SbjtExcelDto.class);
        // 공고인 경우
      }else if(bsnsDivCd.equals("ANCM")){
        AncmExcelDto ancmExcelDto = new AncmExcelDto();
        ancmExcelDto.setErndAncmNo(paramObjMap.get("erndAncmNo"));
        ancmExcelDto.setIrisAncmNo(paramObjMap.get("irisAncmNo"));
        ancmExcelDto.setErndBizYr(paramObjMap.get("erndBizYr"));
        List<AncmExcelDto> ancmExcelList = ancmService.retriveAncmExcelList(ancmExcelDto);
        resultMap = ExcelWriter.createExcelData(ancmExcelList, AncmExcelDto.class);
      }else if(bsnsDivCd.equals("SBJT_TRNS_RESULT")){
        SbjtTrnsResultExcelDto sbjtTrnsResultExcelDto = new SbjtTrnsResultExcelDto();
        sbjtTrnsResultExcelDto.setErndSbjtNo(paramObjMap.get("erndSbjtNo"));
        sbjtTrnsResultExcelDto.setIrisSbjtId(paramObjMap.get("irisSbjtId"));
        sbjtTrnsResultExcelDto.setHanSbjtNm(paramObjMap.get("hanSbjtNm"));
        sbjtTrnsResultExcelDto.setSpvsAgcNm(paramObjMap.get("spvsAgcNm"));
        sbjtTrnsResultExcelDto.setRschRpbPeRschrRegNm(paramObjMap.get("rschRpbPeRschrRegNm"));
        List<SbjtTrnsResultExcelDto> sbjtTrnsResultExcelList = sbjtTrnsResultService.retriveSbjtTrnsResultExcelList(sbjtTrnsResultExcelDto);
        resultMap = ExcelWriter.createExcelData(sbjtTrnsResultExcelList, SbjtTrnsResultExcelDto.class);
      }
      
      return resultMap;
    }
}