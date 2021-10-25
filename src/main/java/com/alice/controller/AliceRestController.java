package com.alice.controller;

import com.alice.dto.alice.AliceRequestDto;
import com.alice.dto.alice.AliceResponseDto;
import com.alice.service.AliceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/alice")
public class AliceRestController {

    private final AliceService aliceService;

    @PostMapping
    public AliceResponseDto returnValue(@RequestBody AliceRequestDto aliceRequestDto) {
        return aliceService.returnValue(aliceRequestDto);
    }

}
