/**
 * 浮动按钮功能
 * 使用浮动按钮时需要注意的是：
 * 1、新建一个div，其id="scrollButton",并且设置其style="text-align: right;width: 40%;position: absolute;left: 0px;top: 50px;z-index:1000;"
 * 2、该div可以放在地方，但是不能放在其他的div中嵌套。可以放在页面的最后
 * 3、调用浮动按钮功能时，需要引入config.jsp页面
 */
function buttonScroll(){
	//设置按钮所在div的高度
	var scrollButton = document.getElementById("beianhao");
	if(scrollButton){
		scrollButton.style.top=document.body.clientHeight-20;//高度定位,修改数值
		//设置按钮所在div距左边框的距离
		scrollButton.style.left=document.body.scrollLeft;
	}
}
//滚动时触发
window.onscroll=buttonScroll;
//窗口大小变化时触发
window.onresize=buttonScroll;
//初始化时触发
window.onload=buttonScroll;