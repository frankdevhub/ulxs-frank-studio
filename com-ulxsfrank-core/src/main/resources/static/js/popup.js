 function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) {
			return unescape(r[2]);
		}
		return null;
   }

 function chkEmail(strEmail) {
	if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(strEmail)) {
		return false;
	}
	else {
		return true;
	}
}


/**
 * 消息警告框
 * @param {提示对话框上显示的内容} message
 * @param {提示对话框上显示的标题} title
 * @param {提示对话框上按钮显示的内容} buttonCapture
 */
var notice = function(message,title,buttonCapture) {
	//判断页面是否存在弹窗层dom,有的话移除重新加载
	var isDom = document.getElementById("popup-backdrop")
	if(isDom) {
		document.body.removeChild(isDom);
	}
	//判断页面是否存在弹窗遮罩层,有的话移除重新加载
	var isDom_back = document.getElementById("noticePopup");
	if(isDom_back) {
		document.body.removeChild(isDom_back);
	}
	
	message == null ? message="nulll" : message,
	title == null ? title="" : title,
	buttonCapture == null ? buttonCapture="确定" : buttonCapture;
	//遮罩层
	var popupBackdrop = document.createElement("div");
	popupBackdrop.setAttribute("class","popup-backdrop fadeIn");
	popupBackdrop.setAttribute("id","popup-backdrop");
	popupBackdrop.setAttribute("style","display: none;")
	//弹窗层
	var noticePopup = document.createElement("div");
	noticePopup.setAttribute("id","noticePopup");
	noticePopup.setAttribute("class","own-popup");
	noticePopup.setAttribute("style","display: none;")
	
	var appendDom = "<div id='popup-in' class='animated pulse'><div class='popup-inner'><div class='popup-title'>"+title+"</div><div class='popup-text'>"+message+"</div></div><div class='popup-buttons' id='notice' onclick=\"noticeHide()\"><span class='popup-button popup-button-bold' ontouchstart=btn_touchstart(this); ontouchend=btn_ontouchend(this);>"+buttonCapture+"</span></div></div>";
	noticePopup.innerHTML = appendDom;
	
	document.body.appendChild(popupBackdrop);
	document.body.appendChild(noticePopup);
	
	document.getElementById("popup-backdrop").style.display = "block";
	document.getElementById("noticePopup").style.display = "flex";
}


/**
 * 消息确认框
 * @param {确认对话框上显示的内容} message
 * @param {确认对话框上显示的标题} title
 */
var confirms = function(message,title) {
	//判断页面是否存在弹窗层dom,有的话移除重新加载
	var isDom = document.getElementById("confirmPopup")
	if(isDom) {
		document.body.removeChild(isDom);
	}
	//判断页面是否存在弹窗遮罩层,有的话移除重新加载
	var isDom_back = document.getElementById("popup-backdrop");
	if(isDom_back) {
		document.body.removeChild(isDom_back);
	}
	
	message == null ? message="nulll" : message,
	title == null ? title="" : title;
	//遮罩层
	var popupBackdrop = document.createElement("div");
	popupBackdrop.setAttribute("class","popup-backdrop fadeIn");
	popupBackdrop.setAttribute("id","popup-backdrop");
	popupBackdrop.setAttribute("style","display: none;")
	//弹窗层
	var confirmPopup = document.createElement("div");
	confirmPopup.setAttribute("id","confirmPopup");
	confirmPopup.setAttribute("class","own-popup");
	confirmPopup.setAttribute("style","display: none;")
	
	var appendDom = '<div id="popup-in" class="animated pulse">' +
		'<div class="popup-inner"><div class="popup-title">'+title+'</div><div class="popup-text">'+message+'</div></div>' +
		'<div class="popup-buttons">' +
		'<span class="popup-button popup-button-bold" style="border-right: 1px #CCC solid;" id="confirmX" onclick=isConfirm_confirm(); ontouchstart=btn_touchstart(this); ontouchend=btn_ontouchend(this);>返 回</span>' +
		'<span class="popup-button popRed" id="confirmY"  onclick=isConfirm_confirm(); ontouchstart=btn_touchstart(this); ontouchend=btn_ontouchend(this);>确认无误</span>' +
		'</div></div>';
	confirmPopup.innerHTML = appendDom;
	
	document.body.appendChild(popupBackdrop);
	document.body.appendChild(confirmPopup);
	
	document.getElementById("popup-backdrop").style.display = "block";
	document.getElementById("confirmPopup").style.display = "flex";
}

/**
 * 提示框
 * @param {输入对话框上显示的内容} message
 * @param {输入对话框上显示的标题} title
 * @param {输入对话框上编辑框显示的提示文字} tip
 */
var prompts = function(message,title,tip) {
	//判断页面是否存在弹窗层dom,有的话移除重新加载
	var isDom = document.getElementById("dialoguePopup")
	if(isDom) {
		document.body.removeChild(isDom);
	}
	//判断页面是否存在弹窗遮罩层,有的话移除重新加载
	var isDom_back = document.getElementById("popup-backdrop");
	if(isDom_back) {
		document.body.removeChild(isDom_back);
	}
	
	message == null ? message="nulll" : message,
	title == null ? title="" : title,
	tip == null ? tip="" : tip;
	//遮罩层
	var popupBackdrop = document.createElement("div");
	popupBackdrop.setAttribute("class","popup-backdrop fadeIn");
	popupBackdrop.setAttribute("id","popup-backdrop");
	popupBackdrop.setAttribute("style","display: none;")
	//弹窗层
	var promptPopup = document.createElement("div");
	promptPopup.setAttribute("id","dialoguePopup");
	promptPopup.setAttribute("class","own-popup");
	promptPopup.setAttribute("style","display: none;")
	
	var appendDom = '<div id="popup-in" class="animated pulse"><div class="popup-inner"><div class="popup-title">'+title+'</div><div class="popup-text">'+message+'</div><div class="popup-input"><input type="text" name="promptObtain" autofocus="" placeholder='+tip+'></div></div><div class="popup-buttons"><span class="popup-button" id="dialogueY" onclick=isConfirm_prompt(); ontouchstart=btn_touchstart(this); ontouchend=btn_ontouchend(this);>确定</span><span class="popup-button popup-button-bold" id="dialogueX" style="border-left: 1px #CCC solid;" onclick=isConfirm_prompt(); ontouchstart=btn_touchstart(this); ontouchend=btn_ontouchend(this);>取消</span></div></div>';
	promptPopup.innerHTML = appendDom;
	
	document.body.appendChild(popupBackdrop);
	document.body.appendChild(promptPopup);
	
	document.getElementById("popup-backdrop").style.display = "block";
	document.getElementById("dialoguePopup").style.display = "flex";
}







/**
 * 关闭 消息警告框
 */
function noticeHide() {
	document.getElementById("popup-backdrop").style.display = "none";
	document.getElementById("noticePopup").style.display = "none";
}

/**
 * 关闭 消息确认框
 */
function isConfirm_confirm(text) {
	document.getElementById("popup-backdrop").style.display = "none";
	document.getElementById("confirmPopup").style.display = "none";
}

function isConfirm_prompt(text) {
	document.getElementById("popup-backdrop").style.display = "none";
	document.getElementById("dialoguePopup").style.display = "none";
}

function btn_touchstart(thises) {
	thises.style.backgroundColor = "rgba(234,234,235,0.95)"
}

function btn_ontouchend(thises) {
	thises.style.backgroundColor = "rgba(255,255,255,.95)"
}
