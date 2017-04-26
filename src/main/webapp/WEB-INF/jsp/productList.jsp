<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<!-- Required meta tags always come first -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>图书列表</title>
<!-- Bootstrap CSS -->
<link href="<%=path%>/bootstrap/css/bootstrap.css" rel="stylesheet"
	type="text/css" media="all" />
<link href="<%=path%>/css/reset.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="<%=path%>/css/style.css" rel="stylesheet" type="text/css"
	media="all" />
</head>
<body>
	<!-- 顶部导航 -->
	<div class="top">
		<!-- 按钮 -->
		<nav class="navbar navbar-light bg-faded">
			<div style="float:left;">
		    	<a href="<%=path %>/toProductAdd" class="add_btn"><img src="<%=path%>/images/add.png" /></a>
			</div>
			<div class="input-group" style="margin: 0 auto">
			    	<input type="text" class="form-control" placeholder="请输入图书关键字" value="${search }" />
			      	<span><a href="javascript:void(0);" class="search_btn"><img src="<%=path%>/images/search.png" /></a></span>
		      	</div>
			<button class="navbar-toggler nav_btn" type="button">
				<img src="<%=path%>/images/icon/more.png">
			</button>
		</nav>
		<!-- 分类 -->
		<div class="nav_pane">
			<div class="container">
				<ul class="nav_ul">
					<div class="row" id="productType">
					</div>
				</ul>
			</div>
		</div>
		<!-- 轮播图 -->
		<div class="mid_silde">
			<div id="carousel-example-generic" class="carousel slide"
				data-ride="carousel">
				<ol class="carousel-indicators">
					<li data-target="#carousel-example-generic" data-slide-to="0"
						class="active"></li>
					<li data-target="#carousel-example-generic" data-slide-to="1"></li>
				</ol>
				<div class="carousel-inner" role="listbox">
					<div class="carousel-item active">
						<img src="<%=path%>/images/1.jpg"
							data-src="holder.js/900x500/auto/#777:#555/text:First slide"
							alt="First slide">
					</div>
					<div class="carousel-item">
						<img src="<%=path%>/images/2.jpg"
							data-src="holder.js/900x500/auto/#666:#444/text:Second slide"
							alt="Second slide">
					</div>
				</div>
				<a class="left carousel-control" href="#carousel-example-generic"
					role="button" data-slide="prev"> <span class="icon-prev"
					aria-hidden="true"></span>
				</a> <a class="right carousel-control" href="#carousel-example-generic"
					role="button" data-slide="next"> <span class="icon-next"
					aria-hidden="true"></span>
				</a>
			</div>
		</div>
	</div>
	<div class="container">
		<!-- 页面中部 -->
		<div class="middle container-fluid">
			<!-- 商品卡片 -->
			<div class="mid_card">
				<div class="row card-group">
					<c:if test="${empty pageBean.pagelist}">
						<div class="container">
							<div class="jumbotron">
								<h1>暂无图书</h1>
								<p>
									没有图书数据呦     o(>﹏<)o不要啊      (°ー°〃)
								</p>
							</div>
						</div>
					</c:if>
					<c:forEach items="${pageBean.pagelist}" var="product">
						<div class="col-md-3 col-xs-6">
							<div class="card">
								<a href="${pageContext.request.contextPath}/toProductDetail/${product.productId}">
									<div class="card_img">
										<img class="card-img-top center-block"
											src="<%=path%>${product.fileSrcs[0]}"
											data-src="holder.js/80px180?text=走丢了Y.Y" alt="图片走丢了">
									</div>
									<div class="card-block">
										<p class="card-title">${product.productName}</p>
									</div>
								</a>
								<p class="card-text">作者：${product.author }</p>
							</div>
						</div>
					</c:forEach>
				</div>
				<!-- 分页插件 -->
				<div id="pagination">
					<a href="<%=path %>/toProductList?page=${pageBean.page+1}&search=${search}" class="next">next</a>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	<jsp:include page="foot.jsp"></jsp:include>
	
	<script src="<%=path%>/js/jquery.min.js"></script>
	<script src="<%=path%>/bootstrap/js/bootstrap.js"></script>
	<script src="<%=path%>/js/holder.js"></script>
	<script src="<%=path%>/js/jquery-ias.min.js"></script>
	
	<script type="text/javascript">
		 // 初始化图书类别信息
		$.get("initProductType", function(data) {
			$.each(data, function(i, item) {
					$("#productType").append(
							"<li class='col-xs-4'>" 
						   +"	<a href='<%=path %>/toProductCateList/"+item.productTypeId+"'>"
						   +"		<img src='<%=path%>/images/list/"+item.productTypeId+".jpg' /> "+ item.productTypeName
						   +"	</a>"
						   +"</li>"
					);
				});
		}, "json");
	
		$(".nav_btn").click(function(event) {
			/* Act on the event */
			$(".nav_pane").toggle().css('height', $("body").height());
			$(".bottom").toggle();
		});
		$('.search_btn').click(function() {
			var search = $(this).closest('.input-group').children("input").val();
			if(search.length != 0) {
				window.location.href = '${pageContext.request.contextPath}/toProductList?search='+search;
				var pageA = $('#pagination > a');
				pageA.attr("href", pageA.href+"?search="+search);
			} else {
				window.location.href = '${pageContext.request.contextPath}/toProductList';
			}
		});
		$(function() {
			/* body... */
			var img_width = $(".card-img-top").width();
			$(".card_img").css('max-height', img_width);
			
		   var ias = $.ias({
			  container:  '.card-group',
			  item:       '.col-xs-6',
			  pagination: '#pagination',
			  next:       '.next',
			  delay: 1200
		    });
		
		    // Add a loader image which is displayed during loading
			ias.extension(new IASSpinnerExtension());

			// Add a link after page 2 which has to be clicked to load the next page
			ias.extension(new IASTriggerExtension({
				text: '点击加载更多= =', offset: 2
			}));
			
			
			// Add a text when there are no more pages left to load
			ias.extension(new IASNoneLeftExtension({
			    text: '已经到底了', // optionally
			}));
			
		  /* 
			$('#horizontalTab').easyResponsiveTabs({
				type : 'default', //Types: default, vertical, accordion
				width : 'auto', //auto or any width like 600px
				fit : true // 100% fit in a container
			}); */
		});
	</script>	
</body>
</html>