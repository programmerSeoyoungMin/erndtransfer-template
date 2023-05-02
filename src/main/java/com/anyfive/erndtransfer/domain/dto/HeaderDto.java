package com.anyfive.erndtransfer.domain.dto;

import java.util.ArrayList;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("HeaderDto")
@RequiredArgsConstructor
public class HeaderDto {
  private String gbn;
  private String key;
  private String name;
  private String width;
  private String type;
  private String align;
  private Boolean show;
  private String exclude;
  private String includeErrorFieldYn;
  //excludeColumns
  private String[] excludeColumns;
  
}