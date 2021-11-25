package dev.yhp.study.last_bbs.vos.user;

import dev.yhp.study.last_bbs.enums.user.RegisterResult;
import dev.yhp.study.last_bbs.interfaces.IResult;
import dev.yhp.study.last_bbs.utils.CryptoUtil;

public class RegisterVo implements IResult<RegisterResult> {
    private final String email;
    private final String password;
    private final String nickname;
    private final String nameFirst;         // 이름
    private final String nameOptional;      // (중간 이름)
    private final String nameLast;          // 성
    private final String contactFirst;      // 010
    private final String contactSecond;     // 1234
    private final String contactThird;      // 5678
    private final String addressPost;       // 우편번호
    private final String addressPrimary;    // 기본 주소
    private final String addressSecondary;  // 상세 주소
    private final String hashedPassword;

    private RegisterResult result;

    public RegisterVo(String email, String password, String nickname, String nameFirst, String nameOptional, String nameLast, String contactFirst, String contactSecond, String contactThird, String addressPost, String addressPrimary, String addressSecondary) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.nameFirst = nameFirst;
        this.nameOptional = nameOptional;
        this.nameLast = nameLast;
        this.contactFirst = contactFirst;
        this.contactSecond = contactSecond;
        this.contactThird = contactThird;
        this.addressPost = addressPost;
        this.addressPrimary = addressPrimary;
        this.addressSecondary = addressSecondary;
        this.hashedPassword = CryptoUtil.Sha512.hash(password, null);
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getNameFirst() {
        return this.nameFirst;
    }

    public String getNameOptional() {
        return this.nameOptional;
    }

    public String getNameLast() {
        return this.nameLast;
    }

    public String getContactFirst() {
        return this.contactFirst;
    }

    public String getContactSecond() {
        return this.contactSecond;
    }

    public String getContactThird() {
        return this.contactThird;
    }

    public String getAddressPost() {
        return this.addressPost;
    }

    public String getAddressPrimary() {
        return this.addressPrimary;
    }

    public String getAddressSecondary() {
        return this.addressSecondary;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    @Override
    public RegisterResult getResult() {
        return this.result;
    }

    @Override
    public String getResultName() {
        return this.result == null ? null : this.result.name();
    }

    @Override
    public void setResult(RegisterResult registerResult) {
        this.result = registerResult;
    }
}