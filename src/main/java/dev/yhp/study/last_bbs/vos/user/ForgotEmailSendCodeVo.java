package dev.yhp.study.last_bbs.vos.user;

import dev.yhp.study.last_bbs.enums.user.ForgotEmailSendCodeResult;
import dev.yhp.study.last_bbs.interfaces.IResult;

public class ForgotEmailSendCodeVo implements IResult<ForgotEmailSendCodeResult> {
    private final String nameFirst;
    private final String nameOptional;
    private final String nameLast;
    private final String contactFirst;
    private final String contactSecond;
    private final String contactThird;

    private String key;
    private String ip;
    private ForgotEmailSendCodeResult result;

    public ForgotEmailSendCodeVo(String nameFirst, String nameOptional, String nameLast, String contactFirst, String contactSecond, String contactThird) {
        this.nameFirst = nameFirst;
        this.nameOptional = nameOptional;
        this.nameLast = nameLast;
        this.contactFirst = contactFirst;
        this.contactSecond = contactSecond;
        this.contactThird = contactThird;
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

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public ForgotEmailSendCodeResult getResult() {
        return this.result;
    }

    @Override
    public String getResultName() {
        return this.result.name();
    }

    @Override
    public void setResult(ForgotEmailSendCodeResult forgotEmailSendCodeResult) {
        this.result = forgotEmailSendCodeResult;
    }
}