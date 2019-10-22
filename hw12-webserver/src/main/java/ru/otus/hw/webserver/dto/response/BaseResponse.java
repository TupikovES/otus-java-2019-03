package ru.otus.hw.webserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {

    public enum Status {
        SUCCESS,
        ERROR
    }

    private Status status;

}
