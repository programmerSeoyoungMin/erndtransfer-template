package com.anyfive.erndtransfer.domain.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anyfive.erndtransfer.domain.dto.TrnsfMasterDto;
import com.anyfive.erndtransfer.domain.dto.TrnsfPrcdDto;
import com.anyfive.erndtransfer.domain.dto.TrnsfTempDto;


@Mapper // Mapper로 등록 시킨다.
public interface TrnsfMapper {

  String retrivePrgrsTrnsfExistYn(TrnsfMasterDto trnsfMasterDto);
  
  String retriveCurrentTrnsfId(TrnsfMasterDto trnsfMasterDto);
  
  String retriveLastTrnsfId(TrnsfMasterDto trnsfMasterDto);
  
  TrnsfMasterDto retriveCurrentTrnsfInfo(TrnsfMasterDto trnsfMasterDto);
  
  TrnsfPrcdDto retrivePrgrsPrcdInfo(TrnsfMasterDto trnsfMasterDto);
  
  List<TrnsfPrcdDto> retrivePrgrsPrcdList(TrnsfMasterDto trnsfMasterDto);

  void registTrnsfMaster(TrnsfMasterDto trnsfMasterDto);

  void registTrnsfTemp(TrnsfTempDto trnsfTempDto);
  
  void updateTrnsfMaster(TrnsfMasterDto trnsfMasterDto);
  
  void registTrnsfPrcdFlfmtRslt(TrnsfMasterDto trnsfMasterDto);

  void callIrisPrcd(TrnsfMasterDto trnsfMasterDto);
  
  void deleteTrnsfMaster(TrnsfMasterDto trnsfMasterDto);
  
  void deleteTrnsfTemp(TrnsfMasterDto trnsfMasterDto);
  
  void callTrnsfSbjtData(TrnsfMasterDto trnsfMasterDto);

}
