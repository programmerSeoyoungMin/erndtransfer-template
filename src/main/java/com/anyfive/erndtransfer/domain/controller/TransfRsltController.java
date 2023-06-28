package com.anyfive.erndtransfer.domain.controller;

import com.anyfive.erndtransfer.domain.dto.TrnsfRsltDto;
import com.anyfive.erndtransfer.domain.service.TrnsfRsltService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/trnsfRslt")
@RequiredArgsConstructor
public class TransfRsltController {
    private final TrnsfRsltService trnsfRsltService;
    @PostMapping("retriveTrnsfRsltList")
    public HashMap<String, Object> retriveTrnsfRsltList(@RequestBody TrnsfRsltDto trnsfRsltDto) {
        List<TrnsfRsltDto> retriveTrnsfRsltList = trnsfRsltService.retriveTrnsfRsltList(trnsfRsltDto);
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("totalCnt", retriveTrnsfRsltList.size());
        resultMap.put("trnsRsltList", retriveTrnsfRsltList);

        return resultMap;
    }
    @PostMapping("retriveTrnsfRsltDtl")
    public HashMap<String, Object> retriveTrnsfRsltDtl(@RequestBody TrnsfRsltDto trnsfRsltDto) {
        List<TrnsfRsltDto> retriveTrnsfRsltDtl = trnsfRsltService.retriveTrnsfRsltDtl(trnsfRsltDto);
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("trnsfRsltDtl", retriveTrnsfRsltDtl);

        return resultMap;
    }
    @PostMapping("retriveTrnsfErrDtl")
    public HashMap<String, Object> retriveTrnsfErrDtl(@RequestBody TrnsfRsltDto trnsfRsltDto) {
        TrnsfRsltDto retriveTrnsfErrDtl = trnsfRsltService.retriveTrnsfErrDtl(trnsfRsltDto);
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("trnsfErrDtl", retriveTrnsfErrDtl);

        return resultMap;
    }

}
