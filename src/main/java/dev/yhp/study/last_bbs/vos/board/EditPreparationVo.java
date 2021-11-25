package dev.yhp.study.last_bbs.vos.board;

import dev.yhp.study.last_bbs.dtos.ArticleDto;
import dev.yhp.study.last_bbs.dtos.BoardDto;
import dev.yhp.study.last_bbs.dtos.UserDto;
import dev.yhp.study.last_bbs.enums.board.EditResult;
import dev.yhp.study.last_bbs.interfaces.IResult;

public class EditPreparationVo implements IResult<EditResult> {
    private final int articleId;

    private UserDto user;
    private ArticleDto article;
    private EditResult result;

    public EditPreparationVo(int articleId) {
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

    @Override
    public EditResult getResult() {
        return this.result;
    }

    @Override
    public String getResultName() {
        return this.result.name();
    }

    @Override
    public void setResult(EditResult readResult) {
        this.result = readResult;
    }
}