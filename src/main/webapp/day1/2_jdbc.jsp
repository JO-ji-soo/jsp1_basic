<%@page import="jdbc.OracleConnectionUtil"%>
<%@page import="java.sql.Connection"%>
<%@page import="project.vo.CustomerVO"%>
<%@page import="java.util.List"%>
<%@page import="project.dao.TblCustomerDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>2_JDBC</title>
</head>
<body>
	<h3>모든 고객을 조회하여 출력하기</h3>
	<p>TblCustomerDao 메소드로 db접속 및 조회 결과 출력하는 연습입니다.</p>
	<hr>
<%

	Connection conn = OracleConnectionUtil.getConnection();
    out.print(conn);
	TblCustomerDao dao = new TblCustomerDao();
	List<CustomerVO> list = dao.allCustomers();
%>
	<table>
<%
	for(CustomerVO vo : list) {
%>
	<tr>
		<td><%=vo.getCustomId() %></td>
		<td><%=vo.getName() %></td>
		<td><%=vo.getEmail() %></td>
		<td><%=vo.getAge() %></td>
		<td><%=vo.getReg_date() %></td>
	</tr>
	

<%
	}
%>
	
	</table>
</body>
</html>