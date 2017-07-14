/**
 * 
 */

// $('#nav_lib_channel li').on('click', function(e) {
// var lis = $(this).parent().find('li');
// $.each(lis, function(idx, item) {
// $(item).removeClass('active');
// });
// $(this).addClass('active');
// var id = 'div_' + $(this).find('a').html().toLowerCase();
// var divs = $(this).parent().parent().find('div');
// e.preventDefault();
// $.each(divs, function(idx, item) {
// if (item.attr('id') == id) {
// item.addClass('view');
// item.removeClass('noview');
// } else {
// item.addClass('noview');
// item.removeClass('view');
// }
// });
// });
var progressTimer;
var ws;
var controller;
var ws_connect = function() {
	var host = $(location).attr('host');
	ws = new WebSocket('ws://' + host + '/ws');
	ws.onopen = function(message) {

	};
	ws.onclose = function(message) {
		console.log('Reconnecting WS');
		setTimeout(function() {
			ws_connect();
		}, 1000);
	};
	ws.onerror = function(message) {
		console.error('[WS ERR] : ' + message.data);
	};
	ws.onmessage = function(message) {
		var jsonObject = JSON.parse(message.data);
		var type = jsonObject.type ? jsonObject.type : "";
		var kind = jsonObject.kind ? jsonObject.kind : 0;
		var time = jsonObject.time ? jsonObject.time : 0;
		var requestBody = jsonObject.request ? jsonObject.request : "";
		var responseBody = jsonObject.response ? jsonObject.response : "";
		var responseJson = jsonObject.responseJson ? jsonObject.responseJson : "";

		if (type != 'LMS' && type != 'lms') {
			var log = '<tr>' + '<td>' + time + '</td>' + '<td>' + type + '</td>' + '<td>' + kind + '</td>' + '<td class="col-xs-2 col-md-2 col-lg-2">' + requestBody + '</td>' + '<td class="col-xs-2 col-md-2 col-lg-2">' + responseBody + '</td>' + '<td class="col-xs-2 col-md-2 col-lg-2">' + responseJson + '</td>' + '</tr>';
			// $('#table_log > tbody > tr:first').before(log);
			if ($('#table_log > tbody > tr').length == 0) {
				$('#table_log > tbody').append(log);
			} else {
				$('#table_log > tbody > tr:first').before(log);
			}

		} else {
			var json_data = JSON.parse(JSON.parse(requestBody).json_data);
			var content_info = json_data.content_info;
			var block_info = json_data.block_info;
			if (content_info.media_content_key == $('#td_mck').html()) {
				$('#tt_json').html(JSON.stringify(json_data, null, 4));
				$('#td_mck').html(content_info.media_content_key)
				var s_count = $('#td_count').html();
				var cnt = (s_count == undefined || s_count == '') ? 0 : parseInt(s_count);
				$('#td_count').html(cnt + 1);

				$('#td_start_at').html(content_info.start_at);
				$('#td_real_playtime').html(content_info.real_playtime);
				$('#td_playtime').html(content_info.playtime);
				$('#td_playtime_percent').html(content_info.playtime_percent);

				var playsection = parseInt(block_info.block_count);
				$('#table_block > tbody > tr').remove();
				for (var idx = 0; idx < playsection; idx++) {
					var b = block_info.blocks['b' + idx];
					var t = block_info.blocks['t' + idx];
					var p = block_info.blocks['p' + idx];
					var playInfo = '<tr>' + '<td>' + idx + '</td>' + '<td>' + b + '</td>' + '<td>' + t + '</td>' + '<td>' + p + '</td>' + '</tr>';
					$('#table_block > tbody').append(playInfo);
				}
				$('#table_session > tbody > tr').remove();
				var sessions = block_info.sessions;
				$.each(sessions, function(idx, item) {
					var sessionInfo = '<tr>' + '<td>' + item.block + '</td>' + '<td>' + item.start_time + '</td>' + '<td>' + item.play_time + '</td>' + '</tr>';
					$('#table_session > tbody').append(sessionInfo);
				});
				
			}
		}
	};
}
var load_controller = function() {
	try {
		controller = new Kollus.VideogatewayController({
			target_window : $('#player').get(0).contentWindow
		});
		controller.on('progress', function(percent, position, duration) {
			$('#prog_play').html(percent + '% (' + position + '/' + duration + ')');
			$('#prog_play').attr('aria-valuenow', percent);
			$('#prog_play').css('width', percent + '%');
			$('#cpTitle').html($('.vjs-caption-overlay-text').html());
		}).on('screenchange', function(screen) {
			var prev_screen = $('#fullscreen').val();
			$('#fullscreen').val(screen);
			addLog('Volume Change : ' + prev_screen + '-->' + screen);
		}).on('volumechange', function(volume) {
			var prev_volume = $('#volume').val();
			$('#volume').val(volume);
			addLog('Volume Change : ' + prev_volume + '-->' + volume);
		}).on('muted', function(muted) {
			addLog("Volume Muted : " + muted);
		}).on('play', function() {
			addLog('Play');
			$('#control_visible').val(controller.get_control_visibility());
			$('#fullscreen').val(controller.get_screen());
			$('#topmost').val(controller.get_topmost());
			$('#scalemode').val(controller.get_scalemode());
			$('#brightness').val(controller.get_brightness());
			$('#contrast').val(controller.get_contrast());
			$('#saturation').val(controller.get_saturation());
			
		}).on('pause', function() {
			addLog('Pause');
		}).on('done', function() {
			addLog('Done');
		}).on('ready', function() {
			addLog('Ready');
		}).on('loaded', function() {
			addLog('Loaded');
		}).on('jumpstepchange', function(jumpstep) {
			var prev_step = $('#step').val();
			$('#step').val(jumpstep);
			addLog('Jumpstep Change : ' + prev_step + '-->' + jumpstep);
		}).on('scalemode', function(scalemode) {
			var prev_scale = $('scalemode').val();
			$('scalemode').val(scalemode);
			addLog('Scalemode Change : ' + prev_scale + '-->' + scalemode);
		}).on('speedchange', function(speed) {
			var prev_speed = $('#speed').val();
			$('#speed').val(speed);
			addLog('Speed Change : ' + prev_speed + '-->' + speed);
		}).on('topmost', function(topmost) {
			var prev_topmost = $('#topmost').val()
			$('#topmost').val(topmost);
			addLog('Topmost Change : ' + prev_topmost + '-->' + topmost);
		}).on('error', function(error_code) {
			addLog('ERR_CD : ' + error_code);
		}).on('videosettingchange', function(videosetting) {
			var b = videosetting.brightness;
			var c = videosetting.contrast;
			var s = videosetting.saturation;
			var prev_videosetting = {};
			prev_videosetting.brightness = $('brightness').val();
			prev_videosetting.contrast = $('contrast').val();
			prev_videosetting.saturation = $('saturation').val();
			$('brightness').val(b);
			$('contrast').val(c);
			$('saturation').val(s);
			addLog('Video Setting Change : ' + JSON.stringify(prev_videosetting) + '-->' + JSON.stringify(videosetting));
		}).on('html5_video_supported', function(is_supported) {
		});

		$('#back').on('click', function() {
			controller.rw();
		});
		$('#start').on('click', function() {
			controller.play();
		});
		$('#pause').on('click', function() {
			controller.pause();
		});
		$('#forward').on('click', function() {
			controller.ff();
		});

		$('#s_up').on('click', function() {
			var speed = parseFloat($('#speed').val()) + 0.1;
			controller.set_speed(speed);
		});
		$('#s_down').on('click', function() {
			var speed = $('#speed').val() - 0.1;
			controller.set_speed(speed);
		});
		$('#step_get').on('click', function() {
			$('#step').val(controller.get_jumpstep());
		});
		$('#step_set').on('click', function() {
			controller.set_jumpstep(parseInt($('#step').val()));
		});
		$('#r_start').on('click', function() {
			controller.set_repeat_start();
		});
		$('#repeat').on('click', function() {
			controller.set_repeat_end();
		});
		$('#remove').on('click', function() {
			controller.unset_repeat();
		});
		$('#v_up').on('click', function() {
			var volume = parseInt($('#volume').val()) + 1;
			controller.set_volume(volume);
		});
		$('#v_down').on('click', function() {
			var volume = parseInt($('#volume').val()) - 1;
			controller.set_volume(volume);
		});
		$('#mute').on('click', function() {
			controller.mute();
		});
		$('#show_control').on('click', function(){
			controller.set_control_visibility(true);
			var control_visible = controller.get_control_visibility()
			$('#control_visible').val(control_visible);
			addLog('Control Visibility : ' + control_visible);
		});
		$('#hide_control').on('click', function(){
			controller.set_control_visibility(false);
			var control_visible = controller.get_control_visibility()
			$('#control_visible').val(control_visible);
			addLog('Control Visibility : ' + control_visible);
		});
		$('#btnF').on('click', function(){
			controller.set_screen();
		});
		$('#btnT').on('click', function(){
			controller.set_topmost();
		});
		$('#btnB').on('click', function(){
			var b = parseInt($('#brightness').val());
			controller.set_brightness(b);
		});
		$('#btnC').on('click', function(){
			var c = parseInt($('#contrast').val());
			controller.set_contrast(c);
		});
		$('#btnS').on('click', function(){
			var s = parseInt($('#saturation').val());
			controller.set_saturation(s);
		});
		$('#div_scalemode > span > button').on('click', function(){
			var scalemode = $(this).attr('id');
			controller.set_scalemode(scalemode);
		});
	} catch (e) {
		console.error(e);
	}
}

var pagination = function(id) {
	$('div.holder').jPages({
		containerId : id,
		perPage : 7,
		startPage : 1,
		startRange : 1,
		midRange : 4,
		endRange : 1

	});
}
var checkProgress = function(_url) {
	$.ajax({
		url : _url,
		type : 'get',
		accept : 'application/json;charset=utf-8',
		success : function(res) {
			var prog = res.result.progress;
			$('#prog_upload').html(prog + '%');
			$('#prog_upload').attr('aria-valuenow', prog);
			$('#prog_upload').css('width', prog + '%');
			if (parseInt(prog) == 100) {
				clearInterval(progressTimer);
			}
		}
	})
}
var getUploadUrl = function(expire_time, 
		upload_title, category_key, is_encryption_upload, 
		is_audio_upload, callback){
	var data = {};
	data.expire_time = expire_time;
	data.title = upload_title;
	data.category_key = category_key;
	data.is_encryption_upload = is_encryption_upload ? 1 : 0;
	data.is_audio_upload = is_audio_upload ? 1 : 0;
	$.ajax({
		url : "upload/url",
		type : "get",
		// contentType:"application/x-www-form-urlencoded",
		data : $.param(data),
		success : function(res) {
			callback(res);
		}
	});
	
} 
var initControl = function() {
	$('#btn_autoplay').html('Autoplay <span class="caret"></span>');
	$('#btn_mute').html('Mute <span class="caret"></span>');
	$('#btn_download').html('download <span class="caret"></span>');
	$('#btn_force_exculsive_player').html('force_exculsive_player <span class="caret"></span>');
	$('#btn_play_downloaded_file').html('play_downloaded_file <span class="caret"></span>');
	$('#input_title').val('');
	$('#input_t').val('0');
	$('#input_s').val('0');
	$('#input_brightness').val('0');
	$('#input_constrast').val('0');
	$('#input_saturation').val('0');
	$('#input_mobile_return_url').val('');
	$('#input_mobile_folder_download').val('');
	$('#input_wmode').val('');
	$('#input_pc_player_version').val('');
	$('#input_player_version').val('');
	$('#input_pc_folder_download').val('');
	$('#input_buffer').val('0');
	$('#input_show_controls_paused').prop('checked', false);
	$('#input_show_poster_ended').prop('checked', false);
	$('#input_overlay_button_position').val('');
}

var makeQueryString = function() {
	var queryString = '';
	var player = $('#btn_player').text();
	var autoplay = $('#btn_autoplay').text();
	if (autoplay.includes('On')) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'autoplay';
		} else {
			queryString += '&autoplay';
		}
	}
	var mute = $('#btn_mute').text();
	if (mute.includes('On')) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'mute';
		} else {
			queryString += '&mute';
		}
	}
	var download = $('#btn_download').text();
	if (download.includes('On')) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'download';
		} else {
			queryString += '&download';
		}
	}
	var force_exculsive_player = $('#btn_force_exculsive_player').text();
	if (force_exculsive_player.includes('On')) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'force_exculsive_player';
		} else {
			queryString += '&force_exculsive_player';
		}
	}
	var play_downloaded_file = $('#btn_play_downloaded_file').text();
	if (play_downloaded_file.includes('On')) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'play_downloaded_file';
		} else {
			queryString += '&play_downloaded_file';
		}
	}

	var title = $('#input_title').val();
	if (title != '' && title != undefined) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'title=' + title;
		} else {
			queryString += '&title=' + title;
		}
	}
	var t = $('#input_t').val();
	if (t != '' && t != '0' && t != undefined) {
		if (queryString == '' || queryString == undefined) {
			queryString = 't=' + t;
		} else {
			queryString += '&t=' + t;
		}
	}
	var s = $('#input_s').val();
	if (s != '' && s != '0' && s != undefined) {
		if (queryString == '' || queryString == undefined) {
			queryString = 's=' + s;
		} else {
			queryString += '&s=' + s;
		}
	}
	var brightness = parseInt($('#input_brightness').val());
	if (brightness <= 50 && brightness >= -50 && brightness != 0 && s != undefined) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'brightness=' + brightness;
		} else {
			queryString += '&brightness=' + brightness;
		}
	}
	var constrast = parseInt($('#input_constrast').val());
	if (constrast <= 50 && constrast >= -50 && constrast != 0 && s != undefined) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'constrast=' + constrast;
		} else {
			queryString += '&constrast=' + constrast;
		}
	}
	var saturation = parseInt($('#input_saturation').val());
	if (saturation <= 50 && saturation >= -50 && saturation != 0 && s != undefined) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'saturation=' + saturation;
		} else {
			queryString += '&saturation=' + saturation;
		}
	}
	var mobile_return_url = $('#input_mobile_return_url').val();
	if (mobile_return_url != '' && mobile_return_url != undefined) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'mobile_return_url=' + mobile_return_url;
		} else {
			queryString += '&mobile_return_url=' + mobile_return_url;
		}
	}
	var mobile_folder_download = $('#input_mobile_folder_download').val();
	if (mobile_folder_download != '' && mobile_folder_download != undefined) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'mobile_folder_download=' + mobile_folder_download;
		} else {
			queryString += '&mobile_folder_download=' + mobile_folder_download;
		}
	}
	var wmode = $('#input_wmode').val();
	if (wmode != '' && wmode != undefined) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'wmode=' + wmode;
		} else {
			queryString += '&wmode=' + wmode;
		}
	}
	var pc_player_version = $('#input_pc_player_version').val();
	if (pc_player_version != '' && pc_player_version != undefined) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'pc_player_version=' + pc_player_version;
		} else {
			queryString += '&pc_player_version=' + pc_player_version;
		}
	}
	var player_version = $('#input_player_version').val();
	if (player_version != '' && player_version != undefined) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'player_version=' + player_version;
		} else {
			queryString += '&player_version=' + player_version;
		}
	}
	var pc_folder_download = $('#input_pc_folder_download').val();
	if (pc_folder_download != '' && pc_folder_download != undefined) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'pc_folder_download=' + pc_folder_download;
		} else {
			queryString += '&pc_folder_download=' + pc_folder_download;
		}
	}
	var buffer = $('#input_buffer').val();
	if (buffer != '' && buffer != '0' && buffer != undefined) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'buffer=' + buffer;
		} else {
			queryString += '&buffer=' + buffer;
		}
	}
	var show_controls_paused = $('#input_show_controls_paused').is(':checked');
	if (show_controls_paused) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'show_controls_paused=true';
		} else {
			queryString += '&show_controls_paused=true';
		}
	}

	var show_poster_ended = $('#input_show_poster_ended').is(':checked');
	if (show_poster_ended) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'show_poster_ended=true';
		} else {
			queryString += '&show_poster_ended=true';
		}
	}

	var overlay_button_position = $('#input_overlay_button_position').val();
	if (overlay_button_position != '' && overlay_button_position != undefined) {
		if (queryString == '' || queryString == undefined) {
			queryString = 'overlay_button_position=' + overlay_button_position;
		} else {
			queryString += '&overlay_button_position=' + overlay_button_position;
		}
	}
	return queryString;
}

$(document).ready(function() {
	load_controller();
	$('#plyaer').on('load', function() {
		if (controller == undefined) {
			load_controller();
		}
	});

	ws_connect();
	$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
		var target = $(e.target).attr("href") // activated tab
	});
	
	getCategory(function(res){
		var cntCaAll = 0;
		$('#sb_upload_category').html('');
		$('#sb_media_category').html('');
		$.each(res, function(idx, item) {
			$('#sb_upload_category').append($('<option>', {
				value : item.key,
				text : item.name + '(' + item.count_of_media_contents + ')'
			}));
			$('#sb_media_category').append($('<option>', {
				value : item.key,
				text : item.name + '(' + item.count_of_media_contents + ')'
			}));
			cntCaAll += item.count_of_media_contents;
		});
	})
	getChannel(function(res) {
		var cntChAll = 0;
		$('#sb_channel').html('');
		$.each(res, function(idx, item) {
			$('#sb_channel').append($('<option>', {
				value : item.key,
				text : item.name + '(' + item.count_of_media_contents + ')'
			}));
			cntChAll += item.count_of_media_contents;
		});
	});
		

	$('#btn_changeUser').on('click', function() {
		$('#fg_accessToken').removeClass('has-error has-danger');
		$('#fg_secretKey').removeClass('has-error has-danger');
		$('#fg_userKey').removeClass('has-error has-danger');
		$('#tb_accessToken').val('');
		$('#tb_accountKey').val('');
		$('#tb_secretKey').val('');
		$('#tb_userKey').val('');
		$('#tb_accessToken').removeAttr('disabled');
		$('#tb_accountKey').removeAttr('disabled');
		$('#tb_secretKey').removeAttr('disabled');
		$('#tb_userKey').removeAttr('disabled');
		$('#btn_loadInfo').removeAttr('disabled');
	});
	$('#sb_media_category').change(function() {
		var categoryKey = $('#sb_media_category option:selected').val();
		var txtLibrary = $('#sb_media_category option:selected').text();
		var start = txtLibrary.indexOf('(');
		var end = txtLibrary.indexOf(')', start + 1);
		var cntCategory = txtLibrary.substring(start + 1, end);
		getCategoryItem(categoryKey, cntCategory,function(res){
			var list_media = $('#ulMediaCategory');
			list_media.html('');
			$.each(res, function(idx, item) {
				var liItem = '<li class="media">' + '<div class="media-left"><img class="media-object" src="' + item.poster_url + '"></div>' + '<div class="media-body">' + '<h4 class="media-heading">' + item.title + '</h4>' + '</div>' + '</li>';
				list_media.append(liItem);
			});
			pagination('ulMediaCategory');
		});
	});
	$('#sb_channel').change(function() {
		var channelKey = $('#sb_channel option:selected').val();
		var txtChannel = $('#sb_channel option:selected').text();
		var start = txtChannel.lastIndexOf('(');
		var end = txtChannel.indexOf(')', start + 1);
		var cntChannel = txtChannel.substring(start + 1, end);
		getChannelItem(channelKey, cntChannel, function(res) {
			var list_media = $('#ulChannel');
			list_media.html('');
			$.each(res, function(idx, item) {
				var liItem = '<li class="media">' + '<div class="media-left"><img class="media-object" src="' + item.poster_url + '"></div>' + '<div class="media-body">' + '<h4 class="media-heading">' + item.title + '</h4>' + '<div class="btn-group">' + '<button role="button" class="btn btn-primary play_url" data-url="' + item.jwt_play_url + '" data-mck="' + item.media_content_key + '">Play-JWT</button>' + '<button role="button" class="btn btn-primary options" data-toggle="modal" data-mck="' + item.media_content_key + ' "data-tartget="#optionModal">Options</button>' + '<button role="button" class="btn btn-primary policy" data-toggle="modal" data-mck="' + item.media_content_key + ' "data-tartget="#policyModal">Policy</button>' + '</div>' + '<div class="input-group">' + '<div class="input-group-addon">Option</div>' + '<input class="form-control" type="text" id="' + item.media_content_key + '"/>' + '</div>' + '</div>' + '</li>';
				list_media.append(liItem);
			});
			pagination('ulChannel');
		});
	});
	

	$('ul.media-list').on('click', 'button.play_url', function() {
		$('#tb_callback_count').val(0);
		var play_url = $(this).data('url');
		var mck = $(this).data('mck');
		var options = $('#' + mck).val();
		$('#player').attr('src', play_url + options);
		$('#tb_url').html(play_url + options);
		$('#td_mck').html(mck)
		// window.open(play_url + options);
	});
	$('ul.media-list').on('click', 'button.options', function() {
		initControl();
		$('#optionModal').modal();
		$('#optionModal').attr('data-mck', $(this).data('mck'));
	});

	$('div.input-group-btn ul.dropdown-menu li a, ' + 'div.btn-group ul.dropdown-menu li a').on('click', function() {
		var index = $(this).parent().parent().parent().find('ul').index($(this).parent().parent());
		$(this).parent().parent().parent().find('button').eq(index - 1).html($(this).text() + '<span class="caret"></span>');
	});
	$('div.input-group span.input-group-btn button.num-up').on('click', function() {
		var value = $(this).parent().siblings('input:first').val();
		if ($.isNumeric(value)) {

			$(this).parent().siblings('input:first').val(parseInt(value) + 1);
		} else {
			$(this).parent().siblings('input:first').val(0)
		}
	});
	$('div.input-group span.input-group-btn button.num-down').on('click', function() {
		var value = $(this).parent().siblings('input:first').val();
		if ($.isNumeric(value)) {
			$(this).parent().siblings('input:first').val(parseInt(value) - 1);
		} else {
			$(this).parent().siblings('input:first').val(0)
		}
	});

	$('div.input-group span.input-group-btn ul.dropdown-menu li a').on('click', function() {
		var value = $(this).text()
		$(this).parent().parent().parent().siblings('input:first').val(value);
	});

	$('input.valid-path').on('blur', function() {
		if (/^(\/[\w^ ]+)+\/?([\w.])+[^.]$/gm.test($(this).val())) {
			$(this).parent().removeClass('has-error');
		} else {
			if ($(this).val() != "" && $(this).val() != undefined) {
				$(this).parent().addClass('has-error');
			} else {
				$(this).parent().removeClass('has-error');
			}
		}
	});
	$('input.required').on('blur', function() {
		if ($(this).val() != "" && $(this).val() != undefined) {
			$(this).parent().removeClass('has-error');
		} else {
			$(this).parent().addClass('has-error');
		}
	});

	$('#acceptOption').on('click', function() {
		var queryString = '';
		queryString = makeQueryString();
		if (queryString != '' && queryString != undefined) {
			var id = '#' + $('#optionModal').attr('data-mck');
			$(id).val('&' + queryString);
		}
		$('#optionModal').modal('hide');
	});

	

	$('#form_upload').bind('ajax:complete', function() {
		return false;
	});
	$('#btn_policy').on('click', function() {
		$.ajax({
			url : '/policy/' + $('#policyModal').data('mck'),
			type : 'get',
			accept : 'application/json; charset=utf-8',
			success : function(response) {
				var drm_k1 = response.drm.k1;
				$.each(drm_k1, function(key, value) {
					$('#tb_drm_k1_' + key).val(value);
				});
				var drm_k2 = response.drm.k2;
				$.each(drm_k2, function(key, value) {
					$('#tb_drm_k2_' + key).val(value);
				});
				var drm_k3 = response.drm.k3;
				$.each(drm_k3, function(key, value) {
					$('#tb_drm_k3_' + key).val(value);
				});
				var play_k1 = response.play.k1;
				$.each(play_k1, function(key, value) {
					$('#tb_play_k1_' + key).val(value);
				});
				var play_k3 = response.play.k3;
				$.each(play_k3, function(key, value) {
					$('#tb_play_k3_' + key).val(value);
				});
			}
		}).done(function() {
			$('#policyModal').modal();
		});
		$('#policyModal').data('mck', "default");
	});
	$('ul.media-list').on('click', 'button.policy', function() {
		$.ajax({
			url : '/policy/' + $(this).data('mck'),
			type : 'get',
			accept : 'application/json; charset=utf-8',
			success : function(response) {
				var drm_k1 = response.drm.k1;
				$.each(drm_k1, function(key, value) {
					$('#tb_drm_k1_' + key).val(value);
				});
				var drm_k2 = response.drm.k2;
				$.each(drm_k2, function(key, value) {
					$('#tb_drm_k2_' + key).val(value);
				});
				var drm_k3 = response.drm.k3;
				$.each(drm_k3, function(key, value) {
					$('#tb_drm_k3_' + key).val(value);
				});
				var play_k1 = response.play.k1;
				$.each(play_k1, function(key, value) {
					$('#tb_play_k1_' + key).val(value);
				});
				var play_k3 = response.play.k3;
				$.each(play_k3, function(key, value) {
					$('#tb_play_k3_' + key).val(value);
				});
			}
		}).done(function() {
			$('#policyModal').modal();
		});
		$('#policyModal').data('mck', $(this).data('mck'));

	});

	$('#acceptPolicy').on('click', function() {
		var tb_drms = $('#drmcallback input');
		var tb_plays = $('#playcallback input');
		var callbackPolicy = {};
		var drmPolicy = {};
		var playPolicy = {};
		$.each(tb_drms, function(idx, item) {
			drmPolicy[$(tb_drms[idx]).attr('id')] = $(tb_drms[idx]).val();
		});
		$.each(tb_plays, function(idx, item) {
			playPolicy[$(tb_plays[idx]).attr('id')] = $(tb_plays[idx]).val();
		});
		callbackPolicy.drm = drmPolicy;
		callbackPolicy.play = playPolicy;
		$.ajax({
			url : '/policy/' + $('#policyModal').data('mck'),
			type : 'post',
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			data : JSON.stringify(callbackPolicy),
			success : function(response) {
				$('#policyModal').modal('toggle');
				if (response) {
					console.log('update policy : ' + $('#policyModal').data('mck'));
				}
			}
		});
	});
	$('#btnUploadUrl').on('click', function() {
		var expire_time = $('#tb_upload_expire_time').val();
		var upload_title = $('#tb_upload_title').val();
		var category_key = $('#sb_category option:selected').val();
		var is_encryption_upload = $('#is_encryption_upload').prop("checked");
		var is_audio_upload = $('#is_audio_upload').prop("checked");
		
		getUploadUrl(expire_time, upload_title, category_key, is_encryption_upload, is_audio_upload, 
				function(res){
			$('#uploadUrl').html(JSON.stringify(res));
			$("#form_upload").attr('action', res.upload_url);
			progressTimer = setInterval(function() {
				checkProgress(res.progress_url);
			}, 500);
		});
	});
});
