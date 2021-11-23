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
				<a href="/"> <span><h1>
							Kollus Example Page <small>for Jsp</small>
						</h1> </span></a>
			</div>
			<div class="row">
				<h4>Web Token(JWT) Createor</h4>
			</div>
		</div>
		<div class="container-fluid">
			<div class="col-md-3 col-xs-3 col-lg-3">
				<div class="panel panel-default" id="pn_paylod">
					<div class="panel-heading" id="ph_paylod">
						<h4>Payload</h4>
					</div>
					<div class="panel-body" id="pb_paylod">
						<div class="input-group">
							<span class="input-group-addon">cuid</span> <input id="tb_cuid" class="form-control" type="text" />
						</div>
						<div class="input-group">
							<span class="input-group-addon">awtc</span> <input id="tb_awtc" class="form-control" type="text" />
						</div>
						<div class="input-group">
							<span class="input-group-addon">expt</span> <input id="tb_expt" class="form-control" type="text" />
						</div>
						<div class="btn-group btn-group-justified" role="group">
							<div class="btn-group btn-group-sm" role="group">
								<button type="button" class="btn btn-default" id="btn_addmc">ADD MC</button>
								<button type="button" class="btn btn-default" id="btn_removemc">REMOVE MC</button>
							</div>
						</div>
						<div class="div_mc">
							<div id="div_mc_0">
								<span class="label label-primary span_index"> 1 </span>
								<div class="input-group">
									<span class="input-group-addon">mckey</span> <input class="form-control tb_mckey" type="text" />
								</div>
								<div class="input-group">
									<span class="input-group-addon">mcpf</span> <input class="form-control tb_mcpf" type="text" />
								</div>
								<div class="checkbox">
									<label><input type="checkbox" class="cb_intr" />intr</label>
								</div>
								<div class="checkbox">
									<label><input type="checkbox" class="cb_seek" />seek</label>
								</div>
								<div class="input-group">
									<span class="input-group-addon">seekable_end</span><input class="form-control tb_seekable" type="number" min="-1" value="-1" />
								</div>
								<div class="checkbox">
									<label><input type="checkbox" class="cb_playrate"/>disable_playrate</label>
								</div>
							</div>
						</div>
					</div>
					<div class="panel-footer" id="pf_payload">
						<div class="btn-group btn-group-justified">
							<div class="btn-group btn-group-lg">
								<button type="button" class="btn btn-default" id="btn_c_p">Create JSON Paylod</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4 col-xs-4 col-lg-4">
				<div class="panel panel-default" id="pn_jwt">
					<div class="panel-heading" id="ph_jwt">
						<h4>JWT</h4>
					</div>
					<div class="panel-body" id="pb_jwt">
						<span>JSON Header</span>
						<textarea id="tt_jwt_header" rows="5" cols="0"></textarea>
						<span>JSON Payload</span>
						<textarea id="tt_jwt_payload" rows="15" cols="0"></textarea>
						<div class="btn-group btn-group-justified">
							<div class="btn-group btn-group-sm">
								<button type="button" class="btn btn-default" id="btn_c_t">Create Token</button>
							</div>
						</div>
						<span>Web Token</span>
						<textarea id="tt_jwt_token" rows="3" cols="0"></textarea>
						<div class="btn-group btn-group-justified">
							<div class="btn-group btn-group-sm">
								<button type="button" class="btn btn-default" id="btn_d_t">Decode Token</button>
							</div>
						</div>
					</div>
					<div class="panel-footer" id="pf_jwt">
						<div class="btn-group btn-group-justified">
							<div class="btn-group btn-group-lg">
								<button type="button" class="btn btn-default" id="btn_p_t" data-url="">Play Test</button>
							</div>
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
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="static/external/jquery/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="static/external/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="static/external/jPages/jPages.js"></script>
	<script type="text/javascript" src="static/external/kollus/kollus.videogateway-controller.min.js"></script>
	<script type="text/javascript" src="static/js/common.js"></script>
	<script type="text/javascript" src="static/js/jwt.js"></script>
</body>
</html>