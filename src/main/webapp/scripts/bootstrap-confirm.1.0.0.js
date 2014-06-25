/* ============================================================
 * bootstrap-confirm.js v1.0.0
 * @author paperen<paperen@gmail.com>
 * @link
 * ============================================================ */
!function( $ ){

	"use strict"

	var Confirm = function (element, options) {
		this.init('confirm', element, options);
	}
	Confirm.prototype = {
		constructor: Confirm
		// 初始化
		,init: function (type, element, options) {
			this.type = type;
			this.$element = $(element);
			this.options = $.extend({}, $.fn[this.type].defaults, options, this.$element.data());
			if ( typeof this.options.action == 'string') {
				if ( this.options.action == '' ) this.options.action = $(element).attr('href');
				if ( typeof this.options.action == 'undefined' ) this.options.action = document.location.href;
			}
			this.$element.on('click.' + this.type, this.options.selector, $.proxy(this.toggle, this));
			this._options = this.options;

			this.initModal();
		}
		// 触发
		,toggle: function(e) {
			e.preventDefault();
			var self = $(e.currentTarget)[this.type](this._options).data(this.type);
			self[self.getModal().hasClass('hide') ? 'show' : 'hide']();
		}
		// 初始化modal的html
		,initModal: function() {
			if ( $('.confirm-modal').length ) return;
			$('body').append( this._options.template );

			var self = this;
			$('.confirm-cancelbtn').bind( 'click', function(){
				self.hide();
			} );
		}
		// 获取modal
		,getModal: function() {
			return $('.confirm-modal');
		}
		// 设置modal标题
		,setModalTitle: function( text ) {
			$(this.getModal()).find('.modal-header h3').html( text );
		}
		// 设置modal内容
		,setModalBody: function( text ) {
			$(this.getModal()).find('.modal-body').html( text );
		}
		// 显示与绑定
		,show: function() {
			var self = this;
			
			//添加前置条件，如果预处理方法返回false，则不调用后续的脚本
			if(typeof self._options.preAction == 'function' ) {
				var ret = self._options.preAction();
				if(!ret) {
					return;
				}
			}
			
			this.setModalTitle( self._options.title );
			this.setModalBody( self._options.message );

			$(this.getModal()).modal('show');

			// bind confirm
			$('.confirm-btn').unbind();
			$('.confirm-btn').bind( 'click', function(e){
				if ( typeof self._options.action == 'function' ) {
					self._options.action();
				} else {
					window.location.href = self._options.action;
				}
			});
		}
		// 隐藏
		,hide: function() {
			$(this.getModal()).modal('hide');
		}
	}

	var old = $.fn.confirm;
	$.fn.confirm = function ( option ) {
		return this.each(function () {
			var $this = $(this)
			, data = $this.data('confirm')
			, options = typeof option == 'object' && option
			if (!data) $this.data('confirm', (data = new Confirm(this, options)));
			if (typeof option == 'string') data[option]();
		});
	}
	$.fn.confirm.Constructor = Confirm;
	$.fn.confirm.defaults = {
		action : '', // 链接或者函数[option]
		title : '系统提示', // modal标题[option]
		message : '确定要执行该操作吗？', // modal内容[option]
		template : '<div class="modal hide fade confirm-modal"><div class="modal-header"><h3></h3></div><div class="modal-body"></div><div class="modal-footer"><a href="#" class="btn btn-success confirm-btn">确定</a><a href="#" class="btn confirm-cancelbtn">取消</a></div></div>'
	}


	$.fn.confirm.noConflict = function () {
		$.fn.confirm = old;
		return this;
	}

}( window.jQuery )