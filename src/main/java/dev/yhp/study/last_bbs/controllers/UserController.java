package dev.yhp.study.last_bbs.controllers;

import dev.yhp.study.last_bbs.dtos.UserDto;
import dev.yhp.study.last_bbs.enums.user.ForgotPasswordSendCodeResult;
import dev.yhp.study.last_bbs.enums.user.LoginResult;
import dev.yhp.study.last_bbs.enums.user.ModifyResult;
import dev.yhp.study.last_bbs.enums.user.RegisterResult;
import dev.yhp.study.last_bbs.services.BoardService;
import dev.yhp.study.last_bbs.services.UserService;
import dev.yhp.study.last_bbs.vos.user.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/user/")
@SessionAttributes(UserDto.MODEL_NAME)
public class UserController extends StandardController {
    private final UserService userService;
    private final BoardService boardService;

    @Autowired
    public UserController(UserService userService, BoardService boardService) {
        this.userService = userService;
        this.boardService = boardService;
    }

    @RequestMapping(
            value = "/login",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String loginGet(
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user) {
        if (user != null) {
            return "redirect:/";
        }
        return "user/login";
    }

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    public String loginPost(
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user,
            HttpServletResponse response,
            Model model,
            LoginVo loginVo) {
        if (user != null) {
            return "redirect:/";
        }
        this.userService.login(loginVo);
        if (loginVo.getResult() == LoginResult.SUCCESS) {
            if (loginVo.isAutoSign()) {
                this.userService.putAutoSignKey(loginVo.getUser());

                Cookie cookie = new Cookie("ask", loginVo.getUser().getAutoSignKey());
                cookie.setMaxAge(60 * 60 * 24 * UserService.Config.AUTO_SIGN_VALID_DAYS);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            model.addAttribute(UserDto.MODEL_NAME, loginVo.getUser());
            return "redirect:/";
        } else {
            model.addAttribute("vo", loginVo);
            return "user/login";
        }
    }

    @RequestMapping(
            value = "/logout",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String logoutGet(
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user,
            SessionStatus sessionStatus,
            HttpServletRequest request,
            HttpServletResponse response) {
        if (user != null) {
            Cookie autoSignKeyCookie = null;
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("ask")) {
                    autoSignKeyCookie = cookie;
                    break;
                }
            }
            if (autoSignKeyCookie != null) {
                this.userService.expireAutoSignKey(autoSignKeyCookie);
                autoSignKeyCookie.setMaxAge(0);
                autoSignKeyCookie.setPath("/");
                response.addCookie(autoSignKeyCookie);
            }
        }
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @RequestMapping(
            value = "/register",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String registerGet(
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user) {
        if (user != null) {
            return "redirect:/";
        }
        return "user/register";
    }

    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    public String registerPost(
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user,
            Model model,
            RegisterVo registerVo) {
        if (user != null) {
            return "redirect:/";
        }
        this.userService.register(registerVo);
        if (registerVo.getResult() == RegisterResult.SUCCESS) {
            return "user/register.success";
        } else {
            model.addAttribute("vo", registerVo);
            return "user/register";
        }
    }

    @RequestMapping(
            value = "/modify",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String modifyGet(
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user) {
        if (user == null) {
            return "redirect:/";
        }
        return "user/modify";
    }

    @RequestMapping(
            value = "/modify",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    public String modifyPost(
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user,
            SessionStatus sessionStatus,
            Model model,
            ModifyVo modifyVo) {
        if (user == null) {
            return "redirect:/";
        }
        modifyVo.setUser(user);
        this.userService.modify(modifyVo);
        if (modifyVo.getResult() == ModifyResult.SUCCESS) {
            sessionStatus.setComplete();
        }
        model.addAttribute("vo", modifyVo);
        return "user/modify";
    }

    @RequestMapping(
            value = "/forgot-email",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String forgotEmailGet(
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user) {
        if (user != null) {
            return "redirect:/";
        }
        return "user/forgot-email";
    }

    @ResponseBody
    @RequestMapping(
            value = "/forgot-email",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String forgotEmailPost(
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user,
            HttpServletRequest request,
            ForgotEmailVo forgotEmailVo) {
        if (user != null) {
            return null;
        }
        forgotEmailVo.setIp(request.getRemoteAddr());
        this.userService.findEmail(forgotEmailVo);
        return String.format("{\"email\":\"%s\"}", forgotEmailVo.getEmail() == null ? "" : forgotEmailVo.getEmail());
    }

    @ResponseBody
    @RequestMapping(
            value = "/forgot-email/send-code",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String forgotEmailSendCodePost(
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user,
            HttpServletRequest request,
            ForgotEmailSendCodeVo forgotEmailSendCodeVo) {
        if (user != null) {
            return null;
        }
        forgotEmailSendCodeVo.setIp(request.getRemoteAddr());
        this.userService.sendCode(forgotEmailSendCodeVo);

        JSONObject respJson = new JSONObject();
        respJson.put("result", forgotEmailSendCodeVo.getResultName().toLowerCase());
        respJson.put("key", forgotEmailSendCodeVo.getKey());
        return respJson.toString();
    }

    @RequestMapping(
            value = "/forgot-password",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String forgotPasswordGet(
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user) {
        if (user != null) {
            return "redirect:/";
        }
        return "user/forgot-password";
    }

    @ResponseBody
    @RequestMapping(
            value = "/forgot-password",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String forgotPasswordPost(
            HttpServletRequest request,
            ForgotPasswordVo vo,
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user) {
        if (user != null) {
            return null;
        }
        vo.setIp(request.getRemoteAddr());
        this.userService.resetPassword(vo);

        JSONObject respJson = new JSONObject();
        respJson.put("result", vo.getResultName().toLowerCase());
        return respJson.toString();
    }

    @ResponseBody
    @RequestMapping(
            value = "/forgot-password/send-code",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String forgotPasswordSendCodePost(
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user,
            HttpServletRequest request,
            ForgotPasswordSendCodeVo vo) {
        if (user != null) {
            return null;
        }
        vo.setIp(request.getRemoteAddr());
        this.userService.sendCode(vo);

        JSONObject respJson = new JSONObject();
        respJson.put("result", vo.getResultName().toLowerCase());
        if (vo.getResult() == ForgotPasswordSendCodeResult.SENT) {
            respJson.put("key", vo.getKey());
        }
        return respJson.toString();
    }
}

















