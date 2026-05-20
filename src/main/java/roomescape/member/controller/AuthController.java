package roomescape.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.member.controller.dto.request.LoginDto;
import roomescape.member.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // TODO 응답 고도화
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto dto) {
        String token = authService.login(dto.toCommand());
        ResponseCookie cookie = ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
                .body(token);
    }

}
