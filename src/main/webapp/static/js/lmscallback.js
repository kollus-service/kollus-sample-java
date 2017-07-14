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
		if (type == 'LMS' || type == 'lms') {
			var json_data = JSON.parse(JSON.parse(requestBody).json_data);
			var content_info = json_data.content_info;
			var block_info = json_data.block_info;
			var user_data = JSON.parse(requestBody).user;
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
				$('#table_lms_users > tbody > tr').remove();
				$.each(user_data, function(key, value) {
					var userdata = '<tr>' + '<td>' + key + '</td>' + '<td>' + value + '</td>' + '</tr>';
					$('#table_lms_users > tbody').append(userdata);
				});
			}
		}

	};
}

$(document).ready(function() {
	ws_connect();
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
				var liItem = '<li class="media" data-url="' + item.jwt_play_url + '" data-mck="' + item.media_content_key + '">' + '<div class="media-left"><img class="media-object" src="' + item.poster_url + '"></div>' + '<div class="media-body">' + '<h4 class="media-heading">' + item.title + '</h4>' + '</div>' + '</li>';
				list_media.append(liItem);
			});
			pagination('ulChannel');
		});
	});

	$('#ulChannel').on('click', 'li.media', function() {
		$(this).parent().find('li.media').removeClass('selectedli');
		$(this).addClass('selectedli');
		$('#player').attr('src', $(this).attr('data-url'));
		$('#td_mck').html($(this).attr('data-mck'))
	});
});