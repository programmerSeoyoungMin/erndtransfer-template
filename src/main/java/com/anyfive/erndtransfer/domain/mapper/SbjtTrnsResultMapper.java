package com.anyfive.erndtransfer.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.anyfive.erndtransfer.domain.dto.*;


@Mapper
public interface SbjtTrnsResultMapper {

  public List<SbjtTrnsResultListDto> retriveSbjtTrnsResultList(SbjtTrnsResultListDto sbjtTrnsResultListDto);

  public int retriveSbjtTrnsResultListCnt(SbjtTrnsResultListDto sbjtTrnsResultListDto);

  public SbjtTrnsResultKwdDto retriveSbjtTrnsResultKwd(SbjtTrnsResultKwdDto sbjtTrnsResultKwdDto);

  public List<SbjtTrnsResultItepdDto> retriveSbjtTrnsResultItepdList(SbjtTrnsResultItepdDto sbjtTrnsResultItepdDto);

  public List<SbjtTrnsResultExcelDto> retriveSbjtTrnsResultExcelList(SbjtTrnsResultExcelDto sbjtTrnsResultExcelDto);

  public SbjtTrnsResultDetailDto retriveSbjtTrnsResultDetail(SbjtTrnsResultDetailDto sbjtTrnsResultDetailDto);
  
}
