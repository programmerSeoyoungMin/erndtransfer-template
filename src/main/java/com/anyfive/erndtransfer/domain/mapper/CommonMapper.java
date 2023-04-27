package com.anyfive.erndtransfer.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anyfive.erndtransfer.domain.dto.*;

@Mapper // Mapper로 등록 시킨다.
public interface CommonMapper {

     List<HeaderDto> retriveHeaderList(HeaderDto dto);
}
