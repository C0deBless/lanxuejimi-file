/// <reference path="jquery-1.4.1.min.js" />
jQuery.fn
		.extend({
			// validate说明：options{ type : 'type' , value : 'value' }
			// type：minValue,maxValue,minLength,maxLength,required ,email
			// value: 要比较的值
			// options{ type : 'server' , url : 'url' , keyname : 'keyname' }
			// type : server表示从服务器端获取数据
			// url : 表示服务器端提供验证服务的url
			// keyname : 表示往服务器端提交字段的名称
			// 服务器端返回格式：若验证正确 返回--true
			// 若验证不正确 返回--false
			validate : function(options, message, messageTargetId, callback) {
				var isValidate = false;
				this
						.each(function() {
							var targetValue = 0;
							var errorMessage;
							var rightMessage;
							if (message.errorMessage != null) {
								errorMessage = message.errorMessage;
							} else {
								errorMessage = "";
							}
							if (message.rightMessage != null) {
								rightMessage = message.rightMessage;
							} else {
								rightMessage = "";
							}
							if ($(this).val() != null) {
								targetValue = $(this).val();

							} else if ($(this).html() != null) {
								targetValue = $(this).html();
							}
							switch (options.type) {
							case 'minValue':
								if (targetValue < options.value) {
									$("#" + messageTargetId).html(errorMessage);
									$("#" + messageTargetId).removeClass(
											"rightmessage");
									$("#" + messageTargetId).addClass(
											"errormessage");
									isValidate = false;
								} else {
									$("#" + messageTargetId).html(rightMessage);
									$("#" + messageTargetId).removeClass(
											"errormessage");
									$("#" + messageTargetId).addClass(
											"rightmessage");
									isValidate = true;
								}
								break;
							case 'maxValue':
								if (targetValue > options.value) {
									$("#" + messageTargetId).html(errorMessage);
									$("#" + messageTargetId).removeClass(
											"rightmessage");
									$("#" + messageTargetId).addClass(
											"errormessage");
									isValidate = false;
								} else {
									$("#" + messageTargetId).html(rightMessage);
									$("#" + messageTargetId).removeClass(
											"errormessage");
									$("#" + messageTargetId).addClass(
											"rightmessage");
									isValidate = true;
								}
								break;
							case 'minLength':
								if (targetValue.length < options.value) {
									$("#" + messageTargetId).html(errorMessage);
									$("#" + messageTargetId).removeClass(
											"rightmessage");
									$("#" + messageTargetId).addClass(
											"errormessage");
									isValidate = false;
								} else {
									$("#" + messageTargetId).html(rightMessage);
									$("#" + messageTargetId).removeClass(
											"errormessage");
									$("#" + messageTargetId).addClass(
											"rightmessage");
									isValidate = true;
								}
								break;
							case 'Length':
								if (targetValue.length = options.value) {
									$("#" + messageTargetId).html(errorMessage);
									$("#" + messageTargetId).removeClass(
											"rightmessage");
									$("#" + messageTargetId).addClass(
											"errormessage");
									isValidate = false;
								} else {
									$("#" + messageTargetId).html(rightMessage);
									$("#" + messageTargetId).removeClass(
											"errormessage");
									$("#" + messageTargetId).addClass(
											"rightmessage");
									isValidate = true;
								}
								break;
							case 'maxLength':
								if (targetValue.length < options.value) {
									$("#" + messageTargetId).html(rightMessage);
									$("#" + messageTargetId).removeClass(
											"errormessage");
									$("#" + messageTargetId).addClass(
											"rightmessage");
									isValidate = true;
								} else {
									$("#" + messageTargetId).html(errorMessage);
									$("#" + messageTargetId).removeClass(
											"rightmessage");
									$("#" + messageTargetId).addClass(
											"errormessage");
									isValidate = false;
								}
								break;
							case 'email':
								var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
								if (!myreg.test(targetValue)) {
									$("#" + messageTargetId).html(errorMessage);
									$("#" + messageTargetId).removeClass(
											"rightmessage");
									$("#" + messageTargetId).addClass(
											"errormessage");
									isValidate = false;
								} else {
									$("#" + messageTargetId).html(rightMessage);
									$("#" + messageTargetId).removeClass(
											"errormessage");
									$("#" + messageTargetId).addClass(
											"rightmessage");
									isValidate = true;
								}
								break;
							case 'required':
								if (targetValue.length <= 0) {
									$("#" + messageTargetId).html(errorMessage);
									$("#" + messageTargetId).removeClass(
											"rightmessage");
									$("#" + messageTargetId).addClass(
											"errormessage");
									isValidate = false;
								} else {
									$("#" + messageTargetId).html(rightMessage);
									$("#" + messageTargetId).removeClass(
											"errormessage");
									$("#" + messageTargetId).addClass(
											"rightmessage");
									isValidate = true;
								}
								break;
							}
						}

						);
				return isValidate;
			}
		});