package com.anyfive.erndtransfer.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anyfive.erndtransfer.domain.dto.TrnsfMasterDto;
import com.anyfive.erndtransfer.domain.dto.TrnsfPrcdDto;
import com.anyfive.erndtransfer.domain.dto.TrnsfTempDto;
import com.anyfive.erndtransfer.domain.mapper.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TrnsfService {
  
  private final TrnsfMapper trnsfMapper;

  public String retrivePrgrsTrnsfExistYn(TrnsfMasterDto trnsfMasterDto) {
    return trnsfMapper.retrivePrgrsTrnsfExistYn(trnsfMasterDto);
  }
  
  public String retriveCurrentTrnsfId(TrnsfMasterDto trnsfMasterDto) {
    return trnsfMapper.retriveCurrentTrnsfId(trnsfMasterDto);
  }
  
  public String retriveLastTrnsfId(TrnsfMasterDto trnsfMasterDto) {
    return trnsfMapper.retriveLastTrnsfId(trnsfMasterDto);
  } 
  
  public TrnsfMasterDto retriveCurrentTrnsfInfo(TrnsfMasterDto trnsfMasterDto) {
    return trnsfMapper.retriveCurrentTrnsfInfo(trnsfMasterDto);
  }

  public TrnsfPrcdDto retrivePrgrsPrcdInfo(TrnsfMasterDto trnsfMasterDto) {
    return trnsfMapper.retrivePrgrsPrcdInfo(trnsfMasterDto);
  }

  public List<TrnsfPrcdDto> retrivePrgrsPrcdList(TrnsfMasterDto trnsfMasterDto) {
    return trnsfMapper.retrivePrgrsPrcdList(trnsfMasterDto);
  }
  
  public String registTrnsfMaster(TrnsfMasterDto trnsfMasterDto) {
    trnsfMapper.registTrnsfMaster(trnsfMasterDto);
    return trnsfMasterDto.getTrnsfId();
  }
  
  public void registTrnsfTemp(TrnsfTempDto trnsfTempDto) {
    trnsfMapper.registTrnsfTemp(trnsfTempDto);
  }
  
  public void updateTrnsfMaster(TrnsfMasterDto trnsfMasterDto) {
    trnsfMapper.updateTrnsfMaster(trnsfMasterDto);
  } 
  
  public void registTrnsfPrcdFlfmtRslt(TrnsfMasterDto trnsfMasterDto) {
    trnsfMapper.registTrnsfPrcdFlfmtRslt(trnsfMasterDto);
  }

  public void callIrisPrcd(TrnsfMasterDto trnsfMasterDto) {
    trnsfMapper.callIrisPrcd(trnsfMasterDto);
  }
  
  public void deleteTrnsfUploadFile(TrnsfMasterDto trnsfMasterDto) {
    trnsfMapper.deleteTrnsfTemp(trnsfMasterDto);
    trnsfMapper.deleteTrnsfMaster(trnsfMasterDto);
  }
  
  public void callTrnsfSbjtData(TrnsfMasterDto trnsfMasterDto) {
    trnsfMapper.callTrnsfSbjtData(trnsfMasterDto);
  }


}
