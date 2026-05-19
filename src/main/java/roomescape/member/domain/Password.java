package roomescape.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {

    private String value;

    public static Password from(String password) {
        return new Password(encrypt(password));
    }

    public static Password load(String encryptedPassword) {
        return new Password(encryptedPassword);
    }

    private static String encrypt(String password) {
        try {
            final String algorithm = "SHA-256";
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = password.getBytes();
            byte[] digest = md.digest(bytes);
            return convertHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(); // TODO
        }
    }

    public static String convertHex(byte[] rawHmac) {
        StringBuilder sb = new StringBuilder();
        for (byte byteData : rawHmac) {
            sb.append(String.format("%02x", byteData));
        }
        return sb.toString();
    }

    public void validateMatches(String password) {
        if (isNotMatches(password)) {
            throw new IllegalArgumentException(); // TODO
        }
    }

    private boolean isNotMatches(String password) {
        return !encrypt(password).equals(this.value);
    }
}
