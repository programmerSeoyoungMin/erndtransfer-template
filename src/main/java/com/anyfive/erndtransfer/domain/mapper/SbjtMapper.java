package com.anyfive.erndtransfer.domain.mapper;

import com.anyfive.erndtransfer.domain.dto.SbjtDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SbjtMapper {

    List<SbjtDto> retriveSbjtList(SbjtDto sbjtDto);
}
