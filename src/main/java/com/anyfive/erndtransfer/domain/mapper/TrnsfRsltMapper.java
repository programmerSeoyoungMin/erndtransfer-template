package com.anyfive.erndtransfer.domain.mapper;

import com.anyfive.erndtransfer.domain.dto.DashboardErrLogsDto;
import com.anyfive.erndtransfer.domain.dto.TrnsfRsltDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrnsfRsltMapper {

    List<TrnsfRsltDto> retriveTrnsfRsltList(TrnsfRsltDto trnsfRsltDto);
    List<TrnsfRsltDto> retriveTrnsfRsltDtl(TrnsfRsltDto trnsfRsltDto);
    TrnsfRsltDto retriveTrnsfErrDtl(TrnsfRsltDto trnsfRsltDto);

}
