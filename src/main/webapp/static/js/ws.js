/**
 * Web Socket Javascript
 */

var ws;
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
			var content_info = JSON.parse(requestBody).content_info;
			var block_info = JSON.parse(requestBody).block_info;
			if (content_info.media_content_key == $('#td_mck').html()) {
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