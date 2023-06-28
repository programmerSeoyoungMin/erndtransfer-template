package com.anyfive.erndtransfer.domain.service;

import com.anyfive.erndtransfer.domain.dto.DashboardErrLogsDto;
import com.anyfive.erndtransfer.domain.dto.TrnsfRsltDto;
import com.anyfive.erndtransfer.domain.mapper.TrnsfRsltMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrnsfRsltService {
    private final TrnsfRsltMapper trnsfRsltMapper;

    public List<TrnsfRsltDto> retriveTrnsfRsltList(TrnsfRsltDto trnsfRsltDto) {
        return trnsfRsltMapper.retriveTrnsfRsltList(trnsfRsltDto);
    }
    public List<TrnsfRsltDto> retriveTrnsfRsltDtl(TrnsfRsltDto trnsfRsltDto) {
        return trnsfRsltMapper.retriveTrnsfRsltDtl(trnsfRsltDto);
    }
    public TrnsfRsltDto retriveTrnsfErrDtl(TrnsfRsltDto trnsfRsltDto) {
        return trnsfRsltMapper.retriveTrnsfErrDtl(trnsfRsltDto);
    }
}
