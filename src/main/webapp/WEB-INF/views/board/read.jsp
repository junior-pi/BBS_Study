<%@ page import="dev.yhp.study.last_bbs.enums.board.ReadResult" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${vo.article.title}</title>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jsp" %>
<main>
    <table>
        <thead>
        <tr>
            <th>제목</th>
            <td colspan="5"><h1>${vo.article.title}</h1></td>
        </tr>
        <tr>
            <th>작성자</th>
            <td>${vo.article.userEmail}</td>
            <th>작성 일시</th>
            <td>${vo.article.formattedTimestamp}</td>
            <th>조회수</th>
            <td>${vo.article.view}</td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th>내용</th>
            <td colspan="5" style="background-color: #f0f0f0; padding: 1rem;">${vo.article.content}</td>
        </tr>
        <c:if test="${user.level <= vo.board.levelComment}">
        <tr>
            <th>댓글</th>
            <td colspan="5">
                <form method="post">
                    <label>
                        <span hidden>댓글</span>
                        <input maxlength="100" name="content" placeholder="댓글" type="text">
                    </label>
                    <input type="submit" value="작성">
                </form>
            </td>
        </tr>
        </c:if>
        <c:forEach var="comment" items="${vo.article.comments}">
        <tr>
            <td></td>
            <td>${comment.userEmail}</td>
            <td colspan="3">${comment.content}</td>
            <td>${comment.formattedTimestamp}</td>
        </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <td>
                <c:if test="${param.is.equals(\"1\")}">
                    <a href="/board/search/${vo.board.id}/${param.sp}?criteria=${param.sc}&keyword=${param.sk}">목록</a>
                </c:if>
                <c:if test="${!param.is.equals(\"1\")}">
                    <a href="/board/list/${vo.board.id}/${param.p}">목록</a>
                </c:if>
            </td>
            <td colspan="4"></td>
            <td>
                <c:if test="${user.level == 1 || user.email.equals(vo.article.userEmail)}">
                    <a href="/board/edit/${vo.article.index}" target="_self">수정</a>
                    <a href="/board/delete/${vo.article.index}" target="_self">삭제</a>
                </c:if>
            </td>
        </tr>
        </tfoot>
    </table>
</main>
<%@ include file="/WEB-INF/parts/footer.jsp" %>
<script>
    <c:if test="${vo.result == ReadResult.NOT_AUTHORIZED}">
    alert('해당 게시글을 읽을 권한이 없습니다.');
    window.history.back();
    </c:if>
    <c:if test="${vo.result == ReadResult.NO_SUCH_ARTICLE}">
    alert('해당 게시글을 찾을 수 없습니다.');
    window.history.back();
    </c:if>
</script>
</body>
</html>