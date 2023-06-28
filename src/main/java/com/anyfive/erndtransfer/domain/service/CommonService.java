package com.anyfive.erndtransfer.domain.service;

import java.util.List;

import com.anyfive.erndtransfer.domain.mapper.*;
import com.anyfive.erndtransfer.domain.dto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommonService {

  private final CommonMapper CommonMapper;

  @Transactional(readOnly = true)
  public List<HeaderDto> retriveHeaderList(HeaderDto dto) {
        return CommonMapper.retriveHeaderList(dto);
  }

}
