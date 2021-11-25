<%@ page import="dev.yhp.study.last_bbs.enums.board.WriteResult" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>글 작성</title>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jsp" %>
<main>
    <form method="post">
        <label>
            <span hidden>제목</span>
            <input autofocus maxlength="100" name="title" type="text" placeholder="제목" value="${vo.title}">
        </label>
        <br>
        <label>
            <span hidden>내용</span>
            <textarea maxlength="10000" name="content" placeholder="내용">${vo.content}</textarea>
        </label>
        <br>
        <input type="reset" value="초기화">
        <input type="submit" value="글 작성">
    </form>
    <a href="#" onclick="window.history.back();">돌아가기</a>
</main>
<%@ include file="/WEB-INF/parts/footer.jsp" %>
<script>
    <c:if test="${vo.result == WriteResult.NOT_AUTHORIZED}">
    alert('그러지마라');
    </c:if>
    <c:if test="${vo.result == WriteResult.NO_SUCH_BOARD}">
    alert('그러지마라');
    </c:if>
</script>
</body>
</html>