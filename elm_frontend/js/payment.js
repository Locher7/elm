window.onload = function(){
	//获取显示隐藏按钮DOM对象
	let showBtn = document.getElementById('showBtn');
	//获取订单明细DOM对象
	let detailedBox = document.getElementById('detailedBox');
	//设置默认订单明细隐藏
	detailedBox.style.display='none';
	
	showBtn.onclick = function(){
		//判断订单明细DOM对象是否隐藏。如果是，就显示，否则就隐藏
		if(detailedBox.style.display=='none'){
			detailedBox.style.display='block';
		}else{
			detailedBox.style.display='none';
		}
	}
}