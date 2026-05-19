package roomescape.member.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PasswordTest {

    @Test
    @DisplayName("암호화 전, 비밀번호와 암호화 후, 비밀번호는 다르다.")
    void from() {
        // given
        String rawPassword = "1234";

        // when
        Password password = Password.from(rawPassword);
        String encryptedPassword = password.getValue();

        // then
        Assertions.assertThat(rawPassword)
                .isNotEqualTo(encryptedPassword);
    }

    @Test
    @DisplayName("암호화 전 비밀번호가 같으면, 암호화 비밀번호는 같다.")
    void same_encrytedPassword() {
        // given
        String rawPassword = "1234";
        Password password = Password.from(rawPassword);
        Password samePassword = Password.from(rawPassword);

        // when & then
        Assertions.assertThat(password.getValue())
                .isEqualTo(samePassword.getValue());
    }
}
