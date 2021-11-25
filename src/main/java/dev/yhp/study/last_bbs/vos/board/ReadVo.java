package dev.yhp.study.last_bbs.vos.board;

import dev.yhp.study.last_bbs.dtos.ArticleDto;
import dev.yhp.study.last_bbs.dtos.BoardDto;
import dev.yhp.study.last_bbs.dtos.UserDto;
import dev.yhp.study.last_bbs.enums.board.ReadResult;
import dev.yhp.study.last_bbs.interfaces.IResult;

public class ReadVo implements IResult<ReadResult> {
    private final int articleId;

    private UserDto user;
    private ArticleDto article;
    private BoardDto board;
    private ReadResult result;

    public ReadVo(int articleId) {
        this.articleId = articleId;
    }

    public int getArticleId() {
        return this.articleId;
    }

    public UserDto getUser() {
        return this.user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public ArticleDto getArticle() {
        return this.article;
    }

    public void setArticle(ArticleDto article) {
        this.article = article;
    }

    public BoardDto getBoard() {
        return this.board;
    }

    public void setBoard(BoardDto board) {
        this.board = board;
    }

    @Override
    public ReadResult getResult() {
        return this.result;
    }

    @Override
    public String getResultName() {
        return this.result.name();
    }

    @Override
    public void setResult(ReadResult readResult) {
        this.result = readResult;
    }
}