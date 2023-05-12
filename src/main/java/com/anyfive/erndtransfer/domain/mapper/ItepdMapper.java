package com.anyfive.erndtransfer.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anyfive.erndtransfer.domain.dto.*;

@Mapper // Mapper로 등록 시킨다.
public interface ItepdMapper {

    List<ItepdDto> retriveItepdList(ItepdDto itepdDto);

    List<ItepdTempDto> retriveItepdTempList(ItepdTempDto dto);

    void registItepd(ItepdDto itepdDto);

    void registItepdTemp(ItepdTempDto itepdDto);

    List<ItepdExcelDto> retriveItepdExcelList(ItepdExcelDto itepdExcelDto);

    List<ItepdDto> retriveErndIoeCdList();

    List<ItepdDto> retriveIrisItepdCdList();

}
