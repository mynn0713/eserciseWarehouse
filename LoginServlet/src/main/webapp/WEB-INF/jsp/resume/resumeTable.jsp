<%@ page import="java.util.List" %>
<%@ page import="com.lagou.edu.pojo.Resume" %><%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/3/1
  Time: 22:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>简历列表</title>
</head>
<body>
    <form action="/resume/addView" method="post"><input type="submit" value="新增"/></form>
    <table>
        <thead>简历列表</thead>
        <tbody>
        <%
            List<Resume> resumeList = (java.util.List<Resume>) request.getAttribute("resumeList");
        %>
                <% for (Resume resume : resumeList) { %>
            <tr>
                    <td><input type="hidden" value="<%=resume.getId()%>"><%= resume.getId() %></td>
                    <td><%= resume.getAddress() %></td>
                    <td><%= resume.getName() %></td>
                    <td><%= resume.getPhone() %></td>
                    <td><form action="/resume/resumeModifyView" method="get">
                        <input type="hidden" name="id" value="<%=resume.getId()%>">
                        <input type="submit" value="编辑">
                    </form></td>
                    <td><form action="/resume/delete" method="get">
                        <input type="hidden" name="id" value="<%=resume.getId()%>">
                        <input type="submit" value="删除">
                    </form></td><br/>
            </tr>
                <% } %>
            </tr>
        </tbody>
    </table>
</body>
</html>
