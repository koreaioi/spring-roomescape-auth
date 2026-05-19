package roomescape.member.controller.dto.request;

import roomescape.member.service.dto.MemberSaveCommand;

public record MemberSaveDto(
        String name,
        String password
) {

    public MemberSaveCommand toCommand() {
        return new MemberSaveCommand(name, password);
    }

}
