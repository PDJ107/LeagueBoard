<%--
  Created by IntelliJ IDEA.
  User: dongju
  Date: 2021/06/23
  Time: 5:09 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <h1>로그인</h1>
  <div>
    <form action="/users/login" method="post">
      계   정 : <input type="text" name="account"><br>
      비밀번호 : <input type="text" name="password">
      <input type="submit" value="전송버튼">
    </form>
  </div>
  <div>
    <form action="board.jsp">
      <button>게시판 이동</button>
    </form>

  </div>
  </body>
</html>
