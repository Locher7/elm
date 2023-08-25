window.onload = function(){
	//获取显示隐藏按钮DOM数组
	let showBtnArr = document.getElementsByClassName('fa-caret-down');
	//获取订单明细DOM数组
	let detailedBoxArr = document.getElementsByClassName('order-detailed');
	//设置默认所有订单明细都隐藏
	for(let i=0;i<detailedBoxArr.length;i++){
		detailedBoxArr[i].style.display='none';
	}
	
	for(let i=0;i<showBtnArr.length;i++){
		showBtnArr[i].onclick = function(){
			if(detailedBoxArr[i].style.display=='none'){
				detailedBoxArr[i].style.display='block';
			}else{
				detailedBoxArr[i].style.display='none';
			}
		}
	}
}