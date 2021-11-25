package dev.yhp.study.last_bbs.mappers;

import dev.yhp.study.last_bbs.dtos.ArticleDto;
import dev.yhp.study.last_bbs.dtos.BoardDto;
import dev.yhp.study.last_bbs.dtos.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface IBoardMapper {
    void deleteArticle(
            @Param("aid") int articleId);

    ArticleDto selectArticle(
            @Param("aid") int articleId);


    ArrayList<ArticleDto> selectArticles(
            @Param("bid") String bid,
            @Param("offset") int offset,
            @Param("limit") int limit);

    ArrayList<ArticleDto> selectArticlesByTitle(
            @Param("bid") String bid,
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("keyword") String keyword);

    int selectArticleCountByTitle(
            @Param("bid") String bid,
            @Param("keyword") String keyword);

    ArrayList<ArticleDto> selectArticlesByContent(
            @Param("bid") String bid,
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("keyword") String keyword);

    int selectArticleCountByContent(
            @Param("bid") String bid,
            @Param("keyword") String keyword);

    ArrayList<ArticleDto> selectArticlesByEmail(
            @Param("bid") String bid,
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("keyword") String keyword);

    int selectArticleCountByEmail(
            @Param("bid") String bid,
            @Param("keyword") String keyword);

    int selectArticleCount(
            @Param("bid") String bid);

    ArrayList<BoardDto> selectBoards();

    BoardDto selectBoard(
            @Param("id") String id);

    BoardDto selectBoardByArticle(
            @Param("aid") int articleId);

    ArrayList<CommentDto> selectComments(
            @Param("aid") int articleId);

    void insertArticle(
            @Param("bid") String bid,
            @Param("uid") String uid,
            @Param("title") String title,
            @Param("content") String content);

    void insertComment(
            @Param("aid") int aid,
            @Param("uid") String uid,
            @Param("content") String content);

    void updateArticle(
            @Param("aid") int aid,
            @Param("title") String title,
            @Param("content") String content);

    void updateArticleViewed(
            @Param("aid") int articleId);
}