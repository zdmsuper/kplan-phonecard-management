(function($){
        //备份jquery的ajax方法
        var _ajax=$.ajax;
        //重写jquery的ajax方法
        $.ajax=function(opt){
            //备份opt中error和success方法
            var fn = {
                error:function(XMLHttpRequest, textStatus, errorThrown){},
                success:function(data, textStatus){},
                complete:function(XMLHttpRequest, textStatus){},
                beforeSend : function(XMLHttpRequest){}
            };
            if(opt.error){
                fn.error=opt.error;
            }
            if(opt.success){
                fn.success=opt.success;
            }
            if(opt.beforeSend){
                fn.beforeSend=opt.beforeSend;
            }
            if(opt.complete){
                fn.complete=opt.complete;
            }
            // //扩展增强处理
            var _opt = $.extend(opt, {
                error: function (xhr,textStatus,errorThrown) {
                    var msg = xhr.responseText;
                    if(typeof msg == 'string') {
                        if (startWith(msg, "<timeout></timeout>")) {
                            $.alert({content:"登录超时，请重新登录！",confirm: function () {
                                location.href='/cpams/login_achieve/login';
                            }});
                            return;
                        } else if (startWith(msg, "<nopower></nopower>")) {
                            $.alert("对不起,您没有操作权限！<br>"+msg.substring(19,msg.length));
                            return;
                        }
                    }
                    fn.error(xhr, textStatus, errorThrown);
                },
                success: function (data, textStatus) {
                    if(data.hasOwnProperty("code") && -1 == data.code){//异常的统一处理
                        $.alert(data.message);
                        return;
                    }
                    //成功回调方法增强处理
                    if(typeof data == 'string'){
                        if (startWith(data, "<timeout></timeout>")) {
                            $.alert({content:"登录超时，请重新登录！",confirm: function () {
                                location.href='/cpams/login_achieve/login';
                            }});
                            return;
                        } else if (startWith(data, "<nopower></nopower>")) {
                            $.alert("对不起,您没有操作权限！<br>"+data.substring(19,data.length));
                            return;
                        }
					}
                    fn.success(data, textStatus);
                },
                beforeSend: function (XHR,obj) {
                    //提交前回调方法
                    $("#home_loading").show();
                    fn.beforeSend(XHR,obj);
                },
                complete: function (XHR, TS) {
                    //请求完成后回调函数 (请求成功或失败之后均调用)。
                    $("#home_loading").hide();
                    fn.complete(XHR, TS);
                }
            });
            return _ajax(_opt);
        };
    })(jQuery);


$(function() {
    //回车提交表单
	//$('...').submit(handler) 有參數，是監聽 submit 事件，不是送出表單

	//$('...').submit() 沒有任何參數，才是觸發 submit 送出表單
    $('form input[type="text"]').bind('keypress', function (event) { 
   	   if (event.keyCode == "13") {
	   		$("form").submit();
   	   }
   })
});