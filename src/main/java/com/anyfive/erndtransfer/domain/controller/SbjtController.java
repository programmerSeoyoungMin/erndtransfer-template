package com.anyfive.erndtransfer.domain.controller;

import com.anyfive.erndtransfer.domain.dto.BsnsDto;
import com.anyfive.erndtransfer.domain.dto.SbjtDto;
import com.anyfive.erndtransfer.domain.service.SbjtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/sbjt")
@RequiredArgsConstructor
public class SbjtController {
    final SbjtService sbjtService;
    @PostMapping("retriveSbjtList")
    public HashMap<String, Object> retriveSbjtList(@RequestBody SbjtDto sbjtDto) {
        List<SbjtDto> sbjtList = sbjtService.retriveSbjtList(sbjtDto);
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("totalCnt", sbjtList.size());
        resultMap.put("sbjtList", sbjtList);

        return resultMap;
    }

}
