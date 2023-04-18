package com.anyfive.erndtransfer.domain.test.service;

import java.util.List;

import com.anyfive.erndtransfer.domain.test.mapper.*;
import com.anyfive.erndtransfer.domain.test.dto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TestService {

      private final TestMapper TestMapper;

      @Transactional(readOnly = true)
      public List<TestDto> selectTest() {
            return TestMapper.selectTest();
      }

}
