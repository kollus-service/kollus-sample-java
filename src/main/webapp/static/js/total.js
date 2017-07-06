/**
 * 
 */


var loadLibrary = function(){
	$.ajax({
		url : 'user/info',
		type : 'post',
		dataType : 'json',
		contentType : 'application/json',
		data : JSON.stringify(data),
		success : function(res) {
			var cntLibAll = 0;
			var cntChAll = 0;
			$('#sb_library').html('');
			$.each(res.library, function(idx, item) {
				$('#sb_library').append($('<option>', {
					value : item.key,
					text : item.name + '(' + item.count_of_media_contents + ')'
				}));
				cntLibAll += item.count_of_media_contents;
				$('#sb_category').append($('<option>', {
					value : item.key,
					text : item.name + '(' + item.count_of_media_contents + ')'
				}));
			});
			$('#sb_library option:first').before($('<option>', {
				value : '',
				text : 'All' + '(' + cntLibAll + ')'
			}));
			$('#sb_library option:eq(0)').attr('selected', 'selected');
			$('#sb_channel').html('');
			$.each(res.channel, function(idx, item) {
				$('#sb_channel').append($('<option>', {
					value : item.key,
					text : item.name + '(' + item.count_of_media_contents + ')'
				}));
				cntChAll += item.count_of_media_contents;
			});
			$('#sb_channel option:eq(0)').attr('selected', 'selected');
		}
	}).done(function() {
		$('#sb_library').change();
		$('#sb_channel').change();
		$('#panel_media_list').addClass('in');

	});
}