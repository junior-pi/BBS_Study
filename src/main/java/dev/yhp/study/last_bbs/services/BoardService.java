package dev.yhp.study.last_bbs.services;

import dev.yhp.study.last_bbs.dtos.ArticleDto;
import dev.yhp.study.last_bbs.dtos.BoardDto;
import dev.yhp.study.last_bbs.dtos.UserDto;
import dev.yhp.study.last_bbs.enums.board.*;
import dev.yhp.study.last_bbs.mappers.IBoardMapper;
import dev.yhp.study.last_bbs.vos.board.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BoardService {
    private static class Config {
        public static final int ARTICLES_PER_PAGE = 10;
        public static final int PAGE_RANGE = 5;
    }

    private final IBoardMapper boardMapper;

    @Autowired
    public BoardService(IBoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public static boolean isAllowedToComment(UserDto user, BoardDto board) {
        return (user == null ? 10 : user.getLevel()) <= board.getLevelComment();
    }

    public static boolean isAllowedToList(UserDto user, BoardDto board) {
        return (user == null ? 10 : user.getLevel()) <= board.getLevelList();
    }

    public static boolean isAllowedToRead(UserDto user, BoardDto board) {
        return (user == null ? 10 : user.getLevel()) <= board.getLevelRead();
    }

    public static boolean isAllowedToWrite(UserDto user, BoardDto board) {
        return (user == null ? 10 : user.getLevel()) <= board.getLevelWrite();
    }

    public void deleteArticle(DeleteVo deleteVo) {
        BoardDto board = this.getBoard(deleteVo.getArticleId());
        if (board == null) {
            deleteVo.setResult(DeleteResult.NO_SUCH_ARTICLE);
            return;
        }
        if (!BoardService.isAllowedToWrite(deleteVo.getUser(), board)) {
            deleteVo.setResult(DeleteResult.NOT_AUTHORIZED);
            return;
        }
        this.boardMapper.deleteArticle(deleteVo.getArticleId());
        deleteVo.setBoard(board);
        deleteVo.setResult(DeleteResult.OKAY);
    }

    public ArrayList<BoardDto> getBoards() {
        return this.boardMapper.selectBoards();
    }

    public BoardDto getBoard(String bid) {
        return this.boardMapper.selectBoard(bid);
    }

    public BoardDto getBoard(int aid) {
        return this.boardMapper.selectBoardByArticle(aid);
    }

    public void getArticles(ListVo listVo) {
        if (listVo.getBoard() == null) {
            listVo.setResult(ListResult.NO_SUCH_BOARD);
            return;
        }
        if (!BoardService.isAllowedToList(listVo.getUser(), listVo.getBoard())) {
            listVo.setResult(ListResult.NOT_AUTHORIZED);
            return;
        }

        int articleCount = this.boardMapper.selectArticleCount(listVo.getBoardId());
        int maxPage = articleCount / Config.ARTICLES_PER_PAGE + (articleCount % Config.ARTICLES_PER_PAGE == 0 ? 0 : 1);
        int leftPage = Math.max(1, listVo.getPage() - Config.PAGE_RANGE);
        int rightPage = Math.min(maxPage, listVo.getPage() + Config.PAGE_RANGE);
        listVo.setMaxPage(maxPage);
        listVo.setLeftPage(leftPage);
        listVo.setRightPage(rightPage);

        ArrayList<ArticleDto> articles = this.boardMapper.selectArticles(
                listVo.getBoardId(),
                Config.ARTICLES_PER_PAGE * (listVo.getPage() - 1),
                Config.ARTICLES_PER_PAGE);
        for(ArticleDto article : articles) {
            article.setComments( this.boardMapper.selectComments( article.getIndex()) );
        }

        listVo.setArticles(articles);
        listVo.setResult(ListResult.OKAY);
    }

    public void searchArticles(SearchVo searchVo) {
        if (searchVo.getBoard() == null) {
            searchVo.setResult(ListResult.NO_SUCH_BOARD);
            return;
        }
        if (!BoardService.isAllowedToList(searchVo.getUser(), searchVo.getBoard())) {
            searchVo.setResult(ListResult.NOT_AUTHORIZED);
            return;
        }

        int articleCount;
        switch (searchVo.getCriteria()) {
            case "content":
                articleCount = this.boardMapper.selectArticleCountByContent(searchVo.getBoardId(), searchVo.getKeyword());
                break;
            case "email":
                articleCount = this.boardMapper.selectArticleCountByEmail(searchVo.getBoardId(), searchVo.getKeyword());
                break;
            default:
                articleCount = this.boardMapper.selectArticleCountByTitle(searchVo.getBoardId(), searchVo.getKeyword());
        }
        int maxPage = articleCount / Config.ARTICLES_PER_PAGE + (articleCount % Config.ARTICLES_PER_PAGE == 0 ? 0 : 1);
        int leftPage = Math.max(1, searchVo.getPage() - Config.PAGE_RANGE);
        int rightPage = Math.min(maxPage, searchVo.getPage() + Config.PAGE_RANGE);
        searchVo.setMaxPage(maxPage);
        searchVo.setLeftPage(leftPage);
        searchVo.setRightPage(rightPage);

        ArrayList<ArticleDto> articles;
        switch (searchVo.getCriteria()) {
            case "content":
                articles = this.boardMapper.selectArticlesByContent(
                        searchVo.getBoardId(),
                        Config.ARTICLES_PER_PAGE * (searchVo.getPage() - 1),
                        Config.ARTICLES_PER_PAGE,
                        searchVo.getKeyword());
                break;
            case "email":
                articles = this.boardMapper.selectArticlesByEmail(
                        searchVo.getBoardId(),
                        Config.ARTICLES_PER_PAGE * (searchVo.getPage() - 1),
                        Config.ARTICLES_PER_PAGE,
                        searchVo.getKeyword());
                break;
            default:
                articles = this.boardMapper.selectArticlesByTitle(
                        searchVo.getBoardId(),
                        Config.ARTICLES_PER_PAGE * (searchVo.getPage() - 1),
                        Config.ARTICLES_PER_PAGE,
                        searchVo.getKeyword());
        }
        for(ArticleDto article : articles) {
            article.setComments( this.boardMapper.selectComments( article.getIndex()) );
        }

        searchVo.setArticles(articles);
        searchVo.setResult(ListResult.OKAY);
    }

    public void read(ReadVo readVo) {
        BoardDto board = this.getBoard(readVo.getArticleId());
        if (board == null) {
            readVo.setResult(ReadResult.NO_SUCH_ARTICLE);
            return;
        }
        if (!BoardService.isAllowedToRead(readVo.getUser(), board)) {
            readVo.setResult(ReadResult.NOT_AUTHORIZED);
            return;
        }
        this.boardMapper.updateArticleViewed(readVo.getArticleId());
        ArticleDto article = this.boardMapper.selectArticle(readVo.getArticleId());
        article.setComments( this.boardMapper.selectComments(readVo.getArticleId()) );
        readVo.setArticle(article);
        readVo.setBoard(board);
    }

    public void writeArticle(WriteVo writeVo) {
        if (writeVo.getBoard() == null) {
            writeVo.setResult(WriteResult.NO_SUCH_BOARD);
            return;
        }
        if (!BoardService.isAllowedToWrite(writeVo.getUser(), writeVo.getBoard())) {
            writeVo.setResult(WriteResult.NOT_AUTHORIZED);
            return;
        }
        this.boardMapper.insertArticle(
                writeVo.getBoard().getId(),
                writeVo.getUser().getEmail(),
                writeVo.getTitle(),
                writeVo.getContent());
        writeVo.setResult(WriteResult.OKAY);
    }

    public void writeComment(CommentVo commentVo) {
        BoardDto board = this.getBoard(commentVo.getArticleId());
        if (board == null || !BoardService.isAllowedToComment(commentVo.getUser(), board)) {
            return;
        }
        this.boardMapper.insertComment(
                commentVo.getArticleId(),
                commentVo.getUser().getEmail(),
                commentVo.getContent());
    }

    public void prepareEditing(EditPreparationVo editPreparationVo) {
        BoardDto board = this.getBoard(editPreparationVo.getArticleId());
        if (board == null) {
            editPreparationVo.setResult(EditResult.NO_SUCH_ARTICLE);
            return;
        }
        if (!BoardService.isAllowedToWrite(editPreparationVo.getUser(), board)) {
            editPreparationVo.setResult(EditResult.NOT_AUTHORIZED);
            return;
        }
        editPreparationVo.setArticle(this.boardMapper.selectArticle(editPreparationVo.getArticleId()));
        editPreparationVo.setResult(EditResult.OKAY);
    }

    public void editArticle(EditVo editVo) {
        BoardDto board = this.getBoard(editVo.getArticleId());
        if (board == null) {
            editVo.setResult(EditResult.NO_SUCH_ARTICLE);
            return;
        }
        if (!BoardService.isAllowedToWrite(editVo.getUser(), board)) {
            editVo.setResult(EditResult.NOT_AUTHORIZED);
            return;
        }
        this.boardMapper.updateArticle(
                editVo.getArticleId(),
                editVo.getTitle(),
                editVo.getContent());
        editVo.setResult(EditResult.OKAY);
    }
}