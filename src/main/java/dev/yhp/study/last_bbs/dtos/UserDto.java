package dev.yhp.study.last_bbs.dtos;

public class UserDto {
    public static final String MODEL_NAME = "user";

    private final int index;
    private final String email;
    private final String password;
    private final String nickname;
    private final String nameFirst;
    private final String nameOptional;
    private final String nameLast;
    private final String contactFirst;
    private final String contactSecond;
    private final String contactThird;
    private final String addressPost;
    private final String addressPrimary;
    private final String addressSecondary;
    private final int level;

    private String autoSignKey;

    public UserDto(int index, String email, String password, String nickname, String nameFirst, String nameOptional, String nameLast, String contactFirst, String contactSecond, String contactThird, String addressPost, String addressPrimary, String addressSecondary, int level) {
        this.index = index;
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
        this.level = level;
    }

    public int getIndex() {
        return this.index;
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

    public int getLevel() {
        return this.level;
    }

    public String getAutoSignKey() {
        return this.autoSignKey;
    }

    public void setAutoSignKey(String autoSignKey) {
        this.autoSignKey = autoSignKey;
    }
}