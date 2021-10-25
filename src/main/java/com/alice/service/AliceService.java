package com.alice.service;

import com.alice.dto.alice.AliceRequestDto;
import com.alice.dto.alice.AliceResponseDto;

public interface AliceService {

    AliceResponseDto returnValue(AliceRequestDto aliceRequestDto);

}

