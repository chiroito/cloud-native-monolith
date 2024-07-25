<%--
  Created by IntelliJ IDEA.
  User: c_hir
  Date: 2024/07/08
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Monolith Application</title>
</head>
<body>
<h1>Monolith Application</h1>
<ul>
    <li><a href="/hello">パラメータのサーブレット</a></li>
    <li><a href="/last">セッションのサーブレット</a></li>
    <li><a href="/transaction?success=true">トランザクションのサーブレット（成功）</a></li>
    <li><a href="/transaction?success=false">トランザクションのサーブレット（失敗）</a></li>
</ul>
</body>
</html>
