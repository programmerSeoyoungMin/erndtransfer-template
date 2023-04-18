package com.anyfive.erndtransfer.domain.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anyfive.erndtransfer.domain.test.dto.*;

@Mapper // Mapper로 등록 시킨다.
public interface TestMapper {

     List<TestDto> selectTest();
}
