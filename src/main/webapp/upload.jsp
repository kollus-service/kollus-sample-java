<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Kollus Sample for Jsp</title>
<!-- css -->
<link rel="stylesheet" href="static/external/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="static/external/bootstrap/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="static/external/jPages/jPages.css" type="text/css" media="all">
<link rel="stylesheet" href="static/css/main.css">
</head>
<body>
	<!-- 메인 DIV -->
	<div class="container-fluid">
		<div class="page-header">
		<div class="row">
				<a href="/"> <span><h1>
							Kollus Example Page <small>for Jsp</small>
						</h1> </span></a>
			</div>
			<div class="row">
				<h4>Kollus HTTP Endpoint Upload</h4>
			</div>
		</div>
		<!-- 사용자정보 DIV -->
		<div class="container-fluid">
			<div class="col-md-6 col-xs-6 col-lg-6 col-md-offset-3 col-xs-offset-3 col-lg-offset-3">
				<div class="input-group">
					<span class="input-group-addon">API 접근 토큰 </span> <input type="text" class="form-control" id="tb_access_token" />
					<span class="input-group-btn">
					<button class="btn btn-default"  type="button" id="btn_category">카테고리 목록</button>
					</span>
				</div>
				<div class="input-group">
				<span class="input-group-addon">카테고리목록</span><select id="sb_category" class="form-control"></select>
				</div>
			</div>
			<div class="col-md-6 col-xs-6 col-lg-6">
				<h4>업로드 URL 가져오기</h4>
				<div class="input-group">
					<span class="input-group-addon">title</span> <input type="text" class="form-control" id="tb_upload_title" />
				</div>
				<div class="input-group">
					<span class="input-group-addon">expire_time</span> <input type="number" class="form-control" id="tb_upload_expire_time" value="600" />
				</div>
				<div class="checkbox">
					<label> <input id="is_encryption_upload" type="checkbox"> 암호화 업로드
					</label>
				</div>
				<div class="checkbox">
					<label> <input id="is_audio_upload" type="checkbox"> 음원 파일 업로드
					</label>
				</div>
				<div class="checkbox">
					<label> <input type="checkbox" disabled> 멀티파트 업로드
					</label>
				</div>
				<div class="btn-group">
					<button type="button" id="btnUploadUrl" class="btn btn-primary">Get Upload Url</button>
					<button type="button" id="btnCancelUrl" class="btn btn-default">Cancel</button>
				</div>
				<div id="uploadUrl" class="well"></div>
			</div>
			<div class="col-md-6 col-xs-6 col-lg-6">
				<h4>업로드 하기</h4>
				<form id="form_upload" class="form" action="" enctype="multipart/form-data" method="post">
					<div class="input-group">
						<span class="input-group-addon">return_url</span> <input type="text" name="return_url" class="form-control" id="return_url" />
					</div>
					<div class="input-group">
						<span class="input-group-addon">disable_alert</span> <input type="number" name="disable_alert" class="form-control" id="disable_alert" value="1" min="0" max="1" />
					</div>
					<div class="input-group">
						<span class="input-group-addon">redirection_scope</span> <input type="text" name="redirection_scope" class="form-control" id="redirection_scope" value="no" />
					</div>
					<div class="input-group">
						<span class="input-group-addon">accept</span> <input type="text" name="accept" class="form-control" id="accept" value="application/json" />
					</div>
					<div class="input-group">
						<span class="input-group-addon">upload_file</span> <input type="file" name="upload-file" class="form-control" id="tb_file" />
					</div>
					<div class="btn-group">
						<button type="submit" id="btnUpload" class="btn btn-primary">Upload</button>
						<button type="button" id="btnUploadCancel" class="btn btn-default">Cancel</button>
					</div>
				</form>
				<div class="progress">
					<div id="prog_upload" class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">0%</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="static/external/jquery/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="static/external/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="static/external/jPages/jPages.js"></script>
	<script type="text/javascript" src="static/external/kollus/kollus.videogateway-controller.min.js"></script>
	<script type="text/javascript" src="static/js/common.js"></script>
	<script type="text/javascript" src="static/js/upload.js"></script>
</body>
</html>