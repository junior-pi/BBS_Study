package dev.yhp.study.last_bbs.vos.board;

public class SearchVo extends ListVo {
    private final String criteria;
    private final String keyword;

    public SearchVo(String boardId, int page, String criteria, String keyword) {
        super(boardId, page);
        this.criteria = criteria;
        this.keyword = keyword;
    }

    public String getCriteria() {
        return this.criteria;
    }

    public String getKeyword() {
        return this.keyword;
    }
}