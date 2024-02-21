<%@page import="project.vo.CustomerVO"%>
<%@page import="java.util.List"%>
<%@page import="project.dao.TblCustomerDao"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>13_jdbc_jstl</title>
<style type="text/css">
	table{width:700px;}
	td{border:1px solid gray; padding:5px;}
</style>
</head>
<body>
	<h3>2번 소스파일 스크립트릿과 출력식을 jstl과 el로 변경하기</h3>
	<hr>
		<p>TblCustomerDao 메소드로 db접속 및 조회 결과 출력하는 연습입니다.</p>
	<hr>
<%
	TblCustomerDao dao = new TblCustomerDao();
	List<CustomerVO> list = dao.allCustomers();
	
	pageContext.setAttribute("list", list);
%>  
	<table>
	<c:forEach items="${list }" var="vo" varStatus="status">
	<c:out value="${status.index }" />, <c:out value="${vo }" />
	
	<tr>
		<td><c:out value="${status.index + 1}"/></td>
		<td><c:out value="${fn:toUpperCase(vo.customId)}"/></td>
		<td><c:out value="${vo.name }"/></td>
		<td><c:out value="${vo.email }"/></td>
		<td><c:out value="${vo.age }"/></td>
		<td><fmt:formatDate value="${vo.reg_date }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>

	</c:forEach>
	</table>
</body>
</html>