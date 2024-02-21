<%@page import="project.vo.CustomerVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10_JSTL and EL</title>
</head>
<body>
	<h3>JSTL 태그라이브러리 테스트</h3>
	<p>	기존에 사용하던 jsp의 스트립트릿, 출력식 기호를 대체합니다.
		이유는 요청처리하는 자바코드와 화면을 구성하는 html 태그를 분리할 때
		jsp파일을 태그형식으로만 작성하기 위함입니다.</p>
	<p> 태그 라이브러리는 맨 위 지시자에서 정의하고 사용합니다. EL기호 는 애트리뷰트 값만 출력할 수 있습니다.</p>
	<% String name = "sana";     //자바의 변수 %>
	<hr>
	<h4>c:으로 시작하는(prefix) core 태그</h4>
	<!-- 1. pageContext 영역에 `애트리뷰트` 이름 age, 값23을 저정합니다. -->
	<c:set var="age" value="23"/>
	
	<!-- 2. 저장된 애트리뷰트 값을 출력합니다. -->
	<h5>age : <c:out value="${age }"/></h5> <!-- age는 애트리뷰트 이름 -->
	<h5>name : <c:out value="${name }"/></h5> <!-- name은 자바변수. 출력안됨. -->
	
	<!-- 3. 자바의 if문을 대체하는 태그 입니다. -->
	<h5>c:if 테스트</h5>
	<!-- 조건식을 test 속성에 작성. EL 기호 안에 씁니다. -->
	<c:if test="${age <20 }">
		<div style="color:green">청소년입니다.</div>
	</c:if>
	<c:if test="${age >=20 }">
		<div style="color:red">성인입니다.</div>
	</c:if>
	
	<!-- 4. if~else 형식 -->
	<h5>c:choose 테스트</h5>
	<c:set var="age" value="23"/> <!-- 애트리뷰트 age값 변경하기 -->
	<c:choose>
		<c:when test="${age <20 }">
			<div style="color:green">청소년입니다.</div>
		</c:when>
		<c:otherwise>
			<div style="color:red">성인입니다.</div>
		</c:otherwise>
	</c:choose>
	<!-- 5.for문 -->
	<h5>c:forEach 테스트</h5>
	<c:forEach var="i" begin="21" end="30" varStatus="status" step="2">
	<!-- step 기본값1 -->
	<div>
		<c:out value="${i }"/>,s
		<c:out value="${i*11 }"/>,
		<!-- varstatus 속성은 for 반복 상태를 알 수 있는 값을 제공합니다. -->
		<c:out value="${status.count }"/>
		<c:out value="${status.step }"/>
	</div>
	</c:forEach>	
	<!-- 6. 자바 객체 활용하기 -->
	<h5>자바 객체 활용하기</h5>
	<%
		//서블릿을 활용하여 가져올 값이지만 여기서는 직접 정의합니다.
		CustomerVO vo = new CustomerVO ("momo22","강모모","momo22@daum.net",29,null);
		//만들어진 객체를 pageContext scope 의 애트리뷰트로 저장합니다.
		//애트리뷰터 이름 customer, 값은 vo
		pageContext.setAttribute("customer",vo);
		%>
		<c:out value="${customer }"/>
		<ul>
		<!-- 객체변수명. getXXX() getter메소드 사용합니다. 그러나 EL은
			 알아서 getter 동작을 합니다. 객체의 속성 이름을 정확히 작성하기
			 객체의 정의된 속성이름과 정확히 일치하지 않으면 오류입니다.
		 -->
		<li>${customer.customId}</li>
		<li>${customer.name}</li>
		<li>${customer.email}</li>
		<li>${customer.age}</li>
		
		</ul>
	
	
	
	
	
</body>
</html>