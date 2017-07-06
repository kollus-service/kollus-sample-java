/**
 * 
 */
var progressTimer;

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




$(document).ready(function(){
	getUserInfo(function(res){
		$('#tb_access_token').val(res.accessToken);
	});
	
	$('#btn_category').on('click', function(){
		getLibrary(function(res){
			var cntCateAll = 0;
			$('#sb_category').html('');
			$.each(res, function(idx, item) {
				$('#sb_category').append($('<option>', {
					value : item.key,
					text : item.name + '(' + item.count_of_media_contents + ')'
				}));
				cntCateAll += item.count_of_media_contents;
			});
			$('#sb_category option:eq(0)').attr('selected', 'selected');
		});
	});
	
	$('#form_upload').bind('ajax:complete', function() {
		return false;
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