<%@ page import="java.util.List" %>
<%@ page import="com.example.pojo.Resume" %><%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/3/1
  Time: 22:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>简历编辑</title>
</head>
<body>
<%
    Resume resume = (Resume) request.getAttribute("resume");
%>
    <form action="<%=request.getContextPath()+"/resume/modify"%>" method="post">
        ID<input type="text" name="id" readonly="readonly" value="<%=resume.getId()%>"/><br/>
        地址<input type="text" name="address" value="<%=resume.getAddress()%>"/><br/>
        姓名<input type="text" name="name" value="<%=resume.getName()%>"/><br/>
        电话<input type="text" name="phone" value="<%=resume.getPhone()%>"/><br/>
        <input type="submit" value="提交"/>
    </form>
    <form action="<%=request.getContextPath()+"/resume/table"%>" method="get">
        <input type="submit" value="返回">
    </form>
</body>
</html>
