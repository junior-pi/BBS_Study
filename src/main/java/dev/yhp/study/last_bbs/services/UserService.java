package dev.yhp.study.last_bbs.services;

import dev.yhp.study.last_bbs.dtos.UserDto;
import dev.yhp.study.last_bbs.enums.user.*;
import dev.yhp.study.last_bbs.mappers.IUserMapper;
import dev.yhp.study.last_bbs.utils.CryptoUtil;
import dev.yhp.study.last_bbs.vos.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserService {
    public static class Config {
        public static final int AUTO_SIGN_KEY_HASH_COUNT = 10;
        public static final int AUTO_SIGN_VALID_DAYS = 7;

        public static final int AUTH_CODE_HASH_COUNT = 9;
        public static final int AUTH_CODE_VALID_MINUTES = 5;
    }

    public static class Regex {
        public static final String EMAIL = "^(?=.{8,50}$)([0-9a-z]([_]?[0-9a-z])*?)@([0-9a-z][0-9a-z\\-]*[0-9a-z]\\.)?([0-9a-z][0-9a-z\\-]*[0-9a-z])\\.([a-z]{2,15})(\\.[a-z]{2})?$";
        public static final String NICKNAME = "^([0-9a-zA-Z가-힣]{2,10})$";
        public static final String NAME_FIRST = "^([가-힣]{1,10})$";
        public static final String NAME_OPTIONAL = "^([가-힣]{0,10})$";
        public static final String NAME_LAST = "^([가-힣]{1,10})$";
        public static final String CONTACT_FIRST = "^(010|070)$";
        public static final String CONTACT_SECOND = "^([0-9]{4})$";
        public static final String CONTACT_THIRD = "^([0-9]{4})$";
        public static final String ADDRESS_POST = "^([0-9]{5})$";
        public static final String ADDRESS_PRIMARY = "^([0-9a-zA-Z가-힣\\- ]{10,100})$";
        public static final String ADDRESS_SECONDARY = "^([0-9a-zA-Z가-힣\\- ]{0,100})$";
        public static final String PASSWORD = "^([0-9a-zA-Z`~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:',<.>/?]{8,50})$";

        public static final String AUTO_SIGN_KEY = "^([0-9a-z]{128})$";

        public static final String AUTH_CODE_KEY = "^([0-9a-z]{128})$";
        public static final String AUTH_CODE = "^([0-9]{6})$";
    }

    private final IUserMapper userMapper;

    @Autowired
    public UserService(IUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public static boolean checkEmail(String email) {
        return email.matches(Regex.EMAIL);
    }

    public static boolean checkNickname(String nickname) {
        return nickname.matches(Regex.NICKNAME);
    }

    public static boolean checkNameFirst(String nameFirst) {
        return nameFirst.matches(Regex.NAME_FIRST);
    }

    public static boolean checkNameOptional(String nameOptional) {
        return nameOptional.matches(Regex.NAME_OPTIONAL);
    }

    public static boolean checkNameLast(String nameLast) {
        return nameLast.matches(Regex.NAME_LAST);
    }

    public static boolean checkContactFirst(String contactFirst) {
        return contactFirst.matches(Regex.CONTACT_FIRST);
    }

    public static boolean checkContactSecond(String contactSecond) {
        return contactSecond.matches(Regex.CONTACT_SECOND);
    }

    public static boolean checkContactThird(String contactThird) {
        return contactThird.matches(Regex.CONTACT_THIRD);
    }

    public static boolean checkAddressPost(String addressPost) {
        return addressPost.matches(Regex.ADDRESS_POST);
    }

    public static boolean checkAddressPrimary(String addressPrimary) {
        return addressPrimary.matches(Regex.ADDRESS_PRIMARY);
    }

    public static boolean checkAddressSecondary(String addressSecondary) {
        return addressSecondary.matches(Regex.ADDRESS_SECONDARY);
    }

    public static boolean checkPassword(String password) {
        return password.matches(Regex.PASSWORD);
    }

    public static boolean checkAuthCodeKey(String authCodeKey) {
        return authCodeKey.matches(Regex.AUTH_CODE_KEY);
    }

    public static boolean checkAuthCode(String authCode) {
        return authCode.matches(Regex.AUTH_CODE);
    }

    public void extendAutoSignKey(Cookie autoSignKeyCookie) {
        if (!autoSignKeyCookie.getValue().matches(Regex.AUTO_SIGN_KEY)) {
            return;
        }
        this.userMapper.updateAutoSingKeyExtended(autoSignKeyCookie.getValue(), Config.AUTO_SIGN_VALID_DAYS);
    }

    public void expireAutoSignKey(Cookie autoSignKeyCookie) {
        if (!autoSignKeyCookie.getValue().matches(Regex.AUTO_SIGN_KEY)) {
            return;
        }
        this.userMapper.updateAutoSignKeyExpired(autoSignKeyCookie.getValue());
    }

    public void login(LoginVo loginVo) {
        if (!UserService.checkEmail(loginVo.getEmail())) {
            loginVo.setResult(LoginResult.FAILURE);
            return;
        }
        UserDto user = this.userMapper.selectUser(loginVo);
        if (user == null) {
            loginVo.setResult(LoginResult.FAILURE);
            return;
        }
        if (user.getLevel() == 10) {
            loginVo.setResult(LoginResult.UNAVAILABLE);
            return;
        }
        loginVo.setResult(LoginResult.SUCCESS);
        loginVo.setUser(user);
    }

    public UserDto login(Cookie autoSignKeyCookie) {
        if (!autoSignKeyCookie.getValue().matches(Regex.AUTO_SIGN_KEY)) {
            return null;
        }
        UserDto user = this.userMapper.selectUserFromCookie(autoSignKeyCookie.getValue());
        if (user == null || user.getLevel() == 10) {
            return null;
        }
        return user;
    }

    public void putAutoSignKey(UserDto user) {
        String key = String.format("%s%s%s%f",
                user.getEmail(),
                user.getPassword(),
                new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()),
                Math.random());
        for (int i = 0; i < Config.AUTO_SIGN_KEY_HASH_COUNT; i++) {
            key = CryptoUtil.Sha512.hash(key, null);
        }
        this.userMapper.insertAutoSignKey(user.getEmail(), key, Config.AUTO_SIGN_VALID_DAYS);
        user.setAutoSignKey(key);
    }

    public void register(RegisterVo registerVo) {
        if (!UserService.checkEmail(registerVo.getEmail()) ||
                !UserService.checkNickname(registerVo.getNickname()) ||
                !UserService.checkNameFirst(registerVo.getNameFirst()) ||
                !UserService.checkNameOptional(registerVo.getNameOptional()) ||
                !UserService.checkNameLast(registerVo.getNameLast()) ||
                !UserService.checkContactFirst(registerVo.getContactFirst()) ||
                !UserService.checkContactSecond(registerVo.getContactSecond()) ||
                !UserService.checkContactThird(registerVo.getContactThird()) ||
                !UserService.checkAddressPost(registerVo.getAddressPost()) ||
                !UserService.checkAddressPrimary(registerVo.getAddressPrimary()) ||
                !UserService.checkAddressSecondary(registerVo.getAddressSecondary())) {
            registerVo.setResult(RegisterResult.FAILURE);
            return;
        }
        if (this.userMapper.selectEmailCount(registerVo.getEmail()) > 0) {
            registerVo.setResult(RegisterResult.DUPLICATE_EMAIL);
            return;
        }
        if (this.userMapper.selectNicknameCount(registerVo.getNickname()) > 0) {
            registerVo.setResult(RegisterResult.DUPLICATE_NICKNAME);
            return;
        }
        if (this.userMapper.selectContactCount(
                registerVo.getContactFirst(),
                registerVo.getContactSecond(),
                registerVo.getContactThird()) > 0) {
            registerVo.setResult(RegisterResult.DUPLICATE_CONTACT);
            return;
        }
        this.userMapper.insertUser(registerVo);
        registerVo.setResult(RegisterResult.SUCCESS);
    }

    public void modify(ModifyVo modifyVo) {
        if (!UserService.checkPassword(modifyVo.getPassword()) ||
                !UserService.checkPassword(modifyVo.getPasswordNew()) ||
                !UserService.checkContactFirst(modifyVo.getContactFirst()) ||
                !UserService.checkContactSecond(modifyVo.getContactSecond()) ||
                !UserService.checkContactThird(modifyVo.getContactThird()) ||
                !UserService.checkAddressPost(modifyVo.getAddressPost()) ||
                !UserService.checkAddressPrimary(modifyVo.getAddressPrimary()) ||
                !UserService.checkAddressSecondary(modifyVo.getAddressSecondary())) {
            modifyVo.setResult(ModifyResult.FAILURE);
            return;
        }
        if (!modifyVo.getUser().getPassword().equals(modifyVo.getHashedPassword())) {
            modifyVo.setResult(ModifyResult.INVALID_PASSWORD);
            return;
        }
        if ((!modifyVo.getUser().getContactFirst().equals(modifyVo.getContactFirst()) &&
                !modifyVo.getUser().getContactSecond().equals(modifyVo.getContactSecond()) &&
                !modifyVo.getUser().getContactThird().equals(modifyVo.getContactThird())) &&
                this.userMapper.selectContactCount(
                        modifyVo.getContactFirst(),
                        modifyVo.getContactSecond(),
                        modifyVo.getContactThird()) > 0) {
            modifyVo.setResult(ModifyResult.DUPLICATE_CONTACT);
            return;
        }
        this.userMapper.updateUser(modifyVo);
        modifyVo.setResult(ModifyResult.SUCCESS);
    }

    public void sendCode(ForgotEmailSendCodeVo forgotEmailSendCodeVo) {
        if (!UserService.checkNameFirst(forgotEmailSendCodeVo.getNameFirst()) ||
                !UserService.checkNameOptional(forgotEmailSendCodeVo.getNameOptional()) ||
                !UserService.checkNameLast(forgotEmailSendCodeVo.getNameLast()) ||
                !UserService.checkContactFirst(forgotEmailSendCodeVo.getContactFirst()) ||
                !UserService.checkContactSecond(forgotEmailSendCodeVo.getContactSecond()) ||
                !UserService.checkContactThird(forgotEmailSendCodeVo.getContactThird())) {
            forgotEmailSendCodeVo.setResult(ForgotEmailSendCodeResult.FAILURE);
            return;
        }
        String email = this.userMapper.selectEmail(forgotEmailSendCodeVo);
        if (email == null) {
            forgotEmailSendCodeVo.setResult(ForgotEmailSendCodeResult.FAILURE);
            return;
        }
        String code = String.valueOf((int) (Math.random() * Math.pow(10, 6))); //  1000000
        String key = String.format("%s|%s", email, code);
        for (int i = 0; i < Config.AUTH_CODE_HASH_COUNT; i++) {
            key = CryptoUtil.Sha512.hash(key, null);
        }
        this.userMapper.insertForgotEmailAuthCode(key, forgotEmailSendCodeVo.getIp(), email, code, Config.AUTH_CODE_VALID_MINUTES);
        forgotEmailSendCodeVo.setKey(key);
        forgotEmailSendCodeVo.setResult(ForgotEmailSendCodeResult.SENT);
    }

    public void findEmail(ForgotEmailVo forgotEmailVo) {
        if (!UserService.checkAuthCode(forgotEmailVo.getAuthCode()) ||
                !UserService.checkAuthCodeKey(forgotEmailVo.getKey())) {
            return;
        }
        String email = this.userMapper.selectEmailByAuthCodeFromEmail(
                forgotEmailVo.getAuthCode(),
                forgotEmailVo.getKey(),
                forgotEmailVo.getIp());
        if (email != null) {
            this.userMapper.updateEmailAuthCodeExpired(forgotEmailVo.getKey());
        }
        forgotEmailVo.setEmail(email);
    }

    public void sendCode(ForgotPasswordSendCodeVo vo) {
        if (!UserService.checkEmail(vo.getEmail()) ||
                !UserService.checkContactFirst(vo.getContactFirst()) ||
                !UserService.checkContactSecond(vo.getContactSecond()) ||
                !UserService.checkContactThird(vo.getContactThird())) {
            vo.setResult(ForgotPasswordSendCodeResult.FAILURE);
            return;
        }
        int userCount = this.userMapper.selectUserCount(
                vo.getEmail(),
                vo.getContactFirst(),
                vo.getContactSecond(),
                vo.getContactThird());
        if (userCount == 0) {
            vo.setResult(ForgotPasswordSendCodeResult.FAILURE);
            return;
        }
        String code = String.valueOf((int) (Math.random() * Math.pow(10, 6)));
        String key = String.format("%s|%s-%s-%s|%s%f",
                vo.getEmail(),
                vo.getContactFirst(),
                vo.getContactSecond(),
                vo.getContactThird(),
                code,
                Math.random());
        for (int i = 0; i < Config.AUTH_CODE_HASH_COUNT; i++) {
            key = CryptoUtil.Sha512.hash(key, null);
        }
        this.userMapper.insertForgotPasswordAuthCode(key, vo.getIp(), vo.getEmail(), code, Config.AUTH_CODE_VALID_MINUTES);
        vo.setCode(code);
        vo.setKey(key);
        vo.setResult(ForgotPasswordSendCodeResult.SENT);
    }

    public void resetPassword(ForgotPasswordVo vo) {
        if (!UserService.checkAuthCode(vo.getAuthCode()) ||
                !UserService.checkAuthCodeKey(vo.getKey())) {
            vo.setResult(ForgotPasswordResult.FAILURE);
            return;
        }
        String email = this.userMapper.selectEmailByAuthCodeFromPassword(
                vo.getAuthCode(),
                vo.getKey(),
                vo.getIp());
        if (email == null) {
            vo.setResult(ForgotPasswordResult.FAILURE);
            return;
        }
        this.userMapper.updatePasswordAuthCodeExpired(vo.getKey());
        this.userMapper.updateUserPassword(email, vo.getHashedPassword());
        vo.setResult(ForgotPasswordResult.SUCCESS);
    }
}




















