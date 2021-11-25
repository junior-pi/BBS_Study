package dev.yhp.study.last_bbs.controllers;

import dev.yhp.study.last_bbs.dtos.UserDto;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class StandardController {
    @ModelAttribute(UserDto.MODEL_NAME)
    protected final UserDto userDto() {
        return null;
    }
}