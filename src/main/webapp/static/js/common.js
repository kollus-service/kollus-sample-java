/**
 * common js function for kollus example
 */

var isEmpty = function(src) {
	return (!src || 0 === src.length);
}
var isBlank = function(src) {
	return (!src || /^s*$/.test(src));
}
String.prototype.isEmpty = function() {
	return (this.length === 0 || !this.trim());
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
var getUserInfo = function(callback) {
	$.ajax({
		url : 'user',
		type : 'get',
		dataType : 'json',
		contentType : 'application/json; charset=UTF-8',
		success : function(res) {
			if (res.error) {
				alert("user infomation load err : " + res.error);
			} else {
				callback(res);
			}
		}
	});
}
var saveUserInfo = function(loadUserInfo, callback) {
	var result = false;
	var data = loadUserInForm();
	$.ajax({
		url : 'user',
		type : 'post',
		dataType : 'json',
		contentType : 'application/json; charset=UTF-8',
		data : JSON.stringify(data),
		success : function(res) {
			if (res.error) {
				result = false;
				alert("user infomation save err : " + res.error);
			} else {
				callback(res);
			}
		}
	});
}
var getLibrary = function(callback) {
	$.ajax({
		url : 'category',
		type : 'get',
		dataType : 'json',
		contentType : 'application/json; charset=UTF-8',
		success : function(res) {
			if (res.error) {
				alert("user infomation load err : " + res.error);
			} else {
				callback(res);
			}
		}
	});

}

var getChannel = function(callback) {
	$.ajax({
		url : 'channel',
		type : 'get',
		dataType : 'json',
		contentType : 'application/json; charset=UTF-8',
		success : function(res) {
			if (res.error) {
				alert("user infomation load err : " + res.error);
			} else {
				callback(res);
			}
		}
	});
}
var getLibraryItem = function(categoryKey, page, callback) {
	var _url = 'media/category/' + categoryKey;
	var data = {};
	data.page = page;
	$.ajax({
		url : url,
		type : 'get',
		data : data,
		success : function(res) {
			callback(res);
		}
	});
}

var getChannelItem = function(channelKey, page, callback) {
	var _url = 'media/channel/' + channelKey;
	var data = {};
	data.page = page;
	$.ajax({
		url : _url,
		type : 'get',
		data : data,
		success : function(res) {
			callback(res);
		}
	});
}

