/**
 * javascript function for query.jsp
 */

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
	return !isEmpty(queryString) ? '&' + queryString : queryString;
}

$(document).ready(function() {
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
	$('#ulChannel').on('click', 'li.media', function() {
		$(this).parent().find('li.media').removeClass('selectedli');
		$(this).addClass('selectedli');
	});
	$('#btn_p').on('click', function() {
		var qs = makeQueryString();
		var url = $('#ulChannel > li.media.selectedli').attr('data-url');
		$('#player').attr('src', url + qs);
		$('#tt_url').html($('#player').attr('src'));
	});
	$('#btn_c').on('click', function() {
		initControl();
	});

	$('div.input-group-btn ul.dropdown-menu li a, ' + 'div.btn-group ul.dropdown-menu li a').on('click', function() {
		var index = $(this).parent().parent().parent().find('ul').index($(this).parent().parent());
		$(this).parent().parent().parent().find('button').eq(index - 1).find('span.btn-span-ellipsis').html($(this).text());
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

});