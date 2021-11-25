package dev.yhp.study.last_bbs.vos.user;

public class ForgotEmailVo {
    private final String authCode;
    private final String key;

    private String ip;
    private String email;

    public ForgotEmailVo(String authCode, String key) {
        this.authCode = authCode;
        this.key = key;
    }

    public String getAuthCode() {
        return this.authCode;
    }

    public String getKey() {
        return this.key;
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

    public void setEmail(String email) {
        this.email = email;
    }
}