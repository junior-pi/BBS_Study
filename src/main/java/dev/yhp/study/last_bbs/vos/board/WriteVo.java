package dev.yhp.study.last_bbs.vos.board;

import dev.yhp.study.last_bbs.dtos.BoardDto;
import dev.yhp.study.last_bbs.dtos.UserDto;
import dev.yhp.study.last_bbs.enums.board.WriteResult;
import dev.yhp.study.last_bbs.interfaces.IResult;

public class WriteVo implements IResult<WriteResult> {
    private final String title;
    private final String content;

    private UserDto user;
    private BoardDto board;
    private WriteResult result;

    public WriteVo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return this.title;
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

    public BoardDto getBoard() {
        return this.board;
    }

    public void setBoard(BoardDto board) {
        this.board = board;
    }

    @Override
    public WriteResult getResult() {
        return this.result;
    }

    @Override
    public String getResultName() {
        return this.result.name();
    }

    @Override
    public void setResult(WriteResult writeResult) {
        this.result = writeResult;
    }
}