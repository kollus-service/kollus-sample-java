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
				<h4>Query String Maker</h4>
			</div>
		</div>
		<div class="container-fluid">
			<div class="col-md-3 col-xs-3 col-lg-3">
				<div class="panel panel-default" id="pn_paylod">
					<div class="panel-heading" id="ph_paylod">
						<h4>Channel</h4>
					</div>
					<div class="panel-body" id="pb_paylod">
						<select id="sb_channel" class="form-control info"></select>
						<ul id="ulChannel" class="media-list">
						</ul>
						<div class="holder"></div>
					</div>
					<div class="panel-footer" id="pf_payload"></div>
				</div>
			</div>
			<div class="col-md-4 col-xs-4 col-lg-4">
				<div class="panel panel-default">
					<div class="panel-heading">Query String Creator</div>
					<div class="panel-body">
						<div class="container-fluid">
							<div class="btn-group btn-group-justified" role="group">
								<div class="btn-group btn-group-sm">
									<button id="btn_autoplay" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										<span class="btn-span-ellipsis">Autoplay</span> <span class="caret"></span>
									</button>
									<ul class="dropdown-menu">
										<li><a>Autoplay On</a></li>
										<li><a>Autoplay Off</a></li>
									</ul>
								</div>
								<div class="btn-group btn-group-sm">
									<button id="btn_mute" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										<span class="btn-span-ellipsis">Mute</span> <span class="caret"></span>
									</button>
									<ul class="dropdown-menu">
										<li><a>Mute On</a></li>
										<li><a>Mute Off</a></li>
									</ul>
								</div>
								<div class="btn-group btn-group-sm">
									<button id="btn_download" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										<span class="btn-span-ellipsis">Download</span> <span class="caret"></span>
									</button>
									<ul class="dropdown-menu">
										<li><a>Download On</a></li>
										<li><a>Download Off</a></li>
									</ul>
								</div>
							</div>
							<div class="container-fluid">
								<div class="btn-group btn-group-justified" role="group">
									<div class="btn-group btn-group-sm">
										<button id="btn_force_exculsive_player" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
											<span class="btn-span-ellipsis">force_exculsive_player</span> <span class="caret"></span>
										</button>
										<ul class="dropdown-menu">
											<li><a>force_exculsive_player On</a></li>
											<li><a>force_exculsive_player Off</a></li>
										</ul>
									</div>
									<div class="btn-group btn-group-sm">
										<button id="btn_play_downloaded_file" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
											<span class="btn-span-ellipsis">play_downloaded_file</span> <span class="caret"></span>
										</button>
										<ul class="dropdown-menu">
											<li><a>play_downloaded_file On</a></li>
											<li><a>play_downloaded_file Off</a></li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<div class="input-group">
							<span class="input-group-addon">Title</span> <input type="text" id="input_title" class="form-control span3">
						</div>
						<div class="input-group">
							<span class="input-group-addon span3">Start Time with Popup</span> <input type="text" id="input_t" class="form-control span3" value="0"> <span class="input-group-btn">
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
									version <span class="caret"></span>
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
									version <span class="caret"></span>
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
							<span class="input-group-addon beautiful"> <input type="checkbox" id="input_show_controls_paused" /></span> <input type="text" class="form-control notyping" value="show_controls_paused" disabled/>
						</div>
						<div class="input-group">
							<span class="input-group-addon beautiful"> <input type="checkbox" id="input_show_poster_ended"></span> <input type="text" class="form-control notyping" value="show_poster_ended" disabled/>
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
						<div class="panel-footer">
							<button type="button" class="btn btn-primary" id="btn_p">Play</button>
							<button type="button" class="btn btn-primary" id="btn_c">Cancel</button>
						</div>
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
						<textarea rows="5" cols="40" id="tt_url"></textarea>
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
	<script type="text/javascript" src="static/js/query.js"></script>
</body>
</html>