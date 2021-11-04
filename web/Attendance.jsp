<%-- 
    Document   : Attendence
    Created on : Nov 3, 2021, 7:58:55 PM
    Author     : Tom
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script>

</script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Attendance</title>
    </head>
    <body>
        <form action="${pageContext.request.contextPath}/at" method="GET">
            <label for="days">Course: </label>
            <select name="cid" id="days">
                <option value="null">Choose day</option>
                <c:forEach items="${requestScope.courses}" var="course">
                    <option value="${course.cID}" ${requestScope.cid == course.cID ? "selected" : ""}>${course.date}</option>
                </c:forEach>
            </select>   
            <input type="submit" value="search">
        </form>
        <form action="${pageContext.request.contextPath}/at" method="POST" ${requestScope.students.size() == 0 ? "hidden" : ""}>
            <h1>Check attendance</h1>
            <input type="hidden" name="cid" value="${requestScope.cid}">
            <table border="1px">
                <tr>
                    <td>Name</td>
                    <td>Absent</td>
                    <td>Present</td>
                </tr>
                <c:if test="${requestScope.students.size() != 0}">
                    <c:forEach items="${requestScope.students}" var="student">
                        <tr>
                            <td><input type="hidden" name="sid" value="${student.id}">${student.name}</td>
                            <%--<c:set var="checked" value="${student.attendances[0].present}"></c:set>--%>
                            <td><input type="radio" name="${student.id}" value="absent" ${!student.attendances[0].present ? "checked" : ""}></td>
                            <td><input type="radio" name="${student.id}" value="attend"${student.attendances[0].present ? "checked" : ""}></td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
            <input type="submit" value="save">
        </form>
    </body>
</html>