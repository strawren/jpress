jQuery.alertTemplate = '<div class="modal hide fade alert-modal"><div class="modal-header"><button class="close" data-dismiss="modal">x</button><h3>警告</h3></div><div class="modal-body"></div><div class="modal-footer"><a href="#" class="btn btn-success cancelbtn-btn" data-dismiss="modal">确定</a></div></div>';
jQuery.alert=function(title,message) {
	if (!$('.alert-modal').length) {
		$('body').append(jQuery.alertTemplate);
	}
	if(title != '' && title != 'undefined') {
		$(".alert-modal").find('.modal-header h3').html(title);
	}
	if(message != '' && message != 'undefined') {
		$(".alert-modal").find('.modal-body').html(message);
	}
	$('.alert-modal').modal('show');
}
