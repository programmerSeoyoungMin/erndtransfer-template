package com.anyfive.erndtransfer.domain.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anyfive.erndtransfer.domain.dto.*;

@Mapper // Mapper로 등록 시킨다.
public interface BsnsMapper {

    List<BsnsDto> retriveBsnsList(BsnsDto bsnsDto);

    List<BsnsTempDto> retriveBsnsTempList(BsnsTempDto dto);

    void registBsns(BsnsDto bsnsDto);

    void registBsnsTemp(BsnsTempDto bsnsDto);

    List<BsnsExcelDto> retriveBsnsExcelList(BsnsExcelDto bsnsExcelDto);
  
    void deleteBsnsTemp(BsnsTempDto bsnsTempDto);

    int retriveBsnsListCnt(BsnsDto bsnsDto);
}
