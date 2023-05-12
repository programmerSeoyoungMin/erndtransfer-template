package com.anyfive.erndtransfer.domain.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("ItepdTempDto")
@RequiredArgsConstructor
public class ItepdTempDto {
  /* upload 날짜 */
  private String uploadDe;
  /* upload 파일명 */
  private String uploadFileNm;
  /* excel 순번 */
  private int excelRowNum;
  /*ERND 비목코드*/
  private String erndIoeCd;
  /*ERND 비목명*/
  private String erndIoeNm;
  /*ERND 상위 비목코드*/
  private String erndUprIoeCd;
  /*ERND 비세목구분(0:비목, 1:현금, 2:현물, 3:미지급)*/
  private String erndIoeTp;
  /*IRIS 비목 그룹*/
  private String irisItepdGrpCd;
  /*IRIS 비목 그룹명*/
  private String irisItepdGrpCdNm;
  /*IRIS 비목 코드*/
  private String irisItepdCd;
  /*IRIS 비목 코드명*/
  private String irisItepdCdNm;
  /*IRIS 상위 비목 코드*/
  private String irisUprItepdCd;
}
