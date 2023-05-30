package com.anyfive.erndtransfer.domain.controller;

import com.anyfive.erndtransfer.domain.dto.AncmDto;
import com.anyfive.erndtransfer.domain.service.AncmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/ancm")
@RequiredArgsConstructor
public class AncmController {
    final AncmService ancmService;
    @PostMapping("retriveAncmList")
    public HashMap<String, Object> retriveAncmList(@RequestBody AncmDto ancmDto) {
        List<AncmDto> ancmList = ancmService.retriveAncmList(ancmDto);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("totalCnt", ancmList.size());
        resultMap.put("ancmList", ancmList);

        return resultMap;
    }

}
