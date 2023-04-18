package com.anyfive.erndtransfer.domain.test.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TestDto {
    @NonNull
    private String test1;
    private String test2;
    private String test3;
}