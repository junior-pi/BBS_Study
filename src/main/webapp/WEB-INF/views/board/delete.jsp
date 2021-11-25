<%@ page import="dev.yhp.study.last_bbs.enums.board.DeleteResult" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>게시글 삭제</title>
    <script>
        <c:if test="${vo.result == DeleteResult.NOT_AUTHORIZED}">
        alert('해당 게시글을 삭제할 권한이 없습니다.');
        window.history.back();
        </c:if>
        <c:if test="${vo.result == DeleteResult.NO_SUCH_ARTICLE}">
        alert('해당 게시글을 찾을 수 없습니다.');
        window.history.back();
        </c:if>
        <c:if test="${vo.result == DeleteResult.OKAY}">
        alert('해당 게시글을 삭제하였습니다.');
        window.location.href='/board/list/${vo.board.id}';
        </c:if>
    </script>
</head>
</html>