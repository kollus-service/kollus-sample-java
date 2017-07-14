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

		if (type == 'PLAY' || type == 'play') {
			var log = '<tr>'+'<td>'+time+'</td>'+'<td>'+kind+'</td>'+'<td>'+'Request'+'</td>'+'<td>'+decodeURI(requestBody)+'</td>'+'</tr>'
				+'<tr>'+'<td>'+time+'</td>'+'<td>'+kind+'</td>'+'<td>'+'Response'+'</td>'+'<td>'+JSON.stringify(responseJson)+'</td>'+'</tr>';
			if ($('#table_log > tbody > tr').length == 0) {
				$('#table_log > tbody').append(log);
			} else {
				$('#table_log > tbody > tr:first').before(log);
			}

		}
	};
}

var savePolicy = function(callback) {
	var tb_plays = $('#pb_pc input');
	var callbackPolicy = {};
	var playPolicy = {};
	$.each(tb_plays, function(idx, item) {
		playPolicy[$(tb_plays[idx]).attr('id')] = $(tb_plays[idx]).val();
	});
	callbackPolicy.play = playPolicy;
	$.ajax({
		url : '/testpolicy/play',
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
		url : '/testpolicy/play',
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
			var play_k1 = res.policy.k1;
			$.each(play_k1, function(key, value) {
				$('#tb_play_k1_' + key).val(value);
			});
			var play_k3 = res.policy.k3;
			$.each(play_k3, function(key, value) {
				$('#tb_play_k3_' + key).val(value);
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
		$('#player').attr('src', $(this).attr('data-url') + '&uservalue0=test');
	});
});