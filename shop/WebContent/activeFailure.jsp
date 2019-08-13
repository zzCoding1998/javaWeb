<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>账户激活失败</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body>
  <div class="wrap">
    <div class="row_flex">
      <div class="message">
        <h1>
          <span>激活失败</span>
          <br>
         	 账户可能被删除了！
        </h1>
        <p>很抱歉！！<a onclick="${pageContext.request.contextPath}/register" style="cursor:pointer;text-decoration:underline">前往注册？？？</a></p>
      </div>
      <div class="scene">
        <img src="${pageContext.request.contextPath }/img/scene.png">
        <div class="charecter">
          <img src="${pageContext.request.contextPath }/img/charecter.png" alt="charecter">
          <div class="hand">
            <img src="${pageContext.request.contextPath }/img/charecter-4-hand-part.png" alt="hand">
          </div>
          <div class="eye">
            <img src="${pageContext.request.contextPath }/img/charecter-4-eye.gif" alt="eye">
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>