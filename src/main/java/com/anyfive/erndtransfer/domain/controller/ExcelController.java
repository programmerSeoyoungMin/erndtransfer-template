package com.anyfive.erndtransfer.domain.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anyfive.erndtransfer.domain.dto.BsnsTempDto;
import com.anyfive.erndtransfer.domain.service.BsnsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/excel")
@RequiredArgsConstructor
public class ExcelController<T> {
  
  private final BsnsService bsnsService;
  
  @PostMapping("/upload")
  public List<HashMap<String,String>> uploadExcelFile(@RequestParam("file") MultipartFile file, @RequestParam("gbn") String gbn) throws Exception{
    // 업로드된 파일 처리 
    
    String filePath = "C:\\uploads\\" + file.getOriginalFilename();
    // 파일 저장
    byte[] bytes = file.getBytes();
    Path path = Paths.get(filePath);
    Files.write(path, bytes);
    List<HashMap<String, String>> excelData = new ArrayList<>();
    
    excelData = saveExcelFileTempTable(filePath, gbn);
    return excelData;  
  }

  private List<HashMap<String,String>> saveExcelFileTempTable(String path, String gbn) throws Exception {
    // 1. 엑셀 파일을 읽어서 List<HashMap<String,String>>에 담기
    FileInputStream fis = new FileInputStream(path);
    Workbook workbook = WorkbookFactory.create(fis);
    Sheet sheet = workbook.getSheetAt(0);
    
    // 첫 번째 행에서 키값을 읽어옴
    Row keyRow = sheet.getRow(0);
    Iterator<Cell> keyCellIter = keyRow.cellIterator();
    List<String> keyList = new ArrayList<>();
    while (keyCellIter.hasNext()) {
        Cell keyCell = keyCellIter.next();
        keyList.add(keyCell.getStringCellValue());
    }
    
    // 데이터를 읽어서 List<HashMap<String, String>>에 저장
    List<HashMap<String, String>> dataList = new ArrayList<>();
    for (int i = 2; i <= sheet.getLastRowNum(); i++) {
        Row dataRow = sheet.getRow(i);
        HashMap<String, String> dataMap = new HashMap<>();
        for (int j = 0; j < keyList.size(); j++) {
            Cell dataCell = dataRow.getCell(j);
            String dataValue = "";
            if (dataCell != null) {
                dataValue = getCellValueToString(dataCell);
            }
            dataMap.put(keyList.get(j), dataValue);
        }
        dataList.add(dataMap);
    }
    
    if (gbn.equals("bsns")) {
      // BSNS TEMP 테이블에 저장
      // BSNS TEMP 테이블에서 오류와 함께 조회한결과 리턴
      for (var data : dataList) {
          BsnsTempDto dto = createObject(BsnsTempDto.class, data);
          String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
          dto.setUploadDe(today);
          dto.setUploadFileNm(path.replace("C:\\uploads\\", ""));
          dto.setExcelRowNum(dataList.indexOf(data)+2);
          bsnsService.registBsnsTemp(dto);
      }
    } 
    return dataList;
  }
  
  private static <T> T createObject(Class<T> classType, HashMap<String, String> data) throws Exception {
    T obj = classType.getDeclaredConstructor().newInstance();
    for (Field field : classType.getDeclaredFields()) {
        String name = field.getName();
        Object value = data.get(name);
        String type = field.getType().getSimpleName();
        
        if (value != null) {
          if(type == "int") {
            value = Integer.parseInt((String) value);
          } else if (type == "long") {
            value = Long.parseLong((String) value);
          } else if (type == "double") {
            value = Double.parseDouble((String) value);
          } else if (type == "float") {
            value = Float.parseFloat((String) value);
          } else {
            value = (String) value;
          }
          field.setAccessible(true);
          field.set(obj, value);
        }
    }
    return obj;
  }

  private String getCellValueToString(Cell cell) {
    int cellType = cell.getCellType();
    String cellValue = "";
    switch (cellType) {
        case Cell.CELL_TYPE_STRING:
            cellValue = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                cellValue = cell.getDateCellValue().toString();
            } else {
                //소숫점 제거
                cellValue = String.valueOf(Math.round(cell.getNumericCellValue()));
            }
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            cellValue = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_FORMULA:
            cellValue = cell.getCellFormula();
            break;
        case Cell.CELL_TYPE_BLANK:
            cellValue = "";
            break;
        default:
            cellValue = "";
    }
    return cellValue;
  }
  
  
}
