<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>最終訪問日</h1>
<p>このページは、セッションに保存されている最終訪問日を出力します。</p>
<%
    if(request.getAttribute("lastVisit") == null){
%>
はじめてのログインです
<%
    } else {
%>
前回の訪問は <%= request.getAttribute("lastVisit")%> です。
<%
    }
%>
</body>
</html>
