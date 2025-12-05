package com.stockmanager.stockmanager_be.dto.response;

import com.stockmanager.stockmanager_be.type.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseEntityDto {

    private ResponseStatus status;
    private String message;
    private Object data;

    public ResponseEntityDto(ResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }


}
