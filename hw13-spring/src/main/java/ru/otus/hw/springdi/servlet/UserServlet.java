package ru.otus.hw.springdi.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.otus.hw.springdi.domain.User;
import ru.otus.hw.springdi.dto.UserDto;
import ru.otus.hw.springdi.dto.response.BaseResponse;
import ru.otus.hw.springdi.dto.response.UserListResponse;
import ru.otus.hw.springdi.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@WebServlet("/users")
@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"ADMIN"}),
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "GET", rolesAllowed = {"ADMIN"}),
                @HttpMethodConstraint(value = "POST", rolesAllowed = {"ADMIN"})
        }
)
public class UserServlet extends HttpServlet {

    private final UserService userService;

    public UserServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<UserDto> userDtoList = userService.getAll()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
        String success = objectMapper.writeValueAsString(new UserListResponse(BaseResponse.Status.SUCCESS, userDtoList));

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();
        writer.println(success);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto = objectMapper.readValue(body, UserDto.class);
        if (userDto != null) {
            userService.save(convert(userDto));
        }
    }

    private UserDto convert(User user) {
        return new UserDto(user.getName(), user.getAge());
    }

    private User convert(UserDto userDto) {
        return new User(null, userDto.getName(), null, userDto.getName(), userDto.getAge(), null);
    }
}
