/**
 * 
 */

var createPayload = function(){
	var payload = {}
	var cuid = $('#tb_cuid').val();
	var awtc = $('#tb_awtc').val();
	var expt = $('#tb_expt').val();
	if(!isEmpty(cuid) && !isBlank(cuid)){
		payload.cuid = cuid;
	}
	if(!isEmpty(awtc) && !isBlank(awtc)){
		payload.awtc = awtc;
	}
	if(!isEmpty(expt) && !isBlank(expt)){
		payload.expt = parseInt(expt);
	}
	var mcs = $('div[id^="div_mc_"]');
	payload.mc = [];
	for(var idx = 0; idx < mcs.length; idx++){
		var mc = $(mcs[idx]);
		var mckey = mc.find("input.tb_mckey").val();
		var mcpf = mc.find("input.tb_mcpf").val();
		var intr = mc.find("input.cb_intr").is(':checked');
		var seek = mc.find("input.cb_seek").is(':checked');
		var seekable = mc.find("input.tb_seekable").val();
		var playrate = mc.find("input.cb_playrate").is(':checked');
		var item = {};
		if(!isEmpty(mckey) && !isBlank(mckey)){
			item.mckey = mckey;
		}
		if(!isEmpty(mcpf) && !isBlank(mcpf)){
			item.mcpf = mcpf;
		}
		if(intr){
			item.intr = intr;
		}
		if(seek){
			item.seek = seek;
		}
		if(seekable >= 0){
			item.seekable_end = parseInt(seekable);
		}
		if(playrate){
			item.disable = playrate;
		}
		payload.mc.push(item);
		
	}
	return payload;
}
var createToken = function(header, payload, callback){
	var data = {};
	data.header = header;
	data.payload = payload;
	$.ajax({
		url : 'token',
		type : 'post',
		dataType : 'json',
		contentType : 'application/json',
		data : JSON.stringify(data),
		success : function(res) {
			if(res.error){
				result = false;
				alert("create Token Err : " + res.error);
			}
			else{
				callback(res);
			}
		}
	});
}
var decodeToken = function(token, callback){
	var data = {};
	data.jwt = token;
	$.ajax({
		url : 'decodetoken',
		type : 'post',
		dataType : 'json',
		contentType : 'application/json',
		data : JSON.stringify(data),
		success : function(res) {
			if(res.error){
				result = false;
				alert("create Token Err : " + res.error);
			}
			else{
				callback(res);
			}
		}
	});	
}

$(document).ready(function(){
	var header = {};
	header.alg = 'HS256'
	header.typ = 'JWT';
	$('#tt_jwt_header').html(JSON.stringify(header, null, 4))
	$('#btn_c_p').on('click',function(){
		var payload = createPayload();
		$('#tt_jwt_payload').html(JSON.stringify(payload, null, 4));
	});
	$('#btn_c_t').on('click',function(){
		var header = $('#tt_jwt_header').html();
		var payload = createPayload();
		createToken(header, payload, function(res){
			$('#tt_jwt_token').html(res.token);
			$('#btn_p_t').attr('data-url', res.playUrl);
		});
	});
	$('#btn_d_t').on('click',function(){
		var token = $('#tt_jwt_token').html();
		decodeToken(token, function(res){
			$('#tt_jwt_token').html(res.token); 
		});
	});
	$('#btn_addmc').on('click',function(){
		var idx = parseInt($('div[id^="div_mc_"]:last').find('span.span_index').html());
		var next = $('div[id^="div_mc_"]:last').clone();
		next.attr('id', 'div_mc_'+idx);
		next.find('span.span_index').html(idx+1);
		next.find("input.tb_mckey").val('');
		next.find("input.tb_mcpf").val('');
		next.find("input.cb_intr").prop('checked',false);
		next.find("input.cb_seek").prop('checked',false);
		next.find("input.tb_seekable").val(-1);
		next.find("input.cb_playrate").prop('checked',false);
		next.appendTo('.div_mc');
	});
	$('#btn_removemc').on('click',function(){
		var idx = parseInt($('div[id^="div_mc_"]:last').find('span.span_index').html());
		if(idx > 1){
			$('div[id^="div_mc_"]:last').remove();
		}
	});
	$('#btn_p_t').on('click', function(){
		$('#player').attr('src', $('#btn_p_t').attr('data-url'));
	});
	$('#btn_d_t').on('click', function(){
		var token = $('#tt_jwt_token').html();
		decodeToken(token, function(res){
			$('#tt_jwt_header').html(JSON.stringify(res.header, null, 4));
			$('#tt_jwt_payload').html(JSON.stringify(res.payload, null, 4));
			if(!isEmpty(res.error)){
				alert(res.error);
			}
		});
	});
	
});