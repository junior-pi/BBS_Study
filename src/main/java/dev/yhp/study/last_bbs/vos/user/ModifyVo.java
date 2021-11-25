package dev.yhp.study.last_bbs.vos.user;

import dev.yhp.study.last_bbs.dtos.UserDto;
import dev.yhp.study.last_bbs.enums.user.ModifyResult;
import dev.yhp.study.last_bbs.interfaces.IResult;
import dev.yhp.study.last_bbs.utils.CryptoUtil;

public class ModifyVo implements IResult<ModifyResult> {
    private final String password;
    private final String passwordNew;
    private final String contactFirst;
    private final String contactSecond;
    private final String contactThird;
    private final String addressPost;
    private final String addressPrimary;
    private final String addressSecondary;
    private final String hashedPassword;
    private final String hashedPasswordNew;

    private UserDto user;
    private ModifyResult result;

    public ModifyVo(String password, String passwordNew, String contactFirst, String contactSecond, String contactThird, String addressPost, String addressPrimary, String addressSecondary) {
        this.password = password;
        this.passwordNew = passwordNew.equals("") ? password : passwordNew;
        this.contactFirst = contactFirst;
        this.contactSecond = contactSecond;
        this.contactThird = contactThird;
        this.addressPost = addressPost;
        this.addressPrimary = addressPrimary;
        this.addressSecondary = addressSecondary;
        this.hashedPassword = CryptoUtil.Sha512.hash(password, null);
        this.hashedPasswordNew = CryptoUtil.Sha512.hash(this.passwordNew, null);
    }

    public String getPassword() {
        return this.password;
    }

    public String getPasswordNew() {
        return this.passwordNew;
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

    public String getHashedPasswordNew() {
        return this.hashedPasswordNew;
    }

    public UserDto getUser() {
        return this.user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public ModifyResult getResult() {
        return this.result;
    }

    @Override
    public String getResultName() {
        return this.result.name();
    }

    @Override
    public void setResult(ModifyResult modifyResult) {
        this.result = modifyResult;
    }
}