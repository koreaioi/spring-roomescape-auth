package roomescape.member.controller.dto.request;

import roomescape.member.service.dto.LoginCommand;

public record LoginDto(
        String name,
        String password
) {
    public LoginCommand toCommand() {
        return new LoginCommand(name, password);
    }
}
