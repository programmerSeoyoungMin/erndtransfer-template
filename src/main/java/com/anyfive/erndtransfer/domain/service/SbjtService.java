package com.anyfive.erndtransfer.domain.service;

import com.anyfive.erndtransfer.domain.dto.SbjtDto;
import com.anyfive.erndtransfer.domain.dto.SbjtExcelDto;
import com.anyfive.erndtransfer.domain.mapper.SbjtMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SbjtService {
    private final SbjtMapper sbjtMapper;

    public List<SbjtDto> retriveSbjtList(SbjtDto sbjtDto) {
        return sbjtMapper.retriveSbjtList(sbjtDto);
    }
    public List<SbjtExcelDto> retriveSbjtExcelList(SbjtExcelDto sbjtExcelDto) {
        return sbjtMapper.retriveSbjtExcelList(sbjtExcelDto);
    }
    public void insertSbjtMappingData(){
        sbjtMapper.insertSbjtMappingData();
    }
}
