package com.anyfive.erndtransfer.domain.service;

import com.anyfive.erndtransfer.domain.dto.AncmDto;

import com.anyfive.erndtransfer.domain.dto.AncmExcelDto;
import com.anyfive.erndtransfer.domain.mapper.AncmMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AncmService {
    private final AncmMapper ancmMapper;

    public List<AncmDto> retriveAncmList(AncmDto ancmDto) {
        return ancmMapper.retriveAncmList(ancmDto);
    }

    public List<AncmExcelDto> retriveAncmExcelList(AncmExcelDto ancmExcelDto) {
        return ancmMapper.retriveAncmExcelList(ancmExcelDto);
    }
    public void insertAncmMappingData(){
        ancmMapper.insertAncmMappingData();
    }
}
