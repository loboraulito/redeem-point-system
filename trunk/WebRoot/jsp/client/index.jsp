<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <% String path = request.getContextPath(); %>
        <title>Insert title here</title>
    </head>
	<link rel="stylesheet" type="text/css" href="<%=path%>/js/jQuery/ux/myPaginationV3.0/css/page.css">
	<!-- <link rel="stylesheet" type="text/css" href="<%=path%>/js/jQuery/ux/myPaginationV3.0/css/style.css"> -->
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/jQuery/pagination.css">
	<script type="text/javascript">
        var path = "<%=path%>";
    </script>
    <script src="<%=path%>/js/jQuery/jquery-1.6.2.js"></script>
	<script src="<%=path%>/js/jQuery/jquery.cookie-min.js"></script>
	<script src="<%=path%>/js/jQuery/ux/myPaginationV3.0/js/myPagination.js"></script>
	<!--  <script src="<%=path%>/js/jQuery/ux/myPaginationV3.0/js/myPagination.min.js"></script> -->
	<!-- <script src="<%=path%>/js/jQuery/pagination.js"></script> -->
    <script src="<%=path%>/js/point/client/test.js"></script>
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
    	<div id="loadimg">Loading....</div>
		<div id="mainpagediv"></div>
		<div id="pagionbar"></div>
    </body>
</html>
