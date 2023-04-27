package com.anyfive.erndtransfer.domain.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anyfive.erndtransfer.domain.dto.BsnsTempDto;
import com.anyfive.erndtransfer.domain.service.BsnsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bsns")
@RequiredArgsConstructor
public class BsnsController {
  
  private final BsnsService bsnsService;
  
  @GetMapping("/retriveBsnsTempList")
  public List<BsnsTempDto> retriveBsnsTempList(@RequestParam(required = false) BsnsTempDto bsnsTempDto) {
      List<BsnsTempDto> bsnsList = bsnsService.retriveBsnsTempList(bsnsTempDto);
      return bsnsList;
  }
  
  
}
