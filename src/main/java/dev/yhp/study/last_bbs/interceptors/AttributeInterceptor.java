package dev.yhp.study.last_bbs.interceptors;

import dev.yhp.study.last_bbs.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AttributeInterceptor implements HandlerInterceptor {
    private final BoardService boardService;

    @Autowired
    public AttributeInterceptor(BoardService boardService) {
        this.boardService = boardService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("boards", this.boardService.getBoards()); // ${} 자동 완성 미지원
        // = model.addAttribute("boards", this.boardService.getBoards()); // ${} 자동 완성 지원
        return true;
    }
}