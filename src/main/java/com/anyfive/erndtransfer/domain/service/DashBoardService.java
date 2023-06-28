package com.anyfive.erndtransfer.domain.service;

import org.springframework.stereotype.Service;

import com.anyfive.erndtransfer.domain.dto.*;
import com.anyfive.erndtransfer.domain.mapper.DashBoardMapper;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DashBoardService {

  private final DashBoardMapper dashBoardMapper;
  
  public MainPanelDto retriveMainPanel() {
    return dashBoardMapper.retriveMainPanel();
  }
  
  public NonTransferablePanelDto retriveNonTransferablePanelNoMapping() {
    return dashBoardMapper.retriveNonTransferablePanelNoMapping();
  }

  public NonTransferablePanelDto retriveNonTransferablePanelNonTransfer() {
    return dashBoardMapper.retriveNonTransferablePanelNonTransfer();
  }
  
  public NonTransferablePanelDto retriveNonTransferablePanelNoPreData() {
    return dashBoardMapper.retriveNonTransferablePanelNoPreData();
  }
  public List<Double> retriveAvgTime() {
    return dashBoardMapper.retriveAvgTime();
  }
  public List<Integer> retriveDataMappingRto() {
    return dashBoardMapper.retriveDataMappingRto();
  }
  public List<DashboardErrLogsDto>  retriveErrLogs() {
    return dashBoardMapper.retriveErrLogs();
  }
}
