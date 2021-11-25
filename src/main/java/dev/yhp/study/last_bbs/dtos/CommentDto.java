package dev.yhp.study.last_bbs.dtos;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentDto {
    private static class Format {
        public static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss";
    }

    private final int index;
    private final int articleId;
    private final String userEmail;
    private final Date timestamp;
    private final String content;
    private final String formattedTimestamp;

    public CommentDto(int index, int articleId, String userEmail, Date timestamp, String content) {
        this.index = index;
        this.articleId = articleId;
        this.userEmail = userEmail;
        this.timestamp = timestamp;
        this.content = content;
        this.formattedTimestamp = new SimpleDateFormat(Format.TIMESTAMP).format(timestamp);
    }

    public int getIndex() {
        return this.index;
    }

    public int getArticleId() {
        return this.articleId;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public String getContent() {
        return this.content;
    }

    public String getFormattedTimestamp() {
        return this.formattedTimestamp;
    }
}