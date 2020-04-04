<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>简历新增</title>
</head>
<body>
<form action="<%=request.getContextPath()+"/resume/add"%>" method="post">
    <%--ID<input type="text" name="id" readonly="readonly" value="<%=resume.getId()%>"/><br/>--%>
    地址<input type="text" name="address"/><br/>
    姓名<input type="text" name="name"/><br/>
    电话<input type="text" name="phone"/><br/>
    <input type="submit" value="提交"/>
</form>
<form action="<%=request.getContextPath()+"/resume/table"%>" method="get">
    <input type="submit" value="返回">
</form>
</body>
</html>
