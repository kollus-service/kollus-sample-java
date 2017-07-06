/**
 * http://usejsdoc.org/
 */

var source='http://v.kr.kollus.com/s?jwt=eyJhbGciOiAiSFMyNTYiLCJ0eXAiOiAiSldUIn0.eyJjdWlkIjogImhkeWFuZzIiLCJleHB0IjogMTQ5NjQxODIwODgwOSwibWMiOiBbeyJtY2tleSI6Ik5WNVE4SFpxIn1dfQ.7bk7w4TLks5rZWd-4ItSsGzo-zYa4dR4tptYs2jGQus&custom_key=08862128becad487b83a497cffcb58fe13a884b331f3d04a1ad730654ef3bf1943da97d8b2af523e964d3272f25e004a5eac138d0dfc3a264d723a1cf552af9b';
$('#callback_jwt').on('click', function(){
	$("#player").attr('src', source);
});

$('#callback_mt').on('click', function(){
	$("#player").attr('src', source+'&uservalue0=mt');
});

$('#callback_jwt_dl').on('click', function(){
	$("#player").attr('src', source+'&download');
});

$('#callback_mt_dl').on('click', function(){
	$("#player").attr('src', source+'&download&uservalue0=mt');
});
