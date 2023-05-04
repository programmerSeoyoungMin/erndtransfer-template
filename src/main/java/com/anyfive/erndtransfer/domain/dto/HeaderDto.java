package com.anyfive.erndtransfer.domain.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("HeaderDto")
@RequiredArgsConstructor
public class HeaderDto {
  private String taskSeTblNm;
  private String key;
  private String name;
  private String width;
  private String align;
  
}