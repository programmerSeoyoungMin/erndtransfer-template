package com.anyfive.erndtransfer.domain.mapper;

import com.anyfive.erndtransfer.domain.dto.AncmDto;
import com.anyfive.erndtransfer.domain.dto.AncmExcelDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AncmMapper {

    List<AncmDto> retriveAncmList(AncmDto ancmDto);
    List<AncmExcelDto> retriveAncmExcelList(AncmExcelDto ancmExcelDto);
}
