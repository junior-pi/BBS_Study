<%@ page import="dev.yhp.study.last_bbs.services.UserService" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>이메일 분실</title>
    <script src="../resources/scripts/class.ajax.js"></script>
    <script src="resources/scripts/forgot-email.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/parts/header.jsp" %>
<main>
    <form id="forgotEmailForm">
        <label>
            <span hidden>이름</span>
            <input maxlength="10" name="nameFirst" placeholder="이름" type="text"
                   data-regex="<%=UserService.Regex.NAME_FIRST%>">
        </label>
        <label>
            <span hidden>중간 이름(선택)</span>
            <input maxlength="10" name="nameOptional" placeholder="중간 이름(선택)" type="text"
                   data-regex="<%=UserService.Regex.NAME_OPTIONAL%>">
        </label>
        <label>
            <span hidden>성</span>
            <input maxlength="10" name="nameLast" placeholder="성" type="text"
                   data-regex="<%=UserService.Regex.NAME_LAST%>">
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
        <input hidden name="key" type="hidden">
        <br>
        <input hidden name="submit" type="submit" value="이메일 찾기">
    </form>
</main>
<%@ include file="/WEB-INF/parts/footer.jsp" %>
</body>
</html>