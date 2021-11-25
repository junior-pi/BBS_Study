package dev.yhp.study.last_bbs.vos.user;

import dev.yhp.study.last_bbs.enums.user.ForgotPasswordSendCodeResult;
import dev.yhp.study.last_bbs.interfaces.IResult;

public class ForgotPasswordSendCodeVo implements IResult<ForgotPasswordSendCodeResult> {
    private final String email;
    private final String contactFirst;
    private final String contactSecond;
    private final String contactThird;

    private String ip;
    private String code;
    private String key;
    private ForgotPasswordSendCodeResult result;

    public ForgotPasswordSendCodeVo(String email, String contactFirst, String contactSecond, String contactThird) {
        this.email = email;
        this.contactFirst = contactFirst;
        this.contactSecond = contactSecond;
        this.contactThird = contactThird;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEmail() {
        return this.email;
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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public ForgotPasswordSendCodeResult getResult() {
        return this.result;
    }

    @Override
    public String getResultName() {
        return this.result.name();
    }

    @Override
    public void setResult(ForgotPasswordSendCodeResult forgotPasswordSendCodeResult) {
        this.result = forgotPasswordSendCodeResult;
    }
}