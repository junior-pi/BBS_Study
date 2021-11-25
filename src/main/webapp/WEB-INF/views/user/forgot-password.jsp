<%@ page import="dev.yhp.study.last_bbs.services.UserService" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>비밀번호 재설정</title>
    <script src="../resources/scripts/class.ajax.js"></script>
    <script src="resources/scripts/forgot-password.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jsp" %>
<main>
    <form id="forgotPasswordForm">
        <label>
            <span hidden>이메일</span>
            <input autofocus maxlength="50" name="email" placeholder="이메일" type="email"
                   data-regex="<%=UserService.Regex.EMAIL%>">
        </label>
        <br>
        <label>
            <span hidden>연락처</span>
            <select name="contactFirst">
                <option value="010" selected>010</option>
                <option value="070">070</option>
            </select>
        </label>
        <label>
            <span hidden>연락처(중간)</span>
            <input maxlength="4" name="contactSecond" placeholder="연락처(중간)" type="number"
                   data-regex="<%=UserService.Regex.CONTACT_SECOND%>">
        </label>
        <label>
            <span hidden>연락처(끝)</span>
            <input maxlength="4" name="contactThird" placeholder="연락처(끝)" type="number"
                   data-regex="<%=UserService.Regex.CONTACT_THIRD%>">
        </label>
        <input name="sendCodeButton" type="button" value="인증코드 전송">
        <br>
        <label hidden rel="authCode">
            <span hidden>인증코드</span>
            <input maxlength="6" name="authCode" placeholder="인증코드" type="number">
        </label>
        <br>
        <label hidden rel="password">
            <span hidden>비밀번호</span>
            <input maxlength="50" name="password" placeholder="비밀번호" type="password"
                   data-regex="<%=UserService.Regex.PASSWORD%>">
        </label>
        <label hidden rel="passwordCheck">
            <span hidden>비밀번호 재확인</span>
            <input maxlength="50" name="passwordCheck" placeholder="비밀번호 재확인" type="password">
        </label>
        <input hidden name="key" type="hidden">
        <br>
        <input hidden name="submit" type="submit" value="비밀번호 재설정">
    </form>
</main>
<%@ include file="/WEB-INF/parts/footer.jsp" %>
</body>
</html>