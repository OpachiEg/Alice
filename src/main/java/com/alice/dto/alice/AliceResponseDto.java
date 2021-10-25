package com.alice.dto.alice;

import com.alice.dto.alice.response.ResponseDto;
import lombok.Data;

@Data
public class AliceResponseDto {

    private ResponseDto response;
    private String version = "1.0";

}
