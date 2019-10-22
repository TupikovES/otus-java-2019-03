package ru.otus.hw.webserver.dto.response;

import lombok.Getter;
import ru.otus.hw.webserver.dto.UserDto;
import ru.otus.hw.webserver.dto.response.BaseResponse;

import java.util.List;

@Getter
public class UserListResponse extends BaseResponse {

    private List<UserDto> users;

    public UserListResponse() {
    }

    public UserListResponse(Status status, List<UserDto> users) {
        super(status);
        this.users = users;
    }
}
