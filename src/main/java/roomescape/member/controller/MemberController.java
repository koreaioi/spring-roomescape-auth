package roomescape.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.common.auth.annotation.AuthGuard;
import roomescape.common.auth.annotation.LoginMember;
import roomescape.member.controller.dto.request.MemberSaveDto;
import roomescape.member.controller.dto.response.MemberDetailDto;
import roomescape.member.domain.Member;
import roomescape.member.domain.Role;
import roomescape.member.service.MemberService;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<MemberDetailDto> register(@RequestBody MemberSaveDto dto) {
        Member member = memberService.register(dto.toCommand());
        MemberDetailDto responseData = MemberDetailDto.from(member);
        return ResponseEntity.ok(responseData);
    }

    @AuthGuard(roles = Role.MEMBER)
    @GetMapping("/test")
    public ResponseEntity<Void> test(@LoginMember Member loginMember) {
        System.out.println("loginMember.getName() = " + loginMember.getName());
        System.out.println("loginMember.getPassword() = " + loginMember.getPassword());
        return ResponseEntity.ok().build();
    }

}
