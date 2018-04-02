<%@ taglib uri="http://java.sun.com/jsp/jstl/core"   prefix="c" %>
<%--不加EL表达式不可用--%>
<%@ page isELIgnored="false" %>
<html>
<body>
<h2>Hello World!s</h2>
<h2>${name}</h2>
<h2>${name2}</h2>
<h2><%= request.getAttribute("name") %></h2>
<h2><%= request.getAttribute("name2") %></h2>
</body>
</html>
