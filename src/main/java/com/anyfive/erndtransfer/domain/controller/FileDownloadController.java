package com.anyfive.erndtransfer.domain.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/file")
public class FileDownloadController {
    
    @GetMapping("/templates/download")
    public ResponseEntity<Resource> downloadFile(HttpServletRequest request) throws IOException, URISyntaxException {
        // 파일명을 파라미터로 전달
        String fileName = request.getParameter("fileName");
        
        // resources의 template 폴더 경로 
        URI uri = ClassLoader.getSystemResource("templates").toURI();
        String mainPath = Paths.get(uri).toString();
        
        Path path = Paths.get(mainPath + "/" + fileName);
        
        // Resource를 사용하여 파일을 로드합니다.
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        
        // 파일 다운로드를 위한 Response Headers 설정.
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        HttpHeaders headers = new HttpHeaders();
        //  response 시, 파일이 다운로드 되도록 설정.
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");
        
        // 파일을 다운로드
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.getFile().length())
                .body(resource);
    }
    
}
