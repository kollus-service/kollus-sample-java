<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Kollus Sample JSP</title>
<link rel="stylesheet" href="static/external/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="static/external/bootstrap/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="static/external/jPages/jPages.css" type="text/css" media="all">
<link rel="stylesheet" href="static/css/main.css">
</head>
<body>
	<div class="container-fluid">
		<div class="page-header">
			<a href="/" alt="Home"><h1>
					Kollus Example Page <small>for Jsp</small>
				</h1> </a>
		</div>
		<div class="navbar navbar-default">
			<div class="container-fluid">
				<form class="navbar-form navbar-left">
					<h3>사용자 정보</h3>
					<div class="form-group" id="fg_accessToken">
						<label for="tb_accessToken" class="control-label">Api 접근 토큰</label> <input id="tb_accessToken" type="text" class="form-control" value="" />
					</div>
					<div class="form-group" id="fg_accountKey">
						<label for="tb_accountKey" class="control-label">서비스 계정 키</label> <input id="tb_accountKey" type="text" class="form-control" value="" />
					</div>
					<div class="form-group" id="fg_secretKey">
						<label for="tb_secretKey" class="control-label">보안 키</label> <input id="tb_secretKey" type="text" class="form-control" value="" />
					</div>
					<div class="form-group" id="fg_userKey">
						<label for="tb_userKey" class="control-label">사용자키</label> <input id="tb_userKey" type="text" class="form-control" value="" />
					</div>
					<div class="btn-group">
						<button class="btn btn-primary" type="button" id="btn_loadInfo">정보 가져오기</button>
						<button class="btn btn-alert" type="button" id="btn_changeUser">사용자변경</button>
					</div>
				</form>
			</div>
		</div>
		<div id="indexLink" class="list-group hide">
			<a href="/total.jsp" class="list-group-item"> One Page Test</a> <a href="/jwt.jsp" class="list-group-item"> WebToken (JWT) Creator</a> <a href="/upload.jsp" class="list-group-item">Upload</a> <a href="/query.jsp" class="list-group-item">Video Gateway Query String</a> <a href="/player.jsp" class="list-group-item">Player Controller for javascript</a> <a href="/playcallback.jsp" class="list-group-item">Play Callback</a> <a href="/drmcallback.jsp" class="list-group-item">DRM Callback</a> <a href="/lmscallback.jsp" class="list-group-item">LMS Callback</a>
		</div>
	</div>
	<script type="text/javascript" src="static/external/jquery/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="static/external/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="static/external/jPages/jPages.js"></script>
	<script type="text/javascript" src="static/external/kollus/kollus.videogateway-controller.min.js"></script>
	<script type="text/javascript" src="static/js/common.js"></script>
	<script type="text/javascript" src="static/js/index.js"></script>
</body>
</html>