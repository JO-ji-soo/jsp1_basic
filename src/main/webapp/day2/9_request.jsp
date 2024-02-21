<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<ul>
		<li>요청 URL : <%= request.getRequestURL() %></li>
		<li>요청 URL : <%= request.getRequestURI() %></li>
		<li>컨텍스트(context): <%= request.getContextPath() %></li>
		
		<li>클라이언트 ip : <%=request.getRemoteAddr() %></li>
		<li>클라이언트 host : <%=request.getRemoteHost() %></li>
		<li>클라이언트 port : <%=request.getRemotePort() %></li>
		<!-- 기준이 서버이므로 remote 멀리 떨어진 것이 클라이언트. local이 서버 -->
		<li>서버 ip : <%=request.getLocalAddr() %></li>
		<li>서버 Name : <%=request.getLocalName() %></li>
		<li>서버 포트 : <%=request.getLocalPort() %></li>
	</ul>
		<!-- 
			요청 URL : http://192.168.30.254:8088/jsp1/day2/9_request.jsp
			요청 URI : /jsp1/day2/9_request.jsp
			컨텍스트(context) : /jsp1				//이클립스에서 프로젝트 이름을 기본으로 설정합니다.
			클라이언트 ip :192.168.30.4
			클라이언트 host : 192.168.30.4
			클라이언트 포트 : 50662					//클라이언트에서 요청을 보내는 포트(임의값)
			서버 ip :192.168.30.254
			서버 Name : DESKTOP-HUJUVSS
			서버 포트 : 8088  						//서버의 특정 서비스 진입하는 포트
		-->
	
</body>
</html>