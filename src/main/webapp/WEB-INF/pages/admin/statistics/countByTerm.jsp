<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<table class="table table-hover responsive">
    <thead>
        <tr>
            <th>分类名称</th>
            <th>访问次数</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${result}" var="record">
        <tr>
            <td>${record.TERMNAME}</td>
            <td>${record.COUNT}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>