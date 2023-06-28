package com.anyfive.erndtransfer.domain.controller;

import com.anyfive.erndtransfer.domain.dto.DashboardErrLogsDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anyfive.erndtransfer.domain.dto.MainPanelDto;
import com.anyfive.erndtransfer.domain.dto.NonTransferablePanelDto;
import com.anyfive.erndtransfer.domain.service.DashBoardService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/dashBoard")
@RequiredArgsConstructor
public class DashBoardController {
  
  private final DashBoardService dashBoardService;

  @PostMapping("/retriveMainPanel")
  public MainPanelDto retriveMainPanel() {
    MainPanelDto mainPanelDto = dashBoardService.retriveMainPanel();
    return mainPanelDto;
  }  
  
  @PostMapping("/retriveNonTransferablePanelNoMapping")
  public NonTransferablePanelDto retriveNonTransferablePanelNoMapping() {
    NonTransferablePanelDto nonTransferablePanelDto = dashBoardService.retriveNonTransferablePanelNoMapping();
    return nonTransferablePanelDto;
  }  
  
  @PostMapping("/retriveNonTransferablePanelNonTransfer")
  public NonTransferablePanelDto retriveNonTransferablePanelNonTransfer() {
    NonTransferablePanelDto nonTransferablePanelDto = dashBoardService.retriveNonTransferablePanelNonTransfer();
    return nonTransferablePanelDto;
  }  
  
  @PostMapping("/retriveNonTransferablePanelNoPreData")
  public NonTransferablePanelDto retriveNonTransferablePanelNoPreData() {
    NonTransferablePanelDto nonTransferablePanelDto = dashBoardService.retriveNonTransferablePanelNoPreData();
    return nonTransferablePanelDto;
  }
  @GetMapping("retriveAvgTime")
  public List<Double> retriveAvgTime() {
    List<Double> avgTime = dashBoardService.retriveAvgTime();
    return avgTime;
  }
  @GetMapping("retriveErrLogs")
  public List<DashboardErrLogsDto> retriveErrLogs() {
    List<DashboardErrLogsDto> errLogs = dashBoardService.retriveErrLogs();
    return errLogs;
  }
  @GetMapping("retriveDataMappingRto")
  public List<Integer> retriveDataMappingRto() {
    List<Integer> mappingRtoList = dashBoardService.retriveDataMappingRto();
    return mappingRtoList;
  }
}
