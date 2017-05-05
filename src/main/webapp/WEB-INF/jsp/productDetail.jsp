<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="UTF-8">
		<!-- Required meta tags always come first -->
    	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
		<meta http-equiv="x-ua-compatible" content="ie=edge">
		<title>图书详情</title>
		<!-- Bootstrap CSS -->
		<link rel="stylesheet" href="<%=path %>/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="<%=path %>/css/lightgallery.css">
		<link rel="stylesheet" href="<%=path %>/css/reset.css">
		<link rel="stylesheet" href="<%=path %>/css/weui.css">
		<link rel="stylesheet" href="<%=path %>/css/style.css">
		<link rel="stylesheet" href="<%=path %>/css/alertify.css">
	</head>
	<body>
		<!-- 顶部导航 -->
		<div class="top">
			<!-- 按钮 -->
			<nav class="navbar navbar-light bg-faded pro_nav">
				<a href="javascript:history.go(-1);"><div class="go_back"></div></a>
				<div class="container">图书详情</div>
			</nav>
		</div>
		<div class="about_mid pro_more">
			<div class="user_top">
				<div class="user_info">
					<div class="user_photo">
						<img src="${productInfo.userInfo.userHeadImgUrl}"/>
					</div>
					<div class="user_name">
						<span>${productInfo.userInfo.userNickName}</span>
					</div>
				</div>
				<div class="info_right">
					<div class="pro_tag">
						<span>${productInfo.productName}</span>
					</div>
					<div class="price_area">
						<span class="pro_price">￥${productInfo.price}</span>
					</div>
				</div>
			</div>
			
			<div class="weui-form-preview__bd">
				 <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">作者</label>
                    <span class="weui-form-preview__value">${productInfo.author }</span>
                </div>
				 <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">类型</label>
                    <span class="weui-form-preview__value">${productInfo.productType.productTypeName }</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">发布日期</label>
                    <span class="weui-form-preview__value"><fmt:formatDate value="${productInfo.pbDate}" type="date" pattern="yyyy-MM-dd"/></span>
                </div>
				<div class="price_loc">
					<div class="user_loc">
						<div class="loc_img"></div>
						<div class="loc_area">${productInfo.userInfo.userProvince}${productInfo.userInfo.userCity}</div>
					</div>
				</div>
			</div>
			<div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">商品描述</h3>
                </div><!-- /.box-header -->
                <div class="box-body no-padding">
                  <div class="mailbox-read-message">
                  	${productInfo.context }
                  </div><!-- /.mailbox-read-message -->
                </div><!-- /.box-body -->
                <div class="box-footer">
                  <ul class="mailbox-attachments clearfix" id="lightgallery">
					<c:forEach items="${productInfo.fileSrcs }" var="img">
						<a href="<%=path%>${img}">
							<span class="mailbox-attachment-icon has-img"><img src="<%=path%>${img}" data-src="holder.js/100px100p?text=走丢了Y.Y" alt="图片走丢了"></span>
						</a>
					</c:forEach>
                  </ul>
                </div><!-- /.box-footer -->
                <div class="box-footer">
                  <div class="pull-xs-right">
                    <button class="btn btn-success btn-block" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-shopping-cart"></i> 加入购物车</button>
                  </div>
                  <button class="btn btn-success btn-default" id="collection" data-favorite="${null != favoriteId ? true : false}"><i class="glyphicon ${null != favoriteId ? 'glyphicon-heart' : 'glyphicon-heart-empty'}"></i> 收藏</button>
                </div><!-- /.box-footer -->
              </div><!-- /. box -->
		</div>
		
		<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">确认购买</h4>
				</div>
				<div class="modal-body">
				        <div class="container">
				           <p>请选择购买数量：</p><span class="tag_name">库存：${productInfo.number }</span>
				           <div class="input-group"> 
								<div class="quantity-select">                           
									<div class="entry value-minus">&nbsp;</div>
									<div class="entry value">1</div>
									<div class="entry value-plus active" data-number="${productInfo.number }">&nbsp;</div>
								</div>
							</div>
				        </div>
				</div>
				<div class="modal-footer">
					<button type="submit" id="addToCar" class="btn btn-success btn-default pull-left"><span class="glyphicon glyphicon-shopping-cart"></span>确认</button>
					<button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span>取消</button>
				</div>
			</div>
		</div>
	</div>
		
		<script src="<%=path %>/js/jquery.min.js"></script>
		<script src="<%=path %>/bootstrap/js/bootstrap.js"></script>
		<script src="<%=path %>/js/lightgallery.js"></script>
		<script src="<%=path%>/js/holder.js"></script>
		<script src="<%=path%>/js/alertify.js"></script>
		<script src="<%=path%>/js/jweixin-1.0.0.js"></script>
		<script src="<%=path%>/js/share.js"></script>
		<script type="text/javascript">
			// 获取链接参数
			function getQueryString(name) {
				var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
				var r = window.location.search.substr(1).match(reg);
				if (r != null) {
				return unescape(r[2]);
				}
				return null;
			};
			$(function() {
				var intoURI = document.referrer;
				if(intoURI.includes('toUserSaling')) {
					$('#buyBtn').remove();
				};
				
				var ua = navigator.userAgent.toLowerCase(),
			    isWechat = ua.indexOf('micromessenger') != -1;//判断是否为微信浏览器

			    var firstShareUserId = getQueryString('firstShareUserId'),
			    shareUrl = window.location.href.split('#')[0];
			    
			    if(firstShareUserId == null) {
			    	shareUrl = shareUrl + "?firstShareUserId=${sessionScope.userInfo.userId}";
			    } else {
			    	shareUrl = shareUrl + "?firstShareUserId=" + firstShareUserId + "&shareUserId=${sessionScope.userInfo.userId}";
			    }
				var shareData = {
				　　　　title: '分享图书《${productInfo.productName}》',
				　　　　desc: '微信分享给朋友，你将获得积分，如果你的朋友通过该链接购买书籍，图书分享者也会得到积分。^_~',
				     imgUrl: '${productInfo.userInfo.userHeadImgUrl}',
				     // 微信分享链接
				     shareUrl: shareUrl,
				     // 其他分享链接
				　　　　success: function(){
						$.ajax({
				            data: firstShareUserId || {firstShareUserId : '${sessionScope.userInfo.userId}', shareUserId: firstShareUserId} && {firstShareUserId : '${sessionScope.userInfo.userId}'},
				            type: "GET",
				            dataType: 'json',
				            url:  "<%=path%>/updateUserPoints",
				            success: function (result) {
				            	if(result.type == "success") {
				            		alertify.success(result.msg);
				            	} else {
				            		alertify.error(result.msg);
				            	}
				            }
				        });
				      }
			　　　　}
				//微信浏览器分享加载
				if(isWechat){
				   $.share(shareData,wx,'<%=path%>');
				}                         
			});
			
			$('#lightgallery').lightGallery();
			
			<!-- 添加收藏 -->
			$('#collection').on('click', function(){
				var $collection = $(this),  isFavorite = $collection.data("favorite");
				$.ajax({
					data: {bookId: '${productInfo.productId}', isFavorite: isFavorite},
		            type: "GET",
		            dataType: 'json',
		            url:  "<%=path%>/updateUserFavorite",
		            success: function (result) {
		            	if(result.type == "success") {
		            		if(isFavorite) {
		            			$('i', $collection).attr("class", "glyphicon glyphicon-heart-empty");
		            			$collection.data("favorite", false);
		            		}else {
		            			$('i', $collection).attr("class", "glyphicon glyphicon-heart");	
		            			$collection.data("favorite", true);
		            		}
		            	} else {
		            		alertify.error(result.msg);
		            	}
		            }
		        });
			});
			
			<!--quantity-->
			$('.value-plus').on('click', function(){
				var divUpd = $(this).parent().find('.value'), newVal = parseInt(divUpd.text(), 10)+1;
				if(newVal > $(this).data("number")) {
					alertify.error('图书数量超过库存！');
					return;
				}
				divUpd.text(newVal);
			});

			$('.value-minus').on('click', function(){
				var divUpd = $(this).parent().find('.value'), newVal = parseInt(divUpd.text(), 10)-1;
				if(newVal>=1) divUpd.text(newVal);
			});
			<!--quantity-->
			
			$("#addToCar").click(function() {
				var num = $(".value").text();
				window.location.href = "${pageContext.request.contextPath}/addToCar/${productInfo.productId}?num=" + num + "";
			});
		</script>
	</body>
</html>