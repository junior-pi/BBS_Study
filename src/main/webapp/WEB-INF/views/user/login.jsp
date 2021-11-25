<%@ page import="dev.yhp.study.last_bbs.enums.user.LoginResult" %>
<%@ page import="dev.yhp.study.last_bbs.services.UserService" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>로그인</title>
    <script src="resources/scripts/login.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jsp" %>
<main>
    <form id="login-form" method="post">
        <label>
            <span hidden>이메일</span>
            <input autofocus maxlength="50" name="email" placeholder="이메일" type="email" value="${vo.email}"
                   data-regex="<%=UserService.Regex.EMAIL%>">
        </label>
        <label>
            <span hidden>비밀번호</span>
            <input maxlength="50" name="password" placeholder="비밀번호" type="password"
                   data-regex="<%=UserService.Regex.PASSWORD%>">
        </label>
        <label>
            <input name="autoSign" type="checkbox">
            <span>자동 로그인</span>
        </label>
        <input type="submit" value="로그인">
    </form>
    <div>
        <a href="register">아직 계정이 없으신가요?</a>
    </div>
    <div>
        <a href="forgot-email">이메일을 잊으셨나요?</a>
    </div>
    <div>
        <a href="forgot-password">비밀번호를 잊으셨나요?</a>
    </div>
</main>
<%@ include file="/WEB-INF/parts/footer.jsp" %>
<script>
    ${vo.result == LoginResult.FAILURE ? "alert('이메일 혹은 비밀번호가 올바르지 않습니다.');" : ""}
    ${vo.result == LoginResult.UNAVAILABLE ? "alert('해당 계정은 현재 이용할 수 없습니다.');" : ""}
</script>
</body>
</html>