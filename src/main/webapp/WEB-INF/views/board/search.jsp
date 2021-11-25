<%@ page import="dev.yhp.study.last_bbs.enums.board.ListResult" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${vo.result == ListResult.NO_SUCH_BOARD ? "존재하지 않는 게시판" : vo.board.name}</title>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jsp" %>
<main>
    <table>
        <caption
                style="text-align: left">${vo.result == ListResult.NO_SUCH_BOARD ? "존재하지 않는 게시판" : vo.board.name}</caption>
        <thead>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성 일시</th>
            <th>작성자</th>
            <th>조회수</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${vo.result == ListResult.NO_SUCH_BOARD}">
            <tr>
                <td colspan="5" style="padding: 2rem; text-align: center">존재하지 않는 게시판입니다.</td>
            </tr>
        </c:if>
        <c:if test="${vo.result == ListResult.NOT_AUTHORIZED}">
            <tr>
                <td colspan="5" style="padding: 2rem; text-align: center">게시판을 확인할 권한이 없습니다.</td>
            </tr>
        </c:if>
        <c:if test="${vo.result == ListResult.OKAY}">
            <c:if test="${vo.articles.size() == 0}">
                <tr>
                    <td colspan="5" style="padding: 2rem; text-align: center">검색 결과가 없습니다.</td>
                </tr>
            </c:if>
            <c:forEach var="article" items="${vo.articles}">
                <tr>
                    <td>${article.index}</td>
                    <td>
                        <a href="/board/read/${article.index}?is=1&sc=${vo.criteria}&sk=${vo.keyword}&sp=${vo.page}">${article.title}</a>
                        <a style="background-color: #a0a0a0; border-radius: 0.125rem; color:#ffffff; font-size:0.7rem; padding:0.125rem 0.25rem;">[${article.comments.size()}]</a>
                    </td>
                    <td>${article.formattedTimestamp}</td>
                    <td>${article.userEmail}</td>
                    <td>${article.view}</td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="5" style="padding: 1rem 0; text-align: center;">
                <form action="/board/search/${vo.boardId}" method="get">
                    <label>
                        <span hidden>기준</span>
                        <select name="criteria">
                            <option value="title" ${vo.criteria.equals("title") ? "selected" : ""}>제목</option>
                            <option value="content" ${vo.criteria.equals("content") ? "selected" : ""}>제목 + 내용</option>
                            <option value="email" ${vo.criteria.equals("email") ? "selected" : ""}>작성자</option>
                        </select>
                    </label>
                    <label>
                        <span hidden>키워드</span>
                        <input maxlength="10" name="keyword" placeholder="검색어" value="${vo.keyword}">
                    </label>
                    <input type="submit" value="검색">
                </form>
            </td>
        </tr>
        <tr>
            <td colspan="5" style="text-align: center">
                <c:if test="${vo.page > 1}">
                <span>
                    <a href="/board/search/${vo.board.id}/1?criteria=${vo.criteria}&keyword=${vo.keyword}" target="_self">&lt;&lt;</a>
                </span>
                </c:if>
                <c:forEach var="i" begin="${vo.leftPage}" end="${vo.rightPage}" step="1">
                    <c:if test="${i == vo.page}">
                    <span>
                        <a>${i}</a>
                    </span>
                    </c:if>
                    <c:if test="${i != vo.page}">
                    <span>
                        <a href="/board/search/${vo.board.id}/${i}?criteria=${vo.criteria}&keyword=${vo.keyword}" target="_self">${i}</a>
                    </span>
                    </c:if>
                </c:forEach>
                <c:if test="${vo.page < vo.maxPage}">
                <span>
                    <a href="/board/search/${vo.board.id}/${vo.maxPage}?criteria=${vo.criteria}&keyword=${vo.keyword}" target="_self">&gt;&gt;</a>
                </span>
                </c:if>
            </td>
        </tr>
        </tfoot>
    </table>
    <a href="/board/list/${vo.boardId}">검색 취소</a>
    <c:if test="${vo.board.levelWrite >= user.level}">
        <a href="/board/write/${vo.boardId}">글 작성</a>
    </c:if>
</main>
<%@ include file="/WEB-INF/parts/footer.jsp" %>
</body>
</html>