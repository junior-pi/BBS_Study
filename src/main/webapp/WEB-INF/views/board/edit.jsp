<%@ page import="dev.yhp.study.last_bbs.enums.board.EditResult" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>글 수정</title>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jsp" %>
<main>
    <form method="post">
        <label>
            <span hidden>제목</span>
            <input autofocus maxlength="100" name="title" type="text" placeholder="제목" value="${vo.article.title}">
        </label>
        <br>
        <label>
            <span hidden>내용</span>
            <textarea maxlength="10000" name="content" placeholder="내용">${vo.article.content}</textarea>
        </label>
        <br>
        <input type="submit" value="글 수정">
    </form>
    <a href="#" onclick="window.history.back();">돌아가기</a>
</main>
<%@ include file="/WEB-INF/parts/footer.jsp" %>
<script>
    <c:if test="${vo.result == EditResult.NOT_AUTHORIZED}">
    alert('그러지마라');
    </c:if>
    <c:if test="${vo.result == EditResult.NO_SUCH_ARTICLE}">
    alert('수정하려는 글이 더 이상 존재하지 않습니다.');
    window.history.back();
    </c:if>
</script>
</body>
</html>