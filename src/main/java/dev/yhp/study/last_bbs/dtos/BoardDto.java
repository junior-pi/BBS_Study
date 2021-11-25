package dev.yhp.study.last_bbs.dtos;

public class BoardDto {
    private final int index;
    private final String id;
    private final String name;
    private final int levelList;
    private final int levelRead;
    private final int levelWrite;
    private final int levelComment;

    public BoardDto(int index, String id, String name, int levelList, int levelRead, int levelWrite, int levelComment) {
        this.index = index;
        this.id = id;
        this.name = name;
        this.levelList = levelList;
        this.levelRead = levelRead;
        this.levelWrite = levelWrite;
        this.levelComment = levelComment;
    }

    public int getIndex() {
        return this.index;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getLevelList() {
        return this.levelList;
    }

    public int getLevelRead() {
        return this.levelRead;
    }

    public int getLevelWrite() {
        return this.levelWrite;
    }

    public int getLevelComment() {
        return this.levelComment;
    }
}