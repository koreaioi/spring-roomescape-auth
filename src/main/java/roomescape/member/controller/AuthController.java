package roomescape.member.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Void> login(@RequestBody LoginDto dto, HttpServletResponse response) {
        String token = authService.login(dto.toCommand());
        response.addHeader("Authorization", token);
        return ResponseEntity.ok().build();
    }

}
