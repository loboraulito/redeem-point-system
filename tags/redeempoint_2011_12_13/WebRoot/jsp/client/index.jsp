<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <% //String path = request.getContextPath(); %>
		<%@include file="/jsp/common/config.jsp" %>
        <title>Insert title here</title>
    </head>
	<link rel="stylesheet" type="text/css" href="<%=path%>/js/jQuery/ux/myPaginationV3.0/css/page.css">
	<!-- <link rel="stylesheet" type="text/css" href="<%=path%>/js/jQuery/ux/myPaginationV3.0/css/style.css"> -->
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/jQuery/pagination.css">
	<script type="text/javascript">
        var path = "<%=path%>";
    </script>
    <script src="<%=path%>/js/jQuery/jquery-1.6.2.js" type="text/javascript"></script>
	<script src="<%=path%>/js/jQuery/jquery.cookie-min.js" type="text/javascript"></script>
	<script src="<%=path%>/js/jQuery/ux/myPaginationV3.0/js/myPagination.js" type="text/javascript"></script>
	<!--  <script src="<%=path%>/js/jQuery/ux/myPaginationV3.0/js/myPagination.min.js"></script> -->
	<!-- <script src="<%=path%>/js/jQuery/pagination.js"></script> -->
    <script src="<%=path%>/js/util/PopBox/scripts/PopBox.js" type="text/javascript"></script>
	<script src="<%=path%>/js/util/imgReady.js" type="text/javascript"></script>
	<script type="text/javascript">
		popBoxShowRevertBar = false;
		popBoxShowRevertText = false;
		popBoxShowRevertImage = false;
	</script>
	<script src="<%=path%>/js/point/client/test.js" type="text/javascript"></script>
    <style type="text/css">
    	ul.mainProductList { }
		ul.mainProductList li { float:left; text-align:center; height:160px; list-style:none; }
		ul.mainProductList li.p2 { width:49.5%; }
		ul.mainProductList li.p3 { width:33%; }
		ul.mainProductList li.p4 { width:24.5%; }
		ul.mainProductList li.p5 { width:19.5%; }
		ul.mainProductList li.p6 { width:16.5%; }
		ul.mainProductList li.p7 { width:14%; }
		ul.mainProductList li.p8 { width:12.4%; }
		ul.mainProductList li .listItem { padding:10px 5px 0; }
		ul.mainProductList li .listItem .productImg { }
		ul.mainProductList li .listItem .productImg img { width:70px; height:70px; }
		ul.mainProductList li .listItem h4 { padding:5px 15px 0; font-weight:normal; height:36px; }
		ul.mainProductList li .listItem h4 a span { color:#f00; }
    </style>
	<body>
		<%@include file="/jsp/common/header.jsp" %>
		<div id="loadimg" style="top:50%;left:40%;z-index:9999;position: absolute;width:40%;height:10%"><img src="<%=path%>/js/jQuery/ux/myPaginationV3.0/images/loading.gif"></img>正在加载中，请稍候...</div><!-- loading -->
		<div id="maskDiv" style="top:0;left:0;z-index:9998;position:absolute;width:100%;height:100%;background-color:#c8c8c8;"></div><!-- 遮罩层 -->
		<div id="giftCart" style="height:20px;z-index:1">礼品兑换车</div><!-- 礼品兑换车 -->
		<div id="mainpagediv" style="overflow-y:auto; overflow-x:hidden;width:100%;z-index:1"></div><!-- 礼品列表 -->
		<div id="pagionbar" style="z-index:1"/><!-- 分页插件 -->
    </body>
</html>
