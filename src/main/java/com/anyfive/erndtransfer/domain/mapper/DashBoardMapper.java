package com.anyfive.erndtransfer.domain.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.anyfive.erndtransfer.domain.dto.*;

import java.util.List;

@Mapper // Mapper로 등록 시킨다.
public interface DashBoardMapper {

  MainPanelDto retriveMainPanel();

  NonTransferablePanelDto retriveNonTransferablePanelNoMapping();

  NonTransferablePanelDto retriveNonTransferablePanelNonTransfer();

  NonTransferablePanelDto retriveNonTransferablePanelNoPreData();

  List<Double> retriveAvgTime();
  List<Integer> retriveDataMappingRto();

  List<DashboardErrLogsDto> retriveErrLogs();
}
