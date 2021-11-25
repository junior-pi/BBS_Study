package dev.yhp.study.last_bbs.vos.board;

import dev.yhp.study.last_bbs.dtos.BoardDto;
import dev.yhp.study.last_bbs.dtos.UserDto;
import dev.yhp.study.last_bbs.enums.board.DeleteResult;
import dev.yhp.study.last_bbs.interfaces.IResult;

public class DeleteVo implements IResult<DeleteResult> {
    private final int articleId;

    private BoardDto board;
    private UserDto user;
    private DeleteResult result;

    public DeleteVo(int articleId) {
        this.articleId = articleId;
    }

    public int getArticleId() {
        return this.articleId;
    }

    public BoardDto getBoard() {
        return this.board;
    }

    public void setBoard(BoardDto board) {
        this.board = board;
    }

    public UserDto getUser() {
        return this.user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public DeleteResult getResult() {
        return this.result;
    }

    @Override
    public String getResultName() {
        return this.result.name();
    }

    @Override
    public void setResult(DeleteResult readResult) {
        this.result = readResult;
    }
}