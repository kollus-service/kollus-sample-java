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
			<div class="row">
				<a href="/" alt="HOME"> <span><h1>
							Kollus Example Page <small>for Jsp</small>
						</h1> </span></a>
			</div>
			<div class="row">
				<h4>Drm Callback</h4>
			</div>
		</div>
		<div class="container-fluid">
			<div class="col-md-4 col-xs-4 col-lg-4">
				<div class="panel panel-default" id="pn_dc">
					<div class="panel-heading" id="ph_dc">
						<h4>Drm Callback</h4>
					</div>
					<div class="panel-body" id="pb_dc">
						<h4>Kind 1</h4>
							<div class="input-group">
								<span class="input-group-addon">result </span> <input type="number" id="tb_drm_k1_result" class="form-control" value="1" min="0" max="1">
							</div>
							<div class="input-group">
								<span class="input-group-addon">expiration_date (s) </span> <input type="number" id="tb_drm_k1_expiration_date" class="form-control" value="3600" min="0">
							</div>
							<div class="input-group">
								<span class="input-group-addon">expiration_count (횟수) </span> <input type="number" id="tb_drm_k1_expiration_count" class="form-control" value="0" min="0">
							</div>
							<div class="input-group">
								<span class="input-group-addon">expiration_playtime (s) </span> <input type="number" id="tb_drm_k1_expiration_playtime" class="form-control" value="0" min="0">
							</div>
							<div class="input-group">
								<span class="input-group-addon">message </span> <input type="text" id="tb_drm_k1_message" class="form-control" value="">
							</div>
							<div class="input-group">
								<span class="input-group-addon">expiration_refresh_popup </span> <input type="number" id="tb_drm_k1_expiration_refresh_popup" class="form-control" value="0" min="0" max="1">
							</div>
							<div class="input-group">
								<span class="input-group-addon">vmcheck </span> <input type="number" id="tb_drm_k1_vmcheck" class="form-control" value="0" min="0" max="1">
							</div>
							<div class="input-group">
								<span class="input-group-addon">check_abuse </span> <input type="number" id="tb_drm_k1_check_abuse" class="form-control" value="0" min="0" max="1">
							</div>
							<h4>Kind 2</h4>
							<div class="input-group">
								<span class="input-group-addon">result </span> <input type="number" id="tb_drm_k2_result" class="form-control" value="1" min="0" max="1">
							</div>
							<div class="input-group">
								<span class="input-group-addon">message </span> <input type="text" id="tb_drm_k2_message" class="form-control" value="">
							</div>
							<div class="input-group">
								<span class="input-group-addon">content_delete</span> <input type="number" id="tb_drm_k2_content_delete" class="form-control" value="0" min="0" max="1">
							</div>
							<h4>Kind 3</h4>
							<div class="input-group">
								<span class="input-group-addon">result </span> <input type="number" id="tb_drm_k3_result" class="form-control" value="1" min="0" max="1">
							</div>
							<div class="input-group">
								<span class="input-group-addon">content_expired</span> <input type="number" id="tb_drm_k3_content_expired" class="form-control" value="0" min="0" max="1">
							</div>
							<div class="input-group">
								<span class="input-group-addon">content_delete</span> <input type="number" id="tb_drm_k3_content_delete" class="form-control" value="0" min="0" max="1">
							</div>
							<div class="input-group">
								<span class="input-group-addon">content_expire_reset</span> <input type="number" id="tb_drm_k3_content_expire_reset" class="form-control" value="0" min="0" max="1">
							</div>
							<div class="input-group">
								<span class="input-group-addon">expiration_date (s) </span> <input type="number" id="tb_drm_k3_expiration_date" class="form-control" value="3600" min="0">
							</div>
							<div class="input-group">
								<span class="input-group-addon">expiration_count (횟수) </span> <input type="number" id="tb_drm_k3_expiration_count" class="form-control" value="0" min="0">
							</div>
							<div class="input-group">
								<span class="input-group-addon">expiration_playtime (s) </span> <input type="number" id="tb_drm_k3_expiration_playtime" class="form-control" value="0" min="0">
							</div>
							<div class="input-group">
								<span class="input-group-addon">message </span> <input type="text" id="tb_drm_k3_message" class="form-control" value="">
							</div>
							<div class="input-group">
								<span class="input-group-addon">check_abuse </span> <input type="number" id="tb_drm_k3_check_abuse" class="form-control" value="0" min="0" max="1">
							</div>
					</div>
					<div class="panel-footer" id="ph_pc">
						<button type="button" class="btn btn-primary" id="btn_a">Accept</button>
						<button type="button" class="btn btn-primary" id="btn_c">Cancel</button>
						<label class="badge" id="lb_accpet">Not Configured</label>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-xs-3 col-lg-3">
				<div class="panel panel-default" id="pn_channel">
					<div class="panel-heading" id="ph_channel">
						<h4>Channel</h4>
					</div>
					<div class="panel-body" id="pb_channel">
						<select id="sb_channel" class="form-control info"></select>
						<ul id="ulChannel" class="media-list">
						</ul>
						<div class="holder"></div>
					</div>
				</div>
			</div>
			<div class="col-md-5 col-xs-5 col-lg-5">
				<div class="panel panel-default" id="pn_player">
					<div class="panel-heading" id="ph_player">
						<h4>Player</h4>
					</div>
					<div class="panel-body" id="pb_player">
						<div class="embed-responsive embed-responsive-4by3" style="border: 1px dotted #333;">
							<iframe id="player" class="embed-responsive-item" src=""> </iframe>
						</div>
					</div>
					<div class="panel-footer" id="pf_player">
						<div class="table-responsive">
							<table id="table_log" class="table">
								<thead>
									<tr>
										<th>Time</th>
										<th>Kind</th>
										<th>Type</th>
										<th class="col-md-2 col-xs-2 col-lg-2">Payload</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="static/external/jquery/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="static/external/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="static/external/jPages/jPages.js"></script>
	<script type="text/javascript" src="static/external/kollus/kollus.videogateway-controller.min.js"></script>
	<script type="text/javascript" src="static/js/common.js"></script>
	<script type="text/javascript" src="static/js/drmcallback.js"></script>
</body>
</html>