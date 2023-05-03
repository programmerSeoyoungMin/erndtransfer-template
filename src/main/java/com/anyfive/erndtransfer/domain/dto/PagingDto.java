package com.anyfive.erndtransfer.domain.dto;

import org.apache.ibatis.type.Alias;

import lombok.RequiredArgsConstructor;

@Alias("PagingDto")
@RequiredArgsConstructor
public class PagingDto {
  private Integer currentPage;
  private Integer limit;
  private Integer offset;

  public Integer getCurrentPage() {
      return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
      this.currentPage = currentPage;
  }

  public Integer getLimit() {
      return limit;
  }

  public void setLimit(Integer pageSize) {
      this.limit = pageSize;
  }

  public Integer getOffset() {
      if (this.currentPage != null && this.limit != null) {
          return (this.currentPage - 1) * this.limit;
      }
      return offset;
  }

  public void setOffset(Integer offset) {
      this.offset = offset;
  }
}
