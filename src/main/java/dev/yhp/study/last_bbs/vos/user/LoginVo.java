package dev.yhp.study.last_bbs.vos.user;

import dev.yhp.study.last_bbs.dtos.UserDto;
import dev.yhp.study.last_bbs.enums.user.LoginResult;
import dev.yhp.study.last_bbs.interfaces.IResult;
import dev.yhp.study.last_bbs.utils.CryptoUtil;

public class LoginVo implements IResult<LoginResult> {
    private final String email;
    private final String password;
    private final String hashedPassword;

    private boolean autoSign;
    private LoginResult result;
    private UserDto user;

    public LoginVo(String email, String password) {
        this.email = email;
        this.password = password;
        this.hashedPassword = CryptoUtil.Sha512.hash(password, null);
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public boolean isAutoSign() {
        return this.autoSign;
    }

    public void setAutoSign(boolean autoSign) {
        this.autoSign = autoSign;
    }

    @Override
    public LoginResult getResult() {
        return this.result;
    }

    @Override
    public String getResultName() {
        return this.result.name();
    }

    @Override
    public void setResult(LoginResult loginResult) {
        this.result = loginResult;
    }

    public UserDto getUser() {
        return this.user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}