<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Study system</title>
</head>
<body>
<h2>Study system</h2>
<div class="groups-form" style="display: flex">
    <div>
        <h2>Add group</h2>
        <form method="post" action="${pageContext.request.contextPath}/groups">
            <div class="form-group">
                <label for="groupName" class="control-label">Name: </label>
                <input id="groupName" class="form-control" type="text"
                       name="groupName"/>
            </div>
            <div class="form-group">
                <label for="course" class="control-label">Course: </label>
                <input id="course" class="form-control" type="text"
                       name="course"/>
            </div>
            <div class="form-group">
                <button id="button" type="submit" class="btn btn-success">Submit</button>
            </div>
        </form>
    </div>
    <div style="margin-left: 20px">
        <h2>Update group</h2>
        <form method="post" action="${pageContext.request.contextPath}/update?entity=group">
            <div class="form-group">
                <label for="updateGroupId" class="control-label">Id: </label>
                <input id="updateGroupId" class="form-control" type="number"
                       name="groupId"/>
            </div>
            <div class="form-group">
                <label for="updatedGroupName" class="control-label">Name: </label>
                <input id="updatedGroupName" class="form-control" type="text"
                       name="updatedGroupName"/>
            </div>
            <div class="form-group">
                <label for="updatedGroupCourse" class="control-label">Course: </label>
                <input id="updatedGroupCourse" class="form-control" type="text"
                       name="updatedGroupCourse"/>
            </div>
            <div class="form-group">
                <button id="updateButton" type="submit" class="btn btn-success">Submit</button>
            </div>
        </form>
    </div>
</div>
<div class="students-form" style="display: flex">
    <div>
        <h2>Add student</h2>
        <form method="post" action="${pageContext.request.contextPath}/students">
            <div class="form-group">
                <label for="firstName" class="control-label">First name: </label>
                <input id="firstName" class="form-control" type="text"
                       name="firstName"/>
            </div>
            <div class="form-group">
                <label for="lastName" class="control-label">Second name: </label>
                <input id="lastName" class="form-control" type="text"
                       name="lastName"/>
            </div>
            <div class="form-group">
                <label for="groupId" class="control-label">Group id: </label>
                <input id="groupId" class="form-control" type="text"
                       name="groupId"/>
            </div>
            <div class="form-group">
                <button id="buttonStudent" type="submit" class="btn btn-success">Submit</button>
            </div>
        </form>
    </div>
    <div style="margin-left: 20px">
        <h2>Update student</h2>
        <form method="post" action="${pageContext.request.contextPath}/update?entity=student">
            <div class="form-group">
                <label for="studentId" class="control-label">Id: </label>
                <input id="studentId" class="form-control" type="text"
                       name="studentId"/>
            </div>
            <div class="form-group">
                <label for="updatedFirstName" class="control-label">Name: </label>
                <input id="updatedFirstName" class="form-control" type="text"
                       name="updatedFirstName"/>
            </div>
            <div class="form-group">
                <label for="updatedLastName" class="control-label">Last name: </label>
                <input id="updatedLastName" class="form-control" type="text"
                       name="updatedLastName"/>
            </div>
            <div class="form-group">
                <label for="updatedGroupId" class="control-label">Group id: </label>
                <input id="updatedGroupId" class="form-control" type="text"
                       name="updatedGroupId"/>
            </div>
            <div class="form-group">
                <button id="buttonUpdateStudent" type="submit" class="btn btn-success">Submit</button>
            </div>
        </form>
    </div>
</div>
<div>
    <a href="${pageContext.request.contextPath}/students">Get all students</a>
    <a href="${pageContext.request.contextPath}/groups">Get all groups</a>
</div>
<div class="groups">
    <h2><c:out value="${groupsTitle}"/></h2>
    <c:forEach items="${groups}" var="group" varStatus="loop">
        <div>
            <h4><c:out value="${loop.index + 1}"/></h4>
            <p>Id: <c:out value="${group.getId()}"/></p>
            <p>Name: <c:out value="${group.getGroupName()}"/></p>
            <p>Course: <c:out value="${group.getCourse()}"/></p>
            <form method="post" action="${pageContext.request.contextPath}/delete?groupId=${group.getId()}">
                <input type="submit" value="delete">
            </form>
        </div>
    </c:forEach>
</div>
<div class="students">
    <h2><c:out value="${studentsTitle}"/></h2>
    <c:forEach items="${students}" var="student" varStatus="loop">
        <div>
            <h4><c:out value="${loop.index +1 }"/></h4>
            <p>Id: <c:out value="${student.getId()}"/></p>
            <p>First name: <c:out value="${student.getFirstName()}"/></p>
            <p>Last name: <c:out value="${student.getLastName()}"/></p>
            <p>Group id: <c:out value="${student.getGroupId()}"/></p>
            <form method="post" action="${pageContext.request.contextPath}/delete?studentId=${student.getId()}">
                <input type="submit" value="delete">
            </form>
        </div>
    </c:forEach>
</div>
</body>
</html>
