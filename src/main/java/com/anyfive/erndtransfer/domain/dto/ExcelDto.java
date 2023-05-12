package com.anyfive.erndtransfer.domain.dto;

import java.util.List;

public interface ExcelDto {
    //excelDto를 상속받는 클래스는 반드시 mapToList()를 구현해야 한다.
    //mapToList에 있는 값들이 엑셀에 출력된다.
    List<String> mapToList();
    
}