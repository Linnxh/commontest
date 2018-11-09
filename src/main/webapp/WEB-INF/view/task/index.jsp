<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--不加EL表达式不可用--%>
<%@ page isELIgnored="false" pageEncoding="utf-8" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<body>

<style>
    tbody td, thead th {
        padding: 10px;
    }
</style>

<h2>Hello World!s</h2>
<h2>${name}</h2>
<h2>${name2}</h2>
<h2><%= request.getAttribute("name") %>
</h2>
<h2><%= request.getAttribute("name2") %>
</h2>


<table border="1" style=" min-height: 25px; line-height: 25px; text-align: center; border-collapse: collapse;">
    <thead>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>请求地址</th>
        <th>执行时间</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody style="">
    <c:forEach var="item" items="${list2 }" varStatus="s">
        <tr>
            <td>${item.id} </td>
            <td>${item.name}</td>
            <td>${item.reqUrl}</td>
            <td>${item.execTime}</td>
            <td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>
                <button onclick="edit()">编辑</button>
                <button onclick="del()">删除</button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<script src="http://localhost:8090/commontest/js/jquery-1.8.0.min.js"></script>
<script>
    function edit() {

    }
</script>

</body>

</html>
