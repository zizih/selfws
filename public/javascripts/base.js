/**
 * @author rain
 */

$(document).ready(function() {

	$('a').mouseover(function() {

	});

	$('.nav_item').mouseover(function() {
		$(this).css({
			"background-color" : "#222222"
		});
	});

	/*
	 没有发挥作用，怎么了？
	 */
	$('#self_modify').click(function() {
		$(this).className = 'hidden';
		$('#self_save').className = 'black_button';
	});

	$('.nav_item').mouseout(function() {
		$(this).css({
			"background-color" : "black"
		});
	});

	/*
	 modify current user info 也发挥不了作用，怎么了？
	 */
	var modify_button = $('#modify_userinfo');

	var modify_active = function() {
		modify_button.hide();
		$('#save_userinfo').removeClass('hidden');
		$('#save_userinfo').value = '保存';
		$('#abandon_modify_userinfo').removeClass('hidden');
	}
	var modify_recovery = function() {
		$('#save_userinfo').attr('class', hidden);
		$('#save_userinfo').value = '修改';
		$('#abandon_modify_userinfo').attr('class', hidden);
	}

	$('#user_info_table #abandon_modify_userinfo').click(function() {
		modify_recovery();
	});

	modify_button.click(function() {
		$('.t2:hidden').removeClass('hidden');
		$('.t1').hide();
		modify_active();
	});

	$('#user_info_table .t1').click(function() {
		$(this).hide(100, function() {
			$(this).next('.t2:hidden').removeClass('hidden');
			modify_active();
		});
	});

	$('#user_info_table #tp3').click(function() {
		$('.tp3:hidden').removeClass('hidden');
	});

	/*
	 about passwd
	 */
	$('#user_info_table #passwd3').blur(function() {
		if ($(this).val() !== $('#passwd2').val()) {
			$('.prot3').removeClass('hidden');
		}
	});

	/*
	 about other user or your firends
	 */

	$('.showIfFollow').blur(function() {
		$(this).hide();
	});

	$('.login_form').css({
		'height' : $('#user_info_height').outerHeight(true) + 1
	});
	$('#user_avatar_height').css({
		'height' : $('#user_info_height').innerHeight()
	});
	$('#show').css({
		'height' : $('#show_user_info_height').outerHeight(true) + 1
	});
	$('#show_user_avatar_height').css({
		'height' : $('#show_user_info_height').innerHeight()
	});

	$('#avatar_id').change({
		// $('.avatar_img').attr('src') =$(this).val();
		// $('.avatar_img').attr('src',$(this).val());
	});

});
function showIfFollow(event) {
	var x = event.clientX;
	var y = event.clientY;
	$('.showIfFollow').removeClass('hidden');
	$('.showIfFollow').css({
		'position' : 'fixed',
		'top' : x,
		'left' : y,
	});
}

function previewAvatar(img) {
	document.getElementById("avatar_img").filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = img.value;
}

function previewAlbum(img) {
	document.getElementById("album_img").filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = img.value;
}

function imagesSelected(myfiles){
	for(var i=0,f;f=myfile[i];i++){
		var imageReader = new FileReader();
		imageReader.onload=(function(afile){
			return function(e);
			var span = document.createElement("span");
			span.innerHTML= ['<img class="images" src="', e.target.result,'" title="', afile.name, '"/>'].join(”);
			$('#html5filetest').insertBefore(span,null);
		})(f);
	}
}








