$(document).ready(function(){
  $("#user-table").hide();
		
  $("#user-content").bind("click", () => {
	isShow = $("#isUserTable").val();
	if(isShow == 0 ){
		$("#user-table").show();
		$("#isUserTable").attr('value',1);
		setTimeout(function() {
  			$("#user-table").hide();
			$("#isUserTable").attr('value',0);
		}, 2000);
	}else{
		$("#user-table").hide();
		$("#isUserTable").attr('value',0);
	}
  })
});


function upnav () {
	document.addEventListener('scroll', onScroll, { passive: true });
	var last = 0;
	const nav = document.querySelector('nav');
	const headerheight = document.querySelector('nav').clientHeight;
		function onScroll()
		{
		  
			const scrollposition = pageYOffset;
			console.log(scrollposition);
			if (scrollposition < 500){
				$("#naviBar").show();
				return;
			}
			
			else if (scrollposition > last || scrollposition<= headerheight) 
			{
				console.log("hello");
				$("#naviBar").hide();
			}
			else if (scrollposition<last) 
			{	
				console.log("hellossssss");
				$("#naviBar").show();
			}
			last = scrollposition; 
		}
}
upnav();