package dev.yhp.study.last_bbs.controllers;

import dev.yhp.study.last_bbs.dtos.BoardDto;
import dev.yhp.study.last_bbs.dtos.UserDto;
import dev.yhp.study.last_bbs.enums.board.EditResult;
import dev.yhp.study.last_bbs.enums.board.WriteResult;
import dev.yhp.study.last_bbs.services.BoardService;
import dev.yhp.study.last_bbs.vos.board.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

// TODO : Search, Comment
@Controller
@RequestMapping(value = "/board/")
@SessionAttributes(UserDto.MODEL_NAME)
public class BoardController extends StandardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping(
            value = {"/list/{bid}", "/list/{bid}/{page}"},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String listGet(
            Model model,
            @PathVariable("bid") String boardId,
            @PathVariable("page") Optional<Integer> optionalPage,
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user) {
        int page = optionalPage.orElse(1);
        ListVo listVo = new ListVo(boardId, page);
        listVo.setUser(user);
        listVo.setBoard(this.boardService.getBoard(boardId));
        this.boardService.getArticles(listVo);
        model.addAttribute("vo", listVo);
        return "board/list";
    }

    @RequestMapping(
            value = {"/search/{bid}", "/search/{bid}/{page}"},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String searchGet(
            Model model,
            @RequestParam("criteria") String criteria,
            @RequestParam("keyword") String keyword,
            @PathVariable("bid") String boardId,
            @PathVariable("page") Optional<Integer> optionalPage,
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user) {
        int page = optionalPage.orElse(1);
        SearchVo searchVo = new SearchVo(boardId, page, criteria, keyword);
        searchVo.setUser(user);
        searchVo.setBoard(this.boardService.getBoard(boardId));
        this.boardService.searchArticles(searchVo);
        model.addAttribute("vo", searchVo);
        return "board/search";
    }

    @RequestMapping(
            value = "/write/{bid}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String writeGet(
            HttpServletResponse response,
            Model model,
            @PathVariable("bid") String boardId,
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user) {
        BoardDto board = this.boardService.getBoard(boardId);
        if (board == null || !BoardService.isAllowedToWrite(user, board)) {
            response.setStatus(404);
            return null;
        }
        return "board/write";
    }

    @RequestMapping(
            value = "/write/{bid}",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    public String writePost(
            HttpServletResponse response,
            WriteVo writeVo,
            Model model,
            @PathVariable("bid") String boardId,
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user) {
        writeVo.setBoard(this.boardService.getBoard(boardId));
        writeVo.setUser(user);
        this.boardService.writeArticle(writeVo);
        model.addAttribute("vo", writeVo);
        if (writeVo.getResult() == WriteResult.OKAY) {
            return "redirect:/board/list/" + boardId;
        } else {
            return "board/write";
        }
    }

    @RequestMapping(
            value = "/read/{aid}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String readGet(
            Model model,
            @PathVariable("aid") int articleId,
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user) {
        ReadVo vo = new ReadVo(articleId);
        vo.setUser(user);
        this.boardService.read(vo);
        model.addAttribute("vo", vo);
        return "board/read";
    }

    @RequestMapping(
            value = "/read/{aid}",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    public String readPost(
            Model model,
            CommentVo commentVo,
            @PathVariable("aid") int articleId,
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user) {
        commentVo.setArticleId(articleId);
        commentVo.setUser(user);
        this.boardService.writeComment(commentVo);
        //return this.readGet(model, articleId, user);
        //return "board/read";

        return "redirect:/board/read/" + articleId;
    }

    @RequestMapping(value = "/delete/{aid}")
    public String deleteGet(
            Model model,
            @PathVariable("aid") int articleId,
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user) {
        DeleteVo vo = new DeleteVo(articleId);
        vo.setUser(user);
        this.boardService.deleteArticle(vo);
        model.addAttribute("vo", vo);
        return "board/delete";
    }

    @RequestMapping(
            value = "/edit/{aid}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String editGet(
            Model model,
            @PathVariable("aid") int articleId,
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user) {
        EditPreparationVo vo = new EditPreparationVo(articleId);
        vo.setUser(user);
        this.boardService.prepareEditing(vo);
        model.addAttribute("vo", vo);
        return "board/edit";
    }

    @RequestMapping(
            value = "/edit/{aid}",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    public String editPost(
            Model model,
            EditVo editVo,
            @PathVariable("aid") int articleId,
            @ModelAttribute(UserDto.MODEL_NAME) UserDto user) {
        editVo.setArticleId(articleId);
        editVo.setUser(user);
        this.boardService.editArticle(editVo);
        if (editVo.getResult() == EditResult.OKAY) {
            return "redirect:/board/read/" + editVo.getArticleId();
        } else {
            model.addAttribute("vo", editVo);
            return "board/edit";
        }
    }
}