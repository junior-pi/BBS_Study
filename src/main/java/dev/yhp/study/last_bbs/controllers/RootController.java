package dev.yhp.study.last_bbs.controllers;

import dev.yhp.study.last_bbs.dtos.UserDto;
import dev.yhp.study.last_bbs.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping(value = "/")
@SessionAttributes(UserDto.MODEL_NAME)
public class RootController extends StandardController {
    @RequestMapping(
            value = "/",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String indexGet() {
        return "index";
    }
}