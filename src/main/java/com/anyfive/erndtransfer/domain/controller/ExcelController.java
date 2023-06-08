package com.anyfive.erndtransfer.domain.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anyfive.erndtransfer.domain.dto.BsnsTempDto;
import com.anyfive.erndtransfer.domain.dto.ItepdTempDto;
import com.anyfive.erndtransfer.domain.dto.TrnsfMasterDto;
import com.anyfive.erndtransfer.domain.dto.TrnsfTempDto;
import com.anyfive.erndtransfer.domain.service.BsnsService;
import com.anyfive.erndtransfer.domain.service.ExcelService;
import com.anyfive.erndtransfer.domain.service.ItepdService;
import com.anyfive.erndtransfer.domain.service.TrnsfService;
import com.anyfive.erndtransfer.domain.util.ExcelWriter;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/excel")
@RequiredArgsConstructor
public class ExcelController<T> {
  
  private final ExcelService excelService;
  private final BsnsService bsnsService;
  private final ItepdService itepdService;
  private final TrnsfService trnsfService;

  
  @PostMapping("/upload")
  public HashMap<String,String> uploadExcelFile(@RequestParam("file") MultipartFile file, @RequestParam("category") String category) throws Exception{
    
    HashMap<String,String> resultMap = new HashMap<String,String>();
    // 업로드된 파일 처리 
    try {
      // 엑셀 파일을 읽어서 Temp Table에 저장
      int rowCnt = saveExcelFileTempTable(file, category);
    
      String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
      resultMap.put("result", "success");
      resultMap.put("uploadDe",today);
      resultMap.put("uploadFileNm", file.getOriginalFilename());
      resultMap.put("uploadRowCnt", String.valueOf(rowCnt));
      
    } catch (Exception e) {
      // 서식오류
      if(e.getMessage().equals("templateError")){
        resultMap.put("result", "templateError"); 
      // 데이터 없음
      } else if (e.getMessage().equals("sizeError")) {
        resultMap.put("result", "sizeError"); 
      // 기타
      } else {
        resultMap.put("result", "error");
      }
    } 
    
    return resultMap;  
  }

  private int saveExcelFileTempTable(MultipartFile file, String category) throws Exception {
    File convFile = new File( file.getOriginalFilename() );
    
    FileOutputStream fos = new FileOutputStream( convFile );
    fos.write( file.getBytes() );
    fos.close();
    
    
    // 1. 엑셀 파일을 읽어서 List<HashMap<String,String>>에 담기
    FileInputStream fis = new FileInputStream(convFile);
    Workbook workbook = WorkbookFactory.create(fis);
    Sheet sheet = workbook.getSheetAt(0);
    fis.close();
    
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
    
    if(dataList.size() == 0) {
      convFile.delete();
      throw new Exception("sizeError");
    }
    
    // 서식 체크
    boolean templateKeyCheck = false;
    if (category.equals("bsns")) {
      templateKeyCheck = checkTemplateKeyExist(BsnsTempDto.class, keyList);
    } else if(category.equals("itepd")) {
      templateKeyCheck = checkTemplateKeyExist(ItepdTempDto.class, keyList);
    } else if(category.equals("trnsfSbjt")) {
      templateKeyCheck = checkTemplateKeyExist(TrnsfTempDto.class, keyList);
    }
    if(!templateKeyCheck){
      convFile.delete();
      throw new Exception("templateError");
    }
    // category 별 저장
    String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    if (category.equals("bsns")) {
      // BSNS TEMP 테이블 데이터 삭제
      BsnsTempDto delDto = new BsnsTempDto();
      delDto.setUploadDe(today);
      delDto.setUploadFileNm(file.getOriginalFilename());
      bsnsService.deleteBsnsTemp(delDto);
      // BSNS TEMP 테이블에 저장
      for (var data : dataList) {
          BsnsTempDto dto = createObject(BsnsTempDto.class, data);
          dto.setUploadDe(today);
          dto.setUploadFileNm(file.getOriginalFilename());
          dto.setExcelRowNum(dataList.indexOf(data)+1);
          bsnsService.registBsnsTemp(dto);
      }
    }else if (category.equals("itepd")) {
      ItepdTempDto delDto = new ItepdTempDto();
      delDto.setUploadDe(today);
      delDto.setUploadFileNm(file.getOriginalFilename());
      itepdService.deleteItepdTemp(delDto);
      // ITEPD TEMP 테이블에 저장
      for(var data : dataList) {
        ItepdTempDto dto = createObject(ItepdTempDto.class, data);
        dto.setUploadDe(today);
        dto.setUploadFileNm(file.getOriginalFilename());
        dto.setExcelRowNum(dataList.indexOf(data)+1);
        itepdService.registItepdTemp(dto);
      }
    }else if(category.equals("trnsfSbjt")) {
      // TRNSF MASTER 테이블 저장
      TrnsfMasterDto trnsfMasterDto = new TrnsfMasterDto();
      trnsfMasterDto.setTrnsfSe("TE");
      trnsfMasterDto.setSbjtNocs(dataList.size());
      trnsfMasterDto.setUldFileNm(file.getOriginalFilename());
      String trnsfId = trnsfService.registTrnsfMaster(trnsfMasterDto);
      
      // TRNSF TEMP 테이블에 저장
      for(var data : dataList) {
        TrnsfTempDto dto = createObject(TrnsfTempDto.class, data);
        dto.setTrnsfId(trnsfId);
        trnsfService.registTrnsfTemp(dto);
      }
    }
    
    convFile.delete();
    
    return dataList.size();
  }
  // 엑셀 서식 체크 (업로드한 엑셀파일의 keyList의 값이 class field에 존재하는지 확인)
  private static boolean checkTemplateKeyExist(Class<?> classType, List<String> keyList) {
    boolean result = true;
    List<String> fieldNameList = new ArrayList<>();
    for (Field field : classType.getDeclaredFields()) {
      fieldNameList.add(field.getName()); 
    }
    for (String key : keyList) {
      if (!fieldNameList.contains(key)) {
          result = false;
      }
    }
    return result;
  }
  
  // 엑셀 데이터 class에 맞게 변환
  private static <T> T createObject(Class<T> classType, HashMap<String, String> data) throws Exception {
    T obj = classType.getDeclaredConstructor().newInstance();
    for (Field field : classType.getDeclaredFields()) {
        String name = field.getName();
        Object value = data.get(name);
        String type = field.getType().getSimpleName();
        
        if (value != null && !value.equals("") && value.toString().length() > 0) {
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
  
  // cell 데이터 to String
  private String getCellValueToString(Cell cell) {
    String cellValue = "";
    CellType cellType = cell.getCellType();
    
    switch (cellType) {
        case STRING:
            cellValue = cell.getStringCellValue();
            break;
        case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                cellValue = cell.getDateCellValue().toString();
            } else {
                //소숫점 제거
                cellValue = String.valueOf(Math.round(cell.getNumericCellValue()));
            }
            break;
        case BOOLEAN:
            cellValue = String.valueOf(cell.getBooleanCellValue());
            break;
        case FORMULA:
            cellValue = cell.getCellFormula();
            break;
        case BLANK:
            cellValue = "";
            break;
        default:
            cellValue = "";
    }
    return cellValue;
  }
  
  
  @PostMapping("/download")
  public ResponseEntity<Resource> excelDownload(@RequestBody HashMap<String,Object> paramObj) throws IOException {
    
    // 엑셀 데이터 생성 
    // ExcelService.java > excelDownload() : 업무별 데이터 생성하도록 함.
    Map<String, Object> excelData = excelService.excelDownload(paramObj);
    
    // 엑셀 Writer 생성
    ExcelWriter excelWriter = new ExcelWriter();
    excelWriter.setWorkbook();
    excelWriter.setData(excelData);
    excelWriter.create();

    Workbook workbook = excelWriter.getWorkbook();
    
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    workbook.write(out);
    workbook.close();
    out.close();
    byte[] data = out.toByteArray();
    
    ByteArrayResource resource = new ByteArrayResource(data);

    HttpHeaders headers = new HttpHeaders();
    //  response 시, 파일이 다운로드 되도록 설정.
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentLength(data.length);
    headers.setContentDispositionFormData("attachment", excelWriter.getFileName());
    
    return ResponseEntity.ok()
              .headers(headers)
              .body(resource);
    
  }
  
}
