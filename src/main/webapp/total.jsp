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
				<h4>One Page Test</h4>
			</div>
		</div>
		<!-- 사용자정보 DIV -->
		<div class="container-fluid">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4>
						<a role="button" data-toggle="collapse" href="#panel_upload" aria-expanded="false" aria-controls="panel_upload">Upload</a>
					</h4>
				</div>
				<div id="panel_upload" class="panel-body collapse">
					<select id="sb_category"></select>
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
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4>
						<a role="button" data-toggle="collapse" href="#panel_media_list" aria-expanded="false" aria-controls="panel_media_list">Media List</a>
					</h4>
				</div>
				<div id="panel_media_list" class="panel-body collapse">
					<!-- 미디어 정보 -->
					<div class="col-xs-7 col-md-7 col-lg-7">
						<ul class="nav nav-tabs nav-pills nav-justified" id="nav_lib_channel">
							<li role="presentation" class="active"><a href="#library" data-toggle="tab">Library</a></li>
							<li role="presentation"><a href="#channel" data-toggle="tab">Channel</a></li>
						</ul>
						<div id="tabContent" class="tab-content">
							<div id="library" class="tab-pane fade active in">
								<!--  <label for="sb_library">라이브러리</label>-->
								<select id="sb_library" class="form-control">
								</select>
								<ul id="ulLibrary" class="media-list">
								</ul>
								<div class="holder"></div>
							</div>
							<div id="channel" class="tab-pane fade">
								<!-- <label for="sb_channel">채널</label>-->
								<select id="sb_channel" class="form-control">
								</select>
								<ul id="ulChannel" class="media-list">
								</ul>
								<div class="holder"></div>
							</div>
						</div>
					</div>
					<div class="col-xs-5 col-md-5 col-lg-5">
						<div class="embed-responsive embed-responsive-4by3">
							<iframe id="player" class="embed-responsive-item"> </iframe>
						</div>
						<div class="container-fluid">
						
							<div class="panel panel-default">
								<div class="panel-heading">
									<a role="button" data-toggle="collapse" href="#tb_url" aria-expanded="false" aria-controls="tb_url">Play URL</a>
								</div>
								<div id="tb_url" class="panel-body collapse"></div>
							</div>
						</div>
						<div class="container-fluid">
							<div class="panel panel-default">
								<div class="panel-heading">
									<a role="button" data-toggle="collapse" href="#tb_controller" aria-expanded="false" aria-controls="tb_controller">Controller</a>
								</div>
								<div id="tb_controller" class="panel-body collapse">
								</div>
							</div>
						</div>
						<div class="container-fluid">
							<div class="panel panel-default">
								<div class="panel-heading">
									<a role="button" data-toggle="collapse" href="#divlms" aria-expanded="false" aria-controls="divlms">LMS</a>
								</div>
								<div id="divlms" class="panel-body collapse">
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
									<h4>
										<a role="button" data-toggle="collapse" href="#div_block" aria-expanded="false" aria-controls="div_block">Block Info</a>
									</h4>
									<div id="div_block" class="table-responsive collapse">
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
									<h4>
										<a role="button" data-toggle="collapse" href="#div_session" aria-expanded="false" aria-controls="div_session">Session Info</a>
									</h4>
									<div id="div_session" class="table-responsive collapse">
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
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4>
						<a role="button" data-toggle="collapse" href="#panel_log" aria-expanded="false" aria-controls="panel_log">Logs</a>
					</h4>
				</div>
				<div id="panel_log" class="panel-body collapse">
					<div class="table-responsive">
						<table id="table_log" class="table">
							<thead>
								<tr>
									<th>Time</th>
									<th>Type</th>
									<th>Kind</th>
									<th class="col-md-2 col-xs-2 col-lg-2">Request</th>
									<th class="col-md-2 col-xs-2 col-lg-2">Response</th>
									<th class="col-md-2 col-xs-2 col-lg-2">JSON</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="optionModal" tabindex="-1" role="dialog" aria-labelledby="optionModalLabel" data-mck="">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-lable="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="optionModalLabel">Player Options</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group btn-group-justified" role="group">
						<div class="btn-group">
							<button id="btn_autoplay" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<span class="btn-span-ellipsis" >Autoplay</span> <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a>Autoplay On</a></li>
								<li><a>Autoplay Off</a></li>
							</ul>
						</div>
						<div class="btn-group">
							<button id="btn_mute" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<span class="btn-span-ellipsis" >Mute</span> <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a>Mute On</a></li>
								<li><a>Mute Off</a></li>
							</ul>
						</div>
						<div class="btn-group">
							<button id="btn_download" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<span class="btn-span-ellipsis" >Download</span> <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a>Download On</a></li>
								<li><a>Download Off</a></li>
							</ul>
						</div>
						<div class="btn-group">
							<button id="btn_force_exculsive_player" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<span class="btn-span-ellipsis" >force_exculsive_player</span> <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a>force_exculsive_player On</a></li>
								<li><a>force_exculsive_player Off</a></li>
							</ul>
						</div>
						<div class="btn-group">
							<button id="btn_play_downloaded_file" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<span class="btn-span-ellipsis" >play_downloaded_file</span> <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a>play_downloaded_file On</a></li>
								<li><a>play_downloaded_file Off</a></li>
							</ul>
						</div>
					</div>
					<div class="input-group">
						<span class="input-group-addon">Title</span> <input type="text" id="input_title" class="form-control">
					</div>
					<div class="input-group">
						<span class="input-group-addon">Start Time with Popup</span> <input type="text" id="input_t" class="form-control" value="0"> <span class="input-group-btn">
							<button type="button" class="btn btn-default num-up">
								<span class="glyphicon glyphicon-menu-up"></span>
							</button>
							<button type="button" class="btn btn-default num-down">
								<span class="glyphicon glyphicon-menu-down"></span>
							</button>
						</span>
					</div>
					<div class="input-group">
						<span class="input-group-addon">Start Time without Popup</span> <input type="text" id="input_s" class="form-control" value="0"> <span class="input-group-btn">
							<button type="button" class="btn btn-default num-up">
								<span class="glyphicon glyphicon-menu-up"></span>
							</button>
							<button type="button" class="btn btn-default num-down">
								<span class="glyphicon glyphicon-menu-down"></span>
							</button>
						</span>
					</div>
					<div class="input-group">
						<span class="input-group-addon">Brightness</span> <input type="text" id="input_brightness" class="form-control" value="0"> <span class="input-group-btn">
							<button type="button" class="btn btn-default num-up">
								<span class="glyphicon glyphicon-menu-up"></span>
							</button>
							<button type="button" class="btn btn-default num-down">
								<span class="glyphicon glyphicon-menu-down"></span>
							</button>
						</span>
					</div>
					<div class="input-group">
						<span class="input-group-addon">Constrast</span> <input type="text" id="input_constrast" class="form-control" value="0"> <span class="input-group-btn">
							<button type="button" class="btn btn-default num-up">
								<span class="glyphicon glyphicon-menu-up"></span>
							</button>
							<button type="button" class="btn btn-default num-down">
								<span class="glyphicon glyphicon-menu-down"></span>
							</button>
						</span>
					</div>
					<div class="input-group">
						<span class="input-group-addon">Saturation</span> <input type="text" id="input_saturation" class="form-control" value="0"> <span class="input-group-btn">
							<button type="button" class="btn btn-default num-up">
								<span class="glyphicon glyphicon-menu-up"></span>
							</button>
							<button type="button" class="btn btn-default num-down">
								<span class="glyphicon glyphicon-menu-down"></span>
							</button>
						</span>
					</div>
					<div class="input-group">
						<span class="input-group-addon">mobile_return_url</span> <input type="text" id="input_mobile_return_url" class="form-control valid-path" value="">
					</div>
					<div class="input-group">
						<span class="input-group-addon">mobile_folder_download</span> <input type="text" id="input_mobile_folder_download" class="form-control" value="">
					</div>
					<div class="input-group">
						<span class="input-group-addon">wmode</span> <input type="text" id="input_wmode" class="form-control"> <span class="input-group-btn">
							<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								wmode <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a>direct</a></li>
								<li><a>transparent</a></li>
								<li><a>window</a></li>
								<li><a>opaque</a></li>
								<li><a>gpu</a></li>
							</ul>
						</span>
					</div>
					<div class="input-group">
						<span class="input-group-addon">pc_player_version</span> <input type="text" id="input_pc_player_version" class="form-control"> <span class="input-group-btn">
							<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								Version <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a>V2</a></li>
								<li><a>V3</a></li>
								<li><a>V3e</a></li>
							</ul>
						</span>
					</div>
					<div class="input-group">
						<span class="input-group-addon">player_version</span> <input type="text" id="input_player_version" class="form-control"> <span class="input-group-btn">
							<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								Version <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a>V2</a></li>
								<li><a>V3</a></li>
								<li><a>V3e</a></li>
								<li><a>html5</a></li>
							</ul>
						</span>
					</div>
					<div class="input-group">
						<span class="input-group-addon">pc_folder_download</span> <input type="text" id="input_pc_folder_download" class="form-control" value="">
					</div>
					<div class="input-group">
						<span class="input-group-addon">buffer</span> <input type="text" id="input_buffer" class="form-control" value="0"> <span class="input-group-btn">
							<button type="button" class="btn btn-default num-up">
								<span class="glyphicon glyphicon-menu-up"></span>
							</button>
							<button type="button" class="btn btn-default num-down">
								<span class="glyphicon glyphicon-menu-down"></span>
							</button>
						</span>
					</div>
					<div class="input-group">
						<span class="input-group-addon"> <input type="checkbox" class="form-control" id="input_show_controls_paused"></span> <span class="input-group-addon">show_controls_paused</span>
					</div>
					<div class="input-group">
						<span class="input-group-addon"> <input type="checkbox" class="form-control" id="input_show_poster_ended"></span> <span class="input-group-addon">show_poster_ended</span>
					</div>
					<div class="input-group">
						<span class="input-group-addon">overlay_button_position</span> <input type="text" id="input_overlay_button_position" class="form-control" value=""> <span class="input-group-btn">
							<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								Position <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a>TR</a></li>
								<li><a>TR</a></li>
								<li><a>BR</a></li>
								<li><a>BL</a></li>
								<li><a>CT</a></li>
							</ul>
						</span>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="acceptOption">Accept</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="policyModal" tabindex="-1" role="dialog" aria-labelledby="policyModalLabel" data-mck="default">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-lable="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="policyModalLabel">Callback Policy</h4>
				</div>
				<div class="modal-body">
					<ul class="nav nav-tabs nav-pills nav-justified" id="nav_policy">
						<li role="presentation" class="active"><a href="#drmcallback" data-toggle="tab">DRM</a></li>
						<li role="presentation"><a href="#playcallback" data-toggle="tab">PLAY</a></li>
					</ul>
					<div id="tabContent" class="tab-content">
						<div id="drmcallback" class="tab-pane fade active in">
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
						<div id="playcallback" class="tab-pane fade active">
							<h4>Kind 1</h4>
							<div class="input-group">
								<span class="input-group-addon">result </span> <input type="number" id="tb_play_k1_result" class="form-control" value="1" min="0" max="1">
							</div>
							<div class="input-group">
								<span class="input-group-addon">expiration_date (s) </span> <input type="number" id="tb_play_k1_expiration_date" class="form-control" value="3600" min="0">
							</div>
							<div class="input-group">
								<span class="input-group-addon">expiration_playtime (s) </span> <input type="number" id="tb_play_k1_expiration_playtime" class="form-control" value="0" min="0">
							</div>
							<div class="input-group">
								<span class="input-group-addon">message </span> <input type="text" id="tb_play_k1_message" class="form-control" value="">
							</div>
							<div class="input-group">
								<span class="input-group-addon">vmcheck </span> <input type="number" id="tb_play_k1_vmcheck" class="form-control" value="0" min="0" max="1"/>
							</div>
							<div class="input-group">
								<span class="input-group-addon">disable_tvout</span> <input type="number" id="tb_play_k1_disable_tvout" class="form-control" value="0" min="0" max="1"/>
							</div>
							
							<div class="input-group">
								<span class="input-group-addon">start_time </span> <input type="text" id="tb_play_k1_start_time" class="form-control"/>
							</div>
							<div class="input-group">
								<span class="input-group-addon">end_time </span> <input type="text" id="tb_play_k1_end_time" class="form-control" />
							</div>
							
							<h4>Kind 3</h4>
							<div class="input-group">
								<span class="input-group-addon">result </span> <input type="number" id="tb_play_k3_result" class="form-control" value="1" min="0" max="1">
							</div>
							<div class="input-group">
								<span class="input-group-addon">message </span> <input type="text" id="tb_play_k3_message" class="form-control" value="">
							</div>
							<div class="input-group">
								<span class="input-group-addon">content_expired</span> <input type="number" id="tb_play_k3_content_expired" class="form-control" value="0" min="0" max="1">
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="acceptPolicy">Accept</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="static/external/jquery/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="static/external/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="static/external/jPages/jPages.js"></script>
	<script type="text/javascript" src="static/external/kollus/kollus.videogateway-controller.min.js"></script>
	<script type="text/javascript" src="static/js/main.js"></script>
</body>
</html>