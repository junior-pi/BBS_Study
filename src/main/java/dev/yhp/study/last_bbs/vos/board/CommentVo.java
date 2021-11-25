package dev.yhp.study.last_bbs.vos.board;

import dev.yhp.study.last_bbs.dtos.UserDto;

public class CommentVo {
    private final String content;

    private UserDto user;
    private int articleId;

    public CommentVo(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public UserDto getUser() {
        return this.user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public int getArticleId() {
        return this.articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}