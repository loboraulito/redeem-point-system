/**
 * 本页面是用于加载一些下拉框的数据的。
 * 使用方法：
 * 1、var sexStore = getCodeListCombo();
 * 2、sexStore.load({params:{codeId:"4af4953627d6f4ff0127d6fbc935000a",codeName:"性别"}});
 * 若要加载数据字典的子节点：
 * 2、store.load({params:{codeId:"8ac388c529a607730129a608c52f0004",codeName:"行政区域",parentCodeId:'320000'}});
 * 加载的时候，参数名称必须是：codeId或者是codeName(二者选一)。参数值应该是系统中存在的数据标准名称，若不存在，将不会有下拉框显示
 * 需要注意的是：sexStore返回的数据是这样的：{success:true,codeList:[{dataKey:'1',dataValue:'男'...}]}
 * 在下拉框定义的地方，注意valueField:"dataKey",displayField:"dataValue"这样才行
 * 将所有需要下拉框的都定义到这里，然后将这个页面引入到需要下拉框的地方，再进行加载操作
 * 
 * 并不是所有的下拉框都适用，有一些下拉框用户可以操作的是不适用的。比如：日志类别
 * 要引入本页面，请按照下面的顺序引入：
 * 1、<script type="text/javascript" src="<%=path %>/js/apps/util/util.js"></script>
 * 2、<script type="text/javascript" src="<%=path %>/js/apps/dataStore.js"></script>
 */
//加载性别下拉框
var sexStore = getCodeListCombo();
//加载学历下拉框
var educationalStore = getCodeListCombo();
//请求处理结果
var processResult = getCodeListCombo();
//请求处理状态
var processStatus = getCodeListCombo();
//家庭关系
var familyRelationStore = getCodeListCombo();




//加载相册分类下拉框
var albumTypeStore = getCodeListCombo();
//加载警报点分类下拉框
var alertTypeStore = getCodeListCombo();
//国家下拉框
var countryStore = getCodeListCombo();
//省下拉框
var provinceStore = getCodeListCombo();
//市（县）下拉框
var cityStore = getCodeListCombo();
//县下拉框
var contyStore = getCodeListCombo();

/**
 * 消费收入主类型
 */
var accountMainTypeStore = getCodeListCombo();
/**
 * 消费次类型
 */
var accountSeSecondTypeStore = getCodeListCombo();
/**
 * 收入次类型
 */
var accountEnSecondTypeStore = getCodeListCombo();
/**
 * 账户类型
 */
var cardTypeStore = getCodeListCombo();
/**
 * 预算类型
 */
//var budgetTypeStore = getCodeListCombo();