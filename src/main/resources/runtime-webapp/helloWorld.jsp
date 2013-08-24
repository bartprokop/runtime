<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head><title>Hello :: Spring Application</title></head>
  <body>
    <h1>Hello - Spring Application</h1>
    <p>springRuntimeServiceInfo.hello() is <c:out value="${message}"/></p>
    <p>applicationContext.getBean(SpringRuntimeServiceInfo.class) == springRuntimeServiceInfo is <c:out value="${check}"/></p>
  </body>
</html>