<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
    <title>分页</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
  </head>
  
  <body>
  <s:if test="pictures.size()>0">
	<nav>
	<ul class="pagination">
		<s:if test="page.totalPage>6">
		<s:if test="page.currentPage==1">
			
		</s:if>
		<s:else>
		<li><a href="<%=request.getContextPath()%>/upload/listPicture.action?catalogid=${catalogid}&page.currentPage=${page.currentPage-1}" aria-label="Previous">
			<span aria-hidden="true">&laquo;</span>
		</a></li>
		</s:else>
			<s:if test="page.totalPage-page.currentPage>5">
				<s:iterator begin="page.currentPage" end="page.currentPage+5" var="p">
					<li <s:if test="#p==page.currentPage">class="active"</s:if>>
					<a href="<%=request.getContextPath()%>/upload/listPicture.action?catalogid=${catalogid}&page.currentPage=${p}">${p }</a> 
					</li>
				</s:iterator>
					<li class="disabled"><a href="#">...</a></li>
			</s:if>
			<s:else>
				<s:iterator begin="page.totalPage-5" end="page.totalPage" var="p">
					<li <s:if test="#p==page.currentPage">class="active"</s:if>>
					<a href="<%=request.getContextPath()%>/upload/listPicture.action?catalogid=${catalogid}&page.currentPage=${p}">${p }</a> 
					</li>
				</s:iterator>
			</s:else>
			<s:if test="page.currentPage>=page.totalPage">
			
			</s:if>
			<s:else>
				<li><a href="<%=request.getContextPath()%>/upload/listPicture.action?catalogid=${catalogid}&page.currentPage=${page.currentPage+1}" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
				</li>
			</s:else>
			</s:if>
	<!-- totalpage<6  -->
	<s:else>
		<s:if test="page.currentPage==1">
		</s:if>
		<s:else>
		<li><a href="<%=request.getContextPath()%>/upload/listPicture.action?catalogid=${catalogid}&page.currentPage=${page.currentPage-1}" aria-label="Previous">
			<span aria-hidden="true">&laquo;</span>
		</a></li>
		</s:else>
				<s:iterator begin="1" end="page.totalPage" var="p">
					<li <s:if test="#p==page.currentPage">class="active"</s:if>>
					<a href="<%=request.getContextPath()%>/upload/listPicture.action?catalogid=${catalogid}&page.currentPage=${p}">${p }</a> 
					</li>
				</s:iterator>
			<s:if test="page.currentPage>=page.totalPage">
			</s:if>
			<s:else>
				<li><a href="<%=request.getContextPath()%>/upload/listPicture.action?catalogid=${catalogid}&page.currentPage=${page.currentPage+1}" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
				</li>
			</s:else>
		</s:else>
	</ul>
	</nav>
</s:if>
  </body>
</html>
