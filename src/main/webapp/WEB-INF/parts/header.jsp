<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <div>
        <c:if test="${user == null}">
            <span><b><a href="/user/login">로그인</a></b>해주세요.</span>
        </c:if>
        <c:if test="${user != null}">
            <span><b>${user.nickname}</b>님 환영합니다.</span>
            <span><a href="/user/modify">정보수정</a></span>
            <span><a href="/user/logout">로그아웃</a></span>
        </c:if>
    </div>
    <div>
        <ul>
            <c:forEach var="board" items="${boards}">
                <li>
                    <a href="/board/list/${board.id}" target="_self">${board.name}</a>
                </li>
            </c:forEach>
        </ul>
    </div>
</header>