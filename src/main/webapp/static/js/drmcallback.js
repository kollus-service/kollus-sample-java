/**
 * 
 */
var ws;

var ws_connect = function() {
	var host = $(location).attr('host');
	ws = new WebSocket('ws://' + host + '/ws');
	ws.onopen = function(message) {
		console.error('WS Connected');
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

		if (type.includes('DRM') || type.includes('drm')) {
			var log = '<tr>' + '<td>' + time + '</td>' + '<td>' + kind + '</td>' + '<td>' + 'Request' + '</td>' + '<td>' + decodeURI(requestBody) + '</td>' + '</tr>' + '<tr>' + '<td>' + time + '</td>' + '<td>' + kind + '</td>' + '<td>' + 'Response' + '</td>' + '<td>' + JSON.stringify(responseJson) + '</td>' + '</tr>';
			if ($('#table_log > tbody > tr').length == 0) {
				$('#table_log > tbody').append(log);
			} else {
				$('#table_log > tbody > tr:first').before(log);
			}
			if (kind == 2) {
				setTimeout(function() {
					$('#player').attr('src', $('#ulChannel > li.media.selectedli').attr('data-url') + '&play_downloaded_file&uservalue0=test');
				}, 5000);
			}

		}
	};
}

var savePolicy = function(callback) {
	var tb_drms = $('#pb_dc input');
	var callbackPolicy = {};
	var drmPolicy = {};
	$.each(tb_drms, function(idx, item) {
		drmPolicy[$(tb_drms[idx]).attr('id')] = $(tb_drms[idx]).val();
	});
	callbackPolicy.drm = drmPolicy;
	$.ajax({
		url : '/testpolicy/drm',
		type : 'post',
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		data : JSON.stringify(callbackPolicy),
		success : function(response) {
			callback(response);
		}
	});
}
var getPolicy = function(callback) {
	$.ajax({
		url : '/testpolicy/drm',
		type : 'get',
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		success : function(response) {
			callback(response);
		}
	});
}

$(document).ready(function() {
	ws_connect();
	getPolicy(function(res) {
		if (res.success) {
			$('#lb_accpet').html('Configured');
			var drm_k1 = res.drm.k1;
			$.each(drm_k1, function(key, value) {
				$('#tb_drm_k1_' + key).val(value);
			});
			var drm_k2 = res.drm.k2;
			$.each(drm_k2, function(key, value) {
				$('#tb_drm_k2_' + key).val(value);
			});
			var drm_k3 = res.drm.k3;
			$.each(drm_k3, function(key, value) {
				$('#tb_drm_k3_' + key).val(value);
			});
		} else {
			$('#lb_accpet').html('Not Configured');
		}
	});

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
				var liItem = '<li class="media" data-url="' + item.jwt_play_url + '">' + '<div class="media-left"><img class="media-object" src="' + item.poster_url + '"></div>' + '<div class="media-body">' + '<h4 class="media-heading">' + item.title + '</h4>' + '</div>' + '</li>';
				list_media.append(liItem);
			});
			pagination('ulChannel');
		});
	});

	$('#btn_a').on('click', function() {
		savePolicy(function(res) {
			if (res.success) {
				$('#lb_accpet').html('Configured');
			}
		})
	});
	$('#ulChannel').on('click', 'li.media', function() {
		$(this).parent().find('li.media').removeClass('selectedli');
		$(this).addClass('selectedli');
		$('#player').attr('src', $(this).attr('data-url') + '&download&play_downloaded_file&uservalue0=test&player_version=v3e');
	});
});