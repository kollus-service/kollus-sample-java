<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title></title>
<link rel="stylesheet"
	href="static/external/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="static/external/bootstrap/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="static/external/jPages/jPages.css"
	type="text/css" media="all">
<link rel="stylesheet" href="static/css/main.css">
<style type="text/css">
.tab-pane {
	padding-left: 5px;
}

.noPadding {
	padding: 0;
}

.input-group {
	padding-left: 3px;
	padding-right: 3px;
}

textarea {
	width: 100%;
	resize: none;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="page-header">
			<div class="row">
				<a href="/"> <span><h1>
							Kollus Example Page <small>for Jsp</small>
						</h1> </span></a>
			</div>
			<div class="row">
				<h4>Player Controller for javascript</h4>
			</div>
		</div>

		<div class="container-fluid main">
			<div class="col-xs-4">
				<div class="panel panel-default" id="pn_paylod">
					<div class="panel-heading" id="ph_paylod">
						<h4>Channel</h4>
					</div>
					<div class="panel-body" id="pb_paylod">
						<select class="form-control info" id="sb_channel"></select>
						<div class="container-fluid">
						<ul id="ulChannel" class="media-list">
						</ul>
						</div>
						<div class="holder"></div>
					</div>
					<div class="panel-footer" id="pf_payload"></div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="panel panel-default" id="pn_player">
					<div class="panel-heading" id="ph_player">
						<h4>Player</h4>
					</div>
					<div class="panel-body" id="pb_player">
						<div class="embed-responsive embed-responsive-4by3"
							style="border: 1px dotted #333;">
							<iframe id="player" class="embed-responsive-item" src="">
							</iframe>
						</div>
					</div>
					<div class="panel-footer" id="pf_player">
					<h4>LMS {json_data} Payload </h4>
						<textarea rows="15" cols="40" id="tt_json"></textarea>
					</div>
				</div>
			</div>
			<div class="col-xs-4">
				<div class="panel panel-default">
					<div class="panel-heading">LMS Callback</div>
					<div id="divlms" class="panel-body">
						<ul class="nav nav-tabs nav-pills nav-justified"
							id="nav_controller">
							<li role="presentation" class="active"><a href="#all"
								data-toggle="tab">All Data</a></li>
							<li role="presentation"><a href="#user" data-toggle="tab">User
									Data</a></li>
						</ul>
						<div id="tabContent" class="tab-content">
							<div id="all" class="tab-pane fade active in">
								<div class="table-responsive">
									<table id="table_lms_props" class="table">
										<tr>
											<th>Prorperty</th>
											<th>Value</th>
										</tr>
										<tr>
											<td>MCK</td>
											<td id="td_mck"></td>
										</tr>
										<tr>
											<td>Count</td>
											<td id="td_count"></td>
										</tr>
										<tr>
											<td>Start_at</td>
											<td id="td_start_at"></td>
										</tr>
										<tr>
											<td>Real_playtime</td>
											<td id="td_real_playtime"></td>
										</tr>
										<tr>
											<td>playtime</td>
											<td id="td_playtime"></td>
										</tr>
										<tr>
											<td>Playtime_percent</td>
											<td id="td_playtime_percent"></td>
										</tr>
									</table>
								</div>
								<h4>Block Info</h4>
								<div id="div_block" class="table-responsive">
									<table id="table_block" class="table">
										<thead>
											<tr>
												<th>block</th>
												<th>b</th>
												<th>t</th>
												<th>p</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
								<h4>Session Info</h4>
								<div id="div_session" class="table-responsive">
									<table id="table_session" class="table">
										<thead>
											<tr>
												<th>block</th>
												<th>start</th>
												<th>play</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
							<div id="user" class="tab-pane fade">
								<div class="table-responsive">
									<table id="table_lms_users" class="table">
										<thead>
											<tr>
												<th>Prorperty</th>
												<th>Value</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript"
		src="static/external/jquery/jquery-3.2.1.min.js"></script>
	<script type="text/javascript"
		src="static/external/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="static/external/jPages/jPages.js"></script>
	<script type="text/javascript"
		src="static/external/kollus/kollus.videogateway-controller.min.js"></script>
	<script type="text/javascript" src="static/js/common.js"></script>
	<script type="text/javascript" src="static/js/lmscallback.js"></script>
</body>
</html>