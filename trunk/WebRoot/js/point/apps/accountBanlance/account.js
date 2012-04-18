/**
 * 账目管理, 由ext系统移植而来
 */
function accountBalance(){
	//账目明细数据解析器
	var accountReader = new Ext.data.JsonReader({
		totalProperty : "totalCount",
		root : "account"
	},[
		{name:"baseinfoid"},//唯一id
		{name:"baseyear"},//年份
		{name:"basemonth"},//月份
		{name:"basedate"},//具体日期
		{name:"accountenter"},//当天收入
		{name:"accountout"},//当天消费
		{name:"accountmargin"},//当天结算
		{name:"remark"},//备注
		{name:"userid"},//备注
		{name:"username"},//备注
		{name:"deletetag"},//删除标志，1：已删除，0未删除
		{name:"margintag"},//结算标志：1：已结算，0未结算
		{name:"accountalert"},//日警告点
		{name:"accountalertmon"}//月报警点
	]);
	//警报点数据解析器
	var alertReader = new Ext.data.JsonReader({
		totalProperty:"totalAlertCount",
		root:"alerts"
	},[
		{name:"alertid"},
		{name:"alerttype"},//警报类型
		{name:"userid"},//用户id
		{name:"username"},//用户名
		{name:"alertvalue"},//警报值
		{name:"begindate"},//开始日期
		{name:"enddate"},//结束日期
		{name:"remark"}//备注
	]);
	//结算结果数据解析器
	var balanceReader = new Ext.data.JsonReader({
		totalProperty:"totalCount",
		root:"balances"
	},[
		{name:"balanceid"},
		{name:"begindate"},//结算范围起始日期
		{name:"enddate"},//结算范围终止日期
		{name:"balanceyear"},//结算年度
		{name:"balancemonth"},//结算月度
		{name:"balancetype"},//结算类型
		{name:"accountenter"},//结算期间总收入
		{name:"accountout"},//结算期间总支出
		{name:"accountmargin"},//差额
		{name:"alertvalue"},//年度结算警告点
		{name:"balance"},//盈利
		{name:"remark"},//备注
		{name:"userid"},//用户id
		{name:"username"}//用户名
	]);
	
	//查看权限数据解析器
	var rightReader = new Ext.data.JsonReader({
		totalProperty:"totalCount",
		root:"rights"
	},[
		{name:"balancerightid"},
		{name:"userid"},//用户id
		{name:"username"},//用户名
		{name:"allowuserid"},//用户名
		{name:"allowusername"}//用户名
	]);
	
	// define a custom summary function
	// 定义自定义结算函数
    Ext.grid.GroupSummary.Calculations['totalCost'] = function(v, record, field){
        return getRound(0 + parseFloat(parseFloat(v) + (parseFloat(record.data.accountenter) - parseFloat(record.data.accountout))),2);
    };
    Ext.grid.GroupSummary.Calculations['currentMonth'] = function(v, record, field){
        return dateFormat(record.data.basemonth,'Y-m',"Y 年 m 月")+" 小结";
    };
    Ext.grid.GroupSummary.Calculations['alertMonth'] = function(v, record, field){
        return record.data.accountalertmon;
    };
    Ext.grid.GroupSummary.Calculations['marginTag'] = function(v, record, field){
        return (record.data.margintag);
    };
    Ext.grid.GroupSummary.Calculations['balanceYear'] = function(v, record, field){
        return (record.data.balanceyear) +" 年度总结";
    };
    
    
}

/**
 * 程序主入口
 */
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';
	accountBalance();
});