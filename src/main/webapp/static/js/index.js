/**
 * javascript function for index.jsp
 */


var loadUserInForm = function(){
	var accessToken = $('#tb_accessToken').val();
	var accountKey = $('#tb_accountKey').val();
	var secretKey = $('#tb_secretKey').val();
	var userKey = $('#tb_userKey').val();
	var err = 0;
	if (accessToken == undefined || accessToken.trim() == '') {
		$('#fg_accessToken').addClass('has-error has-danger');
		err += 1000;
	} else {
		$('#fg_accessToken').removeClass('has-error has-danger');
	}
	if (accountKey == undefined || accountKey.trim() == '') {
		$('#fg_accountKey').addClass('has-error has-danger');
		err += 100;
	} else {
		$('#fg_accountKey').removeClass('has-error has-danger');
	}
	if (secretKey == undefined || secretKey.trim() == '') {
		$('#fg_secretKey').addClass('has-error has-danger');
		err += 10;
	} else {
		$('#fg_secretKey').removeClass('has-error has-danger');
	}
	if (userKey == undefined || userKey.trim() == '') {
		$('#fg_userKey').addClass('has-error has-danger');
		err += 1;
	} else {
		$('#fg_userKey').removeClass('has-error has-danger');
	}
	if (err != 0) {
		alert('사용자 정보를 올바르게 입력해 주세요');
		return;
	}
	var data = {};
	data.accessToken = accessToken;
	data.accountKey = accountKey;
	data.secretKey = secretKey;
	data.userKey = userKey;
	return data;
}

$(document).ready(function() {
	$('#btn_loadInfo').on('click', function(){
		saveUserInfo(loadUserInForm(), function(res){
			$("#indexLink").removeClass('hide');
			$('#tb_accessToken').attr('disabled', 'disabled');
			$('#tb_accountKey').attr('disabled', 'disabled');
			$('#tb_secretKey').attr('disabled', 'disabled');
			$('#tb_userKey').attr('disabled', 'disabled');
			$('#btn_loadInfo').attr('disabled', 'disabled');
		});
	});
	$('#btn_changeUser').on('click', function() {
		$('#fg_accessToken').removeClass('has-error has-danger');
		$('#fg_secretKey').removeClass('has-error has-danger');
		$('#fg_userKey').removeClass('has-error has-danger');
		$('#fg_accountKey').removeClass('has-error has-danger');
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
	getUserInfo(function(res){
		$('#tb_accessToken').val(res.accessToken);
		$('#tb_accountKey').val(res.accountKey);
		$('#tb_secretKey').val(res.secretKey);
		$('#tb_userKey').val(res.userKey);
	});
});
