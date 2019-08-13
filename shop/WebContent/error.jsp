<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>500 ERROR</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath }/css/main.css" />
</head>
<body>
  <div class="wrap">
    <div class="row_flex">
      <div class="message">
        <h1>
          <span>500</span>
          <br>
          Server error
        </h1>
        <p>看什么！！<a onclick="history.back()" style="cursor:pointer;text-decoration:underline">赶紧回去</a></p>
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