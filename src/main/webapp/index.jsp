<%--
  Created by IntelliJ IDEA.
  User: dongju
  Date: 2021/06/23
  Time: 5:09 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
  $(document).ready(function () {
    $('login-btn').click(function() {
      $.ajax({
        type : "POST",
        url : "/users/login",
        data : {"account":$('#login-account').val(),
          "password":$('#login-password').val()},
        success: function (data) {
          alert('로그인 성공');
          alert(data);
          location.reload();
        }, error: function() {
          alert('로그인 정보가 올바르지 않습니다.');
        }
      });
    });
  });
</script>
<html>
  <head>
    <title>로그인</title>
  </head>
  <body>
  <div class="form-group">
    <label>계정</label>
    <input type="text" name="account" id="login-account">
  </div>
  <div class="form-group">
    <label>비밀번호</label>
    <input type="text" name="password" id="login-password">
  </div>
  <button type="button" value="로그인" id="login-btn">
  <div>
    <form action="board">
      <button>게시판 이동</button>
    </form>

  </div>
  </body>
</html>
