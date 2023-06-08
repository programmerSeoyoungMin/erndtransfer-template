package com.anyfive.erndtransfer.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anyfive.erndtransfer.domain.mapper.*;

import lombok.RequiredArgsConstructor;

import com.anyfive.erndtransfer.domain.dto.BsnsDto;
import com.anyfive.erndtransfer.domain.dto.BsnsExcelDto;
import com.anyfive.erndtransfer.domain.dto.BsnsTempDto;


@RequiredArgsConstructor
@Service
public class BsnsService {
  
  private final BsnsMapper bsnsMapper;

  public List<BsnsDto> retriveBsnsList(BsnsDto bsnsDto) {
    return bsnsMapper.retriveBsnsList(bsnsDto);
  }
  
  public List<BsnsTempDto> retriveBsnsTempList(BsnsTempDto dto) {
    return bsnsMapper.retriveBsnsTempList(dto);
  }
  
  public void registBsns(BsnsDto bsnsDto) {
    bsnsMapper.registBsns(bsnsDto);
  }
  
  public void registBsnsTemp(BsnsTempDto dto) {
    bsnsMapper.registBsnsTemp(dto);
  }

  public void saveBsnsList(List<BsnsDto> bsnsDtoList) {
    for(BsnsDto bsnsDto : bsnsDtoList) {
      bsnsMapper.registBsns(bsnsDto);
    }
  }

  public int retriveBsnsListTotalCnt(BsnsDto bsnsDto) {
    bsnsDto.setLimit(null);
    int totalCnt = bsnsMapper.retriveBsnsListCnt(bsnsDto);
    return totalCnt;
  }

  public List<BsnsExcelDto> retriveBsnsExcelList(BsnsExcelDto bsnsExcelDto) {
    return bsnsMapper.retriveBsnsExcelList(bsnsExcelDto);
  }

  public void deleteBsnsTemp(BsnsTempDto bsnsTempDto) {
    bsnsMapper.deleteBsnsTemp(bsnsTempDto);
  }

}
