package com.anyfive.erndtransfer.domain.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.anyfive.erndtransfer.domain.dto.ExcelDto;
import com.anyfive.erndtransfer.domain.dto.ExcelFileName;
import com.anyfive.erndtransfer.domain.dto.ExcelColumnName;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelWriter {

    private Workbook workbook;
    private Map<String, Object> data;
    private String fileName;

    public Workbook getWorkbook() {
      return workbook;
    }

    public void setWorkbook() {
      this.workbook = new XSSFWorkbook();
    }
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
        
    public String getFileName() {
        return fileName;
    }
    
    public void create() throws IOException {
        setFileName(mapToFileName());
        Sheet sheet = workbook.createSheet("sheet1");
        createHead(sheet, mapToHeadList());
        createBody(sheet, mapToBodyList());
    }

    private String mapToFileName() {
        return (String) data.get("filename");
    }

    @SuppressWarnings("unchecked")
    private List<String> mapToHeadList() {
        return (List<String>) data.get("head");
    }

    @SuppressWarnings("unchecked")
    private List<List<String>> mapToBodyList() {
        return (List<List<String>>) data.get("body");
    }

    private void setFileName(String fileName) {
      this.fileName = getFileExtension(fileName);
    }

    private String getFileExtension(String fileName) {
        if (workbook instanceof XSSFWorkbook) {
            fileName += ".xlsx";
        }
        if (workbook instanceof SXSSFWorkbook) {
            fileName += ".xlsx";
        }
        if (workbook instanceof HSSFWorkbook) {
            fileName += ".xls";
        }

        return fileName;
    }

    private void createHead(Sheet sheet, List<String> headList) {
        createRow(sheet, headList, 0);
    }

    private void createBody(Sheet sheet, List<List<String>> bodyList) {
        int rowSize = bodyList.size();
        for (int i = 0; i < rowSize; i++) {
            createRow(sheet, bodyList.get(i), i + 1);
        }
    }

    private void createRow(Sheet sheet, List<String> cellList, int rowNum) {
        int size = cellList.size();
        Row row = sheet.createRow(rowNum);
        CellStyle cellStyle = workbook.createCellStyle();
        
        //CellStyle 지정
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        
        //header인 경우, 가운데정렬 배경색지정
        if(rowNum == 0) {
          cellStyle.setAlignment(HorizontalAlignment.CENTER);
          cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
          cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        
        for (int i = 0; i < size; i++) {
          //header에 없는 column인 경우, cell 생성하지 않음.
          if(rowNum != 0) {
          Row headerRow = sheet.getRow(0);
          Cell headerCell = headerRow.getCell(i);
            if(headerCell == null || headerCell.getStringCellValue() == null || headerCell.getStringCellValue().equals("") ) {
              break;
            }
          }
          row.createCell(i).setCellValue(cellList.get(i));
          row.getCell(i).setCellStyle(cellStyle);
        }
    }
    

    public static Map<String, Object> createExcelData(List<? extends ExcelDto> data, Class<?> target) {
        Map<String, Object> excelData = new HashMap<>();
        excelData.put("filename", createFileName(target));
        excelData.put("head", createHeaderName(target));
        excelData.put("body", createBodyData(data));
        return excelData;
    }

    private static List<String> createHeaderName(Class<?> header) {
        List<String> headData = new ArrayList<>();
        for (Field field : header.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(ExcelColumnName.class)) {
                String headerName = field.getAnnotation(ExcelColumnName.class).headerName();
                if (headerName.equals("")) {
                    headData.add(field.getName());
                } else {
                    headData.add(headerName);
                }
            }
        }
        return headData;
    }

    private static String createFileName(Class<?> file) {
        if (file.isAnnotationPresent(ExcelFileName.class)) {
            String filename = file.getAnnotation(ExcelFileName.class).filename();
            return filename.equals("") ? file.getSimpleName() : filename;
        }
        throw new RuntimeException("excel filename not exist");
    }

    private static List<List<String>> createBodyData(List<? extends ExcelDto> dataList) {
        List<List<String>> bodyData = new ArrayList<>();
        dataList.forEach(v -> bodyData.add(v.mapToList()));
        return bodyData;
    }
}