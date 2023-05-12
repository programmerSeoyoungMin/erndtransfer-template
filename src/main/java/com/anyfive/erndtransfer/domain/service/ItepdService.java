package com.anyfive.erndtransfer.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anyfive.erndtransfer.domain.mapper.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import com.anyfive.erndtransfer.domain.dto.ItepdDto;
import com.anyfive.erndtransfer.domain.dto.ItepdExcelDto;
import com.anyfive.erndtransfer.domain.dto.ItepdTempDto;


@RequiredArgsConstructor
@Service
public class ItepdService {
  
  private final ItepdMapper itepdMapper;

  public List<ItepdDto> retriveItepdList(ItepdDto itepdDto) {
    return itepdMapper.retriveItepdList(itepdDto);
  }
  
  public List<ItepdTempDto> retriveItepdTempList(ItepdTempDto dto) {
    return itepdMapper.retriveItepdTempList(dto);
  }
  
  public void registItepd(ItepdDto itepdDto) {
    itepdMapper.registItepd(itepdDto);
  }
  
  public void registItepdTemp(ItepdTempDto dto) {
    itepdMapper.registItepdTemp(dto);
  }

  public void saveItepdList(List<ItepdDto> itepdDtoList) {
    for(ItepdDto itepdDto : itepdDtoList) {
      itepdMapper.registItepd(itepdDto);
    }
  }
  //TODO 임시 service (count Mapper xml 추가필요)
  public int retriveItepdListTotalCnt(ItepdDto itepdDto) {
    itepdDto.setLimit(null);
    int totalCnt = itepdMapper.retriveItepdList(itepdDto).size();
    return totalCnt;
  }

  public List<ItepdExcelDto> retriveItepdExcelList(ItepdExcelDto itepdExcelDto) {
    return itepdMapper.retriveItepdExcelList(itepdExcelDto);
  }

  public List<ItepdDto> retriveErndIoeCdList() {
    return itepdMapper.retriveErndIoeCdList();
  }

  public List<ItepdDto> retriveIrisItepdCdList() {
    return itepdMapper.retriveIrisItepdCdList();
  }

}
