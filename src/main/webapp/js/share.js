/**
 * 微信分享组件
 * @author helijun
 * @return {[type]}   [description]
 */
(function($, alertify) {
 	//分享
	$.share = function(opts,wx,rootPath) {
		var options = {
	        	currentUrl: window.location.href.split('#')[0],
	        	imgUrl: null,
	        	title: '图书分享',
	        	desc: null,
	        	shareUrl: null,
	        }
		
		$.extend(true, options, opts || {});
		
		//判断是否已经授权
		if(!wechatShare.options.isSignature){
			$.signature(wx,opts,options.currentUrl,rootPath,$.share)
		}else{
			wx.ready(function(){
	            //分享到朋友圈
	            wx.onMenuShareTimeline({
	                title: options.title,
	                link: options.shareUrl,
	                imgUrl: options.imgUrl,
	                success: options.success
	            });

	            //分享给朋友
	            wx.onMenuShareAppMessage({
	                title: options.title,
	                desc: options.desc,
	                link: options.shareUrl,
	                imgUrl: options.imgUrl,
	                success: options.success
	            });
	            
	            // 分享到QQ
	            wx.onMenuShareQQ({
	                title: options.title, // 分享标题
	                desc: options.desc, // 分享描述
	                link: options.shareUrl, // 分享链接
	                imgUrl: options.imgUrl, // 分享图标
	                success: options.success,
	                cancel: function () { 
	                   // 用户取消分享后执行的回调函数
	                }
	            });
	            
	            // 分享到微博
	            wx.onMenuShareWeibo({
	                title: options.title, // 分享标题
	                desc: options.desc, // 分享描述
	                link: options.shareUrl, // 分享链接
	                imgUrl: options.imgUrl, // 分享图标
	                success: options.success,
	                cancel: function () { 
	                    // 用户取消分享后执行的回调函数
	                }
	            });
	            
	            // 分享到QQ空间
	            wx.onMenuShareQZone({
	                title: options.title, // 分享标题
	                desc: options.desc, // 分享描述
	                link: options.shareUrl, // 分享链接
	                imgUrl: options.imgUrl, // 分享图标
	                success: options.success,
	                cancel: function () { 
	                    // 用户取消分享后执行的回调函数
	                }
	            });
	        });
		}
		
	}

	//jsSDK授权
	$.signature = function(wx,opts,currentUrl,rootPath,callback){

		$.ajax({
            data: {url: currentUrl},
            type: "GET",
            url: rootPath + "/getBasicConfig",
            success: function (json) {
                if (json) {
                    var data = JSON.parse(json);
                    
                    wx.config({
                        debug: false,
                        appId: data.appid,
                        timestamp: data.timestamp,
                        nonceStr: data.nonceStr,
                        signature: data.signature,
                        jsApiList: [
                        	'onMenuShareTimeline',
                            'onMenuShareAppMessage',
                            'onMenuShareQQ',
                            'onMenuShareWeibo',
                            'onMenuShareQZone'
                        ]
                    });
                    
                    wechatShare.options.isSignature = true;
                    
                    callback && callback(opts,wx);
                }
            }
        });
	}

	var wechatShare = {
        init: function(){
            var self = wechatShare;
            
        },
        options: {
            isSignature: false
        },

        render: function(){
            var self = wechatShare;
        }
    }

})(jQuery, alertify);