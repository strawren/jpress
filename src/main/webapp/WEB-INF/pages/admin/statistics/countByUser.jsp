<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<table class="table table-hover responsive">
    <thead>
        <tr>
            <th>用户名</th>
            <th>地址</th>
            <th>MAC</th>
            <th>登录时间</th>
            <th>分类</th>
            <th>商品编号</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${result}" var="record">
        <tr>
            <td>${record.USERNAME}</td>
            <td>${record.ADDRESS}</td>
            <td>${record.MAC}</td>
            <td>${record.REQTIME}</td>
            <td>${record.TERMNAME}</td>
            <td>${record.ITEMNO}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>