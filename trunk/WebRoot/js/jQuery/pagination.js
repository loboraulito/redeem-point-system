/**
   * jQuery分页插件
   * 功能：指定页数内静态分页，超过指定页数后ajax请求下一组静态分页
   * @author 陈健
   * @version 1.0 beta
   * @date 2008-04-14
   * @param config 插件配置
   */
jQuery.fn.pagination = function(config){
	init("#"+this.attr("id"),config);
}

/**
   * 初始化，主程序
   * @param t 容器的ID，带#号
   * @param config 插件配置
   */
function init(t,config){
	//公有变量
	var dataStore = config.dataStore;																															//数据
	var totalRecord = config.totalRecord > 0 ? config.totalRecord : 0;																	//总记录数
	if(totalRecord == 0){
		$(t).css("text-align","center");
		$(t).css("line-height","50px");
		$(t).html("很遗憾，没有检索到任何记录！");
		return;
	}
	var configPage = config.perPage > 0 ? config.perPage : 10;																					
	var perPage = $.cookie(t+"_perPage") == null ? configPage : parseInt($.cookie(t+"_perPage"));				//每页显示记录数
	var proxyUrl = config.proxyUrl ? config.proxyUrl : 'pgdataproxy.jsp';															//数据代理地址
	var groupSize = config.groupSize;																																//组大小
	var barPosition = config.barPosition == null ? 'bottom' : config.barPosition;													//工具条位置
	var ajaxParam = config.ajaxParam ? config.ajaxParam : {};//ajax的请求参数

	//私有变量
	var totalPage = Math.ceil(totalRecord/perPage);																									//总页数
	var currentPage = $.cookie(t+"_currentPage") == null ? 1 : parseInt($.cookie(t+"_currentPage"));				//当前页码
	var startRecord;																																								//每页起始记录
	var endRecord;	 																																							//每页结束记录
	var gpStartPage;
	var gpEndPage;
	var gpStartRecord;
	var gpEndRecord;

	//数据容器
	var container = '<div class="pgContainer"></div>'

	//添加工具条
	var toolbar = '<div class="pgToolbar">';
	toolbar += '<div class="pgPanel">';
	toolbar += '<div><select class="pgPerPage">';
	if(config.perPage>0)
		toolbar += '<option value="'+config.perPage+'">'+config.perPage+'</option>';
	toolbar += '<option value="5">5</option>';
	toolbar += '<option value="10">10</option>';
	toolbar += '<option value="15">15</option>';
	toolbar += '<option value="20">20</option>';
	toolbar += '<option value="25">25</option>';
	toolbar += '<option value="40">40</option>';
	toolbar += '</select>&nbsp;</div>';
	toolbar += '<div class="separator"></div>';
	toolbar += '<div class="pgBtn pgFirst" title="首页"></div>';
	toolbar += '<div class="pgBtn pgPrev" title="上页"></div>';
	toolbar += '<div class="separator"></div>';
	toolbar += '<div>第 <input class="pgCurrentPage" type="text" value="' + currentPage + '" title="页码" /> 页 / 共 <span class="pgTotalPage">' + totalPage + '</span> 页</div>';
	toolbar += '<div class="separator"></div>';
	toolbar += '<div class="pgBtn pgNext" title="下页"></div>';
	toolbar += '<div class="pgBtn pgLast" title="尾页"></div>';
	if(groupSize){
		toolbar += '<div class="separator"></div>';
		toolbar += '<div class="pgBtn pgRefresh" title="刷新"></div>';
	}
	toolbar += '<div class="separator"></div>';
	toolbar += '<div class="pgSearchInfo">检索到&nbsp;' + totalRecord + '&nbsp;条记录，显示第&nbsp;<span class="pgStartRecord">' + startRecord + '</span>&nbsp;条&nbsp;-&nbsp;第&nbsp;<span class="pgEndRecord">' + endRecord + '</span>&nbsp;条记录</div>';
	toolbar += '<hr class="cleanFloat" /></div>';
	toolbar += '</div>';

	if(perPage<totalRecord)
		switch(barPosition){
			case 'top':
				$(t).html(toolbar+container);
				break;
			case 'bottom':
				$(t).html(container+toolbar);
				break;
			default:
				$(t).html(toolbar+container+toolbar);
		}
	else
		$(t).html(container);

	var btnRefresh = $(t+" .pgRefresh");														//刷新按钮
	var btnNext =$(t+" .pgNext");																	//下一页按钮
	var btnPrev = $(t+" .pgPrev");																	//上一页按钮
	var btnFirst = $(t+" .pgFirst");																	//首页按钮
	var btnLast = $(t+" .pgLast");																	//末页按钮
	var btnGo = $(t+" .pgNext,"+t+" .pgLast");
	var btnBack = $(t+" .pgPrev,"+t+" .pgFirst");
	var btn = $(t+" .pgFirst,"+t+" .pgPrev,"+t+" .pgNext,"+t+" .pgLast");
	var mask;
	
	var valCurrentPage = $(t+" .pgCurrentPage");
	var valStartRecord = $(t+" .pgStartRecord");
	var valEndRecord =$(t+" .pgEndRecord");
	var valContainer = $(t+" .pgContainer");
	var valPerPage = $(t+" .pgPerPage");
	var valTotalPage = $(t+" .pgTotalPage");

	$(t+" .pgPerPage").attr("value",perPage);
	getGroupStartEnd();
	getStartEnd();
	if(dataStore==null){
		//getRemoteData();
	}
	else{
		getStartEnd();
		loadData();
		refresh();
	}

	//刷新按钮监听
	btnRefresh.bind("mousedown",pressHandler).bind("mouseup",unpressHandler).bind("mouseout",unpressHandler);

	//刷新工具栏
	refresh();
	
	//按钮监听
	btnNext.click(
		function(){
			if(currentPage < totalPage){
				if(!dataStore || currentPage == gpEndPage){
					currentPage += 1;
					getGroupStartEnd();
					getStartEnd();
					getRemoteData();
				}else{
					currentPage += 1;
					getStartEnd();
					loadData();
					refresh();
				}
			}
		}
	);	
	btnPrev.click(
		function(){
			if(currentPage > 1){
				if(!dataStore || currentPage == gpStartPage){
					currentPage -= 1;
					getGroupStartEnd();
					getStartEnd();
					getRemoteData();
				}else{
					currentPage -= 1;
					getStartEnd();
					loadData();
					refresh();
				}
			}
		}
	);
	btnFirst.click(
		function(){
			if(!dataStore || currentPage > 1){
				if(gpStartPage > 1){
					currentPage = 1;
					getGroupStartEnd();
					getStartEnd();
					getRemoteData();
				}else{
					currentPage = 1;
					getStartEnd();
					loadData();
					refresh();
				}
			}
		}
	);
	btnLast.click(
		function(){
			if(!dataStore || currentPage < totalPage){
				if(gpEndPage > 0 && gpEndPage < totalPage){
					currentPage = totalPage;
					getGroupStartEnd();
					getStartEnd();
					getRemoteData();
				}else{
					currentPage = totalPage;
					getStartEnd();
					loadData();
					refresh();
				}
			}
		}
	);
	btnRefresh.click(
		function(){
			getGroupStartEnd();
			getStartEnd();
			getRemoteData();
		}
	);
	
	//页码输入框监听
	valCurrentPage.keydown(
		function(){
			var targetPage = parseInt($(this).val());
			if(event.keyCode==13 && targetPage>=1 && targetPage<=totalPage){
				if(!dataStore || gpStartPage > targetPage || (gpEndPage > 0 && gpEndPage < targetPage)){
					currentPage = targetPage;
					getGroupStartEnd();
					getStartEnd();
					getRemoteData();
				}else{
					currentPage = targetPage;
					getStartEnd();
					loadData();
					refresh();
				}
			}
		}
	);
	
	valPerPage.change(
		function(){
			perPage = parseInt($(this).val());
			currentPage = 1;
			totalPage = Math.ceil(totalRecord/perPage);
			if(groupSize){
				getGroupStartEnd();
				getStartEnd();
				getRemoteData();
			}else{
				getStartEnd();
				loadData();
				refresh();				
			}			
		}
	);
	
	/*********************************init私有函数***************************************************/
	/**
	   * 置为正在检索状态
	   */
	function startLoad(){
		$(t).addClass("container");
		mask = document.createElement('div');
		$(mask).addClass("mask");
		$(mask).css("height",$(t).height());
		$(t).append(mask);
		$(t+" .pgRefresh").addClass("pgLoad");
		$(t+" .pgSearchInfo").html("正在检索中，请稍后...");
	}
	
	/**
	   * 置为结束检索状态
	   */
	function overLoad(){
		$(t+" .pgRefresh").removeClass("pgLoad");
		$(t+" .pgSearchInfo").html('检索到&nbsp;' + totalRecord + '&nbsp;条记录，显示第&nbsp;<span class="pgStartRecord">' + startRecord + '</span>&nbsp;条&nbsp;-&nbsp;第&nbsp;<span class="pgEndRecord">' + endRecord + '</span>&nbsp;条记录');
		$(mask).remove();
		//$(mask).fadeOut("slow");
	}

	/**
	   * 获得远程数据
	   */
	function getRemoteData(){
		startLoad();
		ajaxParam.start = gpStartRecord;
		ajaxParam.limit = gpEndRecord;
		$.ajax(
			{
				type: "POST",
				url: proxyUrl,// + "?startrecord="+gpStartRecord+"&endrecord="+gpEndRecord
				cache: false,
				data: ajaxParam,
				dataType: "script",
				timeout: 30000,
				success: function(msg){
					eval(msg);
					getStartEnd();
					loadData();
					refresh();
					overLoad();
				},
				error: function(){
					alert("请求失败或超时，请稍后再试！");
					overLoad();
					return;
				}
			}
		)
	}
	
	/**
	   * 获得当前页开始结束记录
	   */
	function getStartEnd(){
		if(groupSize){
			startRecord = (currentPage-1)*perPage+1 - gpStartRecord + 1;
			endRecord = Math.min(currentPage*perPage,totalRecord) - gpStartRecord + 1;
		}else{
			startRecord = (currentPage-1)*perPage+1;
			endRecord = Math.min(currentPage*perPage,totalRecord);
		}
	}

	/**
	   * 获得当前组开始结束页码
	   */
	function getGroupStartEnd(){
		if(groupSize==null)
			return;
		var groupId = Math.ceil(currentPage/groupSize);
		gpStartPage = (groupId-1)*groupSize+1;
		gpEndPage = Math.min(groupId*groupSize,totalPage);
		gpStartRecord = (gpStartPage-1)*perPage+1;
		gpEndRecord = Math.min(gpEndPage*perPage,totalRecord);
	}

	/**
	   * 刷新数据容器
	   */
	function loadData(){
		var view = "";
		for(var i=startRecord-1;i<=endRecord-1;i++)
			view += dataStore[i];
		valContainer.html(view);
	}

	/**
	   * 刷新工具栏状态
	   */
	function refresh(){
		//当前页码写入cookie
		$.cookie(t+'_currentPage', currentPage);
		$.cookie(t+'_perPage', perPage);

		valCurrentPage.val(currentPage);
		valStartRecord.html(startRecord);
		valEndRecord.html(endRecord);
		valTotalPage.html(totalPage);
		
		btn.unbind("mousedown",pressHandler);
		btn.bind("mouseup",unpressHandler);
		btn.bind("mouseout",unpressHandler);
		if(currentPage == totalPage){
			enabled();
			btnBack.bind("mousedown",pressHandler);
			btnNext.addClass("pgNextDisabled");
			btnLast.addClass("pgLastDisabled");
		}else	if(currentPage == 1){
			enabled();
			btnGo.bind("mousedown",pressHandler);
			btnPrev.addClass("pgPrevDisabled");
			btnFirst.addClass("pgFirstDisabled");
		}else{
			enabled();
			btnBack.bind("mousedown",pressHandler);
			btnGo.bind("mousedown",pressHandler);
			btnNext.addClass("pgNext");
			btnPrev.addClass("pgPrev");
			btnFirst.addClass("pgFirst");
			btnLast.addClass("pgLast");
		}
	}
	
	/**
	   * 移除按钮disabled状态样式
	   */
	function enabled(){
			btnNext.removeClass("pgNextDisabled");
			btnPrev.removeClass("pgPrevDisabled");
			btnFirst.removeClass("pgFirstDisabled");
			btnLast.removeClass("pgLastDisabled");
	}

	/**
	   * 添加按钮按下状态样式
	   */
	function pressHandler(){
		$(this).addClass("pgPress");
	}

	/**
	   * 移除按钮按下状态样式
	   */
	function unpressHandler(){
		$(this).removeClass("pgPress");
	}

}