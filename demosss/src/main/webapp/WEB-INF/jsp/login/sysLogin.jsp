<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/3/1
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="utf-8" isELIgnored="false"%>
<html>
<head>
    <title>登录页面</title>
</head>
<body>
    ${msg}
    <%System.out.println("=========>request.realpath"+request.getContextPath());%>
    <form action="<%=request.getContextPath()+"/sys/login"%>" method="post" >
        <div>用户名：<input type="text" name="userName" /><br/></div>
        <div>密码：<input type="password" name="passWord"><br/></div>
            <div>登录<input type="submit" value="登录"/><br/></div>
    </form>
</body>
</html>
